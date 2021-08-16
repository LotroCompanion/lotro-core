package delta.games.lotro.lore.tasks.quests;

import java.util.ArrayList;
import java.util.List;

/**
 * Manager for task quests.
 * @author DAM
 */
public class TaskQuestsManager
{
  private List<TaskQuest> _taskQuests;

  /**
   * Constructor.
   * @param taskQuests Task quests.
   */
  public TaskQuestsManager(List<TaskQuest> taskQuests)
  {
    _taskQuests=new ArrayList<TaskQuest>(taskQuests);
  }

  /**
   * Get the task quests.
   * @return a list of task quests, sorted by name.
   */
  public List<TaskQuest> getTaskQuests()
  {
    return new ArrayList<TaskQuest>(_taskQuests);
  }
}
