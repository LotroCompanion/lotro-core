package delta.games.lotro.lore.instances.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.difficulty.DifficultiesManager;
import delta.games.lotro.common.difficulty.Difficulty;
import delta.games.lotro.common.groupSize.GroupSize;
import delta.games.lotro.common.groupSize.GroupSizesManager;
import delta.games.lotro.lore.instances.InstanceMapDescription;
import delta.games.lotro.lore.instances.PrivateEncounter;
import delta.games.lotro.lore.instances.PrivateEncountersManager;
import delta.games.lotro.lore.instances.SkirmishPrivateEncounter;
import delta.games.lotro.lore.maps.MapDescription;
import delta.games.lotro.lore.maps.io.xml.MapDescriptionXMLConstants;
import delta.games.lotro.lore.maps.io.xml.MapDescriptionXMLParser;

/**
 * Parser for the private encounters stored in XML.
 * @author DAM
 */
public class PrivateEncountersXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed data or <code>null</code>.
   */
  public PrivateEncountersManager parseXML(File source)
  {
    PrivateEncountersManager ret=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      ret=parsePrivateEncounters(root);
    }
    return ret;
  }

  /**
   * Build a private encounters manager from an XML tag.
   * @param rootTag Root tag.
   * @return A private encounters manager.
   */
  private PrivateEncountersManager parsePrivateEncounters(Element rootTag)
  {
    PrivateEncountersManager mgr=new PrivateEncountersManager();

    // Private encounters AND skirmish private encounters
    List<Element> privateEncounterTags=DOMParsingTools.getChildTags(rootTag);
    for(Element privateEncounterTag : privateEncounterTags)
    {
      PrivateEncounter privateEncounter=parsePrivateEncounter(privateEncounterTag);
      mgr.addPrivateEncounter(privateEncounter);
    }
    return mgr;
  }

  private PrivateEncounter parsePrivateEncounter(Element privateEncounterTag)
  {
    NamedNodeMap attrs=privateEncounterTag.getAttributes();
    // ID
    int id=DOMParsingTools.getIntAttribute(attrs,PrivateEncountersXMLConstants.ID_ATTR,0);

    String tagName=privateEncounterTag.getNodeName();
    boolean isSkirmishPE=(PrivateEncountersXMLConstants.SKIRMISH_PRIVATE_ENCOUNTER_TAG.equals(tagName));
    PrivateEncounter ret=null;
    ret=(isSkirmishPE)?new SkirmishPrivateEncounter(id):new PrivateEncounter(id);
    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,PrivateEncountersXMLConstants.NAME_ATTR,"");
    ret.setName(name);
    // Content layer
    int contentLayerId=DOMParsingTools.getIntAttribute(attrs,PrivateEncountersXMLConstants.CONTENT_LAYER_ID_ATTR,0);
    ret.setContentLayerId(contentLayerId);
    // Quest ID
    int questId=DOMParsingTools.getIntAttribute(attrs,PrivateEncountersXMLConstants.QUEST_ID_ATTR,0);
    if (questId>0)
    {
      ret.setQuestId(Integer.valueOf(questId));
    }
    // Max players
    int maxPlayers=DOMParsingTools.getIntAttribute(attrs,PrivateEncountersXMLConstants.MAX_PLAYERS_ATTR,0);
    if (maxPlayers>0)
    {
      ret.setMaxPlayers(Integer.valueOf(maxPlayers));
    }
    if (isSkirmishPE)
    {
      SkirmishPrivateEncounter skirmishPE=(SkirmishPrivateEncounter)ret;
      // Category
      String category=DOMParsingTools.getStringAttribute(attrs,PrivateEncountersXMLConstants.CATEGORY_ATTR,null);
      skirmishPE.setCategory(category);
      // Type
      String type=DOMParsingTools.getStringAttribute(attrs,PrivateEncountersXMLConstants.TYPE_ATTR,null);
      skirmishPE.setType(type);
      // Min/max level
      int minLevel=DOMParsingTools.getIntAttribute(attrs,PrivateEncountersXMLConstants.MIN_LEVEL_ATTR,0);
      int maxLevel=DOMParsingTools.getIntAttribute(attrs,PrivateEncountersXMLConstants.MAX_LEVEL_ATTR,0);
      skirmishPE.setLevelScale(minLevel,maxLevel);
      // Level scaling
      int levelScaling=DOMParsingTools.getIntAttribute(attrs,PrivateEncountersXMLConstants.LEVEL_SCALING_ATTR,0);
      if (levelScaling>0)
      {
        skirmishPE.setLevelScaling(Integer.valueOf(levelScaling));
      }
    }
    // Description
    String description=DOMParsingTools.getStringAttribute(attrs,PrivateEncountersXMLConstants.DESCRIPTION_ATTR,"");
    ret.setDescription(description);

    if (isSkirmishPE)
    {
      SkirmishPrivateEncounter skirmishPE=(SkirmishPrivateEncounter)ret;
      // Difficulty tiers
      DifficultiesManager difficultiesMgr=DifficultiesManager.getInstance();
      List<Element> difficultyTierTags=DOMParsingTools.getChildTagsByName(privateEncounterTag,PrivateEncountersXMLConstants.DIFFICULTY_TIER_TAG);
      for(Element difficultyTierTag : difficultyTierTags)
      {
        int difficultyTierCode=DOMParsingTools.getIntAttribute(difficultyTierTag.getAttributes(),PrivateEncountersXMLConstants.DIFFICULTY_TIER_CODE_ATTR,0);
        Difficulty difficulty=difficultiesMgr.getDifficulty(difficultyTierCode);
        skirmishPE.addDifficultyTier(difficulty);
      }
      // Group sizes
      List<Element> groupSizeTags=DOMParsingTools.getChildTagsByName(privateEncounterTag,PrivateEncountersXMLConstants.GROUP_SIZE_TAG);
      for(Element groupSizeTag : groupSizeTags)
      {
        String groupSizeKey=DOMParsingTools.getStringAttribute(groupSizeTag.getAttributes(),PrivateEncountersXMLConstants.GROUP_SIZE_KEY_ATTR,"");
        GroupSize groupSize=GroupSizesManager.getInstance().getByKey(groupSizeKey);
        skirmishPE.addGroupSize(groupSize);
      }
    }

    // Additional content layers
    List<Element> additionalContentLayerTags=DOMParsingTools.getChildTagsByName(privateEncounterTag,PrivateEncountersXMLConstants.CONTENT_LAYER_TAG);
    for(Element additionalContentLayerTag : additionalContentLayerTags)
    {
      NamedNodeMap clAttrs=additionalContentLayerTag.getAttributes();
      // Content Layer ID
      int clId=DOMParsingTools.getIntAttribute(clAttrs,PrivateEncountersXMLConstants.CONTENT_LAYER_ID_ATTR,0);
      ret.addAdditionalContentLayer(clId);
    }

    // Maps
    List<Element> instanceMapTags=DOMParsingTools.getChildTagsByName(privateEncounterTag,PrivateEncountersXMLConstants.INSTANCE_MAP_TAG);
    for(Element instanceMapTag : instanceMapTags)
    {
      InstanceMapDescription map=new InstanceMapDescription();
      Element mapTag=DOMParsingTools.getChildTagByName(instanceMapTag,MapDescriptionXMLConstants.MAP_TAG);
      MapDescription basemap=MapDescriptionXMLParser.parseMapDescription(mapTag);
      map.setMap(basemap);
      // Zones
      List<Element> zoneTags=DOMParsingTools.getChildTagsByName(instanceMapTag,PrivateEncountersXMLConstants.ZONE_TAG);
      for(Element zoneTag : zoneTags)
      {
        // Zone ID
        int zoneId=DOMParsingTools.getIntAttribute(zoneTag.getAttributes(),PrivateEncountersXMLConstants.ZONE_ID_ATTR,0);
        map.addZoneId(zoneId);
      }
      ret.addMapDescription(map);
    }
    // Quests to bestow
    List<Element> questToBestowTags=DOMParsingTools.getChildTagsByName(privateEncounterTag,PrivateEncountersXMLConstants.QUEST_TO_BESTOW_TAG);
    for(Element questToBestowTag : questToBestowTags)
    {
      NamedNodeMap questToBestowAttrs=questToBestowTag.getAttributes();
      // Quest ID
      int questToBestowId=DOMParsingTools.getIntAttribute(questToBestowAttrs,PrivateEncountersXMLConstants.QUEST_ID_ATTR,0);
      ret.addQuestToBestow(questToBestowId);
    }
    return ret;
  }
}
