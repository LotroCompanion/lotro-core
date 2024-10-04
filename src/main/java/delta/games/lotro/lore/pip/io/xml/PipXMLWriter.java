package delta.games.lotro.lore.pip.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.lore.pip.PipDescription;

/**
 * Writes pips to XML files.
 * @author DAM
 */
public class PipXMLWriter
{
  /**
   * Write a file with pips.
   * @param toFile Output file.
   * @param pips Pips to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writePipsFile(File toFile, List<PipDescription> pips)
  {
    PipXMLWriter writer=new PipXMLWriter();
    boolean ok=writer.writePips(toFile,pips,EncodingNames.UTF_8);
    return ok;
  }

  /**
   * Write pips to a XML file.
   * @param outFile Output file.
   * @param pips Pips to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  private boolean writePips(File outFile, final List<PipDescription> pips, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writePips(hd,pips);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void writePips(TransformerHandler hd, List<PipDescription> pips) throws SAXException
  {
    hd.startElement("","",PipXMLConstants.PIPS_TAG,new AttributesImpl());
    for(PipDescription pip : pips)
    {
      writePip(hd,pip);
    }
    hd.endElement("","",PipXMLConstants.PIPS_TAG);
  }

  private void writePip(TransformerHandler hd, PipDescription pip) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Type
    int type=pip.getIdentifier();
    attrs.addAttribute("","",PipXMLConstants.PIP_TYPE_ATTR,XmlWriter.CDATA,String.valueOf(type));
    // Name
    String name=pip.getName();
    attrs.addAttribute("","",PipXMLConstants.PIP_NAME_ATTR,XmlWriter.CDATA,name);
    // Min
    int min=pip.getMin();
    attrs.addAttribute("","",PipXMLConstants.PIP_MIN_ATTR,XmlWriter.CDATA,String.valueOf(min));
    // Max
    int max=pip.getMax();
    attrs.addAttribute("","",PipXMLConstants.PIP_MAX_ATTR,XmlWriter.CDATA,String.valueOf(max));
    // Home
    int home=pip.getHome();
    attrs.addAttribute("","",PipXMLConstants.PIP_HOME_ATTR,XmlWriter.CDATA,String.valueOf(home));
    // Min icon
    Integer minIconId=pip.getIconMin();
    if (minIconId!=null)
    {
      attrs.addAttribute("","",PipXMLConstants.PIP_MIN_ICON_ID_ATTR,XmlWriter.CDATA,minIconId.toString());
    }
    // Max
    Integer maxIconId=pip.getIconMax();
    if (maxIconId!=null)
    {
      attrs.addAttribute("","",PipXMLConstants.PIP_MAX_ICON_ID_ATTR,XmlWriter.CDATA,maxIconId.toString());
    }
    // Home
    Integer homeIconId=pip.getIconHome();
    if (homeIconId!=null)
    {
      attrs.addAttribute("","",PipXMLConstants.PIP_HOME_ICON_ID_ATTR,XmlWriter.CDATA,homeIconId.toString());
    }
    hd.startElement("","",PipXMLConstants.PIP_TAG,attrs);
    hd.endElement("","",PipXMLConstants.PIP_TAG);
  }
}
