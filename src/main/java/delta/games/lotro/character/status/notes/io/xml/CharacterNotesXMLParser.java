package delta.games.lotro.character.status.notes.io.xml;

import java.io.File;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.status.notes.CharacterNotes;

/**
 * Parser for character notes stored in XML.
 * @author DAM
 */
public class CharacterNotesXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed status or <code>null</code>.
   */
  public CharacterNotes parseXML(File source)
  {
    CharacterNotes ret=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      ret=parseNotes(root);
    }
    return ret;
  }

  private CharacterNotes parseNotes(Element root)
  {
    CharacterNotes ret=new CharacterNotes();
    NamedNodeMap attrs=root.getAttributes();
    String text=DOMParsingTools.getStringAttribute(attrs,CharacterNotesXMLConstants.NOTES_TEXT_ATTR,"");
    ret.setText(text);
    return ret;
  }
}
