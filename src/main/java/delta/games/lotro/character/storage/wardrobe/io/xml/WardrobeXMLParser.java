package delta.games.lotro.character.storage.wardrobe.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.storage.wardrobe.Wardrobe;
import delta.games.lotro.character.storage.wardrobe.WardrobeItem;
import delta.games.lotro.common.colors.ColorDescription;
import delta.games.lotro.common.colors.ColorsManager;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemsManager;

/**
 * Parser for wardrobes stored in XML.
 * @author DAM
 */
public class WardrobeXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return the loaded wardrobe or <code>null</code>.
   */
  public Wardrobe parseXML(File source)
  {
    Wardrobe ret=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      ret=parseWardrobe(root);
    }
    return ret;
  }

  private static Wardrobe parseWardrobe(Element mainTag)
  {
    Wardrobe ret=new Wardrobe();
    List<Element> elementTags=DOMParsingTools.getChildTagsByName(mainTag,WardrobeXMLConstants.WARDROBE_ITEM_TAG,false);
    for(Element elementTag : elementTags)
    {
      WardrobeItem wardrobeItem=parseWardrobeElement(elementTag);
      if (wardrobeItem!=null)
      {
        ret.addItem(wardrobeItem);
      }
    }
    return ret;
  }

  /**
   * Read a wardrobe item from a tag.
   * @param elementTag Tag to read.
   * @return the loaded wardrobe item or <code>null</code>.
   */
  private static WardrobeItem parseWardrobeElement(Element elementTag)
  {
    NamedNodeMap attrs=elementTag.getAttributes();
    // Item ID
    int itemId=DOMParsingTools.getIntAttribute(attrs,WardrobeXMLConstants.ELEMENT_ITEM_ID_ATTR,0);
    Item item=ItemsManager.getInstance().getItem(itemId);
    if (item==null)
    {
      return null;
    }
    // Color
    int colorCode=DOMParsingTools.getIntAttribute(attrs,WardrobeXMLConstants.ELEMENT_COLOR_CODE,0);
    ColorsManager colorsMgr=ColorsManager.getInstance();
    ColorDescription color=colorsMgr.getColor(colorCode);
    if (color==null)
    {
      return null;
    }
    WardrobeItem wardrobeItem=new WardrobeItem(item,color);
    return wardrobeItem;
  }
}
