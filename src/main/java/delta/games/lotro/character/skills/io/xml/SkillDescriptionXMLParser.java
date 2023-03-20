package delta.games.lotro.character.skills.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.character.skills.TravelSkill;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.common.enums.MountType;
import delta.games.lotro.common.enums.SkillCategory;
import delta.games.lotro.common.enums.SkillCharacteristicSubCategory;
import delta.games.lotro.common.enums.TravelLink;
import delta.games.lotro.lore.agents.EntityClassification;
import delta.games.lotro.lore.agents.io.xml.AgentsXMLIO;
import delta.games.lotro.lore.collections.mounts.MountDescription;
import delta.games.lotro.lore.collections.pets.CosmeticPetDescription;
import delta.games.lotro.utils.i18n.I18nFacade;
import delta.games.lotro.utils.i18n.I18nRuntimeUtils;

/**
 * Parser for skill descriptions stored in XML.
 * @author DAM
 */
public class SkillDescriptionXMLParser
{

  private SingleLocaleLabelsManager _i18n;
  // All skills
  private LotroEnum<SkillCategory> _categoryEnum;
  // Mounts
  private LotroEnum<MountType> _mountTypes;
  private LotroEnum<SkillCharacteristicSubCategory> _mountCategory;

  /**
   * Constructor.
   */
  public SkillDescriptionXMLParser()
  {
    _i18n=I18nFacade.getLabelsMgr("skills");
    LotroEnumsRegistry registry=LotroEnumsRegistry.getInstance();
    _categoryEnum=registry.get(SkillCategory.class);
    _mountTypes=registry.get(MountType.class);
    _mountCategory=registry.get(SkillCharacteristicSubCategory.class);
  }

