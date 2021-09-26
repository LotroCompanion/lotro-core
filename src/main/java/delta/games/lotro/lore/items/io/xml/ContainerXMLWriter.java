package delta.games.lotro.lore.items.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.common.treasure.FilteredTrophyTable;
import delta.games.lotro.common.treasure.RelicsList;
import delta.games.lotro.common.treasure.TreasureList;
import delta.games.lotro.common.treasure.TrophyList;
import delta.games.lotro.common.treasure.WeightedTreasureTable;
import delta.games.lotro.lore.items.Container;
import delta.games.lotro.lore.items.ItemsContainer;
import delta.games.lotro.lore.items.legendary.relics.Relic;
import delta.games.lotro.lore.items.legendary.relics.RelicsContainer;

/**
 * Writes containers to XML files.
 * @author DAM
 */
public class ContainerXMLWriter
{
  /**
   * Write a file with containers.
   * @param toFile Output file.
   * @param containers Containers to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeContainersFile(File toFile, List<Container> containers)
  {
    ContainerXMLWriter writer=new ContainerXMLWriter();
    boolean ok=writer.writeContainers(toFile,containers,EncodingNames.UTF_8);
    return ok;
  }

  /**
   * Write containers to a XML file.
   * @param outFile Output file.
   * @param containers Loots to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  private boolean writeContainers(File outFile, final List<Container> containers, String encoding)
  {
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",ContainerXMLConstants.CONTAINERS_TAG,new AttributesImpl());
        writeContainers(hd,containers);
        hd.endElement("","",ContainerXMLConstants.CONTAINERS_TAG);
      }
    };
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void writeContainers(TransformerHandler hd, List<Container> containers) throws Exception
  {
    for(Container container : containers)
    {
      writeContainer(hd,container);
    }
  }

  private void writeContainer(TransformerHandler hd, Container container) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();

    // Identifier
    int id=container.getIdentifier();
    attrs.addAttribute("","",ContainerXMLConstants.CONTAINER_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));

    String tag=null;
    if (container instanceof ItemsContainer)
    {
      writeItemsContainer(attrs,(ItemsContainer)container);
      tag=ContainerXMLConstants.CONTAINER_TAG;
    }
    else if (container instanceof RelicsContainer)
    {
      writeRelicsContainer(attrs,(RelicsContainer)container);
      tag=ContainerXMLConstants.RELICS_CONTAINER_TAG;
    }
    hd.startElement("","",tag,attrs);
    hd.endElement("","",tag);
  }

  private void writeItemsContainer(AttributesImpl attrs, ItemsContainer container) throws Exception
  {
    // Filtered trophy table
    FilteredTrophyTable filteredTrophyTable=container.getFilteredTable();
    if (filteredTrophyTable!=null)
    {
      int filteredTrophyTableId=filteredTrophyTable.getIdentifier();
      attrs.addAttribute("","",ContainerXMLConstants.FILTERED_TROPHY_TABLE_ID_ATTR,XmlWriter.CDATA,String.valueOf(filteredTrophyTableId));
    }

    // Weighted treasure table
    WeightedTreasureTable weightedTreasureTable=container.getWeightedTable();
    if (weightedTreasureTable!=null)
    {
      int weightedTreasureTableId=weightedTreasureTable.getIdentifier();
      attrs.addAttribute("","",ContainerXMLConstants.WEIGHTED_TREASURE_TABLE_ID_ATTR,XmlWriter.CDATA,String.valueOf(weightedTreasureTableId));
    }

    // Trophy list
    TrophyList trophyList=container.getTrophyList();
    if (trophyList!=null)
    {
      int trophyListId=trophyList.getIdentifier();
      attrs.addAttribute("","",ContainerXMLConstants.TROPHY_LIST_ID_ATTR,XmlWriter.CDATA,String.valueOf(trophyListId));
    }

    // Barter trophy list
    TrophyList barterTrophyList=container.getBarterTrophyList();
    if (barterTrophyList!=null)
    {
      int barterTrophyListId=barterTrophyList.getIdentifier();
      attrs.addAttribute("","",ContainerXMLConstants.BARTER_TROPHY_LIST_ID_ATTR,XmlWriter.CDATA,String.valueOf(barterTrophyListId));
    }

    // Treasure list
    TreasureList treasureList=container.getTreasureList();
    if (treasureList!=null)
    {
      int treasureListId=treasureList.getIdentifier();
      attrs.addAttribute("","",ContainerXMLConstants.TREASURE_LIST_ID_ATTR,XmlWriter.CDATA,String.valueOf(treasureListId));
    }

    // Custom skirmish loot table
    Integer customSkirmishLootTableId=container.getCustomSkirmishLootTableId();
    if (customSkirmishLootTableId!=null)
    {
      attrs.addAttribute("","",ContainerXMLConstants.CUSTOM_SKIRMISH_LOOT_TABLE_ID_ATTR,XmlWriter.CDATA,customSkirmishLootTableId.toString());
    }
  }

  private void writeRelicsContainer(AttributesImpl attrs, RelicsContainer container) throws Exception
  {
    // Relics treasure group
    RelicsList relicsList=container.getRelicsList();
    if (relicsList!=null)
    {
      int id=relicsList.getIdentifier();
      attrs.addAttribute("","",ContainerXMLConstants.RELICS_LIST_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    }
    // Relic
    Relic relic=container.getRelic();
    if (relic!=null)
    {
      int id=relic.getIdentifier();
      attrs.addAttribute("","",ContainerXMLConstants.RELIC_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    }
  }
}
