package delta.games.lotro.character.status.achievables;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.common.Identifiable;
import delta.games.lotro.lore.deeds.DeedDescription;
import delta.games.lotro.lore.quests.Achievable;
import delta.games.lotro.lore.quests.objectives.Objective;
import delta.games.lotro.lore.quests.objectives.ObjectivesManager;

/**
 * Base class for achievable status.
 * @author DAM
 */
public class AchievableStatus implements Identifiable
{
  private Achievable _achievable;
  private Long _completionDate;
  private Integer _completionCount;
  private AchievableElementState _state;
  private Progress _progress;
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
    _completionDate=null;
    _completionCount=null;
    _state=AchievableElementState.UNDEFINED;
    initObjectiveStatuses();
    updateInternalState();
  }

  /**
   * Copy contents from the given source.
   * @param source Source to use.
   */
  public void copyFrom(AchievableStatus source)
  {
    _completionDate=source._completionDate;
    _completionCount=source._completionCount;
    _state=source._state;
    int nbObjectives=_objectiveStatuses.size();
    for(int i=0;i<nbObjectives;i++)
    {
      AchievableObjectiveStatus to=_objectiveStatuses.get(i);
      AchievableObjectiveStatus from=source._objectiveStatuses.get(i);
      to.copyFrom(from);
    }
  }

  @Override
  public int getIdentifier()
  {
    return _achievable.getIdentifier();
  }

  private void initObjectiveStatuses()
  {
    _objectiveStatuses=new ArrayList<AchievableObjectiveStatus>();
    ObjectivesManager objectivesMgr=_achievable.getObjectives();
    List<Objective> objectives=objectivesMgr.getObjectives();
    for(Objective objective : objectives)
    {
      AchievableObjectiveStatus objectiveStatus=new AchievableObjectiveStatus(this,objective);
      _objectiveStatuses.add(objectiveStatus);
    }
  }

  /**
   * Indicates if all objectives are completed.
   * @return <code>true</code> if they are, <code>false</code> otherwise.
   */
  public boolean areObjectivesCompleted()
  {
    for(AchievableObjectiveStatus objectiveStatus : _objectiveStatuses)
    {
      if (objectiveStatus.getState()!=AchievableElementState.COMPLETED)
      {
        return false;
      }
    }
    return true;
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
   * Update internal state.
   */
  public void updateInternalState()
  {
    for(AchievableObjectiveStatus objectiveStatus : _objectiveStatuses)
    {
      if (_state==AchievableElementState.COMPLETED)
      {
        objectiveStatus.setState(AchievableElementState.COMPLETED);
      }
      objectiveStatus.updateInternalState();
    }
    updateProgress();
  }

  /**
   * Get the progress for this achievable.
   * @return A progress or <code>null</code>.
   */
  public Progress getProgress()
  {
    return _progress;
  }

  private void updateProgress()
  {
    _progress=AchievableProgressUtils.buildProgress(this);
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

  /**
   * Get the completion date.
   * @return A date or <code>null</code> if not completed or completion date is not known.
   */
  public Long getCompletionDate()
  {
    return _completionDate;
  }

  /**
   * Set the completion date.
   * @param completionDate Completion date to set.
   */
  public void setCompletionDate(Long completionDate)
  {
    _completionDate=completionDate;
  }

  /**
   * Get the completion count.
   * @return A completion count, or <code>null</code> if not set.
   */
  public Integer getCompletionCount()
  {
    return _completionCount;
  }

  /**
   * Set the completion count.
   * @param completionCount Completion count to set.
   */
  public void setCompletionCount(Integer completionCount)
  {
    _completionCount=completionCount;
  }

  /**
   * Get the actual completion count.
   * @return a completion count that takes care about the current state.
   */
  public int getActualCompletionCount()
  {
    int ret=0;
    if (_state==AchievableElementState.COMPLETED)
    {
      ret=(_completionCount!=null)?_completionCount.intValue():1;
    }
    else // UNDEFINED, UNDERWAY
    {
      ret=(_completionCount!=null)?_completionCount.intValue():0;
    }
    return ret;
  }

  /**
   * Get the number of completions to do.
   * @return the number of completions to do.
   */
  public int getToDoCompletionCount()
  {
    return (_state==AchievableElementState.COMPLETED)?0:1;
  }

  /**
   * Indicates if this deed status is empty (contains no data).
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isEmpty()
  {
    if ((_completionDate!=null) || (getState()!=AchievableElementState.UNDEFINED))
    {
      return false;
    }
    return true;
  }

  /**
   * Get the previous objective status.
   * @param objectiveStatus Reference objective.
   * @return An objective status, or <code>null</code> if none.
   */
  public AchievableObjectiveStatus getPreviousStatus(AchievableObjectiveStatus objectiveStatus)
  {
    AchievableObjectiveStatus ret=null;
    int index=_objectiveStatuses.indexOf(objectiveStatus);
    if (index>=1)
    {
      ret=_objectiveStatuses.get(index-1);
    }
    return ret;
  }

  /**
   * Get the next objective status.
   * @param objectiveStatus Reference objective.
   * @return An objective status, or <code>null</code> if none.
   */
  public AchievableObjectiveStatus getNextStatus(AchievableObjectiveStatus objectiveStatus)
  {
    AchievableObjectiveStatus ret=null;
    int index=_objectiveStatuses.indexOf(objectiveStatus);
    if ((index>=0) && (index<_objectiveStatuses.size()-1))
    {
      ret=_objectiveStatuses.get(index+1);
    }
    return ret;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    int achievableId=getAchievableId();
    boolean isDeed=(_achievable instanceof DeedDescription);
    String type=(isDeed?"Deed":"Quest");
    sb.append(type).append(' ').append(_achievable.getName()).append(" (").append(achievableId).append("): ");
    sb.append(getState());
    if (_completionDate!=null)
    {
      sb.append(" (").append(new Date(_completionDate.longValue())).append(')');
    }
    if ((_completionCount!=null) && (_completionCount.intValue()>1))
    {
      sb.append(" (x").append(_completionCount).append(')');
    }
    sb.append(EndOfLine.NATIVE_EOL);
    if (_state==AchievableElementState.UNDERWAY)
    {
      for(AchievableObjectiveStatus objectiveStatus : _objectiveStatuses)
      {
        Objective objective=objectiveStatus.getObjective();
        int objectiveIndex=objective.getIndex();
        AchievableElementState objectiveState=objectiveStatus.getState();
        sb.append("\tObjective #").append(objectiveIndex).append(": ").append(objectiveState).append(EndOfLine.NATIVE_EOL);
        if (objectiveState==AchievableElementState.UNDERWAY)
        {
          for(ObjectiveConditionStatus conditionStatus : objectiveStatus.getConditionStatuses())
          {
            sb.append("\t\t").append(conditionStatus).append(EndOfLine.NATIVE_EOL);
          }
        }
      }
    }
    return sb.toString();
  }
}
