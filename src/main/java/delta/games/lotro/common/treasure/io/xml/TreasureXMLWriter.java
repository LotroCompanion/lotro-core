package delta.games.lotro.common.treasure.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.common.requirements.UsageRequirement;
import delta.games.lotro.common.requirements.io.xml.UsageRequirementsXMLWriter;
import delta.games.lotro.common.treasure.FilteredTrophyTable;
import delta.games.lotro.common.treasure.FilteredTrophyTableEntry;
import delta.games.lotro.common.treasure.ItemsTable;
import delta.games.lotro.common.treasure.ItemsTableEntry;
import delta.games.lotro.common.treasure.LootsManager;
import delta.games.lotro.common.treasure.TreasureGroupProfile;
import delta.games.lotro.common.treasure.TreasureList;
import delta.games.lotro.common.treasure.TreasureListEntry;
import delta.games.lotro.common.treasure.TrophyList;
import delta.games.lotro.common.treasure.TrophyListEntry;
import delta.games.lotro.common.treasure.WeightedTreasureTable;
import delta.games.lotro.common.treasure.WeightedTreasureTableEntry;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.utils.Proxy;

/**
 * Writes loot tables to XML files.
 * @author DAM
 */
public class TreasureXMLWriter
{
  /**
   * Write a file with loots.
   * @param toFile Output file.
   * @param lootsMgr Loots to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeLootsFile(File toFile, LootsManager lootsMgr)
  {
    TreasureXMLWriter writer=new TreasureXMLWriter();
    boolean ok=writer.writeLoots(toFile,lootsMgr,EncodingNames.UTF_8);
    return ok;
  }

  /**
   * Write loots to a XML file.
   * @param outFile Output file.
   * @param lootsMgr Loots to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  private boolean writeLoots(File outFile, final LootsManager lootsMgr, String encoding)
  {
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",TreasureXMLConstants.LOOTS_TAG,new AttributesImpl());
        writeLootsManager(hd,lootsMgr);
        hd.endElement("","",TreasureXMLConstants.LOOTS_TAG);
      }
    };
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void writeLootsManager(TransformerHandler hd, LootsManager loots) throws Exception
  {
    // Items tables
    List<ItemsTable> itemsTables=loots.getItemsTables().getItems();
    for(ItemsTable itemsTable : itemsTables)
    {
      writeItemsTable(hd,itemsTable);
    }
    // Treasure lists
    List<TreasureList> treasureLists=loots.getTreasureLists().getItems();
    for(TreasureList treasureList : treasureLists)
    {
      writeTreasureList(hd,treasureList);
    }
    // Trophy lists
    List<TrophyList> trophyLists=loots.getTrophyLists().getItems();
    for(TrophyList trophyList : trophyLists)
    {
      writeTrophyList(hd,trophyList);
    }
    // Weighted treasure tables
    List<WeightedTreasureTable> weightedTreasureTables=loots.getWeightedTreasureTables().getItems();
    for(WeightedTreasureTable weightedTreasureTable : weightedTreasureTables)
    {
      writeWeightedTreasureTable(hd,weightedTreasureTable);
    }
    // Filtered trophy tables
    List<FilteredTrophyTable> filteredTrophyTables=loots.getFilteredTrophyTables().getItems();
    for(FilteredTrophyTable filteredTrophyTable : filteredTrophyTables)
    {
      writeFilteredTrophyTable(hd,filteredTrophyTable);
    }
  }

  private void writeItemsTable(TransformerHandler hd, ItemsTable table) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();

    // Identifier
    int id=table.getIdentifier();
    attrs.addAttribute("","",TreasureXMLConstants.ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    hd.startElement("","",TreasureXMLConstants.ITEMS_TABLE_TAG,attrs);

    for(ItemsTableEntry entry : table.getEntries())
    {
      AttributesImpl entryAttrs=new AttributesImpl();
      // Weight
      int weight=entry.getWeight();
      entryAttrs.addAttribute("","",TreasureXMLConstants.WEIGHT_ATTR,XmlWriter.CDATA,String.valueOf(weight));
      // Item
      writeItemProxy(entry.getItem(),entryAttrs);
      // Quantity
      int quantity=entry.getQuantity();
      if (quantity>1)
      {
        entryAttrs.addAttribute("","",TreasureXMLConstants.QUANTITY_ATTR,XmlWriter.CDATA,String.valueOf(quantity));
      }
      hd.startElement("","",TreasureXMLConstants.ITEMS_TABLE_ENTRY_TAG,entryAttrs);
      hd.endElement("","",TreasureXMLConstants.ITEMS_TABLE_ENTRY_TAG);
    }
    hd.endElement("","",TreasureXMLConstants.ITEMS_TABLE_TAG);
  }

  private void writeTreasureList(TransformerHandler hd, TreasureList list) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();

    // Identifier
    int id=list.getIdentifier();
    attrs.addAttribute("","",TreasureXMLConstants.ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    hd.startElement("","",TreasureXMLConstants.TREASURE_LIST_TAG,attrs);

    for(TreasureListEntry entry : list.getEntries())
    {
      AttributesImpl entryAttrs=new AttributesImpl();
      // Weight
      int weight=entry.getWeight();
      entryAttrs.addAttribute("","",TreasureXMLConstants.WEIGHT_ATTR,XmlWriter.CDATA,String.valueOf(weight));
      // Treasure group profile
      int treasureGroupProfileId=entry.getTreasureGroup().getIdentifier();
      entryAttrs.addAttribute("","",TreasureXMLConstants.TREASURE_GROUP_PROFILE_ID_ATTR,XmlWriter.CDATA,String.valueOf(treasureGroupProfileId));
      hd.startElement("","",TreasureXMLConstants.TREASURE_LIST_ENTRY_TAG,entryAttrs);
      hd.endElement("","",TreasureXMLConstants.TREASURE_LIST_ENTRY_TAG);
    }
    hd.endElement("","",TreasureXMLConstants.TREASURE_LIST_TAG);
  }

  private void writeTrophyList(TransformerHandler hd, TrophyList list) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();

    // Identifier
    int id=list.getIdentifier();
    attrs.addAttribute("","",TreasureXMLConstants.ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Description
    String description=list.getDescription();
    if (description!=null)
    {
      attrs.addAttribute("","",TreasureXMLConstants.DESCRIPTION_ATTR,XmlWriter.CDATA,description);
    }
    // Image ID
    Integer imageId=list.getImageId();
    if (imageId!=null)
    {
      attrs.addAttribute("","",TreasureXMLConstants.IMAGE_ID_ATTR,XmlWriter.CDATA,imageId.toString());
    }
    hd.startElement("","",TreasureXMLConstants.TROPHY_LIST_TAG,attrs);

    for(TrophyListEntry entry : list.getEntries())
    {
      AttributesImpl entryAttrs=new AttributesImpl();
      // Probability
      float probability=entry.getProbability();
      entryAttrs.addAttribute("","",TreasureXMLConstants.DROP_PROBABILITY_ATTR,XmlWriter.CDATA,String.valueOf(probability));
      // Item?
      Proxy<Item> itemProxy=entry.getItem();
      if (itemProxy!=null)
      {
        writeItemProxy(itemProxy,entryAttrs);
      }
      // Treasure group profile?
      TreasureGroupProfile profile=entry.getTreasureGroup();
      if (profile!=null)
      {
        entryAttrs.addAttribute("","",TreasureXMLConstants.TREASURE_GROUP_PROFILE_ID_ATTR,XmlWriter.CDATA,String.valueOf(profile.getIdentifier()));
      }
      // Quantity
      int quantity=entry.getQuantity();
      if (quantity>1)
      {
        entryAttrs.addAttribute("","",TreasureXMLConstants.QUANTITY_ATTR,XmlWriter.CDATA,String.valueOf(quantity));
      }
      hd.startElement("","",TreasureXMLConstants.TROPHY_LIST_ENTRY_TAG,entryAttrs);
      hd.endElement("","",TreasureXMLConstants.TROPHY_LIST_ENTRY_TAG);
    }
    hd.endElement("","",TreasureXMLConstants.TROPHY_LIST_TAG);
  }

  private void writeWeightedTreasureTable(TransformerHandler hd, WeightedTreasureTable list) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();

    // Identifier
    int id=list.getIdentifier();
    attrs.addAttribute("","",TreasureXMLConstants.ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    hd.startElement("","",TreasureXMLConstants.WEIGHTED_TREASURE_TABLE_TAG,attrs);

    for(WeightedTreasureTableEntry entry : list.getEntries())
    {
      AttributesImpl entryAttrs=new AttributesImpl();
      // Weight
      int weight=entry.getWeight();
      entryAttrs.addAttribute("","",TreasureXMLConstants.WEIGHT_ATTR,XmlWriter.CDATA,String.valueOf(weight));
      // Trophy list
      int trophyListId=entry.getTrophyList().getIdentifier();
      entryAttrs.addAttribute("","",TreasureXMLConstants.TROPHY_LIST_ID_ATTR,XmlWriter.CDATA,String.valueOf(trophyListId));
      hd.startElement("","",TreasureXMLConstants.WEIGHTED_TREASURE_TABLE_ENTRY_TAG,entryAttrs);
      hd.endElement("","",TreasureXMLConstants.WEIGHTED_TREASURE_TABLE_ENTRY_TAG);
    }
    hd.endElement("","",TreasureXMLConstants.WEIGHTED_TREASURE_TABLE_TAG);
  }

  private void writeFilteredTrophyTable(TransformerHandler hd, FilteredTrophyTable list) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();

    // Identifier
    int id=list.getIdentifier();
    attrs.addAttribute("","",TreasureXMLConstants.ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    hd.startElement("","",TreasureXMLConstants.FILTERED_TROPHY_TABLE_TAG,attrs);

    for(FilteredTrophyTableEntry entry : list.getEntries())
    {
      AttributesImpl entryAttrs=new AttributesImpl();
      // Trophy list
      TrophyList trophyList=entry.getTrophyList();
      if (trophyList!=null)
      {
        entryAttrs.addAttribute("","",TreasureXMLConstants.TROPHY_LIST_ID_ATTR,XmlWriter.CDATA,String.valueOf(trophyList.getIdentifier()));
      }
      // Weighted treasure table?
      WeightedTreasureTable treasureTable=entry.getTreasureTable();
      if (treasureTable!=null)
      {
        entryAttrs.addAttribute("","",TreasureXMLConstants.WEIGHTED_TREASURE_TABLE_ID_ATTR,XmlWriter.CDATA,String.valueOf(treasureTable.getIdentifier()));
      }
      // Requirement
      UsageRequirement requirement=entry.getUsageRequirement();
      if (!requirement.isEmpty())
      {
        UsageRequirementsXMLWriter.write(entryAttrs,requirement);
      }
      hd.startElement("","",TreasureXMLConstants.FILTERED_TROPHY_TABLE_ENTRY_TAG,entryAttrs);
      hd.endElement("","",TreasureXMLConstants.FILTERED_TROPHY_TABLE_ENTRY_TAG);
    }
    hd.endElement("","",TreasureXMLConstants.FILTERED_TROPHY_TABLE_TAG);
  }

  private void writeItemProxy(Proxy<Item> itemProxy, AttributesImpl attrs) throws Exception
  {
    // ID
    int itemId=itemProxy.getId();
    attrs.addAttribute("","",TreasureXMLConstants.ITEM_ID_ATTR,XmlWriter.CDATA,String.valueOf(itemId));
    // Name
    String name=itemProxy.getName();
    attrs.addAttribute("","",TreasureXMLConstants.NAME_ATTR,XmlWriter.CDATA,name);
  }
}
