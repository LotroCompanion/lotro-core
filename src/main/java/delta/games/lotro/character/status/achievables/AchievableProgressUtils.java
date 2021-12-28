package delta.games.lotro.character.status.achievables;

import java.util.List;

import org.apache.log4j.Logger;

import delta.games.lotro.lore.deeds.DeedDescription;
import delta.games.lotro.lore.quests.Achievable;
import delta.games.lotro.lore.quests.objectives.Objective;
import delta.games.lotro.lore.quests.objectives.ObjectiveCondition;

/**
 * Utility methods related to achievable progress.
 * @author DAM
 */
public class AchievableProgressUtils
{
  private static final Logger LOGGER=Logger.getLogger(AchievableProgressUtils.class);

  /**
   * Build a progress for the given status.
   * @param status Input status.
   * @return A progress or <code>null</code> if not supported.
   */
  public static Progress buildProgress(AchievableStatus status)
  {
    Achievable achievable=status.getAchievable();
    if (achievable instanceof DeedDescription)
    {
      DeedDescription deed=(DeedDescription)achievable;
      AchievableElementState state=status.getState();
      if (state==AchievableElementState.UNDEFINED)
      {
        return null;
      }
      boolean multiConditionDeed=false;
      int maxCount=computeMaxCountUsingCountedDeeds(deed);
      if (maxCount==0)
      {
        multiConditionDeed=true;
        maxCount=computeMaxCountUsingMultiConditionDeeds(deed);
      }
      if (maxCount==0)
      {
        return null;
      }
      if (state==AchievableElementState.COMPLETED)
      {
        return new Progress(maxCount,maxCount);
      }
      int currentCount=0;
      if (multiConditionDeed)
      {
        currentCount=computeCurrentCountUsingMultiConditionDeeds(status,deed);
      }
      else
      {
        currentCount=computeCurrentCountUsingCountedDeeds(status,deed);
      }
      return new Progress(currentCount,maxCount);
    }
    return null;
  }

  private static int computeMaxCountUsingCountedDeeds(DeedDescription deed)
  {
    int totalCount=0;
    int nbObjectivesWithCount=0;
    for(Objective objective : deed.getObjectives().getObjectives())
    {
      boolean hasCount=false;
      for(ObjectiveCondition condition : objective.getConditions())
      {
        int count=condition.getCount();
        if (count>1)
        {
          hasCount=true;
          totalCount+=count;
        }
      }
      if (hasCount)
      {
        nbObjectivesWithCount++;
      }
    }
    if (nbObjectivesWithCount>0)
    {
      //System.out.println("Deed: "+deed.getName()+" => "+nbCounts+" conditions");
      if (nbObjectivesWithCount>1)
      {
        LOGGER.warn("Found deed with several counted objectives: "+deed.getIdentifier());
      }
      return totalCount;
    }
    return 0;
  }

  private static int computeMaxCountUsingMultiConditionDeeds(DeedDescription deed)
  {
    List<Objective> objectives=deed.getObjectives().getObjectives();
    int nbObjectives=objectives.size();
    Objective toUse=objectives.get(nbObjectives-1);
    int nbConditions=toUse.getConditions().size();
    return (nbConditions>1)?nbConditions:0;
  }

  private static int computeCurrentCountUsingCountedDeeds(AchievableStatus status, DeedDescription deed)
  {
    int ret=0;
    for(Objective objective : deed.getObjectives().getObjectives())
    {
      AchievableObjectiveStatus objectiveStatus=status.getObjectiveStatus(objective.getIndex());
      for(ObjectiveCondition condition : objective.getConditions())
      {
        int count=condition.getCount();
        if (count>1)
        {
          ObjectiveConditionStatus conditionStatus=objectiveStatus.getConditionStatus(condition.getIndex());
          AchievableElementState conditionState=conditionStatus.getState();
          if (conditionState==AchievableElementState.COMPLETED)
          {
            ret+=count;
          }
          else if (conditionState==AchievableElementState.UNDERWAY)
          {
            Integer conditionCount=conditionStatus.getCount();
            ret+=((conditionCount!=null)?conditionCount.intValue():0);
          }
        }
      }
    }
    return ret;
  }

  private static int computeCurrentCountUsingMultiConditionDeeds(AchievableStatus status, DeedDescription deed)
  {
    List<Objective> objectives=deed.getObjectives().getObjectives();
    int nbObjectives=objectives.size();
    Objective toUse=objectives.get(nbObjectives-1);
    AchievableObjectiveStatus objectiveStatus=status.getObjectiveStatus(toUse.getIndex());
    int count=0;
    for(ObjectiveCondition condition : toUse.getConditions())
    {
      ObjectiveConditionStatus conditionStatus=objectiveStatus.getConditionStatus(condition.getIndex());
      AchievableElementState conditionState=conditionStatus.getState();
      if (conditionState==AchievableElementState.COMPLETED)
      {
        count++;
      }
    }
    return count;
  }
}
