package delta.games.lotro.character.classes.proficiencies.io.xml;

import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.classes.proficiencies.ClassProficiencies;
import delta.games.lotro.character.classes.proficiencies.TypedClassProficiencies;
import delta.games.lotro.lore.items.ArmourType;
import delta.games.lotro.lore.items.WeaponType;

/**
 * Parser for class descriptions stored in XML.
 * @author DAM
 */
public class ClassProficienciesXMLParser
{
  /**
   * Build a class proficiencies from an XML tag.
   * @param root Root XML tag.
   * @param proficiencies Storage for loaded data.
   */
  public static void parseClassProficiencies(Element root, ClassProficiencies proficiencies)
  {
    Element proficienciesTag=DOMParsingTools.getChildTagByName(root,ClassProficienciesXMLConstants.PROFICIENCIES_TAG);
    if (proficienciesTag!=null)
    {
      parseWeaponProficiencies(proficienciesTag,proficiencies.getWeaponProficiencies());
      parseArmourProficiencies(proficienciesTag,proficiencies.getArmourProficiencies());
    }
  }

  private static void parseWeaponProficiencies(Element root, TypedClassProficiencies<WeaponType> proficiencies)
  {
    List<Element> weaponProficiencyTags=DOMParsingTools.getChildTagsByName(root,ClassProficienciesXMLConstants.WEAPON_PROFICIENCY_TAG);
    for(Element weaponProficiencyTag : weaponProficiencyTags)
    {
      NamedNodeMap attrs=weaponProficiencyTag.getAttributes();
      // Type
      String typeStr=DOMParsingTools.getStringAttribute(attrs,ClassProficienciesXMLConstants.PROFICIENCY_TYPE_ATTR,"");
      WeaponType type=WeaponType.getWeaponTypeByKey(typeStr);
      // Minimum level
      int minimumLevel=DOMParsingTools.getIntAttribute(attrs,ClassProficienciesXMLConstants.PROFICIENCY_LEVEL_ATTR,0);
      proficiencies.addEntry(type,minimumLevel);
    }
  }

  private static void parseArmourProficiencies(Element root, TypedClassProficiencies<ArmourType> proficiencies)
  {
    List<Element> armourProficiencyTags=DOMParsingTools.getChildTagsByName(root,ClassProficienciesXMLConstants.WEAPON_PROFICIENCY_TAG);
    for(Element armourProficiencyTag : armourProficiencyTags)
    {
      NamedNodeMap attrs=armourProficiencyTag.getAttributes();
      // Type
      String typeStr=DOMParsingTools.getStringAttribute(attrs,ClassProficienciesXMLConstants.PROFICIENCY_TYPE_ATTR,"");
      ArmourType type=ArmourType.getArmourTypeByKey(typeStr);
      // Minimum level
      int minimumLevel=DOMParsingTools.getIntAttribute(attrs,ClassProficienciesXMLConstants.PROFICIENCY_LEVEL_ATTR,0);
      proficiencies.addEntry(type,minimumLevel);
    }
  }
}
