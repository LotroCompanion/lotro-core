package delta.games.lotro.character.status.tasks.comparators;

import java.util.Collections;
import java.util.List;

import delta.games.lotro.character.status.tasks.TaskStatus;
import delta.games.lotro.lore.tasks.Task;
import delta.games.lotro.lore.tasks.comparators.TaskItemNameComparator;
import delta.games.lotro.utils.DataProvider;
import delta.games.lotro.utils.comparators.DelegatingComparator;

/**
 * Utility methods to sort task statuses.
 * @author DAM
 */
public class TaskStatusSortUtils
{
  /**
   * Sort task statuses using the task item name.
   * @param taskStatuses Elements to sort.
   */
  public static void sortByTaskItemName(List<TaskStatus> taskStatuses)
  {
    DataProvider<TaskStatus,Task> p=new DataProvider<TaskStatus,Task>()
    {
      public Task getData(TaskStatus taskStatus)
      {
        return taskStatus.getTask();
      }
    };
    DelegatingComparator<TaskStatus,Task> c=new DelegatingComparator<>(p,new TaskItemNameComparator());
    Collections.sort(taskStatuses,c);
  }


}
