package delta.games.lotro.character.storage.bags.io.xml;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.storage.bags.BagsManager;
import delta.games.lotro.lore.items.CountedItem;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemInstance;
import delta.games.lotro.lore.items.io.xml.ItemXMLConstants;
import delta.games.lotro.lore.items.io.xml.ItemXMLParser;

/**
 * Parser for the bags stored in XML.
 * @author DAM
 */
public class BagsXMLParser
{
  private static final Logger LOGGER=Logger.getLogger(BagsXMLParser.class);

  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed bags or <code>null</code>.
   */
  public BagsManager parseXML(File source)
  {
    BagsManager bags=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      bags=parseBags(root);
    }
    return bags;
  }

  private BagsManager parseBags(Element root)
  {
    BagsManager bags=new BagsManager();
    // Max
    int max=DOMParsingTools.getIntAttribute(root.getAttributes(),BagsXMLConstants.BAGS_MAX_ATTR,0);
    bags.setCapacity(max);
    // Slots
    List<Element> slotTags=DOMParsingTools.getChildTagsByName(root,BagsXMLConstants.SLOT_TAG,false);
    for(Element slotTag : slotTags)
    {
      parseBagSlot(bags,slotTag);
    }
    return bags;
  }

  private void parseBagSlot(BagsManager bags, Element slotTag)
  {
    NamedNodeMap attrs=slotTag.getAttributes();
    int index=DOMParsingTools.getIntAttribute(attrs,BagsXMLConstants.SLOT_INDEX_ATTR,-1);
    if (index==-1)
    {
      // No index!
      LOGGER.warn("No index!");
      return;
    }
    int count=DOMParsingTools.getIntAttribute(attrs,BagsXMLConstants.SLOT_COUNT_ATTR,1);
    ItemXMLParser parser=new ItemXMLParser();
    Element itemTag=DOMParsingTools.getChildTagByName(slotTag,ItemXMLConstants.ITEM_TAG);
    if (itemTag!=null)
    {
      ItemInstance<? extends Item> item=parser.parseItemInstance(itemTag);
      if (item!=null)
      {
        CountedItem<ItemInstance<? extends Item>> countedItemInstance=new CountedItem<ItemInstance<? extends Item>>(item,count);
        bags.addBagItem(countedItemInstance,index);
      }
    }
  }
}
