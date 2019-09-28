package delta.games.lotro.character.reputation.io.xml;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.reputation.FactionLevelStatus;
import delta.games.lotro.character.reputation.FactionStatus;
import delta.games.lotro.character.reputation.ReputationStatus;
import delta.games.lotro.lore.reputation.Faction;
import delta.games.lotro.lore.reputation.FactionLevel;
import delta.games.lotro.lore.reputation.FactionsRegistry;

/**
 * Parser for character reputation stored in XML.
 * @author DAM
 */
public class ReputationXMLParser
{
  private static final Logger LOGGER=Logger.getLogger(ReputationXMLParser.class);

  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed data or <code>null</code>.
   */
  public ReputationStatus parseXML(File source)
  {
    ReputationStatus h=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      h=parseReputation(root);
    }
    return h;
  }

  private ReputationStatus parseReputation(Element root)
  {
    ReputationStatus h=new ReputationStatus();

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
          FactionStatus factionStatus=h.getOrCreateFactionStat(faction);
          loadFactionStatus(factionTag,factionStatus);
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
   * Load faction status.
   * @param factionTag Tag to read from.
   * @param factionStatus Storage for faction status.
   */
  public static void loadFactionStatus(Element factionTag, FactionStatus factionStatus)
  {
    factionStatus.reset();
    Faction faction=factionStatus.getFaction();
    // Level tags
    List<Element> levelTags=DOMParsingTools.getChildTagsByName(factionTag,ReputationXMLConstants.FACTION_LEVEL_TAG);
    for(Element levelTag : levelTags)
    {
      NamedNodeMap levelAttrs=levelTag.getAttributes();
      String levelKey=DOMParsingTools.getStringAttribute(levelAttrs,ReputationXMLConstants.FACTION_LEVEL_KEY_ATTR,"");
      FactionLevel level=faction.getLevelByKey(levelKey);
      if (level!=null)
      {
        FactionLevelStatus levelStatus=factionStatus.getStatusForLevel(level);
        long date=DOMParsingTools.getLongAttribute(levelAttrs,ReputationXMLConstants.FACTION_LEVEL_DATE_ATTR,0);
        levelStatus.setCompletionDate(date);
        int xp=DOMParsingTools.getIntAttribute(levelAttrs,ReputationXMLConstants.FACTION_LEVEL_XP_ATTR,0);
        levelStatus.setAcquiredXP(xp);
        boolean completed=DOMParsingTools.getBooleanAttribute(levelAttrs,ReputationXMLConstants.FACTION_LEVEL_COMPLETED_ATTR,false);
        levelStatus.setCompleted(completed);
      }
      else
      {
        LOGGER.warn("Unknown faction level ["+levelKey+"] for faction ["+faction.getName()+"]");
      }
    }
    // Current level
    NamedNodeMap factionAttrs=factionTag.getAttributes();
    String currentFactionLevelKey=DOMParsingTools.getStringAttribute(factionAttrs,ReputationXMLConstants.FACTION_CURRENT_ATTR,null);
    FactionLevel currentLevel=faction.getLevelByKey(currentFactionLevelKey);
    if (currentLevel==null)
    {
      LOGGER.warn("No current level for faction: "+faction.getName()+". Using initial level.");
      currentLevel=faction.getInitialLevel();
    }
    factionStatus.setFactionLevel(currentLevel);
  }
}
