package delta.games.lotro.character.status.allegiances.io.xml;

import java.io.File;
import java.util.BitSet;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.status.allegiances.AllegianceStatus;
import delta.games.lotro.character.status.allegiances.AllegiancesStatusManager;
import delta.games.lotro.lore.allegiances.AllegianceDescription;
import delta.games.lotro.lore.allegiances.Points2LevelCurve;

/**
 * Writes a allegiances status to an XML file.
 * @author DAM
 */
public class AllegiancesStatusXMLWriter
{
  /**
   * Write a status to an XML file.
   * @param outFile Output file.
   * @param status Status to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final AllegiancesStatusManager status, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeStatus(hd,status);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  /**
   * Write a status to the given XML stream.
   * @param hd XML output stream.
   * @param statusMgr Status to write.
   * @throws SAXException If an error occurs.
   */
  private void writeStatus(TransformerHandler hd, AllegiancesStatusManager statusMgr) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Current allegiance
    AllegianceDescription currentAllegiance=statusMgr.getCurrentAllegiance();
    if (currentAllegiance!=null)
    {
      int allegianceID=currentAllegiance.getIdentifier();
      attrs.addAttribute("","",AllegiancesStatusXMLConstants.CURRENT_ALLEGIANCE_ID_ATTR,XmlWriter.CDATA,String.valueOf(allegianceID));
    }
    hd.startElement("","",AllegiancesStatusXMLConstants.ALLEGIANCES_STATUS_TAG,attrs);

    List<AllegianceStatus> allegiancesStatuses=statusMgr.getAll();
    for(AllegianceStatus allegianceStatus : allegiancesStatuses)
    {
      AttributesImpl statusAttrs=new AttributesImpl();
      AllegianceDescription allegiance=allegianceStatus.getAllegiance();
      // ID
      int allegianceID=allegiance.getIdentifier();
      statusAttrs.addAttribute("","",AllegiancesStatusXMLConstants.ALLEGIANCE_ID_ATTR,XmlWriter.CDATA,String.valueOf(allegianceID));
      // Name
      String name=allegiance.getName();
      statusAttrs.addAttribute("","",AllegiancesStatusXMLConstants.ALLEGIANCE_NAME_ATTR,XmlWriter.CDATA,name);
      // Curve ID
      Points2LevelCurve curve=allegianceStatus.getPoints2LevelCurve();
      if (curve!=null)
      {
        int curveID=curve.getIdentifier();
        statusAttrs.addAttribute("","",AllegiancesStatusXMLConstants.ALLEGIANCE_CURVE_ID_ATTR,XmlWriter.CDATA,String.valueOf(curveID));
      }
      // Points
      int points=allegianceStatus.getPointsEarned();
      statusAttrs.addAttribute("","",AllegiancesStatusXMLConstants.ALLEGIANCE_POINTS_EARNED_ATTR,XmlWriter.CDATA,String.valueOf(points));
      // Claimed rewards
      BitSet claimedRewards=allegianceStatus.getClaimedRewards();
      int claimedRewardsFlags=0;
      long[] bitsAsLongs=claimedRewards.toLongArray();
      if ((bitsAsLongs!=null) && (bitsAsLongs.length>0))
      {
        claimedRewardsFlags=(int)(bitsAsLongs[0]);
      }
      statusAttrs.addAttribute("","",AllegiancesStatusXMLConstants.ALLEGIANCE_CLAIMED_REWARDS_ATTR,XmlWriter.CDATA,String.valueOf(claimedRewardsFlags));
      hd.startElement("","",AllegiancesStatusXMLConstants.ALLEGIANCE,statusAttrs);
      hd.endElement("","",AllegiancesStatusXMLConstants.ALLEGIANCE);
    }
    hd.endElement("","",AllegiancesStatusXMLConstants.ALLEGIANCES_STATUS_TAG);
  }
}
