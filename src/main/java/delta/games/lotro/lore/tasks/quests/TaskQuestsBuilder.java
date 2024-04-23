package delta.games.lotro.lore.tasks.quests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import delta.games.lotro.common.enums.QuestCategory;
import delta.games.lotro.lore.quests.QuestDescription;
import delta.games.lotro.lore.quests.QuestsManager;
import delta.games.lotro.lore.quests.objectives.Objective;
import delta.games.lotro.lore.quests.objectives.ObjectiveCondition;
import delta.games.lotro.lore.quests.objectives.ObjectivesManager;
import delta.games.lotro.lore.quests.objectives.QuestCompleteCondition;

/**
 * Builder for the task quests manager.
 * @author DAM
 */
public class TaskQuestsBuilder
{
  /**
   * Build the task quests manager.
   * @return the newly built task quests manager.
   */
  public TaskQuestsManager build()
  {
    List<TaskQuest> taskQuests=findTaskQuests();
    sortTaskQuests(taskQuests);
    TaskQuestsManager ret=new TaskQuestsManager(taskQuests);
    return ret;
  }

  private List<TaskQuest> findTaskQuests()
  {
    List<TaskQuest> ret=new ArrayList<TaskQuest>();
    for(QuestDescription quest : QuestsManager.getInstance().getAll())
    {
      Integer taskCount=isTaskQuest(quest);
      if (taskCount!=null)
      {
        TaskQuest taskQuest=new TaskQuest(quest,taskCount.intValue());
        ret.add(taskQuest);
      }
    }
    return ret;
  }

  private Integer isTaskQuest(QuestDescription quest)
  {
    ObjectivesManager objectivesMgr=quest.getObjectives();
    for(Objective objective : objectivesMgr.getObjectives())
    {
      Integer taskCount=isTaskQuestObjective(objective);
      if (taskCount!=null)
      {
        return taskCount;
      }
    }
    return null;
  }

  private Integer isTaskQuestObjective(Objective objective)
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

  private void sortTaskQuests(List<TaskQuest> taskQuests)
  {
    Comparator<TaskQuest> c=new Comparator<TaskQuest>()
    {
      public int compare(TaskQuest tq1, TaskQuest tq2)
      {
        String name1=tq1.getQuest().getName();
        String name2=tq2.getQuest().getName();
        return name1.compareTo(name2);
      }
    };
    Collections.sort(taskQuests,c);
  }
}
