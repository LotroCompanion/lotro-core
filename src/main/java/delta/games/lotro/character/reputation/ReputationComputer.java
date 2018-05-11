package delta.games.lotro.character.reputation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.log.CharacterLog;
import delta.games.lotro.character.log.CharacterLogItem;
import delta.games.lotro.character.log.CharacterLogItem.LogItemType;
import delta.games.lotro.lore.reputation.Faction;
import delta.games.lotro.lore.reputation.FactionLevel;
import delta.games.lotro.lore.reputation.FactionsRegistry;

/**
 * Computes reputation status/history using the character log.
 * @author DAM
 */
public class ReputationComputer
{
  private static final Logger _logger=Logger.getLogger(ReputationComputer.class);

  private static final String ALE_ASSOCIATION_SEED="Ale Association";
  // Map seeds to well-known faction level keys
  private HashMap<String,String> _seeds;

  /**
   * Build a reputation status for a toon.
   * @param toon Toon to use.
   * @return A reputation.
   */
  public ReputationStatus buildReputationStatus(CharacterFile toon)
  {
    initSeeds();
    ReputationStatus reputation=new ReputationStatus();
    CharacterLog log=toon.getLastCharacterLog();
    if (log!=null)
    {
      loadLog(reputation,log);
    }
    return reputation;
  }

  private void initSeeds()
  {
    _seeds=new HashMap<String,String>();
    _seeds.put("Known to",FactionLevels.ACQUAINTANCE);
    _seeds.put("Friend to",FactionLevels.FRIEND);
    _seeds.put("Friend of",FactionLevels.FRIEND);
    _seeds.put("Ally to",FactionLevels.ALLY);
    _seeds.put("Ally of",FactionLevels.ALLY);
    _seeds.put("Kindred to",FactionLevels.KINDRED);
    _seeds.put("Kindred with",FactionLevels.KINDRED);
    _seeds.put("Kindred of",FactionLevels.KINDRED);
  }

  /**
   * Load reputation status from a character log.
   * @param reputation Reputation to fill.
   * @param log Log items to use.
   */
  private void loadLog(ReputationStatus reputation, CharacterLog log)
  {
    List<CharacterLogItem> items=getDeedItems(log);
    parseItems(reputation,items);
  }

  private List<CharacterLogItem> getDeedItems(CharacterLog log)
  {
    List<CharacterLogItem> ret=new ArrayList<CharacterLogItem>();
    if (log!=null)
    {
      int nb=log.getNbItems();
      for(int i=0;i<nb;i++)
      {
        CharacterLogItem item=log.getLogItem(i);
        if (item!=null)
        {
          LogItemType type=item.getLogItemType();
          if (type==LogItemType.DEED)
          {
            ret.add(item);
          }
        }
      }
      Collections.reverse(ret);
    }
    return ret;
  }

  private void parseItems(ReputationStatus reputation, List<CharacterLogItem> items)
  {
    for(CharacterLogItem item : items)
    {
      String label=item.getLabel();
      long date=item.getDate();
      try
      {
        handleItem(reputation,date,label);
      }
      catch(Exception e)
      {
        _logger.error("Error when parsing deed item ["+label+"]",e);
      }
    }
  }

  private void handleItem(ReputationStatus reputation, long date, String label)
  {
    Faction faction=null;
    String levelKey=null;

    // Handle generic seeds
    for(Map.Entry<String,String> entry : _seeds.entrySet())
    {
      String seed=entry.getKey();
      if (label.startsWith(seed))
      {
        String factionName=label.substring(seed.length()).trim();
        if (factionName.startsWith("the"))
        {
          factionName=factionName.substring(3).trim();
        }
        levelKey=entry.getValue();
        faction=FactionsRegistry.getInstance().getByName(factionName);
        if (faction!=null)
        {
          break;
        }
      }
    }

    // Handle "Ale Association" specificly
    if (label.startsWith(ALE_ASSOCIATION_SEED))
    {
      faction=FactionsRegistry.getInstance().getByName("Ale Association");
      String levelStr=label.substring(ALE_ASSOCIATION_SEED.length()).trim();
      if ("Acquaintance".equals(levelStr)) levelKey=FactionLevels.ACQUAINTANCE;
      else if ("Ally".equals(levelStr)) levelKey=FactionLevels.ALLY;
      else if ("Friend".equals(levelStr)) levelKey=FactionLevels.FRIEND;
    }
    else if (label.equals("Kindred of Malevolence"))
    {
      faction=FactionsRegistry.getInstance().getByName("Ale Association");
      levelKey=FactionLevels.KINDRED;
    }
    // Kindred with Eglain is "Eglan"
    else if (label.equals("Eglan"))
    {
      faction=FactionsRegistry.getInstance().getByName("Eglain");
      levelKey=FactionLevels.KINDRED;
    }
    else if (label.equals("Kindred to the Entwash"))
    {
      faction=FactionsRegistry.getInstance().getByName("Entwash Vale");
      levelKey=FactionLevels.KINDRED;
    }
    // TODO Inn League

    if ((faction!=null) && (levelKey!=null))
    {
      FactionStatus stat=reputation.getOrCreateFactionStat(faction);
      FactionLevel factionLevel=faction.getLevelByKey(levelKey);
      FactionLevelStatus status=stat.getStatusForLevel(factionLevel);
      status.setCompleted(date);
      stat.setFactionLevel(factionLevel);
    }
  }
}
