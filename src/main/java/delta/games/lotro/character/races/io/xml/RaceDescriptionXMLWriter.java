package delta.games.lotro.character.races.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.races.RaceDescription;
import delta.games.lotro.character.races.RaceGender;
import delta.games.lotro.character.races.RaceTrait;
import delta.games.lotro.common.Race;

/**
 * Writes race descriptions to XML files.
 * @author DAM
 */
public class RaceDescriptionXMLWriter
{
  /**
   * Write some race descriptions to a XML file.
   * @param toFile File to write to.
   * @param descriptions Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean write(File toFile, final List<RaceDescription> descriptions)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",RaceDescriptionXMLConstants.RACES_TAG,new AttributesImpl());
        for(RaceDescription description : descriptions)
        {
          writeRaceDescription(hd,description);
        }
        hd.endElement("","",RaceDescriptionXMLConstants.RACES_TAG);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private static void writeRaceDescription(TransformerHandler hd, RaceDescription description) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Key
    Race race=description.getRace();
    attrs.addAttribute("","",RaceDescriptionXMLConstants.RACE_KEY_ATTR,XmlWriter.CDATA,race.getKey());
    hd.startElement("","",RaceDescriptionXMLConstants.RACE_TAG,attrs);
    // Genders
    writeGender(hd,description.getMaleGender());
    writeGender(hd,description.getFemaleGender());
    // Traits
    List<RaceTrait> traits=description.getTraits();
    for(RaceTrait trait : traits)
    {
      AttributesImpl traitAttrs=new AttributesImpl();
      // Min level
      int minLevel=trait.getRequiredLevel();
      traitAttrs.addAttribute("","",RaceDescriptionXMLConstants.RACE_TRAIT_MIN_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(minLevel));
      // Trait identifier
      int traitId=trait.getTrait().getIdentifier();
      traitAttrs.addAttribute("","",RaceDescriptionXMLConstants.RACE_TRAIT_ID_ATTR,XmlWriter.CDATA,String.valueOf(traitId));
      hd.startElement("","",RaceDescriptionXMLConstants.RACE_TRAIT_TAG,traitAttrs);
      hd.endElement("","",RaceDescriptionXMLConstants.RACE_TRAIT_TAG);
    }
    hd.endElement("","",RaceDescriptionXMLConstants.RACE_TAG);
  }

  private static void writeGender(TransformerHandler hd, RaceGender gender) throws SAXException
  {
    if (gender!=null)
    {
      AttributesImpl attrs=new AttributesImpl();
      // Name
      String name=gender.getName();
      attrs.addAttribute("","",RaceDescriptionXMLConstants.GENDER_NAME_ATTR,XmlWriter.CDATA,name);
      // Large icon ID
      int largeIconId=gender.getIconId();
      attrs.addAttribute("","",RaceDescriptionXMLConstants.GENDER_LARGE_ICON_ID_ATTR,XmlWriter.CDATA,String.valueOf(largeIconId));
      // Icon ID
      int iconId=gender.getIconId();
      attrs.addAttribute("","",RaceDescriptionXMLConstants.GENDER_ICON_ID_ATTR,XmlWriter.CDATA,String.valueOf(iconId));
      // Small icon ID
      int smallIconId=gender.getSmallIconId();
      attrs.addAttribute("","",RaceDescriptionXMLConstants.GENDER_SMALL_ICON_ID_ATTR,XmlWriter.CDATA,String.valueOf(smallIconId));

      hd.startElement("","",RaceDescriptionXMLConstants.GENDER_TAG,attrs);
      hd.endElement("","",RaceDescriptionXMLConstants.GENDER_TAG);
    }
  }
}
