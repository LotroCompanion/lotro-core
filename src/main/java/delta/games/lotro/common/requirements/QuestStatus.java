package delta.games.lotro.common.requirements;

import java.util.HashMap;
import java.util.Map;

/**
 * Quest status.
 * @author DAM
 */
public class QuestStatus
{
  /**
   * Minimum index for an underway objective.
   */
  public static final int MIN_UNDERWAY_OBJECTIVE=1;
  /**
   * Maximum index for an underway objective.
   */
  public static final int MAX_UNDERWAY_OBJECTIVE=35;
  private static final String KEY_UNDERWAY="UNDERWAY";
  private static final Map<String,QuestStatus> _statusMap=buildStatuses();
  private String _key;
  private int _objectiveIndex;

  private static Map<String,QuestStatus> buildStatuses()
  {
    Map<String,QuestStatus> ret=new HashMap<String,QuestStatus>();
    for(int i=MIN_UNDERWAY_OBJECTIVE;i<=MAX_UNDERWAY_OBJECTIVE;i++)
    {
      QuestStatus status=new QuestStatus(i);
      ret.put(status.getKey(),status);
    }
    return ret;
  }

  private QuestStatus(String key)
  {
    _key=key;
    _objectiveIndex=0;
    _statusMap.put(key,this);
  }

  private QuestStatus(int objectiveIndex)
  {
    _objectiveIndex=objectiveIndex;
    _key=KEY_UNDERWAY+_objectiveIndex;
  }

  /**
   * Get the identifying key.
   * @return A persistable key.
   */
  public String getKey()
  {
    return _key;
  }

  /**
   * Get the objective index.
   * @return An objective index, starting at 1. 0 if not used.
   */
  public int getObjectiveIndex()
  {
    return _objectiveIndex;
  }

  /**
   * Completed.
   */
  public static final QuestStatus COMPLETED=new QuestStatus("COMPLETED");
  /**
   * Underway.
   */
  public static final QuestStatus UNDERWAY=new QuestStatus("UNDERWAY");
  /**
   * Failed.
   */
  public static final QuestStatus FAILED=new QuestStatus("FAILED");

  /**
   * Get a 'objective underway status'. 
   * @param objectiveIndex Objective index, starting at 1.
   * @return A quest status.
   */
  public static final QuestStatus getUnderwayObjective(int objectiveIndex)
  {
    String key=KEY_UNDERWAY+objectiveIndex;
    return _statusMap.get(key);
  }

  /**
   * Get a status using its external, persistable key.
   * @param key A key.
   * @return A status or <code>null</code> if not found.
   */
  public static final QuestStatus getByKey(String key)
  {
    return _statusMap.get(key);
  }
}
