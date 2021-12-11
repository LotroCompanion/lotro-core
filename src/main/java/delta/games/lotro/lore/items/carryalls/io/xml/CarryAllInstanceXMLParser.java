package delta.games.lotro.lore.items.carryalls.io.xml;

import java.util.List;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemsManager;
import delta.games.lotro.lore.items.carryalls.CarryAllInstance;

/**
 * Parser for the carry-alls stored in XML.
 * @author DAM
 */
public class CarryAllInstanceXMLParser
{
  private static final Logger LOGGER=Logger.getLogger(CarryAllInstanceXMLParser.class);

  /**
   * Read carry-all instance attributes for an item.
   * @param carryAllInstance Data to write to.
   * @param itemElement Root XML tag.
   */
  public static void read(CarryAllInstance carryAllInstance, Element itemElement)
  {
    Element carryAllTag=DOMParsingTools.getChildTagByName(itemElement,CarryAllInstanceXMLConstants.CARRY_ALL);
    if (carryAllTag!=null)
    {
      List<Element> itemTags=DOMParsingTools.getChildTagsByName(carryAllTag,CarryAllInstanceXMLConstants.ITEM_TAG,false);
      for(Element itemTag : itemTags)
      {
        NamedNodeMap itemAttrs=itemTag.getAttributes();
        // ID
        int itemID=DOMParsingTools.getIntAttribute(itemAttrs,CarryAllInstanceXMLConstants.ITEM_ID_ATTR,0);
        Item item=ItemsManager.getInstance().getItem(itemID);
        if (item!=null)
        {
          // Count
          int count=DOMParsingTools.getIntAttribute(itemAttrs,CarryAllInstanceXMLConstants.ITEM_COUNT_ATTR,0);
          carryAllInstance.addItem(item,count);
        }
        else
        {
          LOGGER.warn("Item not found: "+itemID);
        }
      }
    }
  }
}
