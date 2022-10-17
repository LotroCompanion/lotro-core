package delta.games.lotro.character.status.traits.skirmish.io.xml;

import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.status.traits.skirmish.SkirmishTraitsStatus;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.character.traits.TraitsManager;
import delta.games.lotro.common.enums.TraitNature;

/**
 * Writes a trait tree status to an XML document.
 * @author DAM
 */
public class SkirmishTraitsStatusXMLWriter
{
  /**
   * Write skirmish traits status attributes.
   * @param hd Output stream.
   * @param status Data to write.
   * @param statusAttrs Attributes to write to. 
   * @throws SAXException If an error occurs.
   */
  public static void writeTreeAttributes(TransformerHandler hd, SkirmishTraitsStatus status, AttributesImpl statusAttrs) throws SAXException
  {
    hd.startElement("","",SkirmishTraitsStatusXMLConstants.SKIRMISH_TRAITS_STATUS_TAG,new AttributesImpl());
    // Slotted traits
    writeSlottedTraits(hd,status);
    // Trait ranks
    writeTraitRanks(hd,status);
    hd.endElement("","",SkirmishTraitsStatusXMLConstants.SKIRMISH_TRAITS_STATUS_TAG);
  }

  private static void writeSlottedTraits(TransformerHandler hd, SkirmishTraitsStatus status) throws SAXException
  {
    List<TraitNature> natures=status.getTraitNatures();
    for(TraitNature nature : natures)
    {
      int[] traitIDs=status.getSlottedTraits(nature);
      AttributesImpl slottedTraitsAttrs=new AttributesImpl();
      // Nature ID
      slottedTraitsAttrs.addAttribute("","",SkirmishTraitsStatusXMLConstants.SLOTTED_TRAITS_NATURE_ID_ATTR,XmlWriter.CDATA,String.valueOf(nature.getCode()));
      // Nature name
      slottedTraitsAttrs.addAttribute("","",SkirmishTraitsStatusXMLConstants.SLOTTED_TRAITS_NATURE_NAME_ATTR,XmlWriter.CDATA,nature.getLabel());
      // Slots count
      int count=traitIDs.length;
      slottedTraitsAttrs.addAttribute("","",SkirmishTraitsStatusXMLConstants.SLOTTED_TRAITS_SLOTS_COUNT_ATTR,XmlWriter.CDATA,String.valueOf(count));
      hd.startElement("","",SkirmishTraitsStatusXMLConstants.SLOTTED_TRAITS_TAG,slottedTraitsAttrs);
      writeSlottedTraits(hd,traitIDs);
      hd.endElement("","",SkirmishTraitsStatusXMLConstants.SLOTTED_TRAITS_TAG);
    }
  }

  private static void writeSlottedTraits(TransformerHandler hd, int[] traitIDs) throws SAXException
  {
    int nb=traitIDs.length;
    for(int i=0;i<nb;i++)
    {
      int traitID=traitIDs[i];
      if (traitID==0)
      {
        continue;
      }
      AttributesImpl slottedTraitAttrs=new AttributesImpl();
      // Index
      slottedTraitAttrs.addAttribute("","",SkirmishTraitsStatusXMLConstants.SLOTTED_TRAIT_INDEX_ATTR,XmlWriter.CDATA,String.valueOf(i));
      // Trait ID
      slottedTraitAttrs.addAttribute("","",SkirmishTraitsStatusXMLConstants.SLOTTED_TRAIT_ID_ATTR,XmlWriter.CDATA,String.valueOf(traitID));
      // Trait name
      TraitsManager traitsMgr=TraitsManager.getInstance();
      TraitDescription traitDescription=traitsMgr.getTrait(traitID);
      String traitName=(traitDescription!=null)?traitDescription.getName():"";
      slottedTraitAttrs.addAttribute("","",SkirmishTraitsStatusXMLConstants.SLOTTED_TRAIT_NAME_ATTR,XmlWriter.CDATA,traitName);
      hd.startElement("","",SkirmishTraitsStatusXMLConstants.SLOTTED_TRAIT_TAG,slottedTraitAttrs);
      hd.endElement("","",SkirmishTraitsStatusXMLConstants.SLOTTED_TRAIT_TAG);
    }
  }

  private static void writeTraitRanks(TransformerHandler hd, SkirmishTraitsStatus status) throws SAXException
  {
    List<Integer> managedTraits=status.getManagedTraits();
    for(Integer managedTrait : managedTraits)
    {
      AttributesImpl slottedTraitAttrs=new AttributesImpl();
      // Trait ID
      int traitID=managedTrait.intValue();
      slottedTraitAttrs.addAttribute("","",SkirmishTraitsStatusXMLConstants.TRAIT_ID_ATTR,XmlWriter.CDATA,String.valueOf(traitID));
      // Trait name
      TraitsManager traitsMgr=TraitsManager.getInstance();
      TraitDescription traitDescription=traitsMgr.getTrait(traitID);
      String traitName=(traitDescription!=null)?traitDescription.getName():"";
      slottedTraitAttrs.addAttribute("","",SkirmishTraitsStatusXMLConstants.TRAIT_NAME_ATTR,XmlWriter.CDATA,traitName);
      // Trait rank
      int rank=status.getTraitRank(traitID);
      slottedTraitAttrs.addAttribute("","",SkirmishTraitsStatusXMLConstants.TRAIT_RANK_ATTR,XmlWriter.CDATA,String.valueOf(rank));
      hd.startElement("","",SkirmishTraitsStatusXMLConstants.TRAIT_TAG,slottedTraitAttrs);
      hd.endElement("","",SkirmishTraitsStatusXMLConstants.TRAIT_TAG);
    }
  }
}
