package delta.games.lotro.character.achievables;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.lore.quests.objectives.Objective;
import delta.games.lotro.lore.quests.objectives.ObjectiveCondition;

/**
 * Status of a single objective of a single achievable.
 * @author DAM
 */
public class AchievableObjectiveStatus
{
  private AchievableStatus _parent;
  private Objective _objective;
  private AchievableElementState _state;
  //private Long _completionDate;
  private List<ObjectiveConditionStatus> _conditionStatuses;

  /**
   * Constructor.
   * @param achievableStatus Parent status.
   * @param objective Associated objective.
   */
  public AchievableObjectiveStatus(AchievableStatus achievableStatus, Objective objective)
  {
    _parent=achievableStatus;
    _objective=objective;
    _state=AchievableElementState.UNDEFINED;
    initConditionStatuses();
  }

  private void initConditionStatuses()
  {
    _conditionStatuses=new ArrayList<ObjectiveConditionStatus>();
    List<ObjectiveCondition> conditions=_objective.getConditions();
    for(ObjectiveCondition condition : conditions)
    {
      ObjectiveConditionStatus conditionStatus=new ObjectiveConditionStatus(this,condition);
      _conditionStatuses.add(conditionStatus);
    }
  }

  /**
   * Copy contents from the given source.
   * @param source Source to use.
   */
  public void copyFrom(AchievableObjectiveStatus source)
  {
    _state=source._state;
    int nbConditions=_conditionStatuses.size();
    for(int i=0;i<nbConditions;i++)
    {
      ObjectiveConditionStatus to=_conditionStatuses.get(i);
      ObjectiveConditionStatus from=source._conditionStatuses.get(i);
      to.copyFrom(from);
    }
  }

  /**
   * Get the parent status.
   * @return the parent status.
   */
  public AchievableStatus getParentStatus()
  {
    return _parent;
  }

  /**
   * Indicates if all conditions are completed.
   * @return <code>true</code> if they are, <code>false</code> otherwise.
   */
  public boolean areConditionsCompleted()
  {
    for(ObjectiveConditionStatus conditionStatus : _conditionStatuses)
    {
      if (conditionStatus.getState()!=AchievableElementState.COMPLETED)
      {
        return false;
      }
    }
    return true;
  }

  /**
   * Get the associated objective.
   * @return the associated objective.
   */
  public Objective getObjective()
  {
    return _objective;
  }

  /**
   * Get the state of the managed objective.
   * @return A state.
   */
  public AchievableElementState getState()
  {
    return _state;
  }

  /**
   * Set the state of the managed objective.
   * @param state state to set.
   */
  public void setState(AchievableElementState state)
  {
    _state=state;
  }

  /**
   * Update internal state.
   */
  public void updateInternalState()
  {
    for(ObjectiveConditionStatus conditionStatus : _conditionStatuses)
    {
      if (_state==AchievableElementState.COMPLETED)
      {
        conditionStatus.setState(AchievableElementState.COMPLETED);
      }
      conditionStatus.updateInternalState();
    }
  }

  /**
   * Get a condition status.
   * @param conditionIndex Index of condition, starting at 0.
   * @return a condition status or <code>null</code> if no such condition.
   */
  public ObjectiveConditionStatus getConditionStatus(int conditionIndex)
  {
    if ((conditionIndex>=0) && (conditionIndex<_conditionStatuses.size()))
    {
      return _conditionStatuses.get(conditionIndex);
    }
    return null;
  }

  /**
   * Get all the condition statuses.
   * @return a list of objective condition statuses.
   */
  public List<ObjectiveConditionStatus> getConditionStatuses()
  {
    return _conditionStatuses;
  }
}
