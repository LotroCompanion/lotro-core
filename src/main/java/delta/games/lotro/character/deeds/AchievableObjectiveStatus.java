package delta.games.lotro.character.deeds;

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
  private Objective _objective;
  private AchievableElementState _state;
  private List<ObjectiveConditionStatus> _conditionStatuses;

  /**
   * Constructor.
   * @param objective Associated objective.
   */
  public AchievableObjectiveStatus(Objective objective)
  {
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
      ObjectiveConditionStatus conditionStatus=new ObjectiveConditionStatus(condition);
      _conditionStatuses.add(conditionStatus);
    }
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
