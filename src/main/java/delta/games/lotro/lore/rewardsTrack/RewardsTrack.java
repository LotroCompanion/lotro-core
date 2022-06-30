package delta.games.lotro.lore.rewardsTrack;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.Named;

/**
 * @author dmorcellet
 */
public class RewardsTrack implements Identifiable,Named
{
  private int _identifier;
  private String _name;
  private String _description;
  private List<RewardsTrackStep> _steps;
  // ID of the progression to get the item XP amount for an interval as a function of the character level
  private int _xpIntervalsProgressionID;
  // Properties
  // - indicates if the reward track is active or not
  private String _activeProperty;
  private String _claimedMilestonesProperty;
  private String _currentMilestoneProperty;
  private String _lastExperienceGoalProperty;
  private String _currentExperienceProperty;
  private String _nextExperienceGoalProperty;

  /**
   * Constructor.
   * @param identifier Identifier.
   */
  public RewardsTrack(int identifier)
  {
    _identifier=identifier;
    _name="";
    _description="";
    _steps=new ArrayList<RewardsTrackStep>();
  }

  @Override
  public int getIdentifier()
  {
    return _identifier;
  }

  /**
   * Get the name of this rewards track.
   * @return the name of this rewards track.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Set the name of this rewards track.
   * @param name the name to set.
   */
  public void setName(String name)
  {
    if (name==null)
    {
      name="";
    }
    _name=name;
  }

  /**
   * Get the description of this rewards track.
   * @return the description of this rewards track.
   */
  public String getDescription()
  {
    return _description;
  }

  /**
   * Set the description of this rewards track.
   * @param description the description to set.
   */
  public void setDescription(String description)
  {
    if (description==null)
    {
      description="";
    }
    _description=description;
  }

  /**
   * Get the steps of this rewards track.
   * @return A list of steps.
   */
  public List<RewardsTrackStep> getSteps()
  {
    return _steps;
  }

  /**
   * Add a step to this rewards track.
   * @param step Step to add.
   */
  public void addStep(RewardsTrackStep step)
  {
    _steps.add(step);
  }

  /**
   * Get the identifier of the item XP amount progression.
   * @return a progression identifier.
   */
  public int getXpIntervalsProgressionID()
  {
    return _xpIntervalsProgressionID;
  }

  /**
   * Set the identifier of the item XP amount progression.
   * @param xpIntervalsProgressionID the identifier to set.
   */
  public void setXpIntervalsProgressionID(int xpIntervalsProgressionID)
  {
    _xpIntervalsProgressionID=xpIntervalsProgressionID;
  }

  /**
   * Get the name of the property that indicates if this rewards track is active or not.
   * @return A property name.
   */
  public String getActiveProperty()
  {
    return _activeProperty;
  }

  /**
   * Set the name of the 'active' property.
   * @param activeProperty the property name to set.
   */
  public void setActiveProperty(String activeProperty)
  {
    _activeProperty=activeProperty;
  }

  /**
   * Get the name of the 'claimed milestones' property.
   * @return a property name.
   */
  public String getClaimedMilestonesProperty()
  {
    return _claimedMilestonesProperty;
  }

  /**
   * Set the name of the 'claimed milestones' property.
   * @param claimedMilestonesProperty the property name to set.
   */
  public void setClaimedMilestonesProperty(String claimedMilestonesProperty)
  {
    _claimedMilestonesProperty=claimedMilestonesProperty;
  }

  /**
   * Get the name of the 'current milestone' property.
   * @return a property name.
   */
  public String getCurrentMilestoneProperty()
  {
    return _currentMilestoneProperty;
  }

  /**
   * Set the name of the 'current milestone' property.
   * @param currentMilestoneProperty the property name to set.
   */
  public void setCurrentMilestoneProperty(String currentMilestoneProperty)
  {
    _currentMilestoneProperty=currentMilestoneProperty;
  }

  /**
   * Get the name of the 'last experience goal' property.
   * @return a property name.
   */
  public String getLastExperienceGoalProperty()
  {
    return _lastExperienceGoalProperty;
  }

  /**
   * Set the name of the 'last experience goal' property.
   * @param lastExperienceGoalProperty the property name to set.
   */
  public void setLastExperienceGoalProperty(String lastExperienceGoalProperty)
  {
    _lastExperienceGoalProperty=lastExperienceGoalProperty;
  }

  /**
   * Get the name of the 'current XP' property.
   * @return a property name.
   */
  public String getCurrentExperienceProperty()
  {
    return _currentExperienceProperty;
  }

  /**
   * Set the name of the 'current XP' property.
   * @param currentExperienceProperty the property name to set.
   */
  public void setCurrentExperienceProperty(String currentExperienceProperty)
  {
    _currentExperienceProperty=currentExperienceProperty;
  }

  /**
   * Get the name of the 'next experience goal' property.
   * @return a property name.
   */
  /**
   * @return the nextExperienceGoalProperty
   */
  public String getNextExperienceGoalProperty()
  {
    return _nextExperienceGoalProperty;
  }

  /**
   * Set the name of the 'next experience goal' property.
   * @param nextExperienceGoalProperty the property name to set.
   */
  public void setNextExperienceGoalProperty(String nextExperienceGoalProperty)
  {
    _nextExperienceGoalProperty=nextExperienceGoalProperty;
  }
}
