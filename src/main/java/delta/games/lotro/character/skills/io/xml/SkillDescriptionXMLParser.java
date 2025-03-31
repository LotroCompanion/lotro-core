package delta.games.lotro.character.skills.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.character.skills.SkillDetails;
import delta.games.lotro.character.skills.TravelSkill;
import delta.games.lotro.character.skills.combos.SkillCombos;
import delta.games.lotro.character.skills.combos.SkillCombosUtils;
import delta.games.lotro.character.skills.combos.io.xml.SkillCombosXmlIO;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.effects.EffectsManager;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.common.enums.MountType;
import delta.games.lotro.common.enums.SkillCategory;
import delta.games.lotro.common.enums.SkillCharacteristicSubCategory;
import delta.games.lotro.common.enums.TravelLink;
import delta.games.lotro.common.geo.Position;
import delta.games.lotro.common.geo.io.xml.PositionXMLConstants;
import delta.games.lotro.common.geo.io.xml.PositionXMLParser;
import delta.games.lotro.lore.agents.EntityClassification;
import delta.games.lotro.lore.agents.io.xml.AgentsXMLIO;
import delta.games.lotro.lore.collections.mounts.MountDescription;
import delta.games.lotro.lore.collections.pets.CosmeticPetDescription;
import delta.games.lotro.utils.Proxy;
import delta.games.lotro.utils.i18n.I18nFacade;
import delta.games.lotro.utils.i18n.I18nRuntimeUtils;

/**
 * Parser for skill descriptions stored in XML.
 * @author DAM
 */
public class SkillDescriptionXMLParser
{
  private static final Logger LOGGER=LoggerFactory.getLogger(SkillDescriptionXMLParser.class);

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
        if (skill!=null)
        {
          Element detailsTag=DOMParsingTools.getChildTagByName(skillTag,SkillDetailsXMLConstants.DETAILS_TAG);
          if (detailsTag!=null)
          {
            SkillDetails details=SkillDetailsXmlIO.readSkillDetails(detailsTag);
            skill.setDetails(details);
          }
          skills.add(skill);
        }
      }
    }
    SkillCombosUtils.resolveCombos(skills);
    return skills;
  }

  /**
   * Build a skill from an XML tag.
   * @param root Root XML tag.
   * @return A skill.
   */
  private SkillDescription parseSkillTag(Element root)
  {
    SkillDescription ret=null;
    String tagName=root.getTagName();
    if (SkillDescriptionXMLConstants.SKILL_TAG.equals(tagName))
    {
      ret=parseSkill(root);
    }
    else if (MountXMLConstants.MOUNT_TAG.equals(tagName))
    {
      ret=parseMount(root);
    }
    else if (CosmeticPetXMLConstants.PET_TAG.equals(tagName))
    {
      ret=parsePet(root);
    }
    else if (SkillDescriptionXMLConstants.TRAVEL_SKILL_TAG.equals(tagName))
    {
      ret=parseTravelSkill(root);
    }
    if (ret!=null)
    {
      // Requirements
      parseRequirements(root,ret);
      // Combos
      SkillCombos combos=SkillCombosXmlIO.readSkillCombos(root);
      ret.setCombos(combos);
    }
    return ret;
  }

  private TravelSkill parseTravelSkill(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Travel type
    TravelLink type=null;
    int travelTypeCode=DOMParsingTools.getIntAttribute(attrs,SkillDescriptionXMLConstants.SKILL_TRAVEL_TYPE_ATTR,-1);
    if (travelTypeCode>0)
    {
      LotroEnum<TravelLink> travelEnum=LotroEnumsRegistry.getInstance().get(TravelLink.class);
      type=travelEnum.getEntry(travelTypeCode);
    }
    TravelSkill skill=new TravelSkill(type);
    // Shared attributes
    parseSharedSkillAttributes(skill,attrs);
    // Position
    Element positionTag=DOMParsingTools.getChildTagByName(root,PositionXMLConstants.POSITION);
    Position position=PositionXMLParser.parseSimplePosition(positionTag);
    skill.setPosition(position);
    return skill;
  }

  private SkillDescription parseSkill(Element root)
  {
    SkillDescription skill=new SkillDescription();
    NamedNodeMap attrs=root.getAttributes();
    parseSharedSkillAttributes(skill,attrs);
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

  private void parseRequirements(Element root, SkillDescription skill)
  {
    // Required trait
    Element traitTag=DOMParsingTools.getChildTagByName(root,SkillDescriptionXMLConstants.REQUIRED_TRAIT_TAG);
    if (traitTag!=null)
    {
      NamedNodeMap attrs=traitTag.getAttributes();
      int id=DOMParsingTools.getIntAttribute(attrs,SkillDescriptionXMLConstants.REQUIRED_TRAIT_ID_ATTR,0);
      String name=DOMParsingTools.getStringAttribute(attrs,SkillDescriptionXMLConstants.REQUIRED_TRAIT_NAME_ATTR,"");
      Proxy<TraitDescription> trait=new Proxy<TraitDescription>();
      trait.setId(id);
      trait.setName(name);
      skill.setRequiredTrait(trait);
    }
    // Required effects
    List<Element> effectTags=DOMParsingTools.getChildTagsByName(root,SkillDescriptionXMLConstants.REQUIRED_EFFECT_TAG);
    for(Element effectTag : effectTags)
    {
      NamedNodeMap attrs=effectTag.getAttributes();
      int id=DOMParsingTools.getIntAttribute(attrs,SkillDescriptionXMLConstants.REQUIRED_EFFECT_ID_ATTR,0);
      Effect effect=EffectsManager.getInstance().getEffectById(id);
      if (effect!=null)
      {
        skill.addRequiredEffect(effect);
      }
      else
      {
        LOGGER.warn("Effect not found: {}",Integer.valueOf(id));
      }
    }
  }
}
