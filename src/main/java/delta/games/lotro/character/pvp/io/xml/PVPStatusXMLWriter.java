package delta.games.lotro.character.pvp.io.xml;

import java.io.File;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.pvp.PVPStatus;
import delta.games.lotro.lore.pvp.Rank;

/**
 * Writes a PVP status to an XML file.
 * @author DAM
 */
public class PVPStatusXMLWriter
{
  /**
   * Write a PVP data to an XML file.
   * @param outFile Output file.
   * @param data Data to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final PVPStatus data, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeStatus(hd,data);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void writeStatus(TransformerHandler hd, PVPStatus data) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Glory points
    int glory=data.getGloryPoints();
    attrs.addAttribute("","",PVPStatusXMLConstants.GLORY_POINTS_ATTR,XmlWriter.CDATA,String.valueOf(glory));
    // Rank
    Rank rank=data.getRank();
    if (rank!=null)
    {
      int rankCode=rank.getCode();
      attrs.addAttribute("","",PVPStatusXMLConstants.RANK_ATTR,XmlWriter.CDATA,String.valueOf(rankCode));
    }
    // Rating
    float rating=data.getRating();
    attrs.addAttribute("","",PVPStatusXMLConstants.RATING_ATTR,XmlWriter.CDATA,String.valueOf(rating));
    // Prestige
    Rank prestige=data.getPrestige();
    if (prestige!=null)
    {
      int prestigeCode=prestige.getCode();
      attrs.addAttribute("","",PVPStatusXMLConstants.PRESTIGE_ATTR,XmlWriter.CDATA,String.valueOf(prestigeCode));
    }
    // Deaths
    int deaths=data.getDeaths();
    attrs.addAttribute("","",PVPStatusXMLConstants.DEATHS_ATTR,XmlWriter.CDATA,String.valueOf(deaths));
    // Kills
    int kills=data.getKills();
    attrs.addAttribute("","",PVPStatusXMLConstants.KILLS_ATTR,XmlWriter.CDATA,String.valueOf(kills));
    // Kills above rating
    int killsAboveRating=data.getKillsAboveRating();
    attrs.addAttribute("","",PVPStatusXMLConstants.KILLS_ABOVE_RATING_ATTR,XmlWriter.CDATA,String.valueOf(killsAboveRating));
    // Kills below rating
    int killsBelowRating=data.getKillsBelowRating();
    attrs.addAttribute("","",PVPStatusXMLConstants.KILLS_BELOW_RATING_ATTR,XmlWriter.CDATA,String.valueOf(killsBelowRating));
    // Kills to deaths ratio
    float kills2deathsRatio=data.getKill2deathRatio();
    attrs.addAttribute("","",PVPStatusXMLConstants.KILL_TO_DEATH_RATIO_ATTR,XmlWriter.CDATA,String.valueOf(kills2deathsRatio));
    // Highest rating killed
    float highestRatingKilled=data.getHighestRatingKilled();
    attrs.addAttribute("","",PVPStatusXMLConstants.HIGHEST_RATING_KILLED_ATTR,XmlWriter.CDATA,String.valueOf(highestRatingKilled));
    // Death blows
    int deathBlows=data.getDeathBlows();
    attrs.addAttribute("","",PVPStatusXMLConstants.DEATH_BLOWS_ATTR,XmlWriter.CDATA,String.valueOf(deathBlows));
    hd.startElement("","",PVPStatusXMLConstants.PVP_STATUS_TAG,attrs);
    hd.endElement("","",PVPStatusXMLConstants.PVP_STATUS_TAG);
  }
}
