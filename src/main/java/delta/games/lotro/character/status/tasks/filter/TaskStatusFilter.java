package delta.games.lotro.character.status.tasks.filter;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.character.status.achievables.filter.QuestStatusFilter;
import delta.games.lotro.character.status.tasks.TaskStatus;

/**
 * Filter for task statuses.
 * @author DAM
 */
public class TaskStatusFilter implements Filter<TaskStatus>
{
  private QuestStatusFilter _questStatusFilter;
  //private TaskFilter _taskFilter;

  /**
   * Constructor.
   */
  public TaskStatusFilter()
  {
    _questStatusFilter=new QuestStatusFilter();
  }

  /**
   * Get the managed quest status filter.
   * @return the managed quest status filter.
   */
  public QuestStatusFilter getQuestStatusFilter()
  {
    return _questStatusFilter;
  }

  @Override
  public boolean accept(TaskStatus item)
  {
    return _questStatusFilter.accept(item.getStatus());
  }
}
