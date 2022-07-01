package delta.games.lotro.account.status.rewardsTrack.io.xml;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.account.status.rewardsTrack.RewardsTrackStatus;
import delta.games.lotro.account.status.rewardsTrack.RewardsTracksStatusManager;
import delta.games.lotro.lore.rewardsTrack.RewardsTrack;
import delta.games.lotro.lore.rewardsTrack.RewardsTracksManager;

/**
 * Parser for the rewards tracks status stored in XML.
 * @author DAM
 */
public class RewardsTracksStatusXMLParser
{
  private static final Logger LOGGER=Logger.getLogger(RewardsTracksStatusXMLParser.class);

  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed status or <code>null</code>.
   */
  public RewardsTracksStatusManager parseXML(File source)
  {
    RewardsTracksStatusManager status=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      status=parseStatus(root);
    }
    return status;
  }

  private RewardsTracksStatusManager parseStatus(Element root)
  {
    RewardsTracksStatusManager status=new RewardsTracksStatusManager();
    // Status of rewards tracks
    List<Element> statusTags=DOMParsingTools.getChildTagsByName(root,RewardsTracksStatusXMLConstants.REWARDS_TRACK_TAG,false);
    for(Element statusTag : statusTags)
    {
      parseRewardsTrackStatus(status,statusTag);
    }
    return status;
  }

  private void parseRewardsTrackStatus(RewardsTracksStatusManager status, Element statusTag)
  {
    NamedNodeMap attrs=statusTag.getAttributes();
    int id=DOMParsingTools.getIntAttribute(attrs,RewardsTracksStatusXMLConstants.REWARDS_TRACK_ID_ATTR,0);
    // Create status
    RewardsTracksManager mgr=RewardsTracksManager.getInstance();
    RewardsTrack rewardsTrack=mgr.getRewardsTrack(id);
    if (rewardsTrack==null)
    {
      // Unknown rewards track!
      LOGGER.warn("Unknown rewards track: "+id);
      return;
    }
    RewardsTrackStatus newStatus=status.getStatus(rewardsTrack,true);
    // Claimed milestones
    int claimedMilestones=DOMParsingTools.getIntAttribute(attrs,RewardsTracksStatusXMLConstants.REWARDS_TRACK_CLAIMED_MILESTONES_ATTR,0);
    newStatus.setClaimedMilestones(claimedMilestones);
    // Current milestone
    int currentMilestone=DOMParsingTools.getIntAttribute(attrs,RewardsTracksStatusXMLConstants.REWARDS_TRACK_CURRENT_MILESTONE_ATTR,0);
    newStatus.setCurrentMilestone(currentMilestone);
    // Last XP goal
    int lastExperienceGoal=DOMParsingTools.getIntAttribute(attrs,RewardsTracksStatusXMLConstants.REWARDS_TRACK_LAST_XP_GOAL_ATTR,0);
    newStatus.setLastExperienceGoal(lastExperienceGoal);
    // Current XP
    int currentExperience=DOMParsingTools.getIntAttribute(attrs,RewardsTracksStatusXMLConstants.REWARDS_TRACK_CURRENT_XP_ATTR,0);
    newStatus.setCurrentExperience(currentExperience);
    // Next XP goal
    int nextExperienceGoal=DOMParsingTools.getIntAttribute(attrs,RewardsTracksStatusXMLConstants.REWARDS_TRACK_NEXT_XP_GOAL_ATTR,0);
    newStatus.setNextExperienceGoal(nextExperienceGoal);
  }
}
