package delta.games.lotro.character.status.traitTree.io.xml;

import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.classes.traitTree.TraitTree;
import delta.games.lotro.character.classes.traitTree.TraitTreeBranch;
import delta.games.lotro.character.status.traitTree.TraitTreeStatus;
import delta.games.lotro.character.traits.TraitDescription;

/**
 * Writes a trait tree status to an XML document.
 * @author DAM
 */
public class TraitTreeStatusXMLWriter
{
  /**
   * Write trait tree status attributes.
   * @param hd Output stream.
   * @param status Data to write.
   * @param statusAttrs Attributes to write to. 
   * @throws SAXException If an error occurs.
   */
  public static void writeTreeAttributes(TransformerHandler hd, TraitTreeStatus status, AttributesImpl statusAttrs) throws SAXException
  {
    TraitTree tree=status.getTraitTree();
    // Tree ID
    int treeID=tree.getIdentifier();
    statusAttrs.addAttribute("","",TraitTreeStatusXMLConstants.TRAIT_TREE_STATUS_TREE_ID_ATTR,XmlWriter.CDATA,String.valueOf(treeID));
    // Key
    String key=tree.getKey();
    statusAttrs.addAttribute("","",TraitTreeStatusXMLConstants.TRAIT_TREE_STATUS_KEY_ATTR,XmlWriter.CDATA,key);
    // Branch ID
    TraitTreeBranch selectedBranch=status.getSelectedBranch();
    if (selectedBranch!=null)
    {
      int branchID=selectedBranch.getCode();
      statusAttrs.addAttribute("","",TraitTreeStatusXMLConstants.TRAIT_TREE_STATUS_BRANCH_ID_ATTR,XmlWriter.CDATA,String.valueOf(branchID));
    }
    // Cost
    int cost=status.getCost();
    if (cost!=0)
    {
      statusAttrs.addAttribute("","",TraitTreeStatusXMLConstants.TRAIT_TREE_STATUS_COST_ATTR,XmlWriter.CDATA,String.valueOf(cost));
    }
    // Total points
    int totalPoints=status.getTotalPoints();
    if (totalPoints!=0)
    {
      statusAttrs.addAttribute("","",TraitTreeStatusXMLConstants.TRAIT_TREE_STATUS_TOTAL_POINTS_ATTR,XmlWriter.CDATA,String.valueOf(totalPoints));
    }
  }

  /**
   * Write status of the traits in the given traits tree status.
   * @param hd Output stream.
   * @param status Data to write.
   * @throws SAXException If an error occurs.
   */
  public static void writeTraitsStatus(TransformerHandler hd, TraitTreeStatus status) throws SAXException
  {
    // Traits
    TraitTree tree=status.getTraitTree();
    List<TraitDescription> traits=tree.getAllTraits();
    for(TraitDescription trait : traits)
    {
      int traidId=trait.getIdentifier();
      Integer rank=status.getRankForTrait(traidId);
      if (rank!=null)
      {
        AttributesImpl traitAttrs=new AttributesImpl();
        // Trait ID
        traitAttrs.addAttribute("","",TraitTreeStatusXMLConstants.TRAIT_ID_ATTR,XmlWriter.CDATA,String.valueOf(traidId));
        // Trait name
        String name=trait.getName();
        traitAttrs.addAttribute("","",TraitTreeStatusXMLConstants.TRAIT_NAME_ATTR,XmlWriter.CDATA,name);
        // Trait rank
        traitAttrs.addAttribute("","",TraitTreeStatusXMLConstants.TRAIT_RANK_ATTR,XmlWriter.CDATA,rank.toString());
        hd.startElement("","",TraitTreeStatusXMLConstants.TRAIT_TAG,traitAttrs);
        hd.endElement("","",TraitTreeStatusXMLConstants.TRAIT_TAG);
      }
    }
  }

  /**
   * Write trait tree status data.
   * @param hd Output stream.
   * @param status Data to write.
   * @throws SAXException If an error occurs.
   */
  public static void write(TransformerHandler hd, TraitTreeStatus status) throws SAXException
  {
    AttributesImpl statusAttrs=new AttributesImpl();
    writeTreeAttributes(hd,status,statusAttrs);
    hd.startElement("","",TraitTreeStatusXMLConstants.TRAIT_TREE_STATUS_TAG,statusAttrs);
    writeTraitsStatus(hd,status);
    hd.endElement("","",TraitTreeStatusXMLConstants.TRAIT_TREE_STATUS_TAG);
  }
}
