package delta.games.lotro.lore.agents.mobs.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.common.treasure.TreasureList;
import delta.games.lotro.common.treasure.TrophyList;
import delta.games.lotro.lore.agents.io.xml.AgentsXMLIO;
import delta.games.lotro.lore.agents.mobs.MobDescription;
import delta.games.lotro.lore.agents.mobs.MobLoot;

/**
 * Writes mobs to XML files.
 * @author DAM
 */
public class MobsXMLWriter
{
  /**
   * Write a file with mobs.
   * @param toFile Output file.
   * @param data Data to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeMobsFile(File toFile, List<MobDescription> data)
  {
    MobsXMLWriter writer=new MobsXMLWriter();
    boolean ok=writer.writeMobs(toFile,data,EncodingNames.UTF_8);
    return ok;
  }

  /**
   * Write mobs to a XML file.
   * @param outFile Output file.
   * @param data Data to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean writeMobs(File outFile, final List<MobDescription> data, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeMobs(hd,data);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void writeMobs(TransformerHandler hd, List<MobDescription> data) throws Exception
  {
    hd.startElement("","",MobsXMLConstants.MOBS_TAG,new AttributesImpl());
    for(MobDescription mob : data)
    {
      writeMob(hd,mob);
    }
    hd.endElement("","",MobsXMLConstants.MOBS_TAG);
  }

  private void writeMob(TransformerHandler hd, MobDescription mob) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();

    // In-game identifier
    int id=mob.getIdentifier();
    attrs.addAttribute("","",MobsXMLConstants.ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Name
    String name=mob.getName();
    attrs.addAttribute("","",MobsXMLConstants.NAME_ATTR,XmlWriter.CDATA,name);
    // Classification
    AgentsXMLIO.writeClassification(attrs,mob.getClassification());
    // Loot
    MobLoot loot=mob.getLoot();
    if (loot!=null)
    {
      writeMobLoot(attrs,loot);
    }
    hd.startElement("","",MobsXMLConstants.MOB_TAG,attrs);
    hd.endElement("","",MobsXMLConstants.MOB_TAG);
  }

  private void writeMobLoot(AttributesImpl attrs, MobLoot loot) throws Exception
  {
    // Barter trophy list
    TrophyList barterTrophy=loot.getBarterTrophy();
    if (barterTrophy!=null)
    {
      int id=barterTrophy.getIdentifier();
      attrs.addAttribute("","",MobsXMLConstants.BARTER_TROPHY_LIST_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    }
    // Reputation trophy list
    TrophyList reputationTrophy=loot.getReputationTrophy();
    if (reputationTrophy!=null)
    {
      int id=reputationTrophy.getIdentifier();
      attrs.addAttribute("","",MobsXMLConstants.REPUTATION_TROPHY_LIST_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    }
    // Treasure list override
    TreasureList treasureList=loot.getTreasureListOverride();
    if (treasureList!=null)
    {
      int id=treasureList.getIdentifier();
      attrs.addAttribute("","",MobsXMLConstants.TREASURE_LIST_OVERRIDE_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    }
    // Trophy list override
    TrophyList trophyList=loot.getTrophyListOverride();
    if (trophyList!=null)
    {
      int id=trophyList.getIdentifier();
      attrs.addAttribute("","",MobsXMLConstants.TROPHY_LIST_OVERRIDE_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    }
    // Generates trophy
    boolean generatesTrophy=loot.isGeneratesTrophy();
    if (!generatesTrophy)
    {
      attrs.addAttribute("","",MobsXMLConstants.GENERATES_TROPHY_ATTR,XmlWriter.CDATA,String.valueOf(generatesTrophy));
    }
    // Remote lootable
    boolean remoteLootable=loot.isRemoteLootable();
    if (!remoteLootable)
    {
      attrs.addAttribute("","",MobsXMLConstants.REMOTE_LOOTABLE_ATTR,XmlWriter.CDATA,String.valueOf(remoteLootable));
    }
  }
}
