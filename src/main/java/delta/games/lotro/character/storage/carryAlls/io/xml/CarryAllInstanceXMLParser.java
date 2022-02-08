package delta.games.lotro.character.storage.carryAlls.io.xml;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.storage.carryAlls.CarryAllInstance;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemsManager;
import delta.games.lotro.lore.items.carryalls.CarryAll;

/**
 * Parser for the carry-alls stored in XML.
 * @author DAM
 */
public class CarryAllInstanceXMLParser
{
  private static final Logger LOGGER=Logger.getLogger(CarryAllInstanceXMLParser.class);

  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed carry-all or <code>null</code>.
   */
  public static CarryAllInstance parseXML(File source)
  {
    CarryAllInstance ret=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      ret=read(root);
    }
    return ret;
  }

  /**
   * Read a carry-all instance from the given tag.
   * @param rootTag Root XML tag.
   * @return the loaded carry-all or <code>null</code>.
   */
  private static CarryAllInstance read(Element rootTag)
  {
    CarryAllInstance ret=new CarryAllInstance();
    // Reference item ID
    int referenceItemID=DOMParsingTools.getIntAttribute(rootTag.getAttributes(),CarryAllInstanceXMLConstants.CARRY_ALL_ITEM_ID_TAG,0);
    Item referenceItem=ItemsManager.getInstance().getItem(referenceItemID);
    if (referenceItem instanceof CarryAll)
    {
      ret.setReference((CarryAll)referenceItem);
    }
    // Items
    List<Element> itemTags=DOMParsingTools.getChildTagsByName(rootTag,CarryAllInstanceXMLConstants.ITEM_TAG,false);
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
        ret.addItem(item,count);
      }
      else
      {
        LOGGER.warn("Item not found: "+itemID);
      }
    }
    return ret;
  }
}
