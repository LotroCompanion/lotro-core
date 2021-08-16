package delta.games.lotro.lore.tasks.quests;

import delta.games.lotro.lore.quests.QuestDescription;

/**
 * Task related quest.
 * @author DAM
 */
public class TaskQuest
{
  private QuestDescription _quest;
  private int _taskCount;

  /**
   * Constructor.
   * @param quest Associated quest.
   * @param taskCount Tasks count.
   */
  public TaskQuest(QuestDescription quest, int taskCount)
  {
    _quest=quest;
    _taskCount=taskCount;
  }

  /**
   * Get the associated deed.
   * @return the associated deed.
   */
  public QuestDescription getQuest()
  {
    return _quest;
  }

  /**
   * Get the tasks count for this quest.
   * @return a tasks count.
   */
  public int getTasksCount()
  {
    return _taskCount;
  }
}
