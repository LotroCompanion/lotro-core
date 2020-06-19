package delta.games.lotro.character.classes.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.classes.ClassSkill;
import delta.games.lotro.character.classes.ClassTrait;
import delta.games.lotro.character.classes.InitialGearDefinition;
import delta.games.lotro.character.classes.InitialGearElement;
import delta.games.lotro.character.classes.TraitTree;
import delta.games.lotro.character.classes.TraitTreeBranch;
import delta.games.lotro.character.classes.TraitTreeCell;
import delta.games.lotro.character.classes.TraitTreeCellDependency;
import delta.games.lotro.character.classes.TraitTreeProgression;
import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.character.stats.buffs.BuffSpecification;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.Race;

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
  public static boolean write(File toFile, final List<ClassDescription> descriptions)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",ClassDescriptionXMLConstants.CLASSES_TAG,new AttributesImpl());
        for(ClassDescription description : descriptions)
        {
          writeClassDescription(hd,description);
        }
        hd.endElement("","",ClassDescriptionXMLConstants.CLASSES_TAG);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private static void writeClassDescription(TransformerHandler hd, ClassDescription description) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Key
    CharacterClass characterClass=description.getCharacterClass();
    attrs.addAttribute("","",ClassDescriptionXMLConstants.CLASS_KEY_ATTR,XmlWriter.CDATA,characterClass.getKey());
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
    // Tactical DPS stat name
    String tacticalDpsStatName=description.getTacticalDpsStatName();
    attrs.addAttribute("","",ClassDescriptionXMLConstants.CLASS_TACTICAL_DPS_STAT_NAME_ATTR,XmlWriter.CDATA,tacticalDpsStatName);

    hd.startElement("","",ClassDescriptionXMLConstants.CLASS_TAG,attrs);
    // Traits
    List<ClassTrait> traits=description.getTraits();
    for(ClassTrait trait : traits)
    {
      AttributesImpl traitAttrs=new AttributesImpl();
      // Min level
      int minLevel=trait.getRequiredLevel();
      traitAttrs.addAttribute("","",ClassDescriptionXMLConstants.CLASS_TRAIT_MIN_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(minLevel));
      // Trait identifier
      int traitId=trait.getTrait().getIdentifier();
      traitAttrs.addAttribute("","",ClassDescriptionXMLConstants.CLASS_TRAIT_ID_ATTR,XmlWriter.CDATA,String.valueOf(traitId));
      hd.startElement("","",ClassDescriptionXMLConstants.CLASS_TRAIT_TAG,traitAttrs);
      hd.endElement("","",ClassDescriptionXMLConstants.CLASS_TRAIT_TAG);
    }
    // Trait tree
    TraitTree tree=description.getTraitTree();
    if (tree!=null)
    {
      hd.startElement("","",ClassDescriptionXMLConstants.TRAIT_TREE_TAG,new AttributesImpl());
      List<TraitTreeBranch> branches=tree.getBranches();
      for(TraitTreeBranch branch : branches)
      {
        writeTraitTreeBranch(hd,branch);
      }
      hd.endElement("","",ClassDescriptionXMLConstants.TRAIT_TREE_TAG);
    }
    // Skills
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
    // Initial gear
    InitialGearDefinition initialGear=description.getInitialGear();
    List<InitialGearElement> elements=initialGear.getElements();
    for(InitialGearElement element : elements)
    {
      AttributesImpl gearAttrs=new AttributesImpl();
      // Item ID
      int itemId=element.getItemId();
      gearAttrs.addAttribute("","",ClassDescriptionXMLConstants.GEAR_ITEM_ID_ATTR,XmlWriter.CDATA,String.valueOf(itemId));
      // Race
      Race requiredRace=element.getRequiredRace();
      if (requiredRace!=null)
      {
        gearAttrs.addAttribute("","",ClassDescriptionXMLConstants.GEAR_REQUIRED_RACE_ATTR,XmlWriter.CDATA,requiredRace.getKey());
      }
      hd.startElement("","",ClassDescriptionXMLConstants.INITIAL_GEAR_ELEMENT_TAG,gearAttrs);
      hd.endElement("","",ClassDescriptionXMLConstants.INITIAL_GEAR_ELEMENT_TAG);
    }
    // Default buffs
    List<BuffSpecification> buffs=description.getDefaultBuffs();
    for(BuffSpecification buff : buffs)
    {
      AttributesImpl buffAttrs=new AttributesImpl();
      // Buff ID
      String id=buff.getBuffId();
      buffAttrs.addAttribute("","",ClassDescriptionXMLConstants.DEFAULT_BUFF_ID_ATTR,XmlWriter.CDATA,id);
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

  private static void writeTraitTreeBranch(TransformerHandler hd, TraitTreeBranch branch) throws SAXException
  {
    AttributesImpl branchAttrs=new AttributesImpl();
    // Code
    int code=branch.getCode();
    branchAttrs.addAttribute("","",ClassDescriptionXMLConstants.TRAIT_TREE_BRANCH_CODE_ATTR,XmlWriter.CDATA,String.valueOf(code));
    // Name
    String name=branch.getName();
    branchAttrs.addAttribute("","",ClassDescriptionXMLConstants.TRAIT_TREE_BRANCH_NAME_ATTR,XmlWriter.CDATA,name);
    hd.startElement("","",ClassDescriptionXMLConstants.TRAIT_TREE_BRANCH_TAG,branchAttrs);
    // Progression
    TraitTreeProgression progression=branch.getProgression();
    hd.startElement("","",ClassDescriptionXMLConstants.PROGRESSION_TAG,new AttributesImpl());
    List<Integer> steps=progression.getSteps();
    List<TraitDescription> traits=progression.getTraits();
    int nbItems=Math.min(steps.size(),traits.size());
    for(int i=0;i<nbItems;i++)
    {
      AttributesImpl stepAttrs=new AttributesImpl();
      // Required points
      stepAttrs.addAttribute("","",ClassDescriptionXMLConstants.STEP_REQUIRED_POINTS_ATTR,XmlWriter.CDATA,steps.get(i).toString());
      // Trait ID
      int traitId=traits.get(i).getIdentifier();
      stepAttrs.addAttribute("","",ClassDescriptionXMLConstants.STEP_TRAIT_ID_ATTR,XmlWriter.CDATA,String.valueOf(traitId));
      hd.startElement("","",ClassDescriptionXMLConstants.STEP_TAG,stepAttrs);
      hd.endElement("","",ClassDescriptionXMLConstants.STEP_TAG);
    }
    hd.endElement("","",ClassDescriptionXMLConstants.PROGRESSION_TAG);
    // Cells
    hd.startElement("","",ClassDescriptionXMLConstants.CELLS_TAG,new AttributesImpl());
    List<String> cellIds=branch.getCells();
    for(String cellId : cellIds)
    {
      AttributesImpl cellAttrs=new AttributesImpl();
      // Cell ID
      cellAttrs.addAttribute("","",ClassDescriptionXMLConstants.CELL_ID_ATTR,XmlWriter.CDATA,cellId);
      TraitTreeCell cell=branch.getCell(cellId);
      // Trait ID
      int traitId=cell.getTrait().getIdentifier();
      cellAttrs.addAttribute("","",ClassDescriptionXMLConstants.CELL_TRAIT_ID_ATTR,XmlWriter.CDATA,String.valueOf(traitId));
      hd.startElement("","",ClassDescriptionXMLConstants.CELL_TAG,cellAttrs);
      // Dependencies
      List<TraitTreeCellDependency> dependencies=cell.getDependencies();
      for(TraitTreeCellDependency dependency : dependencies)
      {
        AttributesImpl cellDepAttrs=new AttributesImpl();
        // Cell ID
        String depCellId=dependency.getCellId();
        cellDepAttrs.addAttribute("","",ClassDescriptionXMLConstants.CELL_DEPENDENCY_CELL_ID_ATTR,XmlWriter.CDATA,depCellId);
        // Rank
        int rank=dependency.getRank();
        cellDepAttrs.addAttribute("","",ClassDescriptionXMLConstants.CELL_DEPENDENCY_RANK_ATTR,XmlWriter.CDATA,String.valueOf(rank));
        hd.startElement("","",ClassDescriptionXMLConstants.CELL_DEPENDENCY_TAG,cellDepAttrs);
        hd.endElement("","",ClassDescriptionXMLConstants.CELL_DEPENDENCY_TAG);
      }
      hd.endElement("","",ClassDescriptionXMLConstants.CELL_TAG);
    }
    hd.endElement("","",ClassDescriptionXMLConstants.CELLS_TAG);
    hd.endElement("","",ClassDescriptionXMLConstants.TRAIT_TREE_BRANCH_TAG);
  }
}
