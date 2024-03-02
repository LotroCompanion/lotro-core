package delta.games.lotro.character.status.traits.shared.io.xml;

import java.io.File;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.status.traits.shared.SlottedTraitsStatus;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.character.traits.TraitsManager;

/**
 * Writes a slotted trait status to an XML document.
 * @author DAM
 */
public class SlottedTraitsStatusXMLWriter
{
  /**
   * Write a status to an XML file.
   * @param outFile Output file.
   * @param status Status to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final SlottedTraitsStatus status, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeSlottedTraitsStatus(hd,status);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  /**
   * Write status of the slotted traits.
   * @param hd Output stream.
   * @param status Data to write.
   * @throws SAXException If an error occurs.
   */
  public static void writeSlottedTraitsStatus(TransformerHandler hd, SlottedTraitsStatus status) throws SAXException
  {
    hd.startElement("","",SlottedTraitsStatusXMLConstants.TRAITS_TAG,new AttributesImpl());
    // Available traits
    for(Integer traitID : status.getTraitIDs())
    {
      AttributesImpl availableTraitAttrs=new AttributesImpl();
      availableTraitAttrs.addAttribute("","",SlottedTraitsStatusXMLConstants.TRAIT_SLOT_TRAIT_ID_ATTR,XmlWriter.CDATA,traitID.toString());
      TraitDescription trait=TraitsManager.getInstance().getTrait(traitID.intValue());
      if (trait!=null)
      {
        String traitName=trait.getName();
        if (traitName!=null)
        {
          availableTraitAttrs.addAttribute("","",SlottedTraitsStatusXMLConstants.TRAIT_SLOT_TRAIT_NAME_ATTR,XmlWriter.CDATA,traitName);
        }
      }
      hd.startElement("","",SlottedTraitsStatusXMLConstants.TRAIT_AVAILABLE_TAG,availableTraitAttrs);
      hd.endElement("","",SlottedTraitsStatusXMLConstants.TRAIT_AVAILABLE_TAG);
    }
    // Slots status
    int slotsCount=status.getSlotsCount();
    for(int i=0;i<slotsCount;i++)
    {
      AttributesImpl slotAttrs=new AttributesImpl();
      // Index
      slotAttrs.addAttribute("","",SlottedTraitsStatusXMLConstants.TRAIT_SLOT_INDEX_ATTR,XmlWriter.CDATA,String.valueOf(i));
      int traitID=status.getTraitAt(i);
      if (traitID!=0)
      {
        slotAttrs.addAttribute("","",SlottedTraitsStatusXMLConstants.TRAIT_SLOT_TRAIT_ID_ATTR,XmlWriter.CDATA,String.valueOf(traitID));
        TraitDescription trait=TraitsManager.getInstance().getTrait(traitID);
        if (trait!=null)
        {
          String traitName=trait.getName();
          if (traitName!=null)
          {
            slotAttrs.addAttribute("","",SlottedTraitsStatusXMLConstants.TRAIT_SLOT_TRAIT_NAME_ATTR,XmlWriter.CDATA,traitName);
          }
        }
      }
      hd.startElement("","",SlottedTraitsStatusXMLConstants.TRAIT_SLOT_TAG,slotAttrs);
      hd.endElement("","",SlottedTraitsStatusXMLConstants.TRAIT_SLOT_TAG);
    }
    hd.endElement("","",SlottedTraitsStatusXMLConstants.TRAITS_TAG);
  }
}
