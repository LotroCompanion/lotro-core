package delta.games.lotro.character.classes.proficiencies.io.xml;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.classes.proficiencies.ClassProficiencies;
import delta.games.lotro.character.classes.proficiencies.TypedClassProficiencies;
import delta.games.lotro.character.classes.proficiencies.TypedClassProficiencyEntry;
import delta.games.lotro.lore.items.ArmourType;
import delta.games.lotro.lore.items.WeaponType;

/**
 * Writes class proficiencies to XML files.
 * @author DAM
 */
public class ClassProficienciesXMLWriter
{
  /**
   * Write the given class proficiencies to the given output stream.
   * @param hd Output stream.
   * @param proficiencies Data to write.
   * @throws SAXException If an error occurs.
   */
  public static void writeClassProficiencies(TransformerHandler hd, ClassProficiencies proficiencies) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Armour Type for Mitigations
    ArmourType type=proficiencies.getArmourTypeForMitigations();
    attrs.addAttribute("","",ClassProficienciesXMLConstants.PROFICIENCIES_ARMOUR_TYPE_ATTR,XmlWriter.CDATA,type.getKey());
    hd.startElement("","",ClassProficienciesXMLConstants.PROFICIENCIES_TAG,attrs);
    // Weapon proficiencies
    TypedClassProficiencies<WeaponType> weaponProficiencies=proficiencies.getWeaponProficiencies();
    for(TypedClassProficiencyEntry<WeaponType> weaponProficiency : weaponProficiencies.getEntries())
    {
      writeWeaponProficiencyEntry(hd,weaponProficiency);
    }
    // Armour proficiencies
    TypedClassProficiencies<ArmourType> armourProficiencies=proficiencies.getArmourProficiencies();
    for(TypedClassProficiencyEntry<ArmourType> armourProficiency : armourProficiencies.getEntries())
    {
      writeArmourProficiencyEntry(hd,armourProficiency);
    }
    hd.endElement("","",ClassProficienciesXMLConstants.PROFICIENCIES_TAG);
  }

  private static void writeWeaponProficiencyEntry(TransformerHandler hd, TypedClassProficiencyEntry<WeaponType> entry) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Type
    WeaponType type=entry.getValue();
    attrs.addAttribute("","",ClassProficienciesXMLConstants.PROFICIENCY_TYPE_ATTR,XmlWriter.CDATA,type.getKey());
    // Level
    int minLevel=entry.getMinLevel();
    attrs.addAttribute("","",ClassProficienciesXMLConstants.PROFICIENCY_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(minLevel));
    hd.startElement("","",ClassProficienciesXMLConstants.WEAPON_PROFICIENCY_TAG,attrs);
    hd.endElement("","",ClassProficienciesXMLConstants.WEAPON_PROFICIENCY_TAG);
  }

  private static void writeArmourProficiencyEntry(TransformerHandler hd, TypedClassProficiencyEntry<ArmourType> entry) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Type
    ArmourType type=entry.getValue();
    attrs.addAttribute("","",ClassProficienciesXMLConstants.PROFICIENCY_TYPE_ATTR,XmlWriter.CDATA,type.getKey());
    // Level
    int minLevel=entry.getMinLevel();
    attrs.addAttribute("","",ClassProficienciesXMLConstants.PROFICIENCY_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(minLevel));
    hd.startElement("","",ClassProficienciesXMLConstants.ARMOUR_PROFICIENCY_TAG,attrs);
    hd.endElement("","",ClassProficienciesXMLConstants.ARMOUR_PROFICIENCY_TAG);
  }
}
