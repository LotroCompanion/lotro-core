package delta.games.lotro.character.deeds;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.lore.quests.Achievable;
import delta.games.lotro.lore.quests.objectives.Objective;
import delta.games.lotro.lore.quests.objectives.ObjectivesManager;

/**
 * Base class for achievable status.
 * @author DAM
 */
public class AchievableStatus
{
  private Achievable _achievable;
  private AchievableElementState _state;
  private List<AchievableObjectiveStatus> _objectiveStatuses;

  /**
   * Constructor.
   * @param achievable Associated achievable.
   */
  public AchievableStatus(Achievable achievable)
  {
    if (achievable==null)
    {
      throw new IllegalArgumentException("achievable is null");
    }
    _achievable=achievable;
    _state=AchievableElementState.UNDEFINED;
    initObjectiveStatuses();
  }

  private void initObjectiveStatuses()
  {
    _objectiveStatuses=new ArrayList<AchievableObjectiveStatus>();
    ObjectivesManager objectivesMgr=_achievable.getObjectives();
    List<Objective> objectives=objectivesMgr.getObjectives();
    for(Objective objective : objectives)
    {
      AchievableObjectiveStatus objectiveStatus=new AchievableObjectiveStatus(objective);
      _objectiveStatuses.add(objectiveStatus);
    }
  }

  /**
   * Get the associated achievable.
   * @return the associated achievable.
   */
  public Achievable getAchievable()
  {
    return _achievable;
  }

  /**
   * Get the identifier of the managed achievable.
   * @return the identifier of the managed achievable.
   */
  public int getAchievableId()
  {
    return _achievable.getIdentifier();
  }

  /**
   * Get the state of the managed achievable.
   * @return A state.
   */
  public AchievableElementState getState()
  {
    return _state;
  }

  /**
   * Set the state of the managed achievable.
   * @param state State to set.
   */
  public void setState(AchievableElementState state)
  {
    _state=state;
  }

  /**
   * Indicates if this deed is completed, or not.
   * @return A boolean.
   */
  public boolean isCompleted()
  {
    return _state==AchievableElementState.COMPLETED;
  }

  /**
   * Set the completion status.
   * @param completed Completion status to set.
   */
  public void setCompleted(boolean completed)
  {
    if (completed)
    {
      setState(AchievableElementState.COMPLETED);
    }
    else
    {
      setState(AchievableElementState.UNDEFINED);
    }
  }

  /**
   * Get an objective status.
   * @param objectiveIndex Index of objective, starting at 1.
   * @return an objective status or <code>null</code> if no such objective.
   */
  public AchievableObjectiveStatus getObjectiveStatus(int objectiveIndex)
  {
    if ((objectiveIndex>=1) && (objectiveIndex<=_objectiveStatuses.size()))
    {
      return _objectiveStatuses.get(objectiveIndex-1);
    }
    return null;
  }

  /**
   * Get all the objective statuses.
   * @return a list of objective statuses.
   */
  public List<AchievableObjectiveStatus> getObjectiveStatuses()
  {
    return _objectiveStatuses;
  }
}
