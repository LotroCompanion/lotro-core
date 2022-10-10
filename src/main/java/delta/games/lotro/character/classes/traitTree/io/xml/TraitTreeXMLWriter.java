package delta.games.lotro.character.classes.traitTree.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.classes.traitTree.TraitTree;
import delta.games.lotro.character.classes.traitTree.TraitTreeBranch;
import delta.games.lotro.character.classes.traitTree.TraitTreeCell;
import delta.games.lotro.character.classes.traitTree.TraitTreeCellDependency;
import delta.games.lotro.character.classes.traitTree.TraitTreeProgression;
import delta.games.lotro.character.traits.TraitDescription;

/**
 * Writes trait trees to XML files.
 * @author DAM
 */
public class TraitTreeXMLWriter
{
  /**
   * Write a trait tree to a XML file.
   * @param toFile File to write to.
   * @param traitTrees Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean write(File toFile, final List<TraitTree> traitTrees)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",TraitTreeXMLConstants.TRAIT_TREES_TAG,new AttributesImpl());
        for(TraitTree traitTree : traitTrees)
        {
          writeTraitTree(hd,traitTree);
        }
        hd.endElement("","",TraitTreeXMLConstants.TRAIT_TREES_TAG);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private static void writeTraitTree(TransformerHandler hd, TraitTree tree) throws SAXException
  {
    AttributesImpl mainAttrs=new AttributesImpl();
    // ID
    int id=tree.getIdentifier();
    mainAttrs.addAttribute("","",TraitTreeXMLConstants.TRAIT_TREE_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Key
    String key=tree.getKey();
    if (key!=null)
    {
      mainAttrs.addAttribute("","",TraitTreeXMLConstants.TRAIT_TREE_KEY_ATTR,XmlWriter.CDATA,key);
    }
    hd.startElement("","",TraitTreeXMLConstants.TRAIT_TREE_TAG,mainAttrs);
    List<TraitTreeBranch> branches=tree.getBranches();
    for(TraitTreeBranch branch : branches)
    {
      writeTraitTreeBranch(hd,branch);
    }
    hd.endElement("","",TraitTreeXMLConstants.TRAIT_TREE_TAG);
  }

  private static void writeTraitTreeBranch(TransformerHandler hd, TraitTreeBranch branch) throws SAXException
  {
    AttributesImpl branchAttrs=new AttributesImpl();
    // Code
    int code=branch.getCode();
    branchAttrs.addAttribute("","",TraitTreeXMLConstants.TRAIT_TREE_BRANCH_CODE_ATTR,XmlWriter.CDATA,String.valueOf(code));
    // Name
    String name=branch.getName();
    branchAttrs.addAttribute("","",TraitTreeXMLConstants.TRAIT_TREE_BRANCH_NAME_ATTR,XmlWriter.CDATA,name);
    // Main trait ID
    TraitDescription mainTrait=branch.getMainTrait();
    if (mainTrait!=null)
    {
      int mainTraitId=mainTrait.getIdentifier();
      branchAttrs.addAttribute("","",TraitTreeXMLConstants.TRAIT_TREE_BRANCH_TRAIT_ATTR,XmlWriter.CDATA,String.valueOf(mainTraitId));
    }
    hd.startElement("","",TraitTreeXMLConstants.TRAIT_TREE_BRANCH_TAG,branchAttrs);
    // Progression
    TraitTreeProgression progression=branch.getProgression();
    hd.startElement("","",TraitTreeXMLConstants.PROGRESSION_TAG,new AttributesImpl());
    List<Integer> steps=progression.getSteps();
    List<TraitDescription> traits=progression.getTraits();
    int nbItems=Math.min(steps.size(),traits.size());
    for(int i=0;i<nbItems;i++)
    {
      AttributesImpl stepAttrs=new AttributesImpl();
      // Required points
      stepAttrs.addAttribute("","",TraitTreeXMLConstants.STEP_REQUIRED_POINTS_ATTR,XmlWriter.CDATA,steps.get(i).toString());
      TraitDescription trait=traits.get(i);
      // Trait ID
      int traitId=trait.getIdentifier();
      stepAttrs.addAttribute("","",TraitTreeXMLConstants.STEP_TRAIT_ID_ATTR,XmlWriter.CDATA,String.valueOf(traitId));
      // Trait name
      String traitName=trait.getName();
      stepAttrs.addAttribute("","",TraitTreeXMLConstants.CELL_TRAIT_NAME_ATTR,XmlWriter.CDATA,traitName);
      hd.startElement("","",TraitTreeXMLConstants.STEP_TAG,stepAttrs);
      hd.endElement("","",TraitTreeXMLConstants.STEP_TAG);
    }
    hd.endElement("","",TraitTreeXMLConstants.PROGRESSION_TAG);
    // Cells
    hd.startElement("","",TraitTreeXMLConstants.CELLS_TAG,new AttributesImpl());
    List<String> cellIds=branch.getCells();
    for(String cellId : cellIds)
    {
      AttributesImpl cellAttrs=new AttributesImpl();
      // Cell ID
      cellAttrs.addAttribute("","",TraitTreeXMLConstants.CELL_ID_ATTR,XmlWriter.CDATA,cellId);
      TraitTreeCell cell=branch.getCell(cellId);
      TraitDescription trait=cell.getTrait();
      // Trait ID
      int traitId=trait.getIdentifier();
      cellAttrs.addAttribute("","",TraitTreeXMLConstants.CELL_TRAIT_ID_ATTR,XmlWriter.CDATA,String.valueOf(traitId));
      // Trait name
      String traitName=trait.getName();
      cellAttrs.addAttribute("","",TraitTreeXMLConstants.CELL_TRAIT_NAME_ATTR,XmlWriter.CDATA,traitName);
      hd.startElement("","",TraitTreeXMLConstants.CELL_TAG,cellAttrs);
      // Dependencies
      List<TraitTreeCellDependency> dependencies=cell.getDependencies();
      for(TraitTreeCellDependency dependency : dependencies)
      {
        AttributesImpl cellDepAttrs=new AttributesImpl();
        // Cell ID
        String depCellId=dependency.getCellId();
        cellDepAttrs.addAttribute("","",TraitTreeXMLConstants.CELL_DEPENDENCY_CELL_ID_ATTR,XmlWriter.CDATA,depCellId);
        // Rank
        int rank=dependency.getRank();
        cellDepAttrs.addAttribute("","",TraitTreeXMLConstants.CELL_DEPENDENCY_RANK_ATTR,XmlWriter.CDATA,String.valueOf(rank));
        hd.startElement("","",TraitTreeXMLConstants.CELL_DEPENDENCY_TAG,cellDepAttrs);
        hd.endElement("","",TraitTreeXMLConstants.CELL_DEPENDENCY_TAG);
      }
      hd.endElement("","",TraitTreeXMLConstants.CELL_TAG);
    }
    hd.endElement("","",TraitTreeXMLConstants.CELLS_TAG);
    hd.endElement("","",TraitTreeXMLConstants.TRAIT_TREE_BRANCH_TAG);
  }
}
