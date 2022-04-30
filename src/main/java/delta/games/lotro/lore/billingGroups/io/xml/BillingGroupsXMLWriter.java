package delta.games.lotro.lore.billingGroups.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.common.enums.BillingGroup;
import delta.games.lotro.lore.billingGroups.BillingGroupDescription;
import delta.games.lotro.lore.titles.TitleDescription;

/**
 * Writes some billing groups to an XML document.
 * @author DAM
 */
public class BillingGroupsXMLWriter
{
  /**
   * Write some billing groups to a XML file.
   * @param outFile Output file.
   * @param data Data to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final List<BillingGroupDescription> data, String encoding)
  {
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        AttributesImpl attrs=new AttributesImpl();
        hd.startElement("","",BillingGroupsXMLConstants.BILLING_GROUPS_TAG,attrs);
        for(BillingGroupDescription element : data)
        {
          writeBillingGroup(hd,element);
        }
        hd.endElement("","",BillingGroupsXMLConstants.BILLING_GROUPS_TAG);
      }
    };
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private static void writeBillingGroup(TransformerHandler hd, BillingGroupDescription billingGroupDescription) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    // Code
    BillingGroup id=billingGroupDescription.getId();
    int code=id.getCode();
    attrs.addAttribute("","",BillingGroupsXMLConstants.BILLING_GROUP_ID_ATTR,XmlWriter.CDATA,String.valueOf(code));
    // Name
    String name=id.getLabel();
    if (name!=null)
    {
      attrs.addAttribute("","",BillingGroupsXMLConstants.BILLING_GROUP_NAME_ATTR,XmlWriter.CDATA,name);
    }
    hd.startElement("","",BillingGroupsXMLConstants.BILLING_GROUP_TAG,attrs);
    // Titles
    List<TitleDescription> titles=billingGroupDescription.getAccountTitles();
    for(TitleDescription title : titles)
    {
      AttributesImpl titleAttrs=new AttributesImpl();
      // Title ID
      int titleID=title.getIdentifier();
      titleAttrs.addAttribute("","",BillingGroupsXMLConstants.TITLE_ID_ATTR,XmlWriter.CDATA,String.valueOf(titleID));
      // Title name
      String titleName=title.getName();
      if (titleName!=null)
      {
        titleAttrs.addAttribute("","",BillingGroupsXMLConstants.TITLE_NAME_ATTR,XmlWriter.CDATA,titleName);
      }
      hd.startElement("","",BillingGroupsXMLConstants.TITLE_TAG,titleAttrs);
      hd.endElement("","",BillingGroupsXMLConstants.TITLE_TAG);
    }
    hd.endElement("","",BillingGroupsXMLConstants.BILLING_GROUP_TAG);
  }
}
