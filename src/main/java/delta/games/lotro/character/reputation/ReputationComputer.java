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
  private HashMap<String,FactionLevel> _seeds;

  /**
   * Build a reputation data for a toon.
   * @param toon Toon to use.
   * @return A reputation.
   */
  public ReputationData buildReputationData(CharacterFile toon)
  {
    initSeeds();
    ReputationData reputation=new ReputationData();
    CharacterLog log=toon.getLastCharacterLog();
    if (log!=null)
    {
      loadLog(reputation,log);
    }
    return reputation;
  }

  private void initSeeds()
  {
    _seeds=new HashMap<String,FactionLevel>();
    _seeds.put("Known to",FactionLevel.ACQUAINTANCE);
    _seeds.put("Friend to",FactionLevel.FRIEND);
    _seeds.put("Friend of",FactionLevel.FRIEND);
    _seeds.put("Ally to",FactionLevel.ALLY);
    _seeds.put("Ally of",FactionLevel.ALLY);
    _seeds.put("Kindred to",FactionLevel.KINDRED);
    _seeds.put("Kindred with",FactionLevel.KINDRED);
    _seeds.put("Kindred of",FactionLevel.KINDRED);
  }

  /**
   * Load data from a character log.
   * @param reputation Reputation to fill.
   * @param log Log items to use.
   */
  private void loadLog(ReputationData reputation, CharacterLog log)
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

  private void parseItems(ReputationData reputation, List<CharacterLogItem> items)
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

  private void handleItem(ReputationData reputation, long date, String label)
  {
    Faction faction=null;
    FactionLevel level=null;

    // Handle generic seeds
    for(Map.Entry<String,FactionLevel> entry : _seeds.entrySet())
    {
      String seed=entry.getKey();
      if (label.startsWith(seed))
      {
        String factionName=label.substring(seed.length()).trim();
        if (factionName.startsWith("the"))
        {
          factionName=factionName.substring(3).trim();
        }
        level=entry.getValue();
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
      if ("Acquaintance".equals(levelStr)) level=FactionLevel.ACQUAINTANCE;
      else if ("Ally".equals(levelStr)) level=FactionLevel.ALLY;
      else if ("Friend".equals(levelStr)) level=FactionLevel.FRIEND;
    }
    else if (label.equals("Kindred of Malevolence"))
    {
      faction=FactionsRegistry.getInstance().getByName("Ale Association");
      level=FactionLevel.KINDRED;
    }
    // Kindred with Eglain is "Eglan"
    else if (label.equals("Eglan"))
    {
      faction=FactionsRegistry.getInstance().getByName("Eglain");
      level=FactionLevel.KINDRED;
    }
    else if (label.equals("Kindred to the Entwash"))
    {
      faction=FactionsRegistry.getInstance().getByName("Entwash Vale");
      level=FactionLevel.KINDRED;
    }
    // TODO Inn League

    if ((faction!=null) && (level!=null))
    {
      FactionData stat=reputation.getOrCreateFactionStat(faction);
      stat.addUpdate(level,date);
    }
  }
}
