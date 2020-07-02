package delta.games.lotro.character.classes.traitTree.setup.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.classes.ClassesManager;
import delta.games.lotro.character.classes.traitTree.TraitTree;
import delta.games.lotro.character.classes.traitTree.TraitTreeBranch;
import delta.games.lotro.character.classes.traitTree.TraitTreeStatus;
import delta.games.lotro.character.classes.traitTree.setup.TraitTreeSetup;
import delta.games.lotro.common.CharacterClass;

/**
 * Parser for trait tree setups stored in XML.
 * @author DAM
 */
public class TraitTreeSetupXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed setup or <code>null</code>.
   */
  public TraitTreeSetup parseXML(File source)
  {
    TraitTreeSetup ret=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      ret=parseSetup(root);
    }
    return ret;
  }

  private TraitTreeSetup parseSetup(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();

    // Class
    String className=DOMParsingTools.getStringAttribute(attrs,TraitTreeSetupXMLConstants.TRAIT_TREE_SETUP_CLASS_ATTR,null);
    CharacterClass characterClass=CharacterClass.getByKey(className);

    // Build trait tree setup
    ClassDescription classDescription=ClassesManager.getInstance().getClassDescription(characterClass);
    if (classDescription==null)
    {
      return null;
    }
    TraitTree traitTree=classDescription.getTraitTree();
    TraitTreeSetup setup=new TraitTreeSetup(traitTree);
    TraitTreeStatus status=setup.getStatus();

    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,TraitTreeSetupXMLConstants.TRAIT_TREE_SETUP_NAME_ATTR,"");
    setup.setName(name);
    // Branch
    int branchCode=DOMParsingTools.getIntAttribute(attrs,TraitTreeSetupXMLConstants.TRAIT_TREE_SETUP_BRANCH_ATTR,0);
    TraitTreeBranch branch=traitTree.getBranchByCode(branchCode);
    if (branch!=null)
    {
      status.setSelectedBranch(branch);
    }
    // Cost
    int cost=DOMParsingTools.getIntAttribute(attrs,TraitTreeSetupXMLConstants.TRAIT_TREE_SETUP_COST_ATTR,0);
    setup.setCost(cost);
    // Description
    String description=DOMParsingTools.getStringAttribute(attrs,TraitTreeSetupXMLConstants.TRAIT_TREE_SETUP_DESCRIPTION_ATTR,"");
    setup.setDescription(description);

    // Traits
    List<Element> traitTags=DOMParsingTools.getChildTagsByName(root,TraitTreeSetupXMLConstants.TRAIT_TAG);
    for(Element traitTag : traitTags)
    {
      NamedNodeMap traitAttrs=traitTag.getAttributes();
      int traidId=DOMParsingTools.getIntAttribute(traitAttrs,TraitTreeSetupXMLConstants.TRAIT_ID_ATTR,0);
      int rank=DOMParsingTools.getIntAttribute(traitAttrs,TraitTreeSetupXMLConstants.TRAIT_RANK_ATTR,1);
      status.setRankForTrait(traidId,rank);
    }
    return setup;
  }
}
