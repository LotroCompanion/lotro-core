package delta.games.lotro.lore.agents.mobs.io.xml;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.games.lotro.lore.agents.AgentClassification;
import delta.games.lotro.lore.agents.io.xml.AgentsXMLIO;
import delta.games.lotro.lore.agents.mobs.MobLocation;
import delta.games.lotro.lore.agents.mobs.MobSelection;

/**
 * XML I/O for mob selections.
 * @author DAM
 */
public class MobSelectionXMLIO
{
  /**
   * Parse a mob selection from the given SAX attributes.
   * @param attrs Input attributes.
   * @return A mob selection or <code>null</code> if no data.
   */
  public static MobSelection parseMobSelection(Attributes attrs)
  {
    // Where
    MobLocation location=MobLocationXMLIO.parseMobLocation(attrs);
    // What
    AgentClassification what=new AgentClassification();
    AgentsXMLIO.parseClassification(what,attrs);
    MobSelection selection=new MobSelection(location,what);
    return selection;
  }

  /**
   * Write a mob selection tag.
   * @param tag Tag to use.
   * @param hd Output stream.
   * @param spec Data to write.
   * @throws SAXException If a problem occurs.
   */
  public static void writeMobSelection(String tag, TransformerHandler hd, MobSelection spec) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Where
    MobLocation location=spec.getWhere();
    MobLocationXMLIO.writeMobLocation(attrs,location);
    // What
    AgentClassification classification=spec.getWhat();
    AgentsXMLIO.writeClassification(attrs,classification);
    hd.startElement("","",tag,attrs);
    hd.endElement("","",tag);
  }
}
