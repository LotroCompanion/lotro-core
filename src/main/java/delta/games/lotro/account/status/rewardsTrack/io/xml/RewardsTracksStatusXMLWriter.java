package delta.games.lotro.account.status.rewardsTrack.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.account.status.rewardsTrack.RewardsTrackStatus;
import delta.games.lotro.account.status.rewardsTrack.RewardsTracksStatusManager;
import delta.games.lotro.lore.rewardsTrack.RewardsTrack;

/**
 * Writes a rewards tracks status to an XML file.
 * @author DAM
 */
public class RewardsTracksStatusXMLWriter
{
  /**
   * Write a status to an XML file.
   * @param outFile Output file.
   * @param status Status to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final RewardsTracksStatusManager status, String encoding)
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
  private void writeStatus(TransformerHandler hd, RewardsTracksStatusManager statusMgr) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    hd.startElement("","",RewardsTracksStatusXMLConstants.REWARDS_TRACKS_STATUS_TAG,attrs);
    List<RewardsTrackStatus> rewardsTrackStatuses=statusMgr.getAll();
    for(RewardsTrackStatus rewardsTrackStatus : rewardsTrackStatuses)
    {
      AttributesImpl statusAttrs=new AttributesImpl();
      RewardsTrack rewardsTrack=rewardsTrackStatus.getRewardsTrack();
      // ID
      int rewardsTrackID=rewardsTrack.getIdentifier();
      statusAttrs.addAttribute("","",RewardsTracksStatusXMLConstants.REWARDS_TRACK_ID_ATTR,XmlWriter.CDATA,String.valueOf(rewardsTrackID));
      // Name
      String name=rewardsTrack.getName();
      statusAttrs.addAttribute("","",RewardsTracksStatusXMLConstants.REWARDS_TRACK_NAME_ATTR,XmlWriter.CDATA,name);
      // Claimed milestones
      int claimedMilestones=rewardsTrackStatus.getClaimedMilestones();
      statusAttrs.addAttribute("","",RewardsTracksStatusXMLConstants.REWARDS_TRACK_CLAIMED_MILESTONES_ATTR,XmlWriter.CDATA,String.valueOf(claimedMilestones));
      // Current milestone
      int currentMilestone=rewardsTrackStatus.getClaimedMilestones();
      statusAttrs.addAttribute("","",RewardsTracksStatusXMLConstants.REWARDS_TRACK_CURRENT_MILESTONE_ATTR,XmlWriter.CDATA,String.valueOf(currentMilestone));
      // Last XP goal
      int lastExperienceGoal=rewardsTrackStatus.getLastExperienceGoal();
      statusAttrs.addAttribute("","",RewardsTracksStatusXMLConstants.REWARDS_TRACK_LAST_XP_GOAL_ATTR,XmlWriter.CDATA,String.valueOf(lastExperienceGoal));
      // Current XP
      int currentExperience=rewardsTrackStatus.getCurrentExperience();
      statusAttrs.addAttribute("","",RewardsTracksStatusXMLConstants.REWARDS_TRACK_CURRENT_XP_ATTR,XmlWriter.CDATA,String.valueOf(currentExperience));
      // Next XP goal
      int nextExperienceGoal=rewardsTrackStatus.getNextExperienceGoal();
      statusAttrs.addAttribute("","",RewardsTracksStatusXMLConstants.REWARDS_TRACK_NEXT_XP_GOAL_ATTR,XmlWriter.CDATA,String.valueOf(nextExperienceGoal));
      hd.startElement("","",RewardsTracksStatusXMLConstants.REWARDS_TRACK_TAG,statusAttrs);
      hd.endElement("","",RewardsTracksStatusXMLConstants.REWARDS_TRACK_TAG);
    }
    hd.endElement("","",RewardsTracksStatusXMLConstants.REWARDS_TRACKS_STATUS_TAG);
  }
}
