package delta.games.lotro.lore.rewardsTrack.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemsManager;
import delta.games.lotro.lore.rewardsTrack.RewardsTrack;
import delta.games.lotro.lore.rewardsTrack.RewardsTrackStep;
import delta.games.lotro.utils.i18n.I18nFacade;
import delta.games.lotro.utils.i18n.I18nRuntimeUtils;

/**
 * Parser for rewards tracks stored in XML.
 * @author DAM
 */
public class RewardsTracksXMLParser
{
  private SingleLocaleLabelsManager _i18n;

  /**
   * Constructor.
   */
  public RewardsTracksXMLParser()
  {
    _i18n=I18nFacade.getLabelsMgr("rewardsTracks");
  }

  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed rewards tracks.
   */
  public List<RewardsTrack> parseXML(File source)
  {
    List<RewardsTrack> ret=new ArrayList<RewardsTrack>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> rewardsTrackTags=DOMParsingTools.getChildTagsByName(root,RewardsTracksXMLConstants.REWARDS_TRACK_TAG);
      for(Element rewardsTrackTag : rewardsTrackTags)
      {
        RewardsTrack rewardsTrack=parseRewardsTrack(rewardsTrackTag);
        ret.add(rewardsTrack);
      }
    }
    return ret;
  }

  private RewardsTrack parseRewardsTrack(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,RewardsTracksXMLConstants.REWARDS_TRACK_ID_ATTR,0);
    RewardsTrack rewardsTrack=new RewardsTrack(id);
    // Name
    String name=_i18n.getLabel(String.valueOf(id));
    rewardsTrack.setName(name);
    // Description
    String description=DOMParsingTools.getStringAttribute(attrs,RewardsTracksXMLConstants.REWARDS_TRACK_DESCRIPTION_ATTR,"");
    description=I18nRuntimeUtils.getLabel(_i18n,description);
    rewardsTrack.setDescription(description);
    // Progression ID
    int xpIntervalsProgressionID=DOMParsingTools.getIntAttribute(attrs,RewardsTracksXMLConstants.REWARDS_TRACK_PROGRESSION_ID_ATTR,0);
    rewardsTrack.setXpIntervalsProgressionID(xpIntervalsProgressionID);
    // Active property
    String activeProperty=DOMParsingTools.getStringAttribute(attrs,RewardsTracksXMLConstants.REWARDS_TRACK_ACTIVE_PROPERTY_ATTR,"");
    rewardsTrack.setActiveProperty(activeProperty);
    // Claimed milestones property
    String claimedMilestonesProperty=DOMParsingTools.getStringAttribute(attrs,RewardsTracksXMLConstants.REWARDS_TRACK_CLAIMED_MILESTONES_PROPERTY_ATTR,"");
    rewardsTrack.setClaimedMilestonesProperty(claimedMilestonesProperty);
    // Current milestone property
    String currentMilestoneProperty=DOMParsingTools.getStringAttribute(attrs,RewardsTracksXMLConstants.REWARDS_TRACK_CURRENT_MILESTONE_PROPERTY_ATTR,"");
    rewardsTrack.setCurrentMilestoneProperty(currentMilestoneProperty);
    // Last XP goal property
    String lastExperienceGoalProperty=DOMParsingTools.getStringAttribute(attrs,RewardsTracksXMLConstants.REWARDS_TRACK_LAST_XP_GOAL_PROPERTY_ATTR,"");
    rewardsTrack.setLastExperienceGoalProperty(lastExperienceGoalProperty);
    // Current XP property
    String currentExperienceProperty=DOMParsingTools.getStringAttribute(attrs,RewardsTracksXMLConstants.REWARDS_TRACK_CURRENT_XP_PROPERTY_ATTR,"");
    rewardsTrack.setCurrentExperienceProperty(currentExperienceProperty);
    // Next XP goal property
    String nextExperienceGoalProperty=DOMParsingTools.getStringAttribute(attrs,RewardsTracksXMLConstants.REWARDS_TRACK_NEXT_XP_GOAL_PROPERTY_ATTR,"");
    rewardsTrack.setNextExperienceGoalProperty(nextExperienceGoalProperty);

    // Steps
    List<Element> stepTags=DOMParsingTools.getChildTagsByName(root,RewardsTracksXMLConstants.STEP_TAG);
    for(Element stepTag : stepTags)
    {
      NamedNodeMap stepAttrs=stepTag.getAttributes();
      RewardsTrackStep step=new RewardsTrackStep();
      // XP cost multiplier
      float xpCostMultiplier=DOMParsingTools.getFloatAttribute(stepAttrs,RewardsTracksXMLConstants.STEP_XP_COST_MULTIPLIER_ATTR,0);
      step.setXpCostMultiplier(xpCostMultiplier);
      // UI element ID
      int uiElementID=DOMParsingTools.getIntAttribute(stepAttrs,RewardsTracksXMLConstants.STEP_UI_ELEMENT_ID_ATTR,0);
      step.setUiElementID(uiElementID);
      // Large icon ID
      int largeIconID=DOMParsingTools.getIntAttribute(stepAttrs,RewardsTracksXMLConstants.STEP_LARGE_ICON_ID_ATTR,0);
      step.setLargeIconID(largeIconID);
      // Reward
      Element rewardTag=DOMParsingTools.getChildTagByName(stepTag,RewardsTracksXMLConstants.REWARD_TAG);
      if (rewardTag!=null)
      {
        int itemID=DOMParsingTools.getIntAttribute(rewardTag.getAttributes(),RewardsTracksXMLConstants.REWARD_ID_ATTR,0);
        Item item=ItemsManager.getInstance().getItem(itemID);
        step.setReward(item);
      }
      rewardsTrack.addStep(step);
    }
    return rewardsTrack;
  }
}
