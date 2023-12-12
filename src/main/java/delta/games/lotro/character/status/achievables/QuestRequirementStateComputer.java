package delta.games.lotro.character.status.achievables;

import org.apache.log4j.Logger;

import delta.common.utils.collections.filters.Operator;
import delta.games.lotro.common.requirements.AbstractAchievableRequirement;
import delta.games.lotro.common.requirements.CompoundQuestRequirement;
import delta.games.lotro.common.requirements.QuestRequirement;
import delta.games.lotro.common.requirements.QuestStatus;
import delta.games.lotro.common.utils.ComparisonOperator;
import delta.games.lotro.lore.deeds.DeedDescription;
import delta.games.lotro.lore.quests.Achievable;
import delta.games.lotro.lore.quests.QuestDescription;

/**
 * Compute the state of quests/deeds requirements.
 * @author DAM
 */
public class QuestRequirementStateComputer
{
  private static final Logger LOGGER=Logger.getLogger(QuestRequirementStateComputer.class);

  private AchievablesStatusManager _questsStatusMgr;
  private AchievablesStatusManager _deedsStatusMgr;

  /**
   * Constructor.
   * @param questsStatusMgr Quests status manager.
   * @param deedsStatusMgr Deeds status manager.
   */
  public QuestRequirementStateComputer(AchievablesStatusManager questsStatusMgr, AchievablesStatusManager deedsStatusMgr)
  {
    _questsStatusMgr=questsStatusMgr;
    _deedsStatusMgr=deedsStatusMgr;
  }

  /**
   * Assess a requirement.
   * @param requirement Requirement to use.
   * @return <code>true</code> if the requirement is met, <code>false</code> otherwise.
   */
  public boolean assess(AbstractAchievableRequirement requirement)
  {
    if (requirement instanceof QuestRequirement)
    {
      QuestRequirement questRequirement=(QuestRequirement)requirement;
      return assess(questRequirement);
    }
    else if (requirement instanceof CompoundQuestRequirement)
    {
      CompoundQuestRequirement compoundRequirement=(CompoundQuestRequirement)requirement;
      Operator operator=compoundRequirement.getOperator();
      for(AbstractAchievableRequirement childRequirement : compoundRequirement.getRequirements())
      {
        boolean childAssess=assess(childRequirement);
        if ((operator==Operator.OR) && (childAssess))
        {
          return true;
        }
        if ((operator==Operator.AND) && (!childAssess))
        {
          return false;
        }
      }
      return (operator==Operator.AND);
    }
    LOGGER.error("Unmanaged quest requirement class: "+requirement);
    return false;
  }

  private boolean assess(QuestRequirement requirement)
  {
    ComparisonOperator operator=requirement.getOperator();
    Achievable achievable=requirement.getRequiredAchievable().getObject();
    QuestStatus status=requirement.getQuestStatus();
    AchievableStatus achievableStatus=getStatus(achievable);
    if (operator==ComparisonOperator.EQUAL)
    {
      return assessEquals(achievableStatus,status);
    }
    else if (operator==ComparisonOperator.GREATER_OR_EQUAL)
    {
      return assessGreaterOrEquals(achievableStatus,status);
    }
    else if (operator==ComparisonOperator.NOT_EQUAL)
    {
      return assessNotEqual(achievableStatus,status);
    }
    else if (operator==ComparisonOperator.GREATER)
    {
      return assessGreater(achievableStatus,status);
    }
    else if (operator==ComparisonOperator.LESS)
    {
      return assessLess(achievableStatus,status);
    }
    LOGGER.error("Unmanaged quest requirement operator: "+operator);
    return false;
  }

  private boolean assessEquals(AchievableStatus achievableStatus, QuestStatus status)
  {
    AchievableElementState state=achievableStatus.getState();
    if (status==QuestStatus.COMPLETED)
    {
      return state==AchievableElementState.COMPLETED;
    }
    else if (status==QuestStatus.UNDERWAY)
    {
      return state==AchievableElementState.UNDERWAY;
    }
    else if (status==QuestStatus.FAILED)
    {
      LOGGER.warn("Unmanaged EQUAL status: "+status);
      return false;
    }
    int index=status.getObjectiveIndex();
    if (state==AchievableElementState.UNDERWAY)
    {
      AchievableObjectiveStatus objectiveStatus=achievableStatus.getObjectiveStatus(index);
      return (objectiveStatus.getState()==AchievableElementState.UNDERWAY);
    }
    return false;
  }

