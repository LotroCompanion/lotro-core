package delta.games.lotro.lore.tasks.deeds;

import delta.games.lotro.lore.deeds.DeedDescription;

/**
 * Task related deed.
 * @author DAM
 */
public class TaskDeed
{
  private DeedDescription _deed;
  private int _taskCount;

  /**
   * Constructor.
   * @param deed Associated deed.
   * @param taskCount Tasks count.
   */
  public TaskDeed(DeedDescription deed, int taskCount)
  {
    _deed=deed;
    _taskCount=taskCount;
  }

  /**
   * Get the associated deed.
   * @return the associated deed.
   */
  public DeedDescription getDeed()
  {
    return _deed;
  }

  /**
   * Get the tasks count for this deed.
   * @return a tasks count.
   */
  public int getTasksCount()
  {
    return _taskCount;
  }
}
