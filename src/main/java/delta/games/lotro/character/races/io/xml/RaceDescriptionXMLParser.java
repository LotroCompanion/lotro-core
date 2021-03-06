package delta.games.lotro.character.races.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.races.RaceDescription;
import delta.games.lotro.character.races.RaceGender;
import delta.games.lotro.character.races.RaceTrait;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.character.traits.TraitsManager;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.Race;

/**
 * Parser for race descriptions stored in XML.
 * @author DAM
 */
public class RaceDescriptionXMLParser
{
  /**
   * Parse a race descriptions XML file.
   * @param source Source file.
   * @return List of parsed race descriptions.
   */
  public static List<RaceDescription> parseRaceDescriptionsFile(File source)
  {
    List<RaceDescription> descriptions=new ArrayList<RaceDescription>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> raceTags=DOMParsingTools.getChildTagsByName(root,RaceDescriptionXMLConstants.RACE_TAG);
      for(Element raceTag : raceTags)
      {
        RaceDescription description=parseRaceDescription(raceTag);
        descriptions.add(description);
      }
    }
    return descriptions;
  }

  /**
   * Build a race description from an XML tag.
   * @param root Root XML tag.
   * @return A race description.
   */
  private static RaceDescription parseRaceDescription(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Key
    String raceKeyStr=DOMParsingTools.getStringAttribute(attrs,RaceDescriptionXMLConstants.RACE_KEY_ATTR,null);
    Race race=Race.getByKey(raceKeyStr);
    RaceDescription raceDescription=new RaceDescription(race);
    // Description
    String description=DOMParsingTools.getStringAttribute(attrs,RaceDescriptionXMLConstants.RACE_DESCRIPTION_ATTR,"");
    raceDescription.setDescription(description);
    // Genders
    List<Element> genderTags=DOMParsingTools.getChildTagsByName(root,RaceDescriptionXMLConstants.GENDER_TAG);
    RaceGender male=parseGenderDescription(genderTags.get(0));
    raceDescription.setMaleGender(male);
    RaceGender female=null;
    if (genderTags.size()>1)
    {
      female=parseGenderDescription(genderTags.get(1));
      raceDescription.setFemaleGender(female);
    }
    // Allowed classes
    List<Element> allowedClassTags=DOMParsingTools.getChildTagsByName(root,RaceDescriptionXMLConstants.ALLOWED_CLASS_TAG);
    for(Element allowedClassTag : allowedClassTags)
    {
      NamedNodeMap allowedClassAttrs=allowedClassTag.getAttributes();
      // Key
      String classKey=DOMParsingTools.getStringAttribute(allowedClassAttrs,RaceDescriptionXMLConstants.ALLOWED_CLASS_ID_ATTR,"");
      CharacterClass characterClass=CharacterClass.getByKey(classKey);
      raceDescription.addAllowedClass(characterClass);
    }
    // Traits
    List<Element> classTraitTags=DOMParsingTools.getChildTagsByName(root,RaceDescriptionXMLConstants.RACE_TRAIT_TAG);
    for(Element classTraitTag : classTraitTags)
    {
      NamedNodeMap traitAttrs=classTraitTag.getAttributes();
      // Min level
      int minLevel=DOMParsingTools.getIntAttribute(traitAttrs,RaceDescriptionXMLConstants.RACE_TRAIT_MIN_LEVEL_ATTR,1);
      // Trait ID
      int traitId=DOMParsingTools.getIntAttribute(traitAttrs,RaceDescriptionXMLConstants.RACE_TRAIT_ID_ATTR,0);
      TraitDescription trait=TraitsManager.getInstance().getTrait(traitId);
      RaceTrait raceTrait=new RaceTrait(minLevel,trait);
      raceDescription.addTrait(raceTrait);
    }
    // Earnable traits
    List<Element> earnableTraitTags=DOMParsingTools.getChildTagsByName(root,RaceDescriptionXMLConstants.EARNABLE_TRAIT_TAG);
    for(Element earnableTraitTag : earnableTraitTags)
    {
      NamedNodeMap traitAttrs=earnableTraitTag.getAttributes();
      // Trait ID
      int traitId=DOMParsingTools.getIntAttribute(traitAttrs,RaceDescriptionXMLConstants.EARNABLE_TRAIT_ID_ATTR,0);
      TraitDescription trait=TraitsManager.getInstance().getTrait(traitId);
      raceDescription.addEarnableTrait(trait);
    }
    return raceDescription;
  }

  /**
   * Build a gender from an XML tag.
   * @param root Root XML tag.
   * @return A gender.
   */
  private static RaceGender parseGenderDescription(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    RaceGender gender=new RaceGender();
    // Name
    String genderName=DOMParsingTools.getStringAttribute(attrs,RaceDescriptionXMLConstants.GENDER_NAME_ATTR,"");
    gender.setName(genderName);
    // Large icon ID
    int largeIconId=DOMParsingTools.getIntAttribute(attrs,RaceDescriptionXMLConstants.GENDER_LARGE_ICON_ID_ATTR,0);
    gender.setLargeIconId(largeIconId);
    // Icon ID
    int iconId=DOMParsingTools.getIntAttribute(attrs,RaceDescriptionXMLConstants.GENDER_ICON_ID_ATTR,0);
    gender.setIconId(iconId);
    // Small icon ID
    int smallIconId=DOMParsingTools.getIntAttribute(attrs,RaceDescriptionXMLConstants.GENDER_SMALL_ICON_ID_ATTR,0);
    gender.setSmallIconId(smallIconId);
    // Avatar ID
    int avatarId=DOMParsingTools.getIntAttribute(attrs,RaceDescriptionXMLConstants.GENDER_AVATAR_ID_ATTR,0);
    gender.setAvatarId(avatarId);
    return gender;
  }
}
