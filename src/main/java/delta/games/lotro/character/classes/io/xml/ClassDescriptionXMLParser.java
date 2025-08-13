package delta.games.lotro.character.classes.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.classes.AbstractClassDescription;
import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.classes.ClassSkill;
import delta.games.lotro.character.classes.ClassVirtue;
import delta.games.lotro.character.classes.MonsterClassDescription;
import delta.games.lotro.character.classes.proficiencies.io.xml.ClassProficienciesXMLParser;
import delta.games.lotro.character.classes.traitTree.TraitTree;
import delta.games.lotro.character.classes.traitTree.TraitTreesManager;
import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.character.skills.SkillsManager;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.character.traits.TraitsManager;
import delta.games.lotro.character.utils.TraitAndLevel;
import delta.games.lotro.utils.i18n.I18nFacade;
import delta.games.lotro.utils.i18n.I18nRuntimeUtils;

/**
 * Parser for class descriptions stored in XML.
 * @author DAM
 */
public class ClassDescriptionXMLParser
{
  private SingleLocaleLabelsManager _i18n;

  /**
   * Constructor.
   * @param labels Name of the labels file to use.
   */
  public ClassDescriptionXMLParser(String labels)
  {
    _i18n=I18nFacade.getLabelsMgr(labels);
  }

