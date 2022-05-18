package delta.games.lotro.character.log.io.xml;

import java.io.File;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.log.CharacterLog;
import delta.games.lotro.character.log.CharacterLogItem;
import delta.games.lotro.character.log.CharacterLogItem.LogItemType;

/**
 * Writes LOTRO character log to XML documents.
 * @author DAM
 */
public class CharacterLogXMLWriter
{
  /**
   * Write a character log to a XML file.
   * @param outFile Output file.
   * @param log Character log to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final CharacterLog log, String encoding)
  {
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        write(hd,log);
      }
    };
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }
  
  private void write(TransformerHandler hd, CharacterLog log) throws SAXException
  {
    AttributesImpl questAttrs=new AttributesImpl();

    String name=log.getName();
    if (name!=null)
    {
      questAttrs.addAttribute("","",CharacterLogXMLConstants.CHARACTER_LOG_NAME_ATTR,XmlWriter.CDATA,name);
    }
    hd.startElement("","",CharacterLogXMLConstants.CHARACTER_LOG_TAG,questAttrs);
    int nb=log.getNbItems();
    for(int i=0;i<nb;i++)
    {
      CharacterLogItem item=log.getLogItem(i);
      writeLogItem(hd,item);
    }
    hd.endElement("","",CharacterLogXMLConstants.CHARACTER_LOG_TAG);
  }

  private void writeLogItem(TransformerHandler hd, CharacterLogItem item) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    long date=item.getDate();
    attrs.addAttribute("","",CharacterLogXMLConstants.LOG_ITEM_DATE_ATTR,XmlWriter.CDATA,String.valueOf(date));
    LogItemType type=item.getLogItemType();
    if (type!=null)
    {
      attrs.addAttribute("","",CharacterLogXMLConstants.LOG_ITEM_TYPE_ATTR,XmlWriter.CDATA,type.name());
    }
    String label=item.getLabel();
    if (label!=null)
    {
      attrs.addAttribute("","",CharacterLogXMLConstants.LOG_ITEM_LABEL_ATTR,XmlWriter.CDATA,label);
    }
    String url=item.getAssociatedUrl();
    if (url!=null)
    {
      attrs.addAttribute("","",CharacterLogXMLConstants.LOG_ITEM_URL_ATTR,XmlWriter.CDATA,url);
    }
    hd.startElement("","",CharacterLogXMLConstants.LOG_ITEM_TAG,attrs);
    hd.endElement("","",CharacterLogXMLConstants.LOG_ITEM_TAG);
  }
}
