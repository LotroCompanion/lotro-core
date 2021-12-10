package delta.games.lotro.character.storage.carryalls.io.xml;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.storage.carryalls.CarryAll;
import delta.games.lotro.character.storage.carryalls.CarryAllDefinition;
import delta.games.lotro.character.storage.carryalls.CarryAllsDefinitionsManager;
import delta.games.lotro.character.storage.carryalls.CarryAllsManager;
import delta.games.lotro.common.id.InternalGameId;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemsManager;

/**
 * Parser for the carry-alls stored in XML.
 * @author DAM
 */
public class CarryAllsXMLParser
{
  private static final Logger LOGGER=Logger.getLogger(CarryAllsXMLParser.class);

  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed carry-alls.
   */
  public CarryAllsManager parseXML(File source)
  {
    CarryAllsManager ret=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      ret=new CarryAllsManager();
      List<Element> carryAllTags=DOMParsingTools.getChildTagsByName(root,CarryAllsXMLConstants.CARRY_ALL);
      for(Element carryAllTag : carryAllTags)
      {
        CarryAll carryAll=parseCarryAll(carryAllTag);
        ret.addCarryAll(carryAll);
      }
    }
    return ret;
  }

  private CarryAll parseCarryAll(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    // ID
    int definitionID=DOMParsingTools.getIntAttribute(attrs,CarryAllsXMLConstants.CARRY_ALL_DEFINITION_ID_ATTR,0);
    CarryAllsDefinitionsManager mgr=CarryAllsDefinitionsManager.getInstance();
    CarryAllDefinition definition=mgr.getCarryAll(definitionID);
    CarryAll ret=new CarryAll(definition); 
    // Instance ID
    String idStr=DOMParsingTools.getStringAttribute(attrs,CarryAllsXMLConstants.CARRY_ALL_ID_ATTR,null);
    if (idStr!=null)
    {
      InternalGameId id=InternalGameId.fromString(idStr);
      ret.setInstanceId(id);
    }
    List<Element> itemTags=DOMParsingTools.getChildTagsByName(root,CarryAllsXMLConstants.ITEM_TAG,false);
    for(Element itemTag : itemTags)
    {
      NamedNodeMap itemAttrs=itemTag.getAttributes();
      // ID
      int itemID=DOMParsingTools.getIntAttribute(itemAttrs,CarryAllsXMLConstants.ITEM_ID_ATTR,0);
      Item item=ItemsManager.getInstance().getItem(itemID);
      if (item!=null)
      {
        // Count
        int count=DOMParsingTools.getIntAttribute(itemAttrs,CarryAllsXMLConstants.ITEM_COUNT_ATTR,0);
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
