package delta.games.lotro.lore.collections.baubles.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.skills.SkillDescription;

/**
 * Writes the baubles directory to XML files.
 * @author DAM
 */
public class BaublesXMLWriter
{
  /**
   * Write the baubles directory to a XML file.
   * @param toFile File to write to.
   * @param baubles Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean write(File toFile, final List<SkillDescription> baubles)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",BaublesXMLConstants.BAUBLES_TAG,new AttributesImpl());
        for(SkillDescription bauble : baubles)
        {
          writeBauble(hd,bauble);
        }
        hd.endElement("","",BaublesXMLConstants.BAUBLES_TAG);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private static void writeBauble(TransformerHandler hd, SkillDescription bauble) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Identifier
    int id=bauble.getIdentifier();
    attrs.addAttribute("","",BaublesXMLConstants.IDENTIFIER_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Name
    String name=bauble.getName();
    attrs.addAttribute("","",BaublesXMLConstants.NAME_ATTR,XmlWriter.CDATA,name);
    hd.startElement("","",BaublesXMLConstants.BAUBLE_TAG,attrs);
    hd.endElement("","",BaublesXMLConstants.BAUBLE_TAG);
  }
}
