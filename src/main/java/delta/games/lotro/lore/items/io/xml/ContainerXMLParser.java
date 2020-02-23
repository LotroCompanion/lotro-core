package delta.games.lotro.lore.items.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.treasure.FilteredTrophyTable;
import delta.games.lotro.common.treasure.LootsManager;
import delta.games.lotro.common.treasure.TreasureList;
import delta.games.lotro.common.treasure.TrophyList;
import delta.games.lotro.common.treasure.WeightedTreasureTable;
import delta.games.lotro.lore.items.Container;
import delta.games.lotro.lore.items.ItemsContainer;

/**
 * Parser for containers stored in XML.
 * @author DAM
 */
public class ContainerXMLParser
{
  private LootsManager _lootsMgr;

  /**
   * Constructor.
   */
  public ContainerXMLParser()
  {
    _lootsMgr=LootsManager.getInstance();
  }

  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed containers or <code>null</code>.
   */
  public List<Container> parseXML(File source)
  {
    List<Container> ret=new ArrayList<Container>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> tags=DOMParsingTools.getChildTagsByName(root,ContainerXMLConstants.CONTAINER_TAG);
      for(Element tag : tags)
      {
        Container container=parseContainer(tag);
        ret.add(container);
      }
    }
    return ret;
  }

  private Container parseContainer(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    int id=DOMParsingTools.getIntAttribute(attrs,ContainerXMLConstants.CONTAINER_ID_ATTR,0);
    ItemsContainer ret=new ItemsContainer(id);

    // Filtered trophy table
    int filteredTrophyTableId=DOMParsingTools.getIntAttribute(attrs,ContainerXMLConstants.FILTERED_TROPHY_TABLE_ID_ATTR,0);
    if (filteredTrophyTableId!=0)
    {
      FilteredTrophyTable filteredTrophyTable=_lootsMgr.getFilteredTrophyTables().getItem(filteredTrophyTableId);
      ret.setFilteredTable(filteredTrophyTable);
    }
    // Weighted treasure table
    int weightedTreasureTableId=DOMParsingTools.getIntAttribute(attrs,ContainerXMLConstants.WEIGHTED_TREASURE_TABLE_ID_ATTR,0);
    if (weightedTreasureTableId!=0)
    {
      WeightedTreasureTable weightedTreasureTable=_lootsMgr.getWeightedTreasureTables().getItem(weightedTreasureTableId);
      ret.setWeightedTable(weightedTreasureTable);
    }
    // Trophy list
    int trophyListId=DOMParsingTools.getIntAttribute(attrs,ContainerXMLConstants.TROPHY_LIST_ID_ATTR,0);
    if (trophyListId!=0)
    {
      TrophyList trophyList=_lootsMgr.getTrophyLists().getItem(trophyListId);
      ret.setTrophyList(trophyList);
    }
    // Treasure list
    int treasureListId=DOMParsingTools.getIntAttribute(attrs,ContainerXMLConstants.TREASURE_LIST_ID_ATTR,0);
    if (treasureListId!=0)
    {
      TreasureList treasureList=_lootsMgr.getTreasureLists().getItem(treasureListId);
      ret.setTreasureList(treasureList);
    }
    return ret;
  }
}
