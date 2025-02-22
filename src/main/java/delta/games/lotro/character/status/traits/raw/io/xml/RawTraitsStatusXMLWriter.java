package delta.games.lotro.character.status.traits.raw.io.xml;

import java.io.File;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.status.traits.raw.RawTraitsStatus;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.character.traits.TraitsManager;

/**
 * Writes a raw trait status to an XML document.
 * @author DAM
 */
public class RawTraitsStatusXMLWriter
{
  /**
   * Write a status to an XML file.
   * @param outFile Output file.
   * @param status Status to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final RawTraitsStatus status, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeTraitsStatus(hd,status);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  /**
   * Write a traits status.
   * @param hd Output stream.
   * @param status Data to write.
   * @throws SAXException If an error occurs.
   */
  public static void writeTraitsStatus(TransformerHandler hd, RawTraitsStatus status) throws SAXException
  {
    if (status==null)
    {
      return;
    }
    hd.startElement("","",RawTraitsStatusXMLConstants.TRAITS_STATUS_TAG,new AttributesImpl());
    // Acquired traits
    for(Integer traitID : status.getKnownTraits())
    {
      TraitDescription trait=TraitsManager.getInstance().getTrait(traitID.intValue());
      if (trait!=null)
      {
        AttributesImpl attrs=new AttributesImpl();
        // ID
        attrs.addAttribute("","",RawTraitsStatusXMLConstants.TRAIT_ID_ATTR,XmlWriter.CDATA,traitID.toString());
        // Name
        String traitName=trait.getName();
        if (traitName!=null)
        {
          attrs.addAttribute("","",RawTraitsStatusXMLConstants.TRAIT_NAME_ATTR,XmlWriter.CDATA,traitName);
        }
        // Rank
        int rank=status.getTraitRank(traitID.intValue());
        attrs.addAttribute("","",RawTraitsStatusXMLConstants.TRAIT_RANK_ATTR,XmlWriter.CDATA,String.valueOf(rank));
        hd.startElement("","",RawTraitsStatusXMLConstants.TRAIT_TAG,attrs);
        hd.endElement("","",RawTraitsStatusXMLConstants.TRAIT_TAG);
      }
    }
    hd.endElement("","",RawTraitsStatusXMLConstants.TRAITS_STATUS_TAG);
  }
}
