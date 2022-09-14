package delta.games.lotro.character.status.hobbies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.lore.hobbies.HobbyDescription;

/**
 * Storage for all hobbies status data for a single character.
 * @author DAM
 */
public class HobbiesStatusManager
{
  private Map<Integer,HobbyStatus> _status;

  /**
   * Constructor.
   */
  public HobbiesStatusManager()
  {
    _status=new HashMap<Integer,HobbyStatus>();
  }

  /**
   * Clear data.
   */
  public void clear()
  {
    _status.clear();
  }

  /**
   * Get the status of a hobby.
   * @param hobby Targeted hobby.
   * @param createIfNecessary Indicates if the status shall be created if it
   * does not exist.
   * @return A hobby status or <code>null</code>.
   */
  public HobbyStatus get(HobbyDescription hobby, boolean createIfNecessary)
  {
    Integer key=Integer.valueOf(hobby.getIdentifier());
    HobbyStatus ret=_status.get(key);
    if ((ret==null) && (createIfNecessary))
    {
      ret=new HobbyStatus(hobby);
      _status.put(key,ret);
    }
    return ret;
  }

  /**
   * Get all managed statuses.
   * @return A list of statuses, ordered by hobby ID.
   */
  public List<HobbyStatus> getAll()
  {
    List<Integer> ids=new ArrayList<Integer>(_status.keySet());
    Collections.sort(ids);
    List<HobbyStatus> ret=new ArrayList<HobbyStatus>();
    for(Integer id : ids)
    {
      ret.add(_status.get(id));
    }
    return ret;
  }
}
