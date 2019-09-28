package delta.games.lotro.lore.reputation.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.lore.reputation.Faction;
import delta.games.lotro.lore.reputation.FactionLevel;
import delta.games.lotro.lore.reputation.FactionsRegistry;
import delta.games.lotro.lore.reputation.ReputationDeed;

/**
 * Parser for factions stored in XML.
 * @author DAM
 */
public class FactionsXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed factions or <code>null</code>.
   */
  public FactionsRegistry parseXML(File source)
  {
    FactionsRegistry registry=new FactionsRegistry();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      // Factions
      List<Element> factionTags=DOMParsingTools.getChildTagsByName(root,FactionsXMLConstants.FACTION_TAG);
      for(Element factionTag : factionTags)
      {
        Faction faction=parseFaction(factionTag);
        registry.registerFaction(faction);
      }
      // Deeds
      List<Element> deedTags=DOMParsingTools.getChildTagsByName(root,FactionsXMLConstants.DEED_TAG);
      for(Element deedTag : deedTags)
      {
        ReputationDeed deed=parseDeed(deedTag,registry);
        registry.addDeed(deed);
      }
    }
    return registry;
  }

  private Faction parseFaction(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();

    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,FactionsXMLConstants.FACTION_ID_ATTR,0);
    // Key
    String factionKey=DOMParsingTools.getStringAttribute(attrs,FactionsXMLConstants.FACTION_KEY_ATTR,null);
    // Name
    String factionName=DOMParsingTools.getStringAttribute(attrs,FactionsXMLConstants.FACTION_NAME_ATTR,null);
    // Category
    String category=DOMParsingTools.getStringAttribute(attrs,FactionsXMLConstants.FACTION_CATEGORY_ATTR,null);
    // Levels
    List<FactionLevel> levels=parseLevels(root);
    Faction faction=new Faction(factionKey,factionName,category,levels);
    faction.setIdentifier(id);
    // Initial level
    String initialLevelKey=DOMParsingTools.getStringAttribute(attrs,FactionsXMLConstants.FACTION_INITIAL_LEVEL_ATTR,null);
    FactionLevel initialLevel=faction.getLevelByKey(initialLevelKey);
    faction.setInitialLevel(initialLevel);
    // Description
    String description=DOMParsingTools.getStringAttribute(attrs,FactionsXMLConstants.FACTION_DESCRIPTION_ATTR,"");
    faction.setDescription(description);
    // Lowest tier
    int lowestTier=DOMParsingTools.getIntAttribute(attrs,FactionsXMLConstants.FACTION_LOWEST_TIER_ATTR,0);
    faction.setLowestTier(lowestTier);
    // Initial tier
    int initialTier=DOMParsingTools.getIntAttribute(attrs,FactionsXMLConstants.FACTION_INITIAL_TIER_ATTR,0);
    faction.setInitialTier(initialTier);
    // Highest tier
    int highestTier=DOMParsingTools.getIntAttribute(attrs,FactionsXMLConstants.FACTION_HIGHEST_TIER_ATTR,0);
    faction.setHighestTier(highestTier);
    return faction;
  }

  private List<FactionLevel> parseLevels(Element root)
  {
    List<FactionLevel> levels=new ArrayList<FactionLevel>();
    List<Element> levelTags=DOMParsingTools.getChildTagsByName(root,FactionsXMLConstants.LEVEL_TAG);
    for(Element levelTag : levelTags)
    {
      NamedNodeMap attrs=levelTag.getAttributes();
      // Level key
      String levelKey=DOMParsingTools.getStringAttribute(attrs,FactionsXMLConstants.FACTION_LEVEL_KEY_ATTR,null);
      // Level name
      String levelName=DOMParsingTools.getStringAttribute(attrs,FactionsXMLConstants.FACTION_LEVEL_NAME_ATTR,null);
      // Code
      int levelCode=DOMParsingTools.getIntAttribute(attrs,FactionsXMLConstants.FACTION_LEVEL_CODE_ATTR,0);
      // LOTRO points
      int lotroPoints=DOMParsingTools.getIntAttribute(attrs,FactionsXMLConstants.FACTION_LEVEL_LOTRO_POINTS_ATTR,0);
      // Required XP
      int requiredXp=DOMParsingTools.getIntAttribute(attrs,FactionsXMLConstants.FACTION_LEVEL_REQUIRED_XP_ATTR,0);
      FactionLevel level=new FactionLevel(levelKey,levelName,levelCode,lotroPoints,requiredXp);
      // Tier
      int tier=DOMParsingTools.getIntAttribute(attrs,FactionsXMLConstants.FACTION_LEVEL_TIER_ATTR,0);
      level.setTier(tier);
      // Deed key
      String deedKey=DOMParsingTools.getStringAttribute(attrs,FactionsXMLConstants.FACTION_LEVEL_DEED_KEY_ATTR,null);
      level.setDeedKey(deedKey);
      levels.add(level);
    }
    return levels;
  }

  private ReputationDeed parseDeed(Element root, FactionsRegistry registry)
  {
    NamedNodeMap attrs=root.getAttributes();

    // Name
    String deedName=DOMParsingTools.getStringAttribute(attrs,FactionsXMLConstants.DEED_NAME_ATTR,null);
    ReputationDeed deed=new ReputationDeed(deedName);
    // LOTRO points
    int lotroPoints=DOMParsingTools.getIntAttribute(attrs,FactionsXMLConstants.DEED_LOTRO_POINTS_ATTR,0);
    deed.setLotroPoints(lotroPoints);
    // Factions
    List<Element> factionTags=DOMParsingTools.getChildTagsByName(root,FactionsXMLConstants.DEED_FACTION_TAG);
    for(Element factionTag : factionTags)
    {
      NamedNodeMap factionAttrs=factionTag.getAttributes();
      String factionKey=DOMParsingTools.getStringAttribute(factionAttrs,FactionsXMLConstants.DEED_FACTION_KEY_ATTR,null);
      Faction faction=registry.getByKey(factionKey);
      deed.addFaction(faction);
    }
    return deed;
  }
}
