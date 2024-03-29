package delta.games.lotro.lore.reputation.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.lore.reputation.Faction;
import delta.games.lotro.lore.reputation.FactionLevel;
import delta.games.lotro.lore.reputation.FactionsRegistry;
import delta.games.lotro.lore.reputation.ReputationDeed;
import delta.games.lotro.utils.i18n.I18nFacade;
import delta.games.lotro.utils.i18n.I18nRuntimeUtils;

/**
 * Parser for factions stored in XML.
 * @author DAM
 */
public class FactionsXMLParser
{
  private SingleLocaleLabelsManager _i18n;

  /**
   * Constructor.
   */
  public FactionsXMLParser()
  {
    _i18n=I18nFacade.getLabelsMgr("factions");
  }

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
    Faction faction=new Faction(id);
    // Key
    String factionKey=DOMParsingTools.getStringAttribute(attrs,FactionsXMLConstants.FACTION_KEY_ATTR,null);
    faction.setLegacyKey(factionKey);
    // Name
    String factionName=_i18n.getLabel(String.valueOf(id));
    faction.setName(factionName);
    // Category
    String category=DOMParsingTools.getStringAttribute(attrs,FactionsXMLConstants.FACTION_CATEGORY_ATTR,null);
    faction.setCategory(category);
    // Is guild?
    boolean isGuild=DOMParsingTools.getBooleanAttribute(attrs,FactionsXMLConstants.FACTION_GUILD_ATTR,false);
    faction.setIsGuildFaction(isGuild);
    // Levels
    List<FactionLevel> levels=parseLevels(root);
    for(FactionLevel level : levels)
    {
      faction.addFactionLevel(level);
    }
    // Description
    String description=DOMParsingTools.getStringAttribute(attrs,FactionsXMLConstants.FACTION_DESCRIPTION_ATTR,"");
    description=I18nRuntimeUtils.getLabel(_i18n,description);
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
    // Property names:
    // - current tier
    String currentTierPropertyName=DOMParsingTools.getStringAttribute(attrs,FactionsXMLConstants.FACTION_CURRENT_TIER_PROPERTY_ATTR,null);
    faction.setCurrentTierPropertyName(currentTierPropertyName);
    // - current reputation
    String currentReputationPropertyName=DOMParsingTools.getStringAttribute(attrs,FactionsXMLConstants.FACTION_CURRENT_REPUTATION_PROPERTY_ATTR,null);
    faction.setCurrentReputationPropertyName(currentReputationPropertyName);
    return faction;
  }

  private List<FactionLevel> parseLevels(Element root)
  {
    List<FactionLevel> levels=new ArrayList<FactionLevel>();
    List<Element> levelTags=DOMParsingTools.getChildTagsByName(root,FactionsXMLConstants.LEVEL_TAG);
    for(Element levelTag : levelTags)
    {
      NamedNodeMap attrs=levelTag.getAttributes();
      // Tier
      int tier=DOMParsingTools.getIntAttribute(attrs,FactionsXMLConstants.FACTION_LEVEL_TIER_ATTR,0);
      // Level name
      String levelName=DOMParsingTools.getStringAttribute(attrs,FactionsXMLConstants.FACTION_LEVEL_NAME_ATTR,null);
      levelName=I18nRuntimeUtils.getLabel(_i18n,levelName);
      // LOTRO points
      int lotroPoints=DOMParsingTools.getIntAttribute(attrs,FactionsXMLConstants.FACTION_LEVEL_LOTRO_POINTS_ATTR,0);
      // Required XP
      int requiredXp=DOMParsingTools.getIntAttribute(attrs,FactionsXMLConstants.FACTION_LEVEL_REQUIRED_REPUTATION_ATTR,0);
      FactionLevel level=new FactionLevel(tier,levelName,lotroPoints,requiredXp);
      // Level key
      String levelKey=DOMParsingTools.getStringAttribute(attrs,FactionsXMLConstants.FACTION_LEVEL_KEY_ATTR,null);
      level.setLegacyKey(levelKey);
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
      int factionId=DOMParsingTools.getIntAttribute(factionAttrs,FactionsXMLConstants.DEED_FACTION_ID_ATTR,0);
      Faction faction=registry.getById(factionId);
      deed.addFaction(faction);
    }
    return deed;
  }
}
