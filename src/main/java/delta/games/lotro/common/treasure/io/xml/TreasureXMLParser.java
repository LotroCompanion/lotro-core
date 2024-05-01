package delta.games.lotro.common.treasure.io.xml;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.requirements.io.xml.UsageRequirementsXMLParser;
import delta.games.lotro.common.treasure.FilteredTrophyTable;
import delta.games.lotro.common.treasure.FilteredTrophyTableEntry;
import delta.games.lotro.common.treasure.ItemsTable;
import delta.games.lotro.common.treasure.ItemsTableEntry;
import delta.games.lotro.common.treasure.LootTable;
import delta.games.lotro.common.treasure.LootsManager;
import delta.games.lotro.common.treasure.RelicsList;
import delta.games.lotro.common.treasure.RelicsListEntry;
import delta.games.lotro.common.treasure.RelicsTreasureGroup;
import delta.games.lotro.common.treasure.RelicsTreasureGroupEntry;
import delta.games.lotro.common.treasure.TreasureGroupProfile;
import delta.games.lotro.common.treasure.TreasureList;
import delta.games.lotro.common.treasure.TreasureListEntry;
import delta.games.lotro.common.treasure.TrophyList;
import delta.games.lotro.common.treasure.TrophyListEntry;
import delta.games.lotro.common.treasure.WeightedTreasureTable;
import delta.games.lotro.common.treasure.WeightedTreasureTableEntry;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemsManager;
import delta.games.lotro.lore.items.legendary.relics.Relic;
import delta.games.lotro.lore.items.legendary.relics.RelicsManager;

/**
 * Parser for loot tables stored in XML.
 * @author DAM
 */
public class TreasureXMLParser
{
  private static final Logger LOGGER=Logger.getLogger(TreasureXMLParser.class);

  private HashMap<Integer,Element> _nodesMap;
  private LootsManager _lootMgr;

