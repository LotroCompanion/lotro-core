package delta.games.lotro.common.enums.io.xml;

import java.io.File;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumEntry;

/**
 * Writes enums to XML files.
 * @param <T> Type of entries.
 * @author DAM
 */
public class EnumXMLWriter<T extends LotroEnumEntry>
{
  /**
   * Write an enum to a XML file.
   * @param outFile Output file.
   * @param lotroEnum Enum to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean writeEnum(File outFile, final LotroEnum<T> lotroEnum)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeEnum(hd,lotroEnum);
      }
    };
    boolean ret=helper.write(outFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private void writeEnum(TransformerHandler hd, LotroEnum<T> lotroEnum) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    // Identifier
    int identifier=lotroEnum.getIdentifier();
    attrs.addAttribute("","",EnumXMLConstants.ENUM_ID_ATTR,XmlWriter.CDATA,String.valueOf(identifier));
    // Name
    String name=lotroEnum.getName();
    attrs.addAttribute("","",EnumXMLConstants.ENUM_NAME_ATTR,XmlWriter.CDATA,name);
    hd.startElement("","",EnumXMLConstants.ENUM_TAG,attrs);
    for(T entry : lotroEnum.getAll())
    {
      writeEntry(hd,entry);
    }
    hd.endElement("","",EnumXMLConstants.ENUM_TAG);
  }

  private void writeEntry(TransformerHandler hd, T entry) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();

    // Code
    int code=entry.getCode();
    attrs.addAttribute("","",EnumXMLConstants.ENTRY_CODE_ATTR,XmlWriter.CDATA,String.valueOf(code));
    // Key
    String key=entry.getKey();
    if (key!=null)
    {
      attrs.addAttribute("","",EnumXMLConstants.ENTRY_KEY_ATTR,XmlWriter.CDATA,key);
    }
    // Name
    String name=entry.getLabel();
    attrs.addAttribute("","",EnumXMLConstants.ENTRY_NAME_ATTR,XmlWriter.CDATA,name);
    hd.startElement("","",EnumXMLConstants.ENTRY_TAG,attrs);
    hd.endElement("","",EnumXMLConstants.ENTRY_TAG);
  }
}
