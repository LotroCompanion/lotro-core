package delta.games.lotro.character.reputation.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.reputation.FactionData;
import delta.games.lotro.character.reputation.FactionLevelStatus;
import delta.games.lotro.character.reputation.ReputationData;
import delta.games.lotro.lore.reputation.Faction;
import delta.games.lotro.lore.reputation.FactionLevel;
import delta.games.lotro.lore.reputation.FactionsRegistry;

/**
 * Parser for character reputation stored in XML.
 * @author DAM
 */
public class ReputationXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed data or <code>null</code>.
   */
  public ReputationData parseXML(File source)
  {
    ReputationData h=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      h=parseReputation(root);
    }
    return h;
  }

  private ReputationData parseReputation(Element root)
  {
    ReputationData h=new ReputationData();

    List<Element> factionTags=DOMParsingTools.getChildTagsByName(root,ReputationXMLConstants.FACTION_TAG);
    for(Element factionTag : factionTags)
    {
      NamedNodeMap factionAttrs=factionTag.getAttributes();
      // Faction
      String factionKey=DOMParsingTools.getStringAttribute(factionAttrs,ReputationXMLConstants.FACTION_KEY_ATTR,null);
      if (factionKey!=null)
      {
        Faction faction=FactionsRegistry.getInstance().getByKey(factionKey);
        if (faction==null)
        {
          faction=FactionsRegistry.getInstance().getByName(factionKey);
        }
        if (faction!=null)
        {
          FactionData factionData=h.getOrCreateFactionStat(faction);
          loadFactionData(factionTag,factionData);
        }
        else
        {
          // TODO warn
        }
      }
      else
      {
        // TODO warn
      }
    }
    return h;
  }

  /**
   * Load faction data.
   * @param factionTag Tag to read from.
   * @param factionData Storage for faction data.
   */
  public static void loadFactionData(Element factionTag, FactionData factionData)
  {
    factionData.reset();
    Faction faction=factionData.getFaction();
    // Level tags
    List<Element> levelTags=DOMParsingTools.getChildTagsByName(factionTag,ReputationXMLConstants.FACTION_LEVEL_TAG);
    for(Element levelTag : levelTags)
    {
      NamedNodeMap levelAttrs=levelTag.getAttributes();
      String levelKey=DOMParsingTools.getStringAttribute(levelAttrs,ReputationXMLConstants.FACTION_LEVEL_KEY_ATTR,"");
      FactionLevel level=faction.getLevelByKey(levelKey);
      FactionLevelStatus levelStatus=factionData.getStatusForLevel(level);
      long date=DOMParsingTools.getLongAttribute(levelAttrs,ReputationXMLConstants.FACTION_LEVEL_DATE_ATTR,0);
      levelStatus.setCompletionDate(date);
      int xp=DOMParsingTools.getIntAttribute(levelAttrs,ReputationXMLConstants.FACTION_LEVEL_XP_ATTR,0);
      levelStatus.setAcquiredXP(xp);
      boolean completed=DOMParsingTools.getBooleanAttribute(levelAttrs,ReputationXMLConstants.FACTION_LEVEL_COMPLETED_ATTR,false);
      levelStatus.setCompleted(completed);
    }
    // Current level
    NamedNodeMap factionAttrs=factionTag.getAttributes();
    String currentFactionLevelKey=DOMParsingTools.getStringAttribute(factionAttrs,ReputationXMLConstants.FACTION_CURRENT_ATTR,null);
    FactionLevel currentLevel=faction.getLevelByKey(currentFactionLevelKey);
    if (currentLevel!=null)
    {
      factionData.setFactionLevel(currentLevel);
    }
  }
}
