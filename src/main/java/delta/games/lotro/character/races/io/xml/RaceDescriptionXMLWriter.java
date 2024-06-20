package delta.games.lotro.character.races.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.races.NationalityDescription;
import delta.games.lotro.character.races.RaceDescription;
import delta.games.lotro.character.races.RaceGender;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.character.utils.TraitAndLevel;

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

  private static void writeRaceDescription(TransformerHandler hd, RaceDescription raceDescription) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // ID
    int id=raceDescription.getIdentifier();
    attrs.addAttribute("","",RaceDescriptionXMLConstants.RACE_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Code
    int code=raceDescription.getCode();
    attrs.addAttribute("","",RaceDescriptionXMLConstants.RACE_CODE_ATTR,XmlWriter.CDATA,String.valueOf(code));
    // Key
    String key=raceDescription.getKey();
    attrs.addAttribute("","",RaceDescriptionXMLConstants.RACE_KEY_ATTR,XmlWriter.CDATA,key);
    // Legacy label
    String legacyLabel=raceDescription.getLegacyLabel();
    if (legacyLabel!=null)
    {
      attrs.addAttribute("","",RaceDescriptionXMLConstants.RACE_LEGACY_LABEL_ATTR,XmlWriter.CDATA,legacyLabel);
    }
    // Name
    String name=raceDescription.getName();
    attrs.addAttribute("","",RaceDescriptionXMLConstants.RACE_NAME_ATTR,XmlWriter.CDATA,name);
    // Tag
    String tag=raceDescription.getTag();
    attrs.addAttribute("","",RaceDescriptionXMLConstants.RACE_TAG_ATTR,XmlWriter.CDATA,tag);
    // Tall
    boolean tall=raceDescription.isTall();
    attrs.addAttribute("","",RaceDescriptionXMLConstants.RACE_TALL_ATTR,XmlWriter.CDATA,String.valueOf(tall));
    // Description
    String description=raceDescription.getDescription();
    if (description.length()>0)
    {
      attrs.addAttribute("","",RaceDescriptionXMLConstants.RACE_DESCRIPTION_ATTR,XmlWriter.CDATA,description);
    }
    hd.startElement("","",RaceDescriptionXMLConstants.RACE_TAG,attrs);
    // Genders
    writeGender(hd,raceDescription.getMaleGender());
    writeGender(hd,raceDescription.getFemaleGender());
    // Nationalities
    List<NationalityDescription> nationalities=raceDescription.getNationalities();
    for(NationalityDescription nationality : nationalities)
    {
      AttributesImpl nationalityAttrs=new AttributesImpl();
      nationalityAttrs.addAttribute("","",RaceDescriptionXMLConstants.NATIONALITY_ID_ATTR,XmlWriter.CDATA,String.valueOf(nationality.getIdentifier()));
      nationalityAttrs.addAttribute("","",RaceDescriptionXMLConstants.NATIONALITY_NAME_ATTR,XmlWriter.CDATA,String.valueOf(nationality.getName()));
      hd.startElement("","",RaceDescriptionXMLConstants.NATIONALITY_TAG,nationalityAttrs);
      hd.endElement("","",RaceDescriptionXMLConstants.NATIONALITY_TAG);
    }
    // Allowed classes
    List<String> characterClasses=raceDescription.getAllowedClasses();
    for(String characterClass : characterClasses)
    {
      AttributesImpl allowedClassAttrs=new AttributesImpl();
      allowedClassAttrs.addAttribute("","",RaceDescriptionXMLConstants.ALLOWED_CLASS_ID_ATTR,XmlWriter.CDATA,characterClass);
      hd.startElement("","",RaceDescriptionXMLConstants.ALLOWED_CLASS_TAG,allowedClassAttrs);
      hd.endElement("","",RaceDescriptionXMLConstants.ALLOWED_CLASS_TAG);
    }
    // Traits
    List<TraitAndLevel> traits=raceDescription.getTraits();
    for(TraitAndLevel trait : traits)
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
    // Earnable traits
    List<TraitDescription> earnableTraits=raceDescription.getEarnableTraits();
    for(TraitDescription trait : earnableTraits)
    {
      AttributesImpl traitAttrs=new AttributesImpl();
      // Trait identifier
      int traitId=trait.getIdentifier();
      traitAttrs.addAttribute("","",RaceDescriptionXMLConstants.EARNABLE_TRAIT_ID_ATTR,XmlWriter.CDATA,String.valueOf(traitId));
      hd.startElement("","",RaceDescriptionXMLConstants.EARNABLE_TRAIT_TAG,traitAttrs);
      hd.endElement("","",RaceDescriptionXMLConstants.EARNABLE_TRAIT_TAG);
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
      int largeIconId=gender.getLargeIconId();
      attrs.addAttribute("","",RaceDescriptionXMLConstants.GENDER_LARGE_ICON_ID_ATTR,XmlWriter.CDATA,String.valueOf(largeIconId));
      // Icon ID
      int iconId=gender.getIconId();
      attrs.addAttribute("","",RaceDescriptionXMLConstants.GENDER_ICON_ID_ATTR,XmlWriter.CDATA,String.valueOf(iconId));
      // Small icon ID
      int smallIconId=gender.getSmallIconId();
      attrs.addAttribute("","",RaceDescriptionXMLConstants.GENDER_SMALL_ICON_ID_ATTR,XmlWriter.CDATA,String.valueOf(smallIconId));
      // Avatar ID
      int avatarID=gender.getAvatarId();
      attrs.addAttribute("","",RaceDescriptionXMLConstants.GENDER_AVATAR_ID_ATTR,XmlWriter.CDATA,String.valueOf(avatarID));

      hd.startElement("","",RaceDescriptionXMLConstants.GENDER_TAG,attrs);
      hd.endElement("","",RaceDescriptionXMLConstants.GENDER_TAG);
    }
  }
}
