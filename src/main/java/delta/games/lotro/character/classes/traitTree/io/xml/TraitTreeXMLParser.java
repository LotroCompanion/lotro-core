package delta.games.lotro.character.classes.traitTree.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.classes.traitTree.TraitTree;
import delta.games.lotro.character.classes.traitTree.TraitTreeBranch;
import delta.games.lotro.character.classes.traitTree.TraitTreeCell;
import delta.games.lotro.character.classes.traitTree.TraitTreeCellDependency;
import delta.games.lotro.character.classes.traitTree.TraitTreeProgression;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.character.traits.TraitsManager;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.common.enums.TraitTreeBranchType;
import delta.games.lotro.common.enums.TraitTreeType;
import delta.games.lotro.utils.i18n.I18nFacade;
import delta.games.lotro.utils.i18n.I18nRuntimeUtils;

/**
 * Parser for trait trees stored in XML.
 * @author DAM
 */
public class TraitTreeXMLParser
{
  private SingleLocaleLabelsManager _i18n;

  /**
   * Constructor.
   */
  public TraitTreeXMLParser()
  {
    _i18n=I18nFacade.getLabelsMgr("traitTrees");
  }

  /**
   * Parse a trait trees XML file.
   * @param source Source file.
   * @return List of parsed trait trees.
   */
  public List<TraitTree> parseTraitTreesFile(File source)
  {
    List<TraitTree> descriptions=new ArrayList<TraitTree>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> traitTreeTags=DOMParsingTools.getChildTagsByName(root,TraitTreeXMLConstants.TRAIT_TREE_TAG);
      for(Element traitTreeTag : traitTreeTags)
      {
        TraitTree traitTree=parseTraitTree(traitTreeTag);
        descriptions.add(traitTree);
      }
    }
    return descriptions;
  }

  private TraitTree parseTraitTree(Element root)
  {
    NamedNodeMap mainAttrs=root.getAttributes();
    // ID
    int id=DOMParsingTools.getIntAttribute(mainAttrs,TraitTreeXMLConstants.TRAIT_TREE_ID_ATTR,0);
    // Code
    int treeCode=DOMParsingTools.getIntAttribute(mainAttrs,TraitTreeXMLConstants.TRAIT_TREE_CODE_ATTR,0);
    LotroEnum<TraitTreeType> enumType=LotroEnumsRegistry.getInstance().get(TraitTreeType.class);
    TraitTreeType type=enumType.getEntry(treeCode);
    TraitTree tree=new TraitTree(id,type);

    TraitsManager traitsMgr=TraitsManager.getInstance();
    LotroEnum<TraitTreeBranchType> enumBranchType=LotroEnumsRegistry.getInstance().get(TraitTreeBranchType.class);
    List<Element> branchTags=DOMParsingTools.getChildTagsByName(root,TraitTreeXMLConstants.TRAIT_TREE_BRANCH_TAG);
    for(Element branchTag : branchTags)
    {
      NamedNodeMap branchAttrs=branchTag.getAttributes();
      int branchCode=DOMParsingTools.getIntAttribute(branchAttrs,TraitTreeXMLConstants.TRAIT_TREE_BRANCH_CODE_ATTR,0);
      TraitTreeBranchType branchType=enumBranchType.getEntry(branchCode);
      TraitTreeBranch branch=new TraitTreeBranch(branchType);
      tree.addBranch(branch);
      // Description
      String description=DOMParsingTools.getStringAttribute(branchAttrs,TraitTreeXMLConstants.TRAIT_TREE_BRANCH_DESCRIPTION_ATTR,"");
      description=I18nRuntimeUtils.getLabel(_i18n,description);
      branch.setDescription(description);
      // Main trait
      int mainTraitId=DOMParsingTools.getIntAttribute(branchAttrs,TraitTreeXMLConstants.TRAIT_TREE_BRANCH_TRAIT_ATTR,0);
      TraitDescription mainTrait=traitsMgr.getTrait(mainTraitId);
      branch.setMainTrait(mainTrait);
      // Enabled
      boolean enabled=DOMParsingTools.getBooleanAttribute(branchAttrs,TraitTreeXMLConstants.TRAIT_TREE_BRANCH_ENABLED_ATTR,true);
      branch.setEnabled(enabled);
      // Progression
      Element progressionTag=DOMParsingTools.getChildTagByName(branchTag,TraitTreeXMLConstants.PROGRESSION_TAG);
      if (progressionTag!=null)
      {
        TraitTreeProgression progression=branch.getProgression();
        List<Element> stepTags=DOMParsingTools.getChildTagsByName(progressionTag,TraitTreeXMLConstants.STEP_TAG);
        for(Element stepTag : stepTags)
        {
          NamedNodeMap stepAttrs=stepTag.getAttributes();
          // Required points
          int requiredPoints=DOMParsingTools.getIntAttribute(stepAttrs,TraitTreeXMLConstants.STEP_REQUIRED_POINTS_ATTR,0);
          // Trait ID
          int traitId=DOMParsingTools.getIntAttribute(stepAttrs,TraitTreeXMLConstants.STEP_TRAIT_ID_ATTR,0);
          TraitDescription trait=traitsMgr.getTrait(traitId);
          progression.addStep(requiredPoints,trait);
        }
      }
      // Cells
      Element cellsTag=DOMParsingTools.getChildTagByName(branchTag,TraitTreeXMLConstants.CELLS_TAG);
      if (cellsTag!=null)
      {
        List<Element> cellTags=DOMParsingTools.getChildTagsByName(cellsTag,TraitTreeXMLConstants.CELL_TAG);
        for(Element cellTag : cellTags)
        {
          NamedNodeMap cellAttrs=cellTag.getAttributes();
          // Cell ID
          String cellId=DOMParsingTools.getStringAttribute(cellAttrs,TraitTreeXMLConstants.CELL_ID_ATTR,null);
          // Trait ID
          int traitId=DOMParsingTools.getIntAttribute(cellAttrs,TraitTreeXMLConstants.CELL_TRAIT_ID_ATTR,0);
          TraitDescription trait=TraitsManager.getInstance().getTrait(traitId);
          TraitTreeCell cell=new TraitTreeCell(cellId,trait);
          // Dependencies
          List<Element> cellDependencyTags=DOMParsingTools.getChildTagsByName(cellTag,TraitTreeXMLConstants.CELL_DEPENDENCY_TAG);
          for(Element cellDependencyTag : cellDependencyTags)
          {
            NamedNodeMap cellDependencyAttrs=cellDependencyTag.getAttributes();
            String depCellId=DOMParsingTools.getStringAttribute(cellDependencyAttrs,TraitTreeXMLConstants.CELL_DEPENDENCY_CELL_ID_ATTR,null);
            int rank=DOMParsingTools.getIntAttribute(cellDependencyAttrs,TraitTreeXMLConstants.CELL_DEPENDENCY_RANK_ATTR,0);
            TraitTreeCellDependency cellDependency=new TraitTreeCellDependency(depCellId,rank);
            cell.addDependency(cellDependency);
          }
          branch.setCell(cellId,cell);
        }
      }
    }
    return tree;
  }
}
