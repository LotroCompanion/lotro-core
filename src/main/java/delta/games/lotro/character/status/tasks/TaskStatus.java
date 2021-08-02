package delta.games.lotro.character.status.tasks;

import delta.games.lotro.character.status.achievables.AchievableStatus;
import delta.games.lotro.lore.tasks.Task;

/**
 * Status of a single task quest.
 * @author DAM
 */
public class TaskStatus
{
  private Task _task;
  private AchievableStatus _status;

  /**
   * Constructor.
   * @param task Associated task.
   * @param status Associated quest status.
   */
  public TaskStatus(Task task, AchievableStatus status)
  {
    _task=task;
    _status=status;
  }

  /**
   * Get the associated task.
   * @return a task.
   */
  public Task getTask()
  {
    return _task;
  }

  /**
   * Get the associated quest status.
   * @return a status.
   */
  public AchievableStatus getStatus()
  {
    return _status;
  }
}
