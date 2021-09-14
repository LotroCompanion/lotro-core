package delta.games.lotro.lore.instances.loot.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.math.Range;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.common.treasure.TrophyList;
import delta.games.lotro.lore.instances.PrivateEncounter;
import delta.games.lotro.lore.instances.loot.InstanceLootEntry;
import delta.games.lotro.lore.instances.loot.InstanceLootParameters;
import delta.games.lotro.lore.instances.loot.InstanceLoots;

/**
 * Writes instance loots to XML files.
 * @author DAM
 */
public class InstanceLootXMLWriter
{
  /**
   * Write a file with instance loots.
   * @param toFile Output file.
   * @param data Data to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeInstanceLootsFile(File toFile, List<InstanceLoots> data)
  {
    InstanceLootXMLWriter writer=new InstanceLootXMLWriter();
    boolean ok=writer.writeInstanceLoots(toFile,data,EncodingNames.UTF_8);
    return ok;
  }

  /**
   * Write private encounter to a XML file.
   * @param outFile Output file.
   * @param data Data to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean writeInstanceLoots(File outFile, final List<InstanceLoots> data, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeInstanceLoots(hd,data);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void writeInstanceLoots(TransformerHandler hd, List<InstanceLoots> data) throws Exception
  {
    hd.startElement("","",InstanceLootXMLConstants.INSTANCE_LOOTS_TAG,new AttributesImpl());
    for(InstanceLoots instanceLoots : data)
    {
      writeInstanceLoot(hd,instanceLoots);
    }
    hd.endElement("","",InstanceLootXMLConstants.INSTANCE_LOOTS_TAG);
  }

  private void writeInstanceLoot(TransformerHandler hd, InstanceLoots instanceLoot) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();

    PrivateEncounter privateEncounter=instanceLoot.getInstance();
    // Identifier
    int id=privateEncounter.getIdentifier();
    attrs.addAttribute("","",InstanceLootXMLConstants.INSTANCE_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Name
    String name=privateEncounter.getName();
    attrs.addAttribute("","",InstanceLootXMLConstants.INSTANCE_NAME_ATTR,XmlWriter.CDATA,name);
    hd.startElement("","",InstanceLootXMLConstants.INSTANCE_TAG,attrs);
    // Entries
    for(InstanceLootEntry entry : instanceLoot.getEntries())
    {
      AttributesImpl entryAttrs=new AttributesImpl();
      // Parameters
      InstanceLootParameters parameters=entry.getParameters();
      // - Difficulty
      Range difficulty=parameters.getDifficultyTier();
      entryAttrs.addAttribute("","",InstanceLootXMLConstants.MIN_DIFFICULTY_ATTR,XmlWriter.CDATA,difficulty.getMin().toString());
      entryAttrs.addAttribute("","",InstanceLootXMLConstants.MAX_DIFFICULTY_ATTR,XmlWriter.CDATA,difficulty.getMax().toString());
      // - Group size
      Range groupSize=parameters.getGroupSize();
      entryAttrs.addAttribute("","",InstanceLootXMLConstants.MIN_GROUP_SIZE_ATTR,XmlWriter.CDATA,groupSize.getMin().toString());
      entryAttrs.addAttribute("","",InstanceLootXMLConstants.MAX_GROUP_SIZE_ATTR,XmlWriter.CDATA,groupSize.getMax().toString());
      // - Level
      Range level=parameters.getLevel();
      entryAttrs.addAttribute("","",InstanceLootXMLConstants.MIN_LEVEL_ATTR,XmlWriter.CDATA,level.getMin().toString());
      entryAttrs.addAttribute("","",InstanceLootXMLConstants.MAX_LEVEL_ATTR,XmlWriter.CDATA,level.getMax().toString());
      // Trophy list
      TrophyList trophyList=entry.getTrophyList();
      if (trophyList!=null)
      {
        int trophyListId=trophyList.getIdentifier();
        entryAttrs.addAttribute("","",InstanceLootXMLConstants.TROPHY_LIST_ID_ATTR,XmlWriter.CDATA,String.valueOf(trophyListId));
      }
      hd.startElement("","",InstanceLootXMLConstants.LOOT_ENTRY_TAG,entryAttrs);
      hd.endElement("","",InstanceLootXMLConstants.LOOT_ENTRY_TAG);
    }
    hd.endElement("","",InstanceLootXMLConstants.INSTANCE_TAG);
  }
}
