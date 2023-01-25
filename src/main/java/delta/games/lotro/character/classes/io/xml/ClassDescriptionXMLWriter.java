package delta.games.lotro.character.classes.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.classes.AbstractClassDescription;
import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.classes.ClassSkill;
import delta.games.lotro.character.classes.ClassTrait;
import delta.games.lotro.character.classes.MonsterClassDescription;
import delta.games.lotro.character.classes.proficiencies.io.xml.ClassProficienciesXMLWriter;
import delta.games.lotro.character.classes.traitTree.TraitTree;
import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.character.stats.buffs.BuffSpecification;
import delta.games.lotro.character.traits.TraitDescription;

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
  public static boolean write(File toFile, final List<? extends AbstractClassDescription> descriptions)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeClassDescriptions(hd,descriptions);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private static void writeClassDescriptions(TransformerHandler hd, List<? extends AbstractClassDescription> descriptions) throws SAXException
  {
    hd.startElement("","",ClassDescriptionXMLConstants.CLASSES_TAG,new AttributesImpl());
    for(AbstractClassDescription description : descriptions)
    {
      writeClassDescription(hd,description);
    }
    hd.endElement("","",ClassDescriptionXMLConstants.CLASSES_TAG);
  }

  private static void writeClassDescription(TransformerHandler hd, AbstractClassDescription description) throws SAXException
  {
    if (description instanceof ClassDescription)
    {
      writeCharacterClassDescription(hd,(ClassDescription)description);
    }
    else if (description instanceof MonsterClassDescription)
    {
      writeMonsterClassDescription(hd,(MonsterClassDescription)description);
    }
  }

  private static void writeSharedAttributes(AttributesImpl attrs, AbstractClassDescription description)
  {
    // Identifier
    int id=description.getIdentifier();
    attrs.addAttribute("","",ClassDescriptionXMLConstants.CLASS_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Code
    int code=description.getCode();
    attrs.addAttribute("","",ClassDescriptionXMLConstants.CLASS_CODE_ATTR,XmlWriter.CDATA,String.valueOf(code));
    // Key
    String key=description.getKey();
    attrs.addAttribute("","",ClassDescriptionXMLConstants.CLASS_KEY_ATTR,XmlWriter.CDATA,key);
    // String name
    String name=description.getName();
    attrs.addAttribute("","",ClassDescriptionXMLConstants.CLASS_NAME_ATTR,XmlWriter.CDATA,name);
    // Icon ID
    int iconId=description.getIconId();
    attrs.addAttribute("","",ClassDescriptionXMLConstants.CLASS_ICON_ID_ATTR,XmlWriter.CDATA,String.valueOf(iconId));
    // Small icon ID
    int smallIconId=description.getSmallIconId();
    attrs.addAttribute("","",ClassDescriptionXMLConstants.CLASS_SMALL_ICON_ID_ATTR,XmlWriter.CDATA,String.valueOf(smallIconId));
    // Abbreviation
    String abbreviation=description.getAbbreviation();
    attrs.addAttribute("","",ClassDescriptionXMLConstants.CLASS_ABBREVIATION_ATTR,XmlWriter.CDATA,abbreviation);
    // Description
    String descriptionText=description.getDescription();
    attrs.addAttribute("","",ClassDescriptionXMLConstants.CLASS_DESCRIPTION_ATTR,XmlWriter.CDATA,descriptionText);
  }

  private static void writeTraits(TransformerHandler hd, AbstractClassDescription description) throws SAXException
  {
    // Traits
    List<ClassTrait> traits=description.getTraits();
    for(ClassTrait trait : traits)
    {
      AttributesImpl traitAttrs=new AttributesImpl();
      // Min level
      int minLevel=trait.getRequiredLevel();
      traitAttrs.addAttribute("","",ClassDescriptionXMLConstants.CLASS_TRAIT_MIN_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(minLevel));
      // Trait identifier
      TraitDescription traitDescription=trait.getTrait();
      int traitId=traitDescription.getIdentifier();
      traitAttrs.addAttribute("","",ClassDescriptionXMLConstants.CLASS_TRAIT_ID_ATTR,XmlWriter.CDATA,String.valueOf(traitId));
      // Trait name
      String traitName=traitDescription.getName();
      traitAttrs.addAttribute("","",ClassDescriptionXMLConstants.CLASS_TRAIT_NAME_ATTR,XmlWriter.CDATA,traitName);
      hd.startElement("","",ClassDescriptionXMLConstants.CLASS_TRAIT_TAG,traitAttrs);
      hd.endElement("","",ClassDescriptionXMLConstants.CLASS_TRAIT_TAG);
    }
  }

  private static void writeSkills(TransformerHandler hd, AbstractClassDescription description) throws SAXException
  {
    List<ClassSkill> skills=description.getSkills();
    for(ClassSkill classSkill : skills)
    {
      AttributesImpl skillAttrs=new AttributesImpl();
      // Min level
      int minLevel=classSkill.getRequiredLevel();
      skillAttrs.addAttribute("","",ClassDescriptionXMLConstants.CLASS_SKILL_MIN_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(minLevel));
      // Skill identifier
      SkillDescription skill=classSkill.getSkill();
      int skillId=skill.getIdentifier();
      skillAttrs.addAttribute("","",ClassDescriptionXMLConstants.CLASS_SKILL_ID_ATTR,XmlWriter.CDATA,String.valueOf(skillId));
      // Skill name
      String skillName=skill.getName();
      skillAttrs.addAttribute("","",ClassDescriptionXMLConstants.CLASS_SKILL_NAME_ATTR,XmlWriter.CDATA,skillName);
      hd.startElement("","",ClassDescriptionXMLConstants.CLASS_SKILL_TAG,skillAttrs);
      hd.endElement("","",ClassDescriptionXMLConstants.CLASS_SKILL_TAG);
    }
  }

  private static void writeCharacterClassDescription(TransformerHandler hd, ClassDescription description) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    writeSharedAttributes(attrs,description);
    // Tactical DPS stat name
    String tacticalDpsStatName=description.getTacticalDpsStatName();
    if (tacticalDpsStatName!=null)
    {
      attrs.addAttribute("","",ClassDescriptionXMLConstants.CLASS_TACTICAL_DPS_STAT_NAME_ATTR,XmlWriter.CDATA,tacticalDpsStatName);
    }
    // Trait tree
    TraitTree tree=description.getTraitTree();
    if (tree!=null)
    {
      int traitTreeID=tree.getIdentifier();
      attrs.addAttribute("","",ClassDescriptionXMLConstants.CLASS_TRAIT_TREE_ID_ATTR,XmlWriter.CDATA,String.valueOf(traitTreeID));
    }
    hd.startElement("","",ClassDescriptionXMLConstants.CLASS_TAG,attrs);
    // Proficiencies
    ClassProficienciesXMLWriter.writeClassProficiencies(hd,description.getProficiencies());
    // Traits
    writeTraits(hd,description);
    // Skills
    writeSkills(hd,description);
    // Default buffs
    List<BuffSpecification> buffs=description.getDefaultBuffs();
    for(BuffSpecification buff : buffs)
    {
      AttributesImpl buffAttrs=new AttributesImpl();
      // Buff ID
      String buffID=buff.getBuffId();
      buffAttrs.addAttribute("","",ClassDescriptionXMLConstants.DEFAULT_BUFF_ID_ATTR,XmlWriter.CDATA,buffID);
      // Tier
      Integer tier=buff.getTier();
      if (tier!=null)
      {
        buffAttrs.addAttribute("","",ClassDescriptionXMLConstants.DEFAULT_BUFF_TIER,XmlWriter.CDATA,tier.toString());
      }
      hd.startElement("","",ClassDescriptionXMLConstants.DEFAULT_BUFF_TAG,buffAttrs);
      hd.endElement("","",ClassDescriptionXMLConstants.DEFAULT_BUFF_TAG);
    }
    hd.endElement("","",ClassDescriptionXMLConstants.CLASS_TAG);
  }

  private static void writeMonsterClassDescription(TransformerHandler hd, MonsterClassDescription description) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    writeSharedAttributes(attrs,description);
    hd.startElement("","",ClassDescriptionXMLConstants.MONSTER_CLASS_TAG,attrs);
    // Traits
    writeTraits(hd,description);
    // Skills
    writeSkills(hd,description);
    hd.endElement("","",ClassDescriptionXMLConstants.MONSTER_CLASS_TAG);
  }
}