  private boolean assessGreaterOrEquals(AchievableStatus achievableStatus, QuestStatus status)
  {
    AchievableElementState state=achievableStatus.getState();
    if (status==QuestStatus.COMPLETED)
    {
      return state==AchievableElementState.COMPLETED;
    }
    else if (status==QuestStatus.UNDERWAY)
    {
      return ((state==AchievableElementState.UNDERWAY) || (state==AchievableElementState.COMPLETED));
    }
    else if (status==QuestStatus.FAILED)
    {
      LOGGER.warn("Unmanaged GREATER_OR_EQUAL status: "+status);
      return true;
    }
    int index=status.getObjectiveIndex();
    if (state==AchievableElementState.COMPLETED)
    {
      return true;
    }
    if (state==AchievableElementState.UNDERWAY)
    {
      AchievableObjectiveStatus objectiveStatus=achievableStatus.getObjectiveStatus(index);
      AchievableElementState objectiveState=objectiveStatus.getState();
      return ((objectiveState==AchievableElementState.UNDERWAY) || (objectiveState==AchievableElementState.COMPLETED));
    }
    return false;
  }

  private boolean assessNotEqual(AchievableStatus achievableStatus, QuestStatus status)
  {
    AchievableElementState state=achievableStatus.getState();
    if (status==QuestStatus.COMPLETED)
    {
      return state!=AchievableElementState.COMPLETED;
    }
    else if (status==QuestStatus.UNDERWAY)
    {
      return (state!=AchievableElementState.UNDERWAY);
    }
    else if (status==QuestStatus.FAILED)
    {
      LOGGER.warn("Unmanaged NOT_EQUAL status: "+status);
      return true;
    }
    int index=status.getObjectiveIndex();
    if (state==AchievableElementState.UNDERWAY)
    {
      AchievableObjectiveStatus objectiveStatus=achievableStatus.getObjectiveStatus(index);
      AchievableElementState objectiveState=objectiveStatus.getState();
      return (objectiveState!=AchievableElementState.UNDERWAY);
    }
    return true;
  }

  private boolean assessGreater(AchievableStatus achievableStatus, QuestStatus status)
  {
    AchievableElementState state=achievableStatus.getState();
    if (status==QuestStatus.COMPLETED)
    {
      return false;
    }
    else if (status==QuestStatus.UNDERWAY)
    {
      // Not used
      return (state==AchievableElementState.COMPLETED);
    }
    else if (status==QuestStatus.FAILED)
    {
      // Not used
    }
    else
    {
      int index=status.getObjectiveIndex();
      if (state==AchievableElementState.UNDERWAY)
      {
        AchievableObjectiveStatus objectiveStatus=achievableStatus.getObjectiveStatus(index);
        AchievableElementState objectiveState=objectiveStatus.getState();
        return (objectiveState==AchievableElementState.COMPLETED);
      }
      return (state==AchievableElementState.COMPLETED);
    }
    LOGGER.warn("Unmanaged GREATER status: "+status);
    return false;
  }

  private boolean assessLess(AchievableStatus achievableStatus, QuestStatus status)
  {
    AchievableElementState state=achievableStatus.getState();
    if (status==QuestStatus.UNDERWAY)
    {
      return (state==AchievableElementState.UNDEFINED);
    }
    // Not used
    LOGGER.warn("Unmanaged LESS status: "+status);
    return false;
  }

  private AchievableStatus getStatus(Achievable achievable)
  {
    AchievableStatus achievableStatus=null;
    if (achievable instanceof QuestDescription)
    {
      achievableStatus=_questsStatusMgr.get(achievable,false);
    }
    else if (achievable instanceof DeedDescription)
    {
      achievableStatus=_deedsStatusMgr.get(achievable,false);
    }
    if (achievableStatus==null)
    {
      achievableStatus=new AchievableStatus(achievable);
    }
    return achievableStatus;
  }
}
