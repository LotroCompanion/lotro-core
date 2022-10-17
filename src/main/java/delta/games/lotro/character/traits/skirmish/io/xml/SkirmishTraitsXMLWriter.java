package delta.games.lotro.character.traits.skirmish.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.traits.TraitDescription;

/**
 * Writes a skirmish traits directory to XML.
 * @author DAM
 */
public class SkirmishTraitsXMLWriter
{
  /**
   * Write the skirmish traits directory to a XML file.
   * @param toFile File to write to.
   * @param traits Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean write(File toFile, final List<TraitDescription> traits)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",SkirmishTraitsXMLConstants.SKIRMISH_TRAITS_TAG,new AttributesImpl());
        for(TraitDescription trait : traits)
        {
          writeTrait(hd,trait);
        }
        hd.endElement("","",SkirmishTraitsXMLConstants.SKIRMISH_TRAITS_TAG);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private static void writeTrait(TransformerHandler hd, TraitDescription trait) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Identifier
    int id=trait.getIdentifier();
    attrs.addAttribute("","",SkirmishTraitsXMLConstants.TRAIT_IDENTIFIER_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Name
    String name=trait.getName();
    attrs.addAttribute("","",SkirmishTraitsXMLConstants.TRAIT_NAME_ATTR,XmlWriter.CDATA,name);
    hd.startElement("","",SkirmishTraitsXMLConstants.TRAIT_TAG,attrs);
    hd.endElement("","",SkirmishTraitsXMLConstants.TRAIT_TAG);
  }
}
