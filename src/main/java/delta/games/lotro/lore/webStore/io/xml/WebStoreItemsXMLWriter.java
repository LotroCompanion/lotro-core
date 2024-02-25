package delta.games.lotro.lore.webStore.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.common.enums.BillingGroup;
import delta.games.lotro.lore.webStore.WebStoreItem;

/**
 * Writes some web store items to an XML document.
 * @author DAM
 */
public class WebStoreItemsXMLWriter
{
  /**
   * Write some web store items to a XML file.
   * @param outFile Output file.
   * @param data Data to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final List<WebStoreItem> data, String encoding)
  {
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        AttributesImpl attrs=new AttributesImpl();
        hd.startElement("","",WebStoreItemsXMLConstants.WEB_STORE_ITEMS_TAG,attrs);
        for(WebStoreItem element : data)
        {
          writeWebStoreItem(hd,element);
        }
        hd.endElement("","",WebStoreItemsXMLConstants.WEB_STORE_ITEMS_TAG);
      }
    };
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private static void writeWebStoreItem(TransformerHandler hd, WebStoreItem webStoreItem) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Identifier
    int id=webStoreItem.getIdentifier();
    attrs.addAttribute("","",WebStoreItemsXMLConstants.WEB_STORE_ITEM_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Name
    String name=webStoreItem.getName();
    if (name.length()>0)
    {
      attrs.addAttribute("","",WebStoreItemsXMLConstants.WEB_STORE_ITEM_NAME_ATTR,XmlWriter.CDATA,name);
    }
    // Free for subscribers
    boolean freeForSubscribers=webStoreItem.isFreeForSubscribers();
    if (freeForSubscribers)
    {
      attrs.addAttribute("","",WebStoreItemsXMLConstants.WEB_STORE_ITEM_FREE_FOR_SUBSCRIBERS_ATTR,XmlWriter.CDATA,String.valueOf(freeForSubscribers));
    }
    // Billing token
    BillingGroup billingToken=webStoreItem.getBillingToken();
    if (billingToken!=null)
    {
      attrs.addAttribute("","",WebStoreItemsXMLConstants.WEB_STORE_ITEM_BILLING_GROUP_ATTR,XmlWriter.CDATA,String.valueOf(billingToken.getCode()));
    }
    // Item ID
    int itemID=webStoreItem.getItemID();
    if (itemID!=0)
    {
      attrs.addAttribute("","",WebStoreItemsXMLConstants.WEB_STORE_ITEM_ITEM_ID_ATTR,XmlWriter.CDATA,String.valueOf(itemID));
    }
    // SKU
    String sku=webStoreItem.getSku();
    if (sku.length()>0)
    {
      attrs.addAttribute("","",WebStoreItemsXMLConstants.WEB_STORE_ITEM_SKU_ATTR,XmlWriter.CDATA,sku);
    }
    // Short name
    String shortName=webStoreItem.getShortName();
    if (shortName.length()>0)
    {
      attrs.addAttribute("","",WebStoreItemsXMLConstants.WEB_STORE_ITEM_SHORT_NAME_ATTR,XmlWriter.CDATA,shortName);
    }
    hd.startElement("","",WebStoreItemsXMLConstants.WEB_STORE_ITEM_TAG,attrs);
    hd.endElement("","",WebStoreItemsXMLConstants.WEB_STORE_ITEM_TAG);
  }
}
