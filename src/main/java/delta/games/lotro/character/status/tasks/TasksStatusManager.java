package delta.games.lotro.character.status.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.character.status.achievables.AchievableStatus;
import delta.games.lotro.character.status.achievables.AchievablesStatusManager;
import delta.games.lotro.character.status.tasks.comparators.TaskStatusSortUtils;
import delta.games.lotro.lore.quests.QuestDescription;
import delta.games.lotro.lore.tasks.Task;
import delta.games.lotro.lore.tasks.TasksRegistry;

/**
 * Storage for all tasks statuses for a single character.
 * @author DAM
 */
public class TasksStatusManager
{
  private int _completedTasks;
  private Map<Integer,TaskStatus> _statuses;

  /**
   * Constructor.
   */
  public TasksStatusManager()
  {
    _completedTasks=0;
    _statuses=new HashMap<Integer,TaskStatus>(); 
  }

  /**
   * Initialize from a quests status manager.
   * @param questsStatus Quests status.
   */
  public void init(AchievablesStatusManager questsStatus)
  {
    _statuses.clear();
    List<Task> tasks=TasksRegistry.getInstance().getTasks();
    for(Task task : tasks)
    {
     QuestDescription quest=task.getQuest();
      AchievableStatus status=questsStatus.get(quest,true);
      TaskStatus taskStatus=new TaskStatus(task,status);
      int taskId=quest.getIdentifier();
      _statuses.put(Integer.valueOf(taskId),taskStatus);
      if (status.isCompleted())
      {
        Integer count=status.getCompletionCount();
        int countInt=(count!=null)?count.intValue():1;
        _completedTasks+=countInt;
      }
    }
  }

  /**
   * Get the number of completed tasks.
   * @return the number of completed tasks.
   */
  public int getCompletedTasksCount()
  {
    return _completedTasks;
  }

  /**
   * Get the managed statuses.
   * @return a list of task statuses.
   */
  public List<TaskStatus> getTasksStatuses()
  {
    List<TaskStatus> ret=new ArrayList<TaskStatus>(_statuses.values());
    TaskStatusSortUtils.sortByTaskItemName(ret);
    return ret;
  }

  /**
   * Get the status for a task.
   * @param taskId Task/quest identifier.
   * @return A tasks status or <code>null</code> if not found.
   */
  public TaskStatus getStatus(int taskId)
  {
    return _statuses.get(Integer.valueOf(taskId));
  }
}
