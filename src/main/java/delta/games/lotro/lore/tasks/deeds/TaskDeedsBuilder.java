package delta.games.lotro.lore.tasks.deeds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import delta.games.lotro.common.enums.QuestCategory;
import delta.games.lotro.lore.deeds.DeedDescription;
import delta.games.lotro.lore.deeds.DeedsManager;
import delta.games.lotro.lore.quests.objectives.Objective;
import delta.games.lotro.lore.quests.objectives.ObjectiveCondition;
import delta.games.lotro.lore.quests.objectives.ObjectivesManager;
import delta.games.lotro.lore.quests.objectives.QuestCompleteCondition;

/**
 * Builder for the task deeds manager.
 * @author DAM
 */
public class TaskDeedsBuilder
{
  /**
   * Build the task deeds manager.
   * @return the newly built task deeds manager.
   */
  public TaskDeedsManager build()
  {
    List<TaskDeed> taskDeeds=findTaskDeeds();
    sortTaskDeeds(taskDeeds);
    TaskDeedsManager ret=new TaskDeedsManager(taskDeeds);
    return ret;
  }

  private List<TaskDeed> findTaskDeeds()
  {
    List<TaskDeed> ret=new ArrayList<TaskDeed>();
    for(DeedDescription deed : DeedsManager.getInstance().getAll())
    {
      Integer taskCount=isTaskDeed(deed);
      if (taskCount!=null)
      {
        TaskDeed taskDeed=new TaskDeed(deed,taskCount.intValue());
        ret.add(taskDeed);
      }
    }
    return ret;
  }

  private Integer isTaskDeed(DeedDescription deed)
  {
    ObjectivesManager objectivesMgr=deed.getObjectives();
    for(Objective objective : objectivesMgr.getObjectives())
    {
      Integer taskCount=isTaskDeedObjective(objective);
      if (taskCount!=null)
      {
        return taskCount;
      }
    }
    return null;
  }

  private Integer isTaskDeedObjective(Objective objective)
  {
    for(ObjectiveCondition condition : objective.getConditions())
    {
      if (condition instanceof QuestCompleteCondition)
      {
        QuestCompleteCondition questCondition=(QuestCompleteCondition)condition;
        QuestCategory questCategory=questCondition.getQuestCategory();
        if ((questCategory!=null) && (questCategory.getCode()==112)) // Task
        {
          int tasksCount=questCondition.getCount();
          return Integer.valueOf(tasksCount);
        }
      }
    }
    return null;
  }

  private void sortTaskDeeds(List<TaskDeed> taskDeeds)
  {
    Comparator<TaskDeed> c=new Comparator<TaskDeed>()
    {
      public int compare(TaskDeed td1, TaskDeed td2)
      {
        return Integer.compare(td1.getTasksCount(),td2.getTasksCount());
      }
    };
    Collections.sort(taskDeeds,c);
  }
}
