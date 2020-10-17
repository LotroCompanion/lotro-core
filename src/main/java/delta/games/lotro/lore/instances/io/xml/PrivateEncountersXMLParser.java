package delta.games.lotro.lore.instances.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.lore.geo.BlockReference;
import delta.games.lotro.lore.instances.PrivateEncounter;
import delta.games.lotro.lore.instances.PrivateEncountersManager;
import delta.games.lotro.lore.instances.SkirmishPrivateEncounter;
import delta.games.lotro.lore.instances.InstanceMapDescription;

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
      List<Element> difficultyTierTags=DOMParsingTools.getChildTagsByName(privateEncounterTag,PrivateEncountersXMLConstants.DIFFICULTY_TIER_TAG);
      for(Element difficultyTierTag : difficultyTierTags)
      {
        String difficultyTierName=DOMParsingTools.getStringAttribute(difficultyTierTag.getAttributes(),PrivateEncountersXMLConstants.DIFFICULTY_TIER_NAME_ATTR,"");
        skirmishPE.addDifficultyTier(difficultyTierName);
      }
      // Group sizes
      List<Element> groupSizeTags=DOMParsingTools.getChildTagsByName(privateEncounterTag,PrivateEncountersXMLConstants.GROUP_SIZE_TAG);
      for(Element groupSizeTag : groupSizeTags)
      {
        String groupSizeName=DOMParsingTools.getStringAttribute(groupSizeTag.getAttributes(),PrivateEncountersXMLConstants.GROUP_SIZE_NAME_ATTR,"");
        skirmishPE.addGroupSize(groupSizeName);
      }
    }

    // Maps
    List<Element> mapTags=DOMParsingTools.getChildTagsByName(privateEncounterTag,PrivateEncountersXMLConstants.MAP_TAG);
    for(Element mapTag : mapTags)
    {
      NamedNodeMap zoneAndMapAttrs=mapTag.getAttributes();
      // Basemap ID
      int basemapIdInt=DOMParsingTools.getIntAttribute(zoneAndMapAttrs,PrivateEncountersXMLConstants.BASEMAP_ID_ATTR,0);
      Integer basemapId=(basemapIdInt!=0)?Integer.valueOf(basemapIdInt):null;

      InstanceMapDescription map=new InstanceMapDescription(basemapId);
      // Zones
      List<Element> zoneTags=DOMParsingTools.getChildTagsByName(mapTag,PrivateEncountersXMLConstants.ZONE_TAG);
      for(Element zoneTag : zoneTags)
      {
        // Zone ID
        int zoneId=DOMParsingTools.getIntAttribute(zoneTag.getAttributes(),PrivateEncountersXMLConstants.ZONE_ID_ATTR,0);
        map.addZoneId(zoneId);
      }
      // Blocks
      List<Element> blockTags=DOMParsingTools.getChildTagsByName(mapTag,PrivateEncountersXMLConstants.BLOCK_TAG);
      for(Element blockTag : blockTags)
      {
        NamedNodeMap blockAttrs=blockTag.getAttributes();
        BlockReference block=new BlockReference();
        // Region
        int region=DOMParsingTools.getIntAttribute(blockAttrs,PrivateEncountersXMLConstants.BLOCK_REGION_ATTR,0);
        block.setRegion(region);
        // X
        int x=DOMParsingTools.getIntAttribute(blockAttrs,PrivateEncountersXMLConstants.BLOCK_X_ATTR,0);
        // Y
        int y=DOMParsingTools.getIntAttribute(blockAttrs,PrivateEncountersXMLConstants.BLOCK_Y_ATTR,0);
        block.setBlock(x,y);
        map.addBlock(block);
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
