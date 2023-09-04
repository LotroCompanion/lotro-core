package delta.games.lotro.lore.agents.npcs.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.common.CharacterSex;
import delta.games.lotro.lore.agents.npcs.NpcDescription;

/**
 * Writes NPCs to XML files.
 * @author DAM
 */
public class NPCsXMLWriter
{
  /**
   * Write a file with NPCs.
   * @param toFile Output file.
   * @param data Data to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeNPCsFile(File toFile, List<NpcDescription> data)
  {
    NPCsXMLWriter writer=new NPCsXMLWriter();
    boolean ok=writer.writeNPCs(toFile,data,EncodingNames.UTF_8);
    return ok;
  }

  /**
   * Write NPCs to a XML file.
   * @param outFile Output file.
   * @param data Data to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean writeNPCs(File outFile, final List<NpcDescription> data, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeNPCs(hd,data);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void writeNPCs(TransformerHandler hd, List<NpcDescription> data) throws Exception
  {
    hd.startElement("","",NPCsXMLConstants.NPCS_TAG,new AttributesImpl());
    for(NpcDescription npc : data)
    {
      writeNPC(hd,npc);
    }
    hd.endElement("","",NPCsXMLConstants.NPCS_TAG);
  }

  private void writeNPC(TransformerHandler hd, NpcDescription npc) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();

    // In-game identifier
    int id=npc.getIdentifier();
    attrs.addAttribute("","",NPCsXMLConstants.ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Name
    String name=npc.getName();
    attrs.addAttribute("","",NPCsXMLConstants.NAME_ATTR,XmlWriter.CDATA,name);
    // Gender
    CharacterSex gender=npc.getGender();
    if (gender!=null)
    {
      attrs.addAttribute("","",NPCsXMLConstants.GENDER_ATTR,XmlWriter.CDATA,gender.getKey());
    }
    // Title
    String title=npc.getTitle();
    if (!title.isEmpty())
    {
      attrs.addAttribute("","",NPCsXMLConstants.TITLE_ATTR,XmlWriter.CDATA,title);
    }
    hd.startElement("","",NPCsXMLConstants.NPC_TAG,attrs);
    hd.endElement("","",NPCsXMLConstants.NPC_TAG);
  }
}