  /**
   * Constructor.
   */
  public TreasureXMLParser()
  {
    _nodesMap=new HashMap<Integer,Element>();
  }

  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed loot tables or <code>null</code>.
   */
  public LootsManager parseXML(File source)
  {
    _lootMgr=new LootsManager();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      // Fetch all tags
      List<Element> tags=DOMParsingTools.getChildTags(root);
      for(Element tag : tags)
      {
        int id=DOMParsingTools.getIntAttribute(tag.getAttributes(),TreasureXMLConstants.ID_ATTR,0);
        _nodesMap.put(Integer.valueOf(id),tag);
      }
      // Decode tags
      while(!_nodesMap.isEmpty())
      {
        Element tag=_nodesMap.entrySet().iterator().next().getValue();
        decodeTag(tag);
      }
    }
    return _lootMgr;
  }

  private void decodeTag(Element element)
  {
    int id=DOMParsingTools.getIntAttribute(element.getAttributes(),TreasureXMLConstants.ID_ATTR,0);
    String tagName=element.getTagName();
    if (TreasureXMLConstants.ITEMS_TABLE_TAG.equals(tagName))
    {
      parseItemsTable(element);
    }
    else if (TreasureXMLConstants.TREASURE_LIST_TAG.equals(tagName))
    {
      parseTreasureList(element);
    }
    else if (TreasureXMLConstants.TROPHY_LIST_TAG.equals(tagName))
    {
      parseTrophyList(element);
    }
    else if (TreasureXMLConstants.WEIGHTED_TREASURE_TABLE_TAG.equals(tagName))
    {
      parseWeightedTreasureTable(element);
    }
    else if (TreasureXMLConstants.FILTERED_TROPHY_TABLE_TAG.equals(tagName))
    {
      parseFilteredTrophyTable(element);
    }
    else if (TreasureXMLConstants.RELICS_LIST_TAG.equals(tagName))
    {
      parseRelicsList(element);
    }
    else if (TreasureXMLConstants.RELICS_TREASURE_GROUP_TAG.equals(tagName))
    {
      parseRelicsTreasureGroup(element);
    }
    _nodesMap.remove(Integer.valueOf(id));
  }

  private void parseItemsTable(Element root)
  {
    int id=DOMParsingTools.getIntAttribute(root.getAttributes(),TreasureXMLConstants.ID_ATTR,0);
    ItemsTable ret=new ItemsTable(id);
    List<Element> entryTags=DOMParsingTools.getChildTagsByName(root,TreasureXMLConstants.ITEMS_TABLE_ENTRY_TAG);
    for(Element entryTag : entryTags)
    {
      NamedNodeMap entryAttrs=entryTag.getAttributes();
      // Weight
      int weight=DOMParsingTools.getIntAttribute(entryAttrs,TreasureXMLConstants.WEIGHT_ATTR,0);
      // Item
      int itemId=DOMParsingTools.getIntAttribute(entryAttrs,TreasureXMLConstants.ITEM_ID_ATTR,-1);
      Item item=ItemsManager.getInstance().getItem(itemId);
      // Quantity
      int quantity=DOMParsingTools.getIntAttribute(entryAttrs,TreasureXMLConstants.QUANTITY_ATTR,1);
      ItemsTableEntry entry=new ItemsTableEntry(weight,item,quantity);
      ret.addEntry(entry);
    }
    _lootMgr.getTables().add(ret);
  }

  private void parseTreasureList(Element root)
  {
    int id=DOMParsingTools.getIntAttribute(root.getAttributes(),TreasureXMLConstants.ID_ATTR,0);
    TreasureList ret=new TreasureList(id);
    List<Element> entryTags=DOMParsingTools.getChildTagsByName(root,TreasureXMLConstants.TREASURE_LIST_ENTRY_TAG);
    for(Element entryTag : entryTags)
    {
      NamedNodeMap entryAttrs=entryTag.getAttributes();
      // Weight
      int weight=DOMParsingTools.getIntAttribute(entryAttrs,TreasureXMLConstants.WEIGHT_ATTR,0);
      // Treasure group profile
      int treasureGroupProfileId=DOMParsingTools.getIntAttribute(entryAttrs,TreasureXMLConstants.TREASURE_GROUP_PROFILE_ID_ATTR,0);
      TreasureGroupProfile treasureGroupProfile=getTreasureGroupProfile(treasureGroupProfileId);
      TreasureListEntry entry=new TreasureListEntry(weight,treasureGroupProfile);
      ret.addEntry(entry);
    }
    _lootMgr.getTables().add(ret);
  }

  private TreasureGroupProfile getTreasureGroupProfile(int id)
  {
    LootTable ret=_lootMgr.getTables().getItem(id);
    if (ret==null)
    {
      Element tag=_nodesMap.get(Integer.valueOf(id));
      if (tag!=null)
      {
        decodeTag(tag);
        ret=getTreasureGroupProfile(id);
      }
    }
    return (TreasureGroupProfile)ret;
  }

  private void parseTrophyList(Element root)
  {
    // ID
    NamedNodeMap attrs=root.getAttributes();
    int id=DOMParsingTools.getIntAttribute(attrs,TreasureXMLConstants.ID_ATTR,0);
    TrophyList ret=new TrophyList(id);
    // Description
    String description=DOMParsingTools.getStringAttribute(attrs,TreasureXMLConstants.DESCRIPTION_ATTR,null);
    ret.setDescription(description);
    // Image ID
    int imageId=DOMParsingTools.getIntAttribute(attrs,TreasureXMLConstants.IMAGE_ID_ATTR,0);
    if (imageId>0)
    {
      ret.setImageId(Integer.valueOf(imageId));
    }
    // Entries
    List<Element> entryTags=DOMParsingTools.getChildTagsByName(root,TreasureXMLConstants.TROPHY_LIST_ENTRY_TAG);
    for(Element entryTag : entryTags)
    {
      NamedNodeMap entryAttrs=entryTag.getAttributes();
      // Probability
      float probability=DOMParsingTools.getFloatAttribute(entryAttrs,TreasureXMLConstants.DROP_PROBABILITY_ATTR,0);

      // Item?
      TrophyListEntry entry=null;
      Item item=null;
      TreasureGroupProfile treasureGroup=null;
      int itemId=DOMParsingTools.getIntAttribute(entryAttrs,TreasureXMLConstants.ITEM_ID_ATTR,-1);
      if (itemId>0)
      {
        item=ItemsManager.getInstance().getItem(itemId);
        // Quantity
        int quantity=DOMParsingTools.getIntAttribute(entryAttrs,TreasureXMLConstants.QUANTITY_ATTR,1);
        entry=new TrophyListEntry(probability,item,quantity);
      }
      else
      {
        // Treasure group profile?
        int treasureGroupProfileId=DOMParsingTools.getIntAttribute(entryAttrs,TreasureXMLConstants.TREASURE_GROUP_PROFILE_ID_ATTR,-1);
        treasureGroup=getTreasureGroupProfile(treasureGroupProfileId);
        entry=new TrophyListEntry(probability,treasureGroup);
      }
      // Group drop
      boolean groupDrop=DOMParsingTools.getBooleanAttribute(entryAttrs,TreasureXMLConstants.GROUP_DROP_ATTR,false);
      entry.setGroupDrop(groupDrop);
      ret.addEntry(entry);
    }
    _lootMgr.getTables().add(ret);
  }

  private void parseWeightedTreasureTable(Element root)
  {
    int id=DOMParsingTools.getIntAttribute(root.getAttributes(),TreasureXMLConstants.ID_ATTR,0);
    WeightedTreasureTable ret=new WeightedTreasureTable(id);
    List<Element> entryTags=DOMParsingTools.getChildTagsByName(root,TreasureXMLConstants.WEIGHTED_TREASURE_TABLE_ENTRY_TAG);
    for(Element entryTag : entryTags)
    {
      NamedNodeMap entryAttrs=entryTag.getAttributes();
      // Weight
      int weight=DOMParsingTools.getIntAttribute(entryAttrs,TreasureXMLConstants.WEIGHT_ATTR,0);
      // Trophy list
      int trophyListId=DOMParsingTools.getIntAttribute(entryAttrs,TreasureXMLConstants.TROPHY_LIST_ID_ATTR,0);
      TrophyList trophyList=getTrophyList(trophyListId);
      WeightedTreasureTableEntry entry=new WeightedTreasureTableEntry(weight,trophyList);
      ret.addEntry(entry);
    }
    _lootMgr.getTables().add(ret);
  }

  private TrophyList getTrophyList(int id)
  {
    TrophyList ret=(TrophyList)_lootMgr.getTables().getItem(id);
    if (ret==null)
    {
      Element tag=_nodesMap.get(Integer.valueOf(id));
      if (tag!=null)
      {
        decodeTag(tag);
        ret=getTrophyList(id);
      }
    }
    return ret;
  }

  private LootTable getLootTable(int id)
  {
    LootTable ret=_lootMgr.getTables().getItem(id);
    if (ret==null)
    {
      Element tag=_nodesMap.get(Integer.valueOf(id));
      if (tag!=null)
      {
        decodeTag(tag);
        ret=getLootTable(id);
      }
    }
    return ret;
  }

  private FilteredTrophyTable parseFilteredTrophyTable(Element root)
  {
    int id=DOMParsingTools.getIntAttribute(root.getAttributes(),TreasureXMLConstants.ID_ATTR,0);
    FilteredTrophyTable ret=new FilteredTrophyTable(id);
    List<Element> entryTags=DOMParsingTools.getChildTagsByName(root,TreasureXMLConstants.FILTERED_TROPHY_TABLE_ENTRY_TAG);
    for(Element entryTag : entryTags)
    {
      NamedNodeMap entryAttrs=entryTag.getAttributes();
      // Trophy list?
      int trophyListId=DOMParsingTools.getIntAttribute(entryAttrs,TreasureXMLConstants.TROPHY_LIST_ID_ATTR,0);
      int weightedTreasureTableId=DOMParsingTools.getIntAttribute(entryAttrs,TreasureXMLConstants.WEIGHTED_TREASURE_TABLE_ID_ATTR,0);
      int tableId=Math.max(trophyListId,weightedTreasureTableId);
      LootTable lootTable=getLootTable(tableId);
      FilteredTrophyTableEntry entry=new FilteredTrophyTableEntry(lootTable);
      UsageRequirementsXMLParser.parseRequirements(entry.getUsageRequirement(),entryTag);
      ret.addEntry(entry);
    }
    _lootMgr.getTables().add(ret);
    return ret;
  }

  private RelicsTreasureGroup parseRelicsTreasureGroup(Element root)
  {
    int id=DOMParsingTools.getIntAttribute(root.getAttributes(),TreasureXMLConstants.ID_ATTR,0);
    RelicsTreasureGroup ret=new RelicsTreasureGroup(id);
    // Pull counts
    List<Element> countTags=DOMParsingTools.getChildTagsByName(root,TreasureXMLConstants.PULL_TAG);
    for(Element countTag : countTags)
    {
      NamedNodeMap countAttrs=countTag.getAttributes();
      // Count
      int count=DOMParsingTools.getIntAttribute(countAttrs,TreasureXMLConstants.COUNT_ATTR,0);
      // Weight
      int weight=DOMParsingTools.getIntAttribute(countAttrs,TreasureXMLConstants.WEIGHT_ATTR,0);
      ret.addCount(count,weight);
    }
    // Entries
    List<Element> entryTags=DOMParsingTools.getChildTagsByName(root,TreasureXMLConstants.RELICS_TREASURE_GROUP_ENTRY_TAG);
    for(Element entryTag : entryTags)
    {
      NamedNodeMap entryAttrs=entryTag.getAttributes();
      // Weight
      int weight=DOMParsingTools.getIntAttribute(entryAttrs,TreasureXMLConstants.WEIGHT_ATTR,0);
      // Relic
      int relicId=DOMParsingTools.getIntAttribute(entryAttrs,TreasureXMLConstants.RELIC_ID_ATTR,0);
      RelicsManager relicsMgr=RelicsManager.getInstance();
      Relic relic=relicsMgr.getById(relicId);
      if (relic!=null)
      {
        RelicsTreasureGroupEntry entry=new RelicsTreasureGroupEntry(weight,relic);
        ret.addEntry(entry);
      }
      else
      {
        LOGGER.warn("Relic not found: "+relicId);
      }
    }
    _lootMgr.getRelicsTreasureGroups().add(ret);
    return ret;
  }

  private RelicsList parseRelicsList(Element root)
  {
    int id=DOMParsingTools.getIntAttribute(root.getAttributes(),TreasureXMLConstants.ID_ATTR,0);
    RelicsList ret=new RelicsList(id);
    // Entries
    List<Element> entryTags=DOMParsingTools.getChildTagsByName(root,TreasureXMLConstants.RELICS_LIST_ENTRY_TAG);
    for(Element entryTag : entryTags)
    {
      NamedNodeMap entryAttrs=entryTag.getAttributes();
      // Probability
      float probability=DOMParsingTools.getFloatAttribute(entryAttrs,TreasureXMLConstants.DROP_PROBABILITY_ATTR,0);
      // Relic?
      Relic relic=null;
      int relicId=DOMParsingTools.getIntAttribute(entryAttrs,TreasureXMLConstants.RELIC_ID_ATTR,0);
      if (relicId!=0)
      {
        RelicsManager relicsMgr=RelicsManager.getInstance();
        relic=relicsMgr.getById(relicId);
      }
      // Relics treasure group
      RelicsTreasureGroup relicTreasureGroup=null;
      int relicTreasureGroupId=DOMParsingTools.getIntAttribute(entryAttrs,TreasureXMLConstants.RELICS_TREASURE_GROUP_ID_ATTR,0);
      if (relicTreasureGroupId!=0)
      {
        relicTreasureGroup=getRelicTreasureGroup(relicTreasureGroupId);
      }
      RelicsListEntry entry=new RelicsListEntry(probability,relic,relicTreasureGroup);
      ret.addEntry(entry);
    }
    _lootMgr.getRelicsLists().add(ret);
    return ret;
  }

  private RelicsTreasureGroup getRelicTreasureGroup(int id)
  {
    RelicsTreasureGroup ret=_lootMgr.getRelicsTreasureGroups().getItem(id);
    if (ret==null)
    {
      Element tag=_nodesMap.get(Integer.valueOf(id));
      if (tag!=null)
      {
        decodeTag(tag);
        ret=getRelicTreasureGroup(id);
      }
    }
    return ret;
  }
}
