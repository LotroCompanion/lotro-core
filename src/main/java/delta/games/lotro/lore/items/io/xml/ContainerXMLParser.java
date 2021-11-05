package delta.games.lotro.lore.items.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.treasure.LootTable;
import delta.games.lotro.common.treasure.LootsManager;
import delta.games.lotro.common.treasure.RelicsList;
import delta.games.lotro.lore.items.Container;
import delta.games.lotro.lore.items.ItemsContainer;
import delta.games.lotro.lore.items.containers.LootType;
import delta.games.lotro.lore.items.legendary.relics.Relic;
import delta.games.lotro.lore.items.legendary.relics.RelicsContainer;
import delta.games.lotro.lore.items.legendary.relics.RelicsManager;

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
        Container container=parseItemContainer(tag);
        ret.add(container);
      }
      List<Element> relicContainerTags=DOMParsingTools.getChildTagsByName(root,ContainerXMLConstants.RELICS_CONTAINER_TAG);
      for(Element relicContainerTag : relicContainerTags)
      {
        Container container=parseRelicContainer(relicContainerTag);
        ret.add(container);
      }
    }
    return ret;
  }

  private Container parseItemContainer(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    int id=DOMParsingTools.getIntAttribute(attrs,ContainerXMLConstants.CONTAINER_ID_ATTR,0);
    ItemsContainer ret=new ItemsContainer(id);

    for(LootType lootType : LootType.values())
    {
      String tag=lootType.getTag();
      int tableId=DOMParsingTools.getIntAttribute(attrs,tag,0);
      if (tableId!=0)
      {
        LootTable table=_lootsMgr.getTables().getItem(tableId);
        ret.set(lootType,table);
      }
    }
    // Custom skirmish loot table
    int customSkirmishLootTableId=DOMParsingTools.getIntAttribute(attrs,ContainerXMLConstants.CUSTOM_SKIRMISH_LOOT_TABLE_ID_ATTR,0);
    if (customSkirmishLootTableId!=0)
    {
      ret.setCustomSkirmishLootTableId(Integer.valueOf(customSkirmishLootTableId));
    }
    return ret;
  }

  private RelicsContainer parseRelicContainer(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    int id=DOMParsingTools.getIntAttribute(attrs,ContainerXMLConstants.CONTAINER_ID_ATTR,0);
    RelicsContainer ret=new RelicsContainer(id);

    // Relic
    Relic relic=null;
    int relicId=DOMParsingTools.getIntAttribute(attrs,ContainerXMLConstants.RELIC_ID_ATTR,0);
    if (relicId!=0)
    {
      relic=RelicsManager.getInstance().getById(relicId);
      ret.setRelic(relic);
    }
    // Relics list
    RelicsList relicsList=null;
    int relicsListId=DOMParsingTools.getIntAttribute(attrs,ContainerXMLConstants.RELICS_LIST_ID_ATTR,0);
    if (relicsListId!=0)
    {
      relicsList=_lootsMgr.getRelicsLists().getItem(relicsListId);
      ret.setRelicsList(relicsList);
    }
    return ret;
  }
}
