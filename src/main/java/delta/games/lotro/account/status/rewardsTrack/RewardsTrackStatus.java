package delta.games.lotro.account.status.rewardsTrack;

import delta.games.lotro.lore.rewardsTrack.RewardsTrack;

/**
 * Status of a rewards track.
 * @author DAM
 */
public class RewardsTrackStatus
{
  private RewardsTrack _rewardsTrack;
  private int _claimedMilestones;
  private int _currentMilestone;
  private int _lastExperienceGoal;
  private int _currentExperience;
  private int _nextExperienceGoal;

  /**
   * Constructor.
   * @param rewardsTrack Associated rewards track.
   */
  public RewardsTrackStatus(RewardsTrack rewardsTrack)
  {
    _rewardsTrack=rewardsTrack;
    _claimedMilestones=0;
    _currentMilestone=0;
    _lastExperienceGoal=0;
    _currentExperience=0;
    _nextExperienceGoal=0;
  }

  /**
   * Get the associated rewards track.
   * @return a rewards track.
   */
  public RewardsTrack getRewardsTrack()
  {
    return _rewardsTrack;
  }

  /**
   * Get the claimed milestones.
   * @return the claimed milestones.
   */
  public int getClaimedMilestones()
  {
    return _claimedMilestones;
  }

  /**
   * Set the claimed milestones.
   * @param claimedMilestones the value to set.
   */
  public void setClaimedMilestones(int claimedMilestones)
  {
    _claimedMilestones=claimedMilestones;
  }

  /**
   * Get the current/last unlocked milestone.
   * @return the current milestone.
   */
  public int getCurrentMilestone()
  {
    return _currentMilestone;
  }

  /**
   * Set the current milestone.
   * @param currentMilestone the value to set.
   */
  public void setCurrentMilestone(int currentMilestone)
  {
    _currentMilestone=currentMilestone;
  }

  /**
   * Get the last experience goal.
   * @return the normalized XP value for the last finished milestone.
   */
  public int getLastExperienceGoal()
  {
    return _lastExperienceGoal;
  }

  /**
   * Set the last experience goal.
   * @param lastExperienceGoal the normalized XP value to set.
   */
  public void setLastExperienceGoal(int lastExperienceGoal)
  {
    _lastExperienceGoal=lastExperienceGoal;
  }

  /**
   * Get the current XP.
   * @return the current normalized XP value.
   */
  public int getCurrentExperience()
  {
    return _currentExperience;
  }

  /**
   * Set the current XP.
   * @param currentExperience the normalized XP value to set.
   */
  public void setCurrentExperience(int currentExperience)
  {
    _currentExperience=currentExperience;
  }

  /**
   * Get the next experience goal.
   * @return the normalized XP value for the next milestone.
   */
  public int getNextExperienceGoal()
  {
    return _nextExperienceGoal;
  }

  /**
   * Set the next experience goal.
   * @param nextExperienceGoal the normalized XP value to set.
   */
  public void setNextExperienceGoal(int nextExperienceGoal)
  {
    _nextExperienceGoal=nextExperienceGoal;
  }
}
