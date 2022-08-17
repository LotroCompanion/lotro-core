package delta.games.lotro.character.classes.traitTree.setup.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.classes.traitTree.TraitTree;
import delta.games.lotro.character.classes.traitTree.TraitTreeBranch;
import delta.games.lotro.character.classes.traitTree.TraitTreeStatus;
import delta.games.lotro.character.classes.traitTree.setup.TraitTreeSetup;
import delta.games.lotro.character.traits.TraitDescription;

/**
 * Writes trait tree setups to XML files.
 * @author DAM
 */
public class TraitTreeSetupXMLWriter
{
  /**
   * Write a trait tree setup to a XML file.
   * @param outFile Output file.
   * @param setup Data to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final TraitTreeSetup setup, String encoding)
  {
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        write(hd,setup);
      }
    };
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void write(TransformerHandler hd, TraitTreeSetup setup) throws SAXException
  {
    AttributesImpl setupAttrs=new AttributesImpl();
    // Name
    String name=setup.getName();
    setupAttrs.addAttribute("","",TraitTreeSetupXMLConstants.TRAIT_TREE_SETUP_NAME_ATTR,XmlWriter.CDATA,name);
    // Key
    String key=setup.getKey();
    setupAttrs.addAttribute("","",TraitTreeSetupXMLConstants.TRAIT_TREE_SETUP_CLASS_ATTR,XmlWriter.CDATA,key);
    // Branch
    TraitTreeBranch branch=setup.getSelectedBranch();
    if (branch!=null)
    {
      int branchCode=branch.getCode();
      setupAttrs.addAttribute("","",TraitTreeSetupXMLConstants.TRAIT_TREE_SETUP_BRANCH_ATTR,XmlWriter.CDATA,String.valueOf(branchCode));
    }
    // Cost
    int cost=setup.getCost();
    setupAttrs.addAttribute("","",TraitTreeSetupXMLConstants.TRAIT_TREE_SETUP_COST_ATTR,XmlWriter.CDATA,String.valueOf(cost));
    // Description
    String description=setup.getDescription();
    if (description.length()>0)
    {
      setupAttrs.addAttribute("","",TraitTreeSetupXMLConstants.TRAIT_TREE_SETUP_DESCRIPTION_ATTR,XmlWriter.CDATA,description);
    }
    hd.startElement("","",TraitTreeSetupXMLConstants.TRAIT_TREE_SETUP_TAG,setupAttrs);

    // Traits
    TraitTree tree=setup.getTraitTree();
    List<TraitDescription> traits=tree.getAllTraits();
    TraitTreeStatus status=setup.getStatus();
    for(TraitDescription trait : traits)
    {
      int traidId=trait.getIdentifier();
      Integer rank=status.getRankForTrait(traidId);
      if (rank!=null)
      {
        AttributesImpl traitAttrs=new AttributesImpl();
        // Trait ID
        traitAttrs.addAttribute("","",TraitTreeSetupXMLConstants.TRAIT_ID_ATTR,XmlWriter.CDATA,String.valueOf(traidId));
        // Trait rank
        traitAttrs.addAttribute("","",TraitTreeSetupXMLConstants.TRAIT_RANK_ATTR,XmlWriter.CDATA,rank.toString());
        hd.startElement("","",TraitTreeSetupXMLConstants.TRAIT_TAG,traitAttrs);
        hd.endElement("","",TraitTreeSetupXMLConstants.TRAIT_TAG);
      }
    }
    hd.endElement("","",TraitTreeSetupXMLConstants.TRAIT_TREE_SETUP_TAG);
  }
}
