package delta.games.lotro.common.enums.directory.io.xml;

import java.io.File;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.common.enums.directory.LotroEnumDescription;
import delta.games.lotro.common.enums.directory.LotroEnumsDirectory;

/**
 * Writes the enums directory to an XML file.
 * @author DAM
 */
public class EnumsDirectoryXMLWriter
{
  /**
   * Write the enums directory to a XML file.
   * @param outFile Output file.
   * @param directory Directory to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean writeEnumsDirectory(File outFile, final LotroEnumsDirectory directory)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeEnumsDirectory(hd,directory);
      }
    };
    boolean ret=helper.write(outFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private void writeEnumsDirectory(TransformerHandler hd, LotroEnumsDirectory directory) throws SAXException
  {
    hd.startElement("","",EnumsDirectoryXMLConstants.ENUMS_DIRECTORY_TAG,new AttributesImpl());
    for(LotroEnumDescription description : directory.getAll())
    {
      AttributesImpl attrs=new AttributesImpl();
      // Identifier
      int identifier=description.getIdentifier();
      attrs.addAttribute("","",EnumsDirectoryXMLConstants.ENUM_ID_ATTR,XmlWriter.CDATA,String.valueOf(identifier));
      // Name
      String name=description.getName();
      attrs.addAttribute("","",EnumsDirectoryXMLConstants.ENUM_NAME_ATTR,XmlWriter.CDATA,name);
      // Class name
      String className=description.getEntryClassName();
      attrs.addAttribute("","",EnumsDirectoryXMLConstants.ENUM_CLASSNAME_ATTR,XmlWriter.CDATA,className);
      hd.startElement("","",EnumsDirectoryXMLConstants.ENUM_TAG,attrs);
      hd.endElement("","",EnumsDirectoryXMLConstants.ENUM_TAG);
    }
    hd.endElement("","",EnumsDirectoryXMLConstants.ENUMS_DIRECTORY_TAG);
  }
}
