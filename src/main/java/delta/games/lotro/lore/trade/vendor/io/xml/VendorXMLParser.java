package delta.games.lotro.lore.trade.vendor.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.lore.agents.npcs.NPCsManager;
import delta.games.lotro.lore.agents.npcs.NpcDescription;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.trade.vendor.SellList;
import delta.games.lotro.lore.trade.vendor.VendorNpc;
import delta.games.lotro.utils.Proxy;
import delta.games.lotro.utils.io.xml.SharedXMLUtils;

/**
 * Parser for vendor data stored in XML.
 * @author DAM
 */
public class VendorXMLParser
{
  private static final Logger LOGGER=Logger.getLogger(VendorXMLParser.class);

  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed data.
   */
  public List<VendorNpc> parseXML(File source)
  {
    List<VendorNpc> ret=new ArrayList<VendorNpc>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      Map<Integer,SellList> sellLists=new HashMap<Integer,SellList>();
      List<Element> sellListTags=DOMParsingTools.getChildTagsByName(root,VendorXMLConstants.SELL_LIST_TAG,false);
      for(Element sellListTag : sellListTags)
      {
        SellList sellList=parseSellList(sellListTag);
        sellLists.put(Integer.valueOf(sellList.getIdentifier()),sellList);
      }
      List<Element> vendorTags=DOMParsingTools.getChildTagsByName(root,VendorXMLConstants.VENDOR_TAG);
      for(Element vendorTag : vendorTags)
      {
        VendorNpc vendor=parseVendor(vendorTag,sellLists);
        if (vendor!=null)
        {
          ret.add(vendor);
        }
      }
    }
    return ret;
  }

  private VendorNpc parseVendor(Element root, Map<Integer,SellList> sellLists)
  {
    NamedNodeMap attrs=root.getAttributes();
    // NPC
    // - Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,VendorXMLConstants.VENDOR_ID_ATTR,0);
    NpcDescription npc=NPCsManager.getInstance().getNPCById(id);
    if (npc==null)
    {
      LOGGER.warn("NPC not found: ID="+id);
      return null;
    }
    VendorNpc ret=new VendorNpc(npc);
    // Buys
    boolean buys=DOMParsingTools.getBooleanAttribute(attrs,VendorXMLConstants.VENDOR_BUYS_ATTR,false);
    ret.setBuys(buys);
    // Sells wearable items
    boolean sellsWearableItems=DOMParsingTools.getBooleanAttribute(attrs,VendorXMLConstants.VENDOR_SELLS_WEARABLE_ITEMS_ATTR,false);
    ret.setSellsWearableItems(sellsWearableItems);
    // Sell factor
    float sellFactor=DOMParsingTools.getFloatAttribute(attrs,VendorXMLConstants.VENDOR_SELL_FACTOR_ATTR,1.0f);
    ret.setSellFactor(sellFactor);
    // Discounts
    List<Element> discountTags=DOMParsingTools.getChildTagsByName(root,VendorXMLConstants.DISCOUNT_TAG);
    for(Element discountTag : discountTags)
    {
      int discountId=DOMParsingTools.getIntAttribute(discountTag.getAttributes(),VendorXMLConstants.DISCOUNT_ID_ATTR,0);
      if (discountId!=0)
      {
        ret.addDiscount(discountId);
      }
    }
    // Sell lists
    List<Element> sellListTags=DOMParsingTools.getChildTagsByName(root,VendorXMLConstants.SELL_LIST_TAG);
    for(Element sellListTag : sellListTags)
    {
      int sellListId=DOMParsingTools.getIntAttribute(sellListTag.getAttributes(),VendorXMLConstants.SELL_LIST_ID,0);
      SellList sellList=sellLists.get(Integer.valueOf(sellListId));
      ret.addSellList(sellList);
    }
    return ret;
  }

  private SellList parseSellList(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,VendorXMLConstants.SELL_LIST_ID,0);
    SellList sellList=new SellList(id);
    // Entries
    List<Element> sellEntryTags=DOMParsingTools.getChildTagsByName(root,VendorXMLConstants.SELL_ENTRY_TAG);
    for(Element sellEntryTag : sellEntryTags)
    {
      Proxy<Item> item=SharedXMLUtils.parseItemProxy(sellEntryTag);
      if (item!=null)
      {
        sellList.addItem(item);
      }
    }
    return sellList;
  }
}
