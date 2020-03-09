package delta.games.lotro.character.reputation.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.reputation.FactionLevelStatus;
import delta.games.lotro.character.reputation.FactionStatus;
import delta.games.lotro.character.reputation.ReputationStatus;
import delta.games.lotro.lore.reputation.Faction;
import delta.games.lotro.lore.reputation.FactionLevel;
import delta.games.lotro.lore.reputation.FactionsRegistry;

/**
 * Writes reputation of LOTRO characters to XML files.
 * @author DAM
 */
public class ReputationXMLWriter
{
  /**
   * Write the reputation of a character to a XML file.
   * @param outFile Output file.
   * @param reputationStatus Data to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final ReputationStatus reputationStatus, String encoding)
  {
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        write(hd,reputationStatus);
      }
    };
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void write(TransformerHandler hd, ReputationStatus reputation) throws Exception
  {
    AttributesImpl reputationAttrs=new AttributesImpl();
    hd.startElement("","",ReputationXMLConstants.REPUTATION_TAG,reputationAttrs);
    List<Faction> factions=FactionsRegistry.getInstance().getAll();
    for(Faction faction : factions)
    {
      FactionStatus factionStatus=reputation.getFactionStatus(faction);
      if (factionStatus!=null)
      {
        writeFactionStatus(hd,factionStatus);
      }
    }
    hd.endElement("","",ReputationXMLConstants.REPUTATION_TAG);
  }

  /**
   * Write faction status.
   * @param hd Output stream.
   * @param factionStatus Data to write.
   * @throws Exception If an error occurs.
   */
  public static void writeFactionStatus(TransformerHandler hd, FactionStatus factionStatus) throws Exception
  {
    AttributesImpl factionAttrs=new AttributesImpl();
    Faction faction=factionStatus.getFaction();
    int factionId=faction.getIdentifier();
    factionAttrs.addAttribute("","",ReputationXMLConstants.FACTION_ID_ATTR,XmlWriter.CDATA,String.valueOf(factionId));
    // Current level
    FactionLevel currentLevel=factionStatus.getFactionLevel();
    if (currentLevel!=null)
    {
      int currentTier=currentLevel.getTier();
      factionAttrs.addAttribute("","",ReputationXMLConstants.FACTION_CURRENT_TIER_ATTR,XmlWriter.CDATA,String.valueOf(currentTier));
    }
    // Current reputation
    Integer reputation=factionStatus.getReputation();
    if (reputation!=null)
    {
      factionAttrs.addAttribute("","",ReputationXMLConstants.FACTION_CURRENT_REPUTATION_ATTR,XmlWriter.CDATA,reputation.toString());
    }
    hd.startElement("","",ReputationXMLConstants.FACTION_TAG,factionAttrs);
    // Levels
    FactionLevel[] levels=faction.getLevels();
    for(FactionLevel level : levels)
    {
      FactionLevelStatus levelStatus=factionStatus.getStatusForLevel(level);
      AttributesImpl factionLevelAttrs=new AttributesImpl();
      // Tier
      int tier=level.getTier();
      factionLevelAttrs.addAttribute("","",ReputationXMLConstants.FACTION_LEVEL_TIER_ATTR,XmlWriter.CDATA,String.valueOf(tier));
      // Date
      long date=levelStatus.getCompletionDate();
      factionLevelAttrs.addAttribute("","",ReputationXMLConstants.FACTION_LEVEL_DATE_ATTR,XmlWriter.CDATA,String.valueOf(date));
      hd.startElement("","",ReputationXMLConstants.FACTION_LEVEL_TAG,factionLevelAttrs);
      hd.endElement("","",ReputationXMLConstants.FACTION_LEVEL_TAG);
    }
    hd.endElement("","",ReputationXMLConstants.FACTION_TAG);
  }
}
