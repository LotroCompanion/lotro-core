package delta.games.lotro.character.cosmetics.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.cosmetics.Outfit;
import delta.games.lotro.character.cosmetics.OutfitElement;
import delta.games.lotro.character.cosmetics.OutfitsManager;
import delta.games.lotro.character.gear.GearSlot;
import delta.games.lotro.common.colors.ColorDescription;
import delta.games.lotro.common.colors.ColorsManager;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemsManager;

/**
 * Parser for outfits stored in XML.
 * @author DAM
 */
public class OutfitsXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return the loaded friends or <code>null</code>.
   */
  public OutfitsManager parseXML(File source)
  {
    OutfitsManager ret=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      ret=parseOutfits(root);
    }
    return ret;
  }

  private static OutfitsManager parseOutfits(Element mainTag)
  {
    OutfitsManager ret=new OutfitsManager();
    // Outfits
    List<Element> outfitTags=DOMParsingTools.getChildTagsByName(mainTag,OutfitsXMLConstants.OUTFIT_TAG,false);
    for(Element outfitTag : outfitTags)
    {
      parseOutfit(ret,outfitTag);
    }
    // Current
    NamedNodeMap attrs=mainTag.getAttributes();
    // Index
    int currentIndex=DOMParsingTools.getIntAttribute(attrs,OutfitsXMLConstants.OUTFITS_CURRENT_INDEX_ATTR,0);
    ret.setCurrentOutfitIndex(currentIndex);
    return ret;
  }

  private static void parseOutfit(OutfitsManager outfitsMgr, Element outfitTag)
  {
    Outfit ret=new Outfit();
    NamedNodeMap attrs=outfitTag.getAttributes();
    // Index
    int index=DOMParsingTools.getIntAttribute(attrs,OutfitsXMLConstants.OUTFIT_INDEX_ATTR,0);
    // Elements
    List<Element> elementTags=DOMParsingTools.getChildTagsByName(outfitTag,OutfitsXMLConstants.ELEMENT_TAG);
    for(Element elementTag : elementTags)
    {
      NamedNodeMap elementAttrs=elementTag.getAttributes();
      // Visible
      boolean visible=DOMParsingTools.getBooleanAttribute(elementAttrs,OutfitsXMLConstants.ELEMENT_VISIBLE_ATTR,true);
      // Slot
      String slotName=DOMParsingTools.getStringAttribute(elementAttrs,OutfitsXMLConstants.ELEMENT_SLOT_ATTR,"");
      GearSlot slot=GearSlot.getByKey(slotName);
      // Element
      OutfitElement element=parseOutfitElement(elementTag);
      if ((slot!=null) && (element!=null))
      {
        ret.setSlot(slot,element);
        ret.setSlotVisible(slot,visible);
      }
    }
    outfitsMgr.addOutfit(index,ret);
  }

  /**
   * Read an outfit element from a tag.
   * @param elementTag Tag to read.
   * @return the loaded element or <code>null</code>.
   */
  private static OutfitElement parseOutfitElement(Element elementTag)
  {
    NamedNodeMap attrs=elementTag.getAttributes();
    // Item ID
    int itemId=DOMParsingTools.getIntAttribute(attrs,OutfitsXMLConstants.ELEMENT_ITEM_ID_ATTR,0);
    Item item=ItemsManager.getInstance().getItem(itemId);
    if (item==null)
    {
      return null;
    }
    OutfitElement ret=new OutfitElement();
    ret.setItem(item);
    // Color
    int colorCode=DOMParsingTools.getIntAttribute(attrs,OutfitsXMLConstants.ELEMENT_COLOR_CODE,0);
    ColorsManager colorsMgr=ColorsManager.getInstance();
    ColorDescription color=colorsMgr.getColor(colorCode);
    ret.setColor(color);
    return ret;
  }
}
