package delta.games.lotro.character.classes.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.classes.ClassTrait;
import delta.games.lotro.character.classes.TraitTree;
import delta.games.lotro.character.classes.TraitTreeBranch;
import delta.games.lotro.character.classes.TraitTreeProgression;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.character.traits.TraitsManager;
import delta.games.lotro.common.CharacterClass;

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
  public static List<ClassDescription> parseClassDescriptionsFile(File source)
  {
    List<ClassDescription> descriptions=new ArrayList<ClassDescription>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> classTags=DOMParsingTools.getChildTagsByName(root,ClassDescriptionXMLConstants.CLASS_TAG);
      for(Element classTag : classTags)
      {
        ClassDescription description=parseClassDescription(classTag);
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
  private static ClassDescription parseClassDescription(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Key
    String classKeyStr=DOMParsingTools.getStringAttribute(attrs,ClassDescriptionXMLConstants.CLASS_KEY_ATTR,null);
    CharacterClass characterClass=CharacterClass.getByKey(classKeyStr);
    ClassDescription description=new ClassDescription(characterClass);
    // Icon ID
    int iconId=DOMParsingTools.getIntAttribute(attrs,ClassDescriptionXMLConstants.CLASS_ICON_ID_ATTR,0);
    description.setIconId(iconId);
    // Small icon ID
    int smallIconId=DOMParsingTools.getIntAttribute(attrs,ClassDescriptionXMLConstants.CLASS_SMALL_ICON_ID_ATTR,0);
    description.setSmallIconId(smallIconId);

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
      description.addTrait(classTrait);
    }
    // Traits tree
    Element traitTreeTag=DOMParsingTools.getChildTagByName(root,ClassDescriptionXMLConstants.TRAIT_TREE_TAG);
    if (traitTreeTag!=null)
    {
      TraitTree tree=parseTraitTree(traitTreeTag);
      description.setTraitTree(tree);
    }
    return description;
  }

  private static TraitTree parseTraitTree(Element root)
  {
    TraitTree tree=new TraitTree();
    List<Element> branchTags=DOMParsingTools.getChildTagsByName(root,ClassDescriptionXMLConstants.TRAIT_TREE_BRANCH_TAG);
    for(Element branchTag : branchTags)
    {
      String name=DOMParsingTools.getStringAttribute(branchTag.getAttributes(),ClassDescriptionXMLConstants.TRAIT_TREE_BRANCH_NAME_ATTR,null);
      TraitTreeBranch branch=new TraitTreeBranch(name);
      tree.addBranch(branch);
      // Progression
      Element progressionTag=DOMParsingTools.getChildTagByName(branchTag,ClassDescriptionXMLConstants.PROGRESSION_TAG);
      if (progressionTag!=null)
      {
        TraitTreeProgression progression=branch.getProgression();
        List<Element> stepTags=DOMParsingTools.getChildTagsByName(progressionTag,ClassDescriptionXMLConstants.STEP_TAG);
        for(Element stepTag : stepTags)
        {
          NamedNodeMap stepAttrs=stepTag.getAttributes();
          // Required points
          int requiredPoints=DOMParsingTools.getIntAttribute(stepAttrs,ClassDescriptionXMLConstants.STEP_REQUIRED_POINTS_ATTR,0);
          // Trait ID
          int traitId=DOMParsingTools.getIntAttribute(stepAttrs,ClassDescriptionXMLConstants.STEP_TRAIT_ID_ATTR,0);
          TraitDescription trait=TraitsManager.getInstance().getTrait(traitId);
          progression.addStep(requiredPoints,trait);
        }
      }
      // Cells
      Element cellsTag=DOMParsingTools.getChildTagByName(branchTag,ClassDescriptionXMLConstants.CELLS_TAG);
      if (cellsTag!=null)
      {
        List<Element> cellTags=DOMParsingTools.getChildTagsByName(cellsTag,ClassDescriptionXMLConstants.CELL_TAG);
        for(Element cellTag : cellTags)
        {
          NamedNodeMap cellAttrs=cellTag.getAttributes();
          // Cell ID
          String cellId=DOMParsingTools.getStringAttribute(cellAttrs,ClassDescriptionXMLConstants.CELL_ID_ATTR,null);
          // Trait ID
          int traitId=DOMParsingTools.getIntAttribute(cellAttrs,ClassDescriptionXMLConstants.CELL_TRAIT_ID_ATTR,0);
          TraitDescription trait=TraitsManager.getInstance().getTrait(traitId);
          branch.setCell(cellId,trait);
        }
      }
    }
    return tree;
  }
}
