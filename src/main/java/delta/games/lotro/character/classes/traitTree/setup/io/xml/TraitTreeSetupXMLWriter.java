package delta.games.lotro.character.classes.traitTree.setup.io.xml;

import java.io.File;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.classes.traitTree.setup.TraitTreeSetup;
import delta.games.lotro.character.status.traitTree.io.xml.TraitTreeStatusXMLWriter;

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
    TraitTreeStatusXMLWriter.writeTreeAttributes(setup.getStatus(),setupAttrs);
    // Description
    String description=setup.getDescription();
    if (description.length()>0)
    {
      setupAttrs.addAttribute("","",TraitTreeSetupXMLConstants.TRAIT_TREE_SETUP_DESCRIPTION_ATTR,XmlWriter.CDATA,description);
    }
    hd.startElement("","",TraitTreeSetupXMLConstants.TRAIT_TREE_SETUP_TAG,setupAttrs);

    TraitTreeStatusXMLWriter.writeTraitsStatus(hd,setup.getStatus());
    hd.endElement("","",TraitTreeSetupXMLConstants.TRAIT_TREE_SETUP_TAG);
  }
}
