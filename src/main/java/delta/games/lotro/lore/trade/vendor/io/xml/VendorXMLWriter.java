package delta.games.lotro.lore.trade.vendor.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.lore.agents.npcs.NpcDescription;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.trade.vendor.SellList;
import delta.games.lotro.lore.trade.vendor.VendorNpc;
import delta.games.lotro.utils.Proxy;
import delta.games.lotro.utils.io.xml.SharedXMLUtils;

/**
 * Writes vendors to XML files.
 * @author DAM
 */
public class VendorXMLWriter
{
  /**
   * Write a file with vendors.
   * @param toFile Output file.
   * @param vendors Vendors to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeVendorsFile(File toFile, final List<VendorNpc> vendors)
  {
    Collections.sort(vendors,new IdentifiableComparator<VendorNpc>());
    XmlWriter xmlWriter=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",VendorXMLConstants.VENDORS_TAG,new AttributesImpl());
        writeVendors(hd,vendors);
        hd.endElement("","",VendorXMLConstants.VENDORS_TAG);
      }
    };
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,xmlWriter);
    return ret;
  }

  /**
   * Write vendors to a XML file.
   * @param hd Output.
   * @param vendors Vendors to write.
   * @throws Exception if an error occurs.
   */
  private static void writeVendors(TransformerHandler hd, final List<VendorNpc> vendors) throws Exception
  {
    Map<Integer,SellList> sellListsMap=new HashMap<Integer,SellList>();
    for(VendorNpc vendor : vendors)
    {
      for(SellList sellList : vendor.getSellLists())
      {
        sellListsMap.put(Integer.valueOf(sellList.getIdentifier()),sellList);
      }
      writeVendor(hd,vendor);
    }
    List<SellList> sellLists=new ArrayList<SellList>(sellListsMap.values());
    Collections.sort(sellLists,new IdentifiableComparator<SellList>());
    for(SellList sellList : sellLists)
    {
      writeSellList(hd,sellList);
    }
  }

  private static void writeVendor(TransformerHandler hd, VendorNpc barterer) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();

    // Identifier
    int id=barterer.getIdentifier();
    attrs.addAttribute("","",VendorXMLConstants.VENDOR_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // NPC
    NpcDescription npc=barterer.getNpc();
    // Name
    String name=npc.getName();
    attrs.addAttribute("","",VendorXMLConstants.VENDOR_NAME_ATTR,XmlWriter.CDATA,name);
    // Title
    String title=npc.getTitle();
    if (title.length()>0)
    {
      attrs.addAttribute("","",VendorXMLConstants.VENDOR_TITLE_ATTR,XmlWriter.CDATA,title);
    }
    // Buys
    boolean buys=barterer.buys();
    if (buys)
    {
      attrs.addAttribute("","",VendorXMLConstants.VENDOR_BUYS_ATTR,XmlWriter.CDATA,String.valueOf(buys));
    }
    // Sells wearable items
    boolean sellsWearableItems=barterer.sellsWearableItems();
    if (sellsWearableItems)
    {
      attrs.addAttribute("","",VendorXMLConstants.VENDOR_SELLS_WEARABLE_ITEMS_ATTR,XmlWriter.CDATA,String.valueOf(sellsWearableItems));
    }
    // Sell factor
    float sellFactor=barterer.getSellFactor();
    attrs.addAttribute("","",VendorXMLConstants.VENDOR_SELL_FACTOR_ATTR,XmlWriter.CDATA,String.valueOf(sellFactor));
    hd.startElement("","",VendorXMLConstants.VENDOR_TAG,attrs);
    // Discounts
    for(Integer discountId : barterer.getDiscounts())
    {
      AttributesImpl discountAttrs=new AttributesImpl();
      discountAttrs.addAttribute("","",VendorXMLConstants.DISCOUNT_ID_ATTR,XmlWriter.CDATA,discountId.toString());
      hd.startElement("","",VendorXMLConstants.DISCOUNT_TAG,discountAttrs);
      hd.endElement("","",VendorXMLConstants.DISCOUNT_TAG);
    }
    // Sell lists
    for(SellList sellList : barterer.getSellLists())
    {
      AttributesImpl sellListAttrs=new AttributesImpl();
      // Identifier
      int sellListId=sellList.getIdentifier();
      sellListAttrs.addAttribute("","",VendorXMLConstants.SELL_LIST_ID,XmlWriter.CDATA,String.valueOf(sellListId));
      hd.startElement("","",VendorXMLConstants.SELL_LIST_TAG,sellListAttrs);
      hd.endElement("","",VendorXMLConstants.SELL_LIST_TAG);
    }
    hd.endElement("","",VendorXMLConstants.VENDOR_TAG);
  }

  private static void writeSellList(TransformerHandler hd, SellList sellList) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();

    // Identifier
    int id=sellList.getIdentifier();
    attrs.addAttribute("","",VendorXMLConstants.SELL_LIST_ID,XmlWriter.CDATA,String.valueOf(id));
    hd.startElement("","",VendorXMLConstants.SELL_LIST_TAG,attrs);
    // Entries
    for(Proxy<Item> entry : sellList.getItems())
    {
      SharedXMLUtils.writeProxy(hd,VendorXMLConstants.SELL_ENTRY_TAG,entry);
    }
    hd.endElement("","",VendorXMLConstants.SELL_LIST_TAG);
  }
}
