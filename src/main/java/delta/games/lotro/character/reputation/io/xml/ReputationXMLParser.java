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
      int factionId=DOMParsingTools.getIntAttribute(factionAttrs,ReputationXMLConstants.FACTION_ID_ATTR,0);
      if (factionId!=0)
      {
        Faction faction=FactionsRegistry.getInstance().getById(factionId);
        if (faction!=null)
        {
          FactionStatus factionStatus=h.getOrCreateFactionStat(faction);
          loadFactionStatus(factionTag,factionStatus);
        }
        else
        {
          LOGGER.warn("Could not find faction with ID="+factionId);
        }
      }
      else
      {
        LOGGER.warn("Could not find faction ID in faction status tag!");
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
      int tier=DOMParsingTools.getIntAttribute(levelAttrs,ReputationXMLConstants.FACTION_LEVEL_TIER_ATTR,0);
      FactionLevel level=faction.getLevelByTier(tier);
      if (level!=null)
      {
        FactionLevelStatus levelStatus=factionStatus.getStatusForLevel(level);
        long date=DOMParsingTools.getLongAttribute(levelAttrs,ReputationXMLConstants.FACTION_LEVEL_DATE_ATTR,0);
        levelStatus.setCompletionDate(date);
      }
      else
      {
        LOGGER.warn("Unknown faction tier ["+tier+"] for faction ["+faction.getName()+"]");
      }
    }
    NamedNodeMap factionAttrs=factionTag.getAttributes();
    // Current level
    FactionLevel currentLevel=null;
    int currentTier=DOMParsingTools.getIntAttribute(factionAttrs,ReputationXMLConstants.FACTION_CURRENT_TIER_ATTR,-1);
    if (currentTier>=0)
    {
      currentLevel=faction.getLevelByTier(currentTier);
    }
    factionStatus.setFactionLevel(currentLevel);
    // Current reputation
    // - initialize from faction level
    factionStatus.setReputationFromFactionLevel();
    // - load reputation amount if any
    int currentReputationValue=DOMParsingTools.getIntAttribute(factionAttrs,ReputationXMLConstants.FACTION_CURRENT_REPUTATION_ATTR,-1);
    if (currentReputationValue>=0)
    {
      Integer currentReputation=Integer.valueOf(currentReputationValue);
      factionStatus.setReputation(currentReputation);
    }
  }
}
