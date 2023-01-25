package delta.games.lotro.character.status.traitTree.io.xml;

import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.classes.ClassesManager;
import delta.games.lotro.character.classes.traitTree.TraitTree;
import delta.games.lotro.character.classes.traitTree.TraitTreeBranch;
import delta.games.lotro.character.classes.traitTree.TraitTreesManager;
import delta.games.lotro.character.classes.traitTree.setup.io.xml.TraitTreeSetupXMLConstants;
import delta.games.lotro.character.status.traitTree.TraitTreeStatus;

/**
 * Parser for trait tree statuses stored in XML.
 * @author DAM
 */
public class TraitTreeStatusXMLParser
{
  /**
   * Parse a trait tree status from an XML document.
   * @param root Root tag.
   * @return the loaded trait tree status.
   */
  public static TraitTreeStatus parseTraitTreeStatus(Element root)
  {
    TraitTree traitTree=findTraitTree(root);
    if (traitTree==null)
    {
      return null;
    }
    TraitTreeStatus status=new TraitTreeStatus(traitTree);
    NamedNodeMap attrs=root.getAttributes();
    // Branch
    int branchCode=DOMParsingTools.getIntAttribute(attrs,TraitTreeStatusXMLConstants.TRAIT_TREE_STATUS_BRANCH_ID_ATTR,0);
    if (branchCode!=0)
    {
      TraitTreeBranch branch=traitTree.getBranchByCode(branchCode);
      if (branch!=null)
      {
        status.setSelectedBranch(branch);
      }
    }
    // Cost
    int cost=DOMParsingTools.getIntAttribute(attrs,TraitTreeStatusXMLConstants.TRAIT_TREE_STATUS_COST_ATTR,0);
    status.setCost(cost);
    // Total points
    int totalPoints=DOMParsingTools.getIntAttribute(attrs,TraitTreeStatusXMLConstants.TRAIT_TREE_STATUS_TOTAL_POINTS_ATTR,0);
    status.setTotalPoints(totalPoints);

    // Traits
    List<Element> traitTags=DOMParsingTools.getChildTagsByName(root,TraitTreeStatusXMLConstants.TRAIT_TAG);
    for(Element traitTag : traitTags)
    {
      NamedNodeMap traitAttrs=traitTag.getAttributes();
      int traidId=DOMParsingTools.getIntAttribute(traitAttrs,TraitTreeStatusXMLConstants.TRAIT_ID_ATTR,0);
      int rank=DOMParsingTools.getIntAttribute(traitAttrs,TraitTreeStatusXMLConstants.TRAIT_RANK_ATTR,0);
      if (rank>0)
      {
        status.setRankForTrait(traidId,rank);
      }
    }
    return status;
  }

  /**
   * Find the trait tree to use using:
   * <ul>
   * <li>trait tree ID, or
   * <li>trait tree key (class)
   * </ul>
   * @param root Tag to read from.
   * @return A trait tree or <code>null</code> if not found.
   */
  private static TraitTree findTraitTree(Element root)
  {
    TraitTree traitTree=null;
    NamedNodeMap attrs=root.getAttributes();
    // Tree ID
    int treeId=DOMParsingTools.getIntAttribute(attrs,TraitTreeStatusXMLConstants.TRAIT_TREE_STATUS_TREE_ID_ATTR,0);
    if (treeId!=0)
    {
      TraitTreesManager traitTreesMgr=TraitTreesManager.getInstance();
      traitTree=traitTreesMgr.getTraitTree(treeId);
    }
    if (traitTree==null)
    {
      // Class
      String classKey=DOMParsingTools.getStringAttribute(attrs,TraitTreeSetupXMLConstants.TRAIT_TREE_SETUP_CLASS_ATTR,null);
      // Build trait tree setup
      ClassDescription classDescription=ClassesManager.getInstance().getCharacterClassByKey(classKey);
      if (classDescription!=null)
      {
        traitTree=classDescription.getTraitTree();
      }
    }
    return traitTree;
  }
}
