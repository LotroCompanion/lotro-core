package delta.games.lotro.lore.tasks.deeds;

import java.util.ArrayList;
import java.util.List;

/**
 * Manager for task deeds.
 * @author DAM
 */
public class TaskDeedsManager
{
  private List<TaskDeed> _taskDeeds;

  /**
   * Constructor.
   * @param taskDeeds Task deeds.
   */
  public TaskDeedsManager(List<TaskDeed> taskDeeds)
  {
    _taskDeeds=new ArrayList<TaskDeed>(taskDeeds);
  }

  /**
   * Get the task deeds.
   * @return a list of task deeds, sorted by increasing tasks count.
   */
  public List<TaskDeed> getTaskDeeds()
  {
    return new ArrayList<TaskDeed>(_taskDeeds);
  }

  /**
   * Get the task deed in progress (if any).
   * @param tasksCount The tasks count.
   * @return A task deed or <code>null</code> if all deeds were done.
   */
  public TaskDeed getTaskDeedInProgress(int tasksCount)
  {
    TaskDeed achieved=getAchievedTaskDeed(tasksCount);
    if (achieved==null)
    {
      return _taskDeeds.get(0);
    }
    int index=_taskDeeds.indexOf(achieved)+1;
    if (index>=_taskDeeds.size())
    {
      return null;
    }
    return _taskDeeds.get(index);
  }

  /**
   * Get the last achieved task deed (if any).
   * @param tasksCount The tasks count.
   * @return A task deed or <code>null</code> if none completed.
   */
  public TaskDeed getAchievedTaskDeed(int tasksCount)
  {
    TaskDeed ret=null;
    for(TaskDeed taskDeed : _taskDeeds)
    {
      if (taskDeed.getTasksCount()<=tasksCount)
      {
        ret=taskDeed;
      }
    }
    return ret;
  }
}