  /**
   * Parse a class descriptions XML file.
   * @param source Source file.
   * @return List of parsed class descriptions.
   */
  public List<AbstractClassDescription> parseClassDescriptionsFile(File source)
  {
    List<AbstractClassDescription> descriptions=new ArrayList<AbstractClassDescription>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> classTags=DOMParsingTools.getChildTags(root);
      for(Element classTag : classTags)
      {
        AbstractClassDescription description=parseClassDescription(classTag);
        descriptions.add(description);
      }
    }
    return descriptions;
  }

  /**
   * Build a class description from an XML tag.
   * @param root Root XML tag.
   * @return A class description.
   */
  private AbstractClassDescription parseClassDescription(Element root)
  {
    String tagName=root.getTagName();
    boolean isMonsterClass=ClassDescriptionXMLConstants.MONSTER_CLASS_TAG.equals(tagName);
    NamedNodeMap attrs=root.getAttributes();
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,ClassDescriptionXMLConstants.CLASS_ID_ATTR,0);
    // Code
    int code=DOMParsingTools.getIntAttribute(attrs,ClassDescriptionXMLConstants.CLASS_CODE_ATTR,0);
    // Key
    String key=DOMParsingTools.getStringAttribute(attrs,ClassDescriptionXMLConstants.CLASS_KEY_ATTR,null);
    // Build class
    AbstractClassDescription ret=null;
    ClassDescription description=null;
    MonsterClassDescription monsterClass=null;
    if (isMonsterClass)
    {
      monsterClass=new MonsterClassDescription(id,code,key);
      ret=monsterClass;
    }
    else
    {
      description=new ClassDescription(id,code,key);
      ret=description;
    }
    // Name
    String name=_i18n.getLabel(String.valueOf(id));
    ret.setName(name);
    // Tag
    String tag=DOMParsingTools.getStringAttribute(attrs,ClassDescriptionXMLConstants.CLASS_TAG_ATTR,"");
    ret.setTag(tag);
    // Icon ID
    int iconId=DOMParsingTools.getIntAttribute(attrs,ClassDescriptionXMLConstants.CLASS_ICON_ID_ATTR,0);
    ret.setIconId(iconId);
    // Small icon ID
    int smallIconId=DOMParsingTools.getIntAttribute(attrs,ClassDescriptionXMLConstants.CLASS_SMALL_ICON_ID_ATTR,0);
    ret.setSmallIconId(smallIconId);
    // Abbreviation
    String abbreviation=DOMParsingTools.getStringAttribute(attrs,ClassDescriptionXMLConstants.CLASS_ABBREVIATION_ATTR,"");
    ret.setAbbreviation(abbreviation);
    // Description
    String descriptionText=DOMParsingTools.getStringAttribute(attrs,ClassDescriptionXMLConstants.CLASS_DESCRIPTION_ATTR,"");
    descriptionText=I18nRuntimeUtils.getLabel(_i18n,descriptionText);
    ret.setDescription(descriptionText);
    if (description!=null)
    {
      // Tactical DPS stat name
      String tacticalDpsStatName=DOMParsingTools.getStringAttribute(attrs,ClassDescriptionXMLConstants.CLASS_TACTICAL_DPS_STAT_NAME_ATTR,"");
      description.setTacticalDpsStatName(tacticalDpsStatName);
      // Trait tree
      int traitTreeID=DOMParsingTools.getIntAttribute(attrs,ClassDescriptionXMLConstants.CLASS_TRAIT_TREE_ID_ATTR,0);
      if (traitTreeID!=0)
      {
        TraitTreesManager traitTreesMgr=TraitTreesManager.getInstance();
        TraitTree traitTree=traitTreesMgr.getTraitTree(traitTreeID);
        description.setTraitTree(traitTree);
      }
      // Proficiencies
      ClassProficienciesXMLParser.parseClassProficiencies(root,description.getProficiencies());
    }
    // Virtues
    if (isMonsterClass)
    {
      parseVirtues(root,monsterClass);
    }
    // Traits
    parseTraits(root,ret);
    // Skills
    parseSkills(root,ret);
    return ret;
  }

  private void parseVirtues(Element root, MonsterClassDescription ret)
  {
    List<Element> classVirtueTags=DOMParsingTools.getChildTagsByName(root,ClassDescriptionXMLConstants.CLASS_VIRTUE_TAG);
    for(Element classVirtueTag : classVirtueTags)
    {
      NamedNodeMap attrs=classVirtueTag.getAttributes();
      // Id
      int traitId=DOMParsingTools.getIntAttribute(attrs,ClassDescriptionXMLConstants.CLASS_VIRTUE_ID_ATTR,0);
      TraitDescription trait=TraitsManager.getInstance().getTrait(traitId);
      // Start rank
      int startRank=DOMParsingTools.getIntAttribute(attrs,ClassDescriptionXMLConstants.CLASS_VIRTUE_START_RANK_ATTR,0);
      // Max rank
      int maxRank=DOMParsingTools.getIntAttribute(attrs,ClassDescriptionXMLConstants.CLASS_VIRTUE_MAX_RANK_ATTR,0);
      ClassVirtue classVirtue=new ClassVirtue(startRank,maxRank,trait);
      ret.addVirtue(classVirtue);
    }
  }

  private void parseTraits(Element root, AbstractClassDescription ret)
  {
    List<Element> classTraitTags=DOMParsingTools.getChildTagsByName(root,ClassDescriptionXMLConstants.CLASS_TRAIT_TAG);
    for(Element classTraitTag : classTraitTags)
    {
      NamedNodeMap traitAttrs=classTraitTag.getAttributes();
      // Min level
      int minLevel=DOMParsingTools.getIntAttribute(traitAttrs,ClassDescriptionXMLConstants.CLASS_TRAIT_MIN_LEVEL_ATTR,1);
      // Trait ID
      int traitId=DOMParsingTools.getIntAttribute(traitAttrs,ClassDescriptionXMLConstants.CLASS_TRAIT_ID_ATTR,0);
      TraitDescription trait=TraitsManager.getInstance().getTrait(traitId);
      TraitAndLevel classTrait=new TraitAndLevel(minLevel,trait);
      ret.addTrait(classTrait);
    }
  }

  private void parseSkills(Element root, AbstractClassDescription ret)
  {
    List<Element> classSkillTags=DOMParsingTools.getChildTagsByName(root,ClassDescriptionXMLConstants.CLASS_SKILL_TAG);
    for(Element classSkillTag : classSkillTags)
    {
      NamedNodeMap skillAttrs=classSkillTag.getAttributes();
      // Min level
      int minLevel=DOMParsingTools.getIntAttribute(skillAttrs,ClassDescriptionXMLConstants.CLASS_SKILL_MIN_LEVEL_ATTR,1);
      // Skill ID
      int skillId=DOMParsingTools.getIntAttribute(skillAttrs,ClassDescriptionXMLConstants.CLASS_SKILL_ID_ATTR,0);
      SkillDescription skill=SkillsManager.getInstance().getSkill(skillId);
      ClassSkill classSkill=new ClassSkill(minLevel,skill);
      ret.addSkill(classSkill);
    }
  }
}
