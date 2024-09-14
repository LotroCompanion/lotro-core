package delta.games.lotro.common.inductions.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.common.inductions.Induction;
import delta.games.lotro.common.properties.ModPropertyList;
import delta.games.lotro.common.properties.io.ModPropertyListIO;

/**
 * Writes inductions to XML files.
 * @author DAM
 */
public class InductionXMLWriter
{
  /**
   * Write a file with inductions.
   * @param toFile Output file.
   * @param inductions Inductions to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeInductionsFile(File toFile, List<Induction> inductions)
  {
    InductionXMLWriter writer=new InductionXMLWriter();
    boolean ok=writer.writeInductions(toFile,inductions,EncodingNames.UTF_8);
    return ok;
  }

  /**
   * Write inductions to a XML file.
   * @param outFile Output file.
   * @param inductions Inductions to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  private boolean writeInductions(File outFile, final List<Induction> inductions, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeInductions(hd,inductions);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void writeInductions(TransformerHandler hd, List<Induction> inductions) throws SAXException
  {
    hd.startElement("","",InductionXMLConstants.INDUCTIONS_TAG,new AttributesImpl());
    for(Induction induction : inductions)
    {
      writeInduction(hd,induction);
    }
    hd.endElement("","",InductionXMLConstants.INDUCTIONS_TAG);
  }

  private void writeInduction(TransformerHandler hd, Induction induction) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Identifier
    int id=induction.getIdentifier();
    attrs.addAttribute("","",InductionXMLConstants.INIDUCTION_IDENTIFIER_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Duration
    float duration=induction.getDuration();
    attrs.addAttribute("","",InductionXMLConstants.INDUCTION_DURATION_ATTR,XmlWriter.CDATA,String.valueOf(duration));
    // Modifiers
    ModPropertyList addMods=induction.getAddMods();
    String addModsStr=ModPropertyListIO.asPersistentString(addMods);
    if (!addModsStr.isEmpty())
    {
      attrs.addAttribute("","",InductionXMLConstants.INDUCTION_ADD_MODIFIERS_ATTR,XmlWriter.CDATA,String.valueOf(addModsStr));
    }
    ModPropertyList multiplyMods=induction.getMultiplyMods();
    String multiplyModsStr=ModPropertyListIO.asPersistentString(multiplyMods);
    if (!multiplyModsStr.isEmpty())
    {
      attrs.addAttribute("","",InductionXMLConstants.INDUCTION_MULTIPLY_MODIFIERS_ATTR,XmlWriter.CDATA,String.valueOf(multiplyModsStr));
    }
    hd.startElement("","",InductionXMLConstants.INDUCTION_TAG,attrs);
    hd.endElement("","",InductionXMLConstants.INDUCTION_TAG);
  }
}
