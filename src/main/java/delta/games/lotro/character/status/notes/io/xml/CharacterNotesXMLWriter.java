package delta.games.lotro.character.status.notes.io.xml;

import java.io.File;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.status.notes.CharacterNotes;

/**
 * Writes character notes to an XML file.
 * @author DAM
 */
public class CharacterNotesXMLWriter
{
  /**
   * Write character notes to an XML file.
   * @param outFile Output file.
   * @param data Data to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final CharacterNotes data)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeNotes(hd,data);
      }
    };
    boolean ret=helper.write(outFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  /**
   * Write character notes to the given XML stream.
   * @param hd XML output stream.
   * @param data Data to write.
   * @throws SAXException If an error occurs.
   */
  private void writeNotes(TransformerHandler hd, CharacterNotes data) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Text
    String text=data.getText();
    attrs.addAttribute("","",CharacterNotesXMLConstants.NOTES_TEXT_ATTR,XmlWriter.CDATA,text);
    hd.startElement("","",CharacterNotesXMLConstants.NOTES_TAG,attrs);
    hd.endElement("","",CharacterNotesXMLConstants.NOTES_TAG);
  }
}
