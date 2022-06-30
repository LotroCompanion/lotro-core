package delta.games.lotro.lore.rewardsTrack.io.xml;

import java.io.File;
import java.util.Collections;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.rewardsTrack.RewardsTrack;
import delta.games.lotro.lore.rewardsTrack.RewardsTrackStep;

/**
 * Writes rewards tracks to XML files.
 * @author DAM
 */
public class RewardsTracksXMLWriter
{
  /**
   * Write a file with rewards tracks.
   * @param toFile Output file.
   * @param rewardsTracks Data to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeRewardsTracksFile(File toFile, List<RewardsTrack> rewardsTracks)
  {
    RewardsTracksXMLWriter writer=new RewardsTracksXMLWriter();
    Collections.sort(rewardsTracks,new IdentifiableComparator<RewardsTrack>());
    boolean ok=writer.writeRewardsTracks(toFile,rewardsTracks,EncodingNames.UTF_8);
    return ok;
  }

  /**
   * Write rewards tracks to a XML file.
   * @param outFile Output file.
   * @param rewardsTracks Data to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean writeRewardsTracks(File outFile, final List<RewardsTrack> rewardsTracks, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeRewardsTracks(hd,rewardsTracks);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void writeRewardsTracks(TransformerHandler hd, List<RewardsTrack> rewardsTracks) throws SAXException
  {
    hd.startElement("","",RewardsTracksXMLConstants.REWARDS_TRACKS_TAG,new AttributesImpl());
    for(RewardsTrack rewardsTrack : rewardsTracks)
    {
      writeRewardsTrack(hd,rewardsTrack);
    }
    hd.endElement("","",RewardsTracksXMLConstants.REWARDS_TRACKS_TAG);
  }

  private void writeRewardsTrack(TransformerHandler hd, RewardsTrack rewardsTrack) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();

    // In-game identifier
    int id=rewardsTrack.getIdentifier();
    attrs.addAttribute("","",RewardsTracksXMLConstants.REWARDS_TRACK_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Name
    String name=rewardsTrack.getName();
    attrs.addAttribute("","",RewardsTracksXMLConstants.REWARDS_TRACK_NAME_ATTR,XmlWriter.CDATA,name);
    // Description
    String description=rewardsTrack.getDescription();
    attrs.addAttribute("","",RewardsTracksXMLConstants.REWARDS_TRACK_DESCRIPTION_ATTR,XmlWriter.CDATA,description);
    // Progression ID
    int progressionID=rewardsTrack.getXpIntervalsProgressionID();
    attrs.addAttribute("","",RewardsTracksXMLConstants.REWARDS_TRACK_PROGRESSION_ID_ATTR,XmlWriter.CDATA,String.valueOf(progressionID));
    // Active property
    String activeProperty=rewardsTrack.getActiveProperty();
    attrs.addAttribute("","",RewardsTracksXMLConstants.REWARDS_TRACK_ACTIVE_PROPERTY_ATTR,XmlWriter.CDATA,activeProperty);
    // Claimed milestone property
    String claimedMilestonesProperty=rewardsTrack.getClaimedMilestonesProperty();
    attrs.addAttribute("","",RewardsTracksXMLConstants.REWARDS_TRACK_CLAIMED_MILESTONES_PROPERTY_ATTR,XmlWriter.CDATA,claimedMilestonesProperty);
    // Current milestone property
    String currentMilestoneProperty=rewardsTrack.getCurrentMilestoneProperty();
    attrs.addAttribute("","",RewardsTracksXMLConstants.REWARDS_TRACK_CURRENT_MILESTONE_PROPERTY_ATTR,XmlWriter.CDATA,currentMilestoneProperty);
    // Last XP goal
    String lastXPGoalProperty=rewardsTrack.getLastExperienceGoalProperty();
    attrs.addAttribute("","",RewardsTracksXMLConstants.REWARDS_TRACK_LAST_XP_GOAL_PROPERTY_ATTR,XmlWriter.CDATA,lastXPGoalProperty);
    // Current XP
    String currentXPProperty=rewardsTrack.getCurrentExperienceProperty();
    attrs.addAttribute("","",RewardsTracksXMLConstants.REWARDS_TRACK_CURRENT_XP_PROPERTY_ATTR,XmlWriter.CDATA,currentXPProperty);
    // Next XP goal
    String nextXPGoalProperty=rewardsTrack.getNextExperienceGoalProperty();
    attrs.addAttribute("","",RewardsTracksXMLConstants.REWARDS_TRACK_NEXT_XP_GOAL_PROPERTY_ATTR,XmlWriter.CDATA,nextXPGoalProperty);
    hd.startElement("","",RewardsTracksXMLConstants.REWARDS_TRACK_TAG,attrs);
    // Steps
    for(RewardsTrackStep step : rewardsTrack.getSteps())
    {
      AttributesImpl stepAttrs=new AttributesImpl();
      // XP Cost multiplier
      float xpCostMultiplier=step.getXpCostMultiplier();
      attrs.addAttribute("","",RewardsTracksXMLConstants.STEP_XP_COST_MULTIPLIER_ATTR,XmlWriter.CDATA,String.valueOf(xpCostMultiplier));
      int uiElementID=step.getUiElementID();
      attrs.addAttribute("","",RewardsTracksXMLConstants.STEP_UI_ELEMENT_ID_ATTR,XmlWriter.CDATA,String.valueOf(uiElementID));
      hd.startElement("","",RewardsTracksXMLConstants.STEP_TAG,stepAttrs);
      // Reward
      Item item=step.getReward();
      if (item!=null)
      {
        AttributesImpl itemAttrs=new AttributesImpl();
        // ID
        int itemID=item.getIdentifier();
        itemAttrs.addAttribute("","",RewardsTracksXMLConstants.REWARD_ID_ATTR,XmlWriter.CDATA,String.valueOf(itemID));
        // Name
        String itemName=item.getName();
        itemAttrs.addAttribute("","",RewardsTracksXMLConstants.REWARD_NAME_ATTR,XmlWriter.CDATA,itemName);
        hd.startElement("","",RewardsTracksXMLConstants.REWARD_TAG,itemAttrs);
        hd.endElement("","",RewardsTracksXMLConstants.REWARD_TAG);
      }
      hd.endElement("","",RewardsTracksXMLConstants.STEP_TAG);
    }
    hd.endElement("","",RewardsTracksXMLConstants.REWARDS_TRACK_TAG);
  }
}
