package delta.games.lotro.character.classes.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.classes.AbstractClassDescription;
import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.classes.ClassSkill;
import delta.games.lotro.character.classes.ClassTrait;
import delta.games.lotro.character.classes.MonsterClassDescription;
import delta.games.lotro.character.classes.proficiencies.io.xml.ClassProficienciesXMLParser;
import delta.games.lotro.character.classes.traitTree.TraitTree;
import delta.games.lotro.character.classes.traitTree.TraitTreesManager;
import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.character.skills.SkillsManager;
import delta.games.lotro.character.stats.buffs.BuffSpecification;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.character.traits.TraitsManager;

/**
 * Parser for class descriptions stored in XML.
 * @author DAM
 */
public class ClassDescriptionXMLParser
{
  /**
   * Parse a class descriptions XML file.
   * @param source Source file.
   * @return List of parsed class descriptions.
   */
  public static List<AbstractClassDescription> parseClassDescriptionsFile(File source)
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
  private static AbstractClassDescription parseClassDescription(Element root)
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
    String name=DOMParsingTools.getStringAttribute(attrs,ClassDescriptionXMLConstants.CLASS_NAME_ATTR,null);
    ret.setName(name);
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
    // Traits
    List<Element> classTraitTags=DOMParsingTools.getChildTagsByName(root,ClassDescriptionXMLConstants.CLASS_TRAIT_TAG);
    for(Element classTraitTag : classTraitTags)
    {
      NamedNodeMap traitAttrs=classTraitTag.getAttributes();
      // Min level
      int minLevel=DOMParsingTools.getIntAttribute(traitAttrs,ClassDescriptionXMLConstants.CLASS_TRAIT_MIN_LEVEL_ATTR,1);
      // Trait ID
      int traitId=DOMParsingTools.getIntAttribute(traitAttrs,ClassDescriptionXMLConstants.CLASS_TRAIT_ID_ATTR,0);
      TraitDescription trait=TraitsManager.getInstance().getTrait(traitId);
      ClassTrait classTrait=new ClassTrait(minLevel,trait);
      ret.addTrait(classTrait);
    }
    // Skills
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
    if (description!=null)
    {
      // Default buffs
      List<Element> buffTags=DOMParsingTools.getChildTagsByName(root,ClassDescriptionXMLConstants.DEFAULT_BUFF_TAG);
      for(Element buffTag : buffTags)
      {
        NamedNodeMap buffAttrs=buffTag.getAttributes();
        // Buff ID
        String buffId=DOMParsingTools.getStringAttribute(buffAttrs,ClassDescriptionXMLConstants.DEFAULT_BUFF_ID_ATTR,null);
        // Tier
        int tierValue=DOMParsingTools.getIntAttribute(buffAttrs,ClassDescriptionXMLConstants.DEFAULT_BUFF_TIER,0);
        Integer tier=(tierValue!=0)?Integer.valueOf(tierValue):null;
        BuffSpecification buff=new BuffSpecification(buffId,tier);
        description.addDefaultBuff(buff);
      }
    }
    return ret;
  }
}
