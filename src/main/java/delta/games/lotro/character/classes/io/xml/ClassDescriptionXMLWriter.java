package delta.games.lotro.character.classes.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.classes.ClassTrait;
import delta.games.lotro.common.CharacterClass;

/**
 * Writes class descriptions to XML files.
 * @author DAM
 */
public class ClassDescriptionXMLWriter
{
  /**
   * Write some class descriptions to a XML file.
   * @param toFile File to write to.
   * @param descriptions Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean write(File toFile, final List<ClassDescription> descriptions)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",ClassDescriptionXMLConstants.CLASSES_TAG,new AttributesImpl());
        for(ClassDescription description : descriptions)
        {
          writeClassDescription(hd,description);
        }
        hd.endElement("","",ClassDescriptionXMLConstants.CLASSES_TAG);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private static void writeClassDescription(TransformerHandler hd, ClassDescription description) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Key
    CharacterClass characterClass=description.getCharacterClass();
    attrs.addAttribute("","",ClassDescriptionXMLConstants.CLASS_KEY_ATTR,XmlWriter.CDATA,characterClass.getKey());
    // Icon ID
    int iconId=description.getIconId();
    attrs.addAttribute("","",ClassDescriptionXMLConstants.CLASS_ICON_ID_ATTR,XmlWriter.CDATA,String.valueOf(iconId));
    // Small icon ID
    int smallIconId=description.getSmallIconId();
    attrs.addAttribute("","",ClassDescriptionXMLConstants.CLASS_SMALL_ICON_ID_ATTR,XmlWriter.CDATA,String.valueOf(smallIconId));

    hd.startElement("","",ClassDescriptionXMLConstants.CLASS_TAG,attrs);
    // Traits
    List<ClassTrait> traits=description.getTraits();
    for(ClassTrait trait : traits)
    {
      AttributesImpl traitAttrs=new AttributesImpl();
      // Min level
      int minLevel=trait.getRequiredLevel();
      traitAttrs.addAttribute("","",ClassDescriptionXMLConstants.CLASS_TRAIT_MIN_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(minLevel));
      // Trait identifier
      int traitId=trait.getTrait().getIdentifier();
      traitAttrs.addAttribute("","",ClassDescriptionXMLConstants.CLASS_TRAIT_ID_ATTR,XmlWriter.CDATA,String.valueOf(traitId));
      hd.startElement("","",ClassDescriptionXMLConstants.CLASS_TRAIT_TAG,traitAttrs);
      hd.endElement("","",ClassDescriptionXMLConstants.CLASS_TRAIT_TAG);
    }
    hd.endElement("","",ClassDescriptionXMLConstants.CLASS_TAG);
  }
}