  /**
   * Parse a skills XML file.
   * @param source Source file.
   * @return List of parsed skills.
   */
  public List<SkillDescription> parseSkillsFile(File source)
  {
    List<SkillDescription> skills=new ArrayList<SkillDescription>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> skillTags=DOMParsingTools.getChildTags(root);
      for(Element skillTag : skillTags)
      {
        SkillDescription skill=parseSkillTag(skillTag);
        skills.add(skill);
      }
    }
    return skills;
  }

  /**
   * Build a skill from an XML tag.
   * @param root Root XML tag.
   * @return A skill.
   */
  private SkillDescription parseSkillTag(Element root)
  {
    String tagName=root.getTagName();
    if (SkillDescriptionXMLConstants.SKILL_TAG.equals(tagName))
    {
      return parseSkill(root);
    }
    else if (MountXMLConstants.MOUNT_TAG.equals(tagName))
    {
      return parseMount(root);
    }
    else if (CosmeticPetXMLConstants.PET_TAG.equals(tagName))
    {
      return parsePet(root);
    }
    return null;
  }

  private SkillDescription parseSkill(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    SkillDescription skill=buildSkill(root);
    parseSharedSkillAttributes(skill,attrs);
    return skill;
  }

  private SkillDescription buildSkill(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Travel?
    int travelTypeCode=DOMParsingTools.getIntAttribute(attrs,SkillDescriptionXMLConstants.SKILL_TRAVEL_TYPE_ATTR,-1);
    if (travelTypeCode>0)
    {
      LotroEnum<TravelLink> travelEnum=LotroEnumsRegistry.getInstance().get(TravelLink.class);
      TravelLink type=travelEnum.getEntry(travelTypeCode);
      return new TravelSkill(type);
    }
    SkillDescription skill=new SkillDescription();
    return skill;
  }

  /**
   * Build a mount from an XML tag.
   * @param root Root XML tag.
   * @return A mount.
   */
  private MountDescription parseMount(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    MountDescription ret=new MountDescription();

    // Shared attributes
    parseSharedSkillAttributes(ret,attrs);
    // Initial Name
    String initialName=DOMParsingTools.getStringAttribute(attrs,MountXMLConstants.MOUNT_INITIAL_NAME_ATTR,"");
    initialName=I18nRuntimeUtils.getLabel(_i18n,initialName);
    ret.setInitialName(initialName);
    // Mount Category
    int categoryCode=DOMParsingTools.getIntAttribute(attrs,MountXMLConstants.MOUNT_CATEGORY_ATTR,0);
    SkillCharacteristicSubCategory mountCategory=_mountCategory.getEntry(categoryCode);
    ret.setMountCategory(mountCategory);
    // Mount type
    int mountTypeCode=DOMParsingTools.getIntAttribute(attrs,MountXMLConstants.MOUNT_MOUNT_TYPE_ATTR,0);
    MountType mountType=_mountTypes.getEntry(mountTypeCode);
    ret.setMountType(mountType);
    // Source description
    String sourceDescription=DOMParsingTools.getStringAttribute(attrs,MountXMLConstants.MOUNT_SOURCE_DESCRIPTION_ATTR,"");
    sourceDescription=I18nRuntimeUtils.getLabel(_i18n,sourceDescription);
    ret.setSourceDescription(sourceDescription);
    // Morale
    int morale=DOMParsingTools.getIntAttribute(attrs,MountXMLConstants.MOUNT_MORALE_ATTR,0);
    ret.setMorale(morale);
    // Speed
    float speed=DOMParsingTools.getFloatAttribute(attrs,MountXMLConstants.MOUNT_SPEED_ATTR,0);
    ret.setSpeed(speed);
    // Tall
    boolean tall=DOMParsingTools.getBooleanAttribute(attrs,MountXMLConstants.MOUNT_TALL_ATTR,false);
    ret.setTall(tall);
    // Peer Mount ID
    int peerMountId=DOMParsingTools.getIntAttribute(attrs,MountXMLConstants.MOUNT_PEER_ID_ATTR,0);
    ret.setPeerMountId(peerMountId);
    return ret;
  }

  private CosmeticPetDescription parsePet(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    CosmeticPetDescription ret=new CosmeticPetDescription();

    // Shared attributes
    parseSharedSkillAttributes(ret,attrs);
    // Initial Name
    String initialName=DOMParsingTools.getStringAttribute(attrs,CosmeticPetXMLConstants.PET_INITIAL_NAME_ATTR,"");
    initialName=I18nRuntimeUtils.getLabel(_i18n,initialName);
    ret.setInitialName(initialName);
    // Source description
    String sourceDescription=DOMParsingTools.getStringAttribute(attrs,CosmeticPetXMLConstants.PET_SOURCE_DESCRIPTION_ATTR,"");
    sourceDescription=I18nRuntimeUtils.getLabel(_i18n,sourceDescription);
    ret.setSourceDescription(sourceDescription);
    // Entity classification
    EntityClassification classification=ret.getClassification();
    AgentsXMLIO.parseEntityClassification(classification,attrs);
    return ret;
  }

  private void parseSharedSkillAttributes(SkillDescription skill, NamedNodeMap attrs)
  {
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,SkillDescriptionXMLConstants.SKILL_IDENTIFIER_ATTR,0);
    skill.setIdentifier(id);
    // Name
    String name=_i18n.getLabel(String.valueOf(id));
    skill.setName(name);
    // Category
    int categoryCode=DOMParsingTools.getIntAttribute(attrs,SkillDescriptionXMLConstants.SKILL_CATEGORY_ATTR,0);
    if (categoryCode!=0)
    {
      SkillCategory category=_categoryEnum.getEntry(categoryCode);
      skill.setCategory(category);
    }
    // Icon ID
    int iconId=DOMParsingTools.getIntAttribute(attrs,SkillDescriptionXMLConstants.SKILL_ICON_ID_ATTR,0);
    skill.setIconId(iconId);
    // Description
    String description=DOMParsingTools.getStringAttribute(attrs,SkillDescriptionXMLConstants.SKILL_DESCRIPTION_ATTR,"");
    description=I18nRuntimeUtils.getLabel(_i18n,description);
    skill.setDescription(description);
  }
}
