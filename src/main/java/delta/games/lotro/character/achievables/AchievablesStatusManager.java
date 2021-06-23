package delta.games.lotro.character.achievables;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.lore.quests.Achievable;

/**
 * Storage for all deed OR quest statuses for a single character.
 * @author DAM
 */
public class AchievablesStatusManager
{
  private Map<Integer,AchievableStatus> _status;

  /**
   * Constructor.
   */
  public AchievablesStatusManager()
  {
    _status=new HashMap<Integer,AchievableStatus>();
  }

  /**
   * Get the status of an achievable.
   * @param achievable Targeted achievable.
   * @param createIfNecessary Indicates if the status shall be created if it
   * does not exist.
   * @return An achievable status or <code>null</code>.
   */
  public AchievableStatus get(Achievable achievable, boolean createIfNecessary)
  {
    Integer key=Integer.valueOf(achievable.getIdentifier());
    AchievableStatus ret=_status.get(key);
    if ((ret==null) && (createIfNecessary))
    {
      ret=new AchievableStatus(achievable);
      _status.put(key,ret);
    }
    return ret;
  }

  /**
   * Cleanup un-needed entries.
   */
  public void cleanup()
  {
    List<Integer> keys=new ArrayList<Integer>(_status.keySet());
    for(Integer key : keys)
    {
      AchievableStatus status=_status.get(key);
      if (status.isEmpty())
      {
        _status.remove(key);
      }
    }
  }

  /**
   * Get all managed statuses.
   * @return A list of statuses, ordered by achievable ID.
   */
  public List<AchievableStatus> getAll()
  {
    List<Integer> ids=new ArrayList<Integer>(_status.keySet());
    Collections.sort(ids);
    List<AchievableStatus> ret=new ArrayList<AchievableStatus>();
    for(Integer id : ids)
    {
      ret.add(_status.get(id));
    }
    return ret;
  }
}
