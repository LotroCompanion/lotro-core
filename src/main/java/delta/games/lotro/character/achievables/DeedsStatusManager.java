package delta.games.lotro.character.achievables;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.lore.deeds.DeedDescription;
import delta.games.lotro.lore.deeds.DeedsManager;

/**
 * Storage for all deed statuses for a single character.
 * @author DAM
 */
public class DeedsStatusManager
{
  private Map<Integer,AchievableStatus> _status;

  /**
   * Constructor.
   */
  public DeedsStatusManager()
  {
    _status=new HashMap<Integer,AchievableStatus>();
  }

  /**
   * Get a deed status.
   * @param deedKey Key of the targeted deed.
   * @param createIfNecessary Indicates if the status shall be created if it
   * does not exist.
   * @return A deed status or <code>null</code>.
   */
  public AchievableStatus get(String deedKey, boolean createIfNecessary)
  {
    AchievableStatus ret=null;
    DeedDescription deed=DeedsManager.getInstance().getDeed(deedKey);
    if (deed!=null)
    {
      ret=get(deed,createIfNecessary);
    }
    return ret;
  }

  /**
   * Get a deed status.
   * @param deed Targeted deed.
   * @param createIfNecessary Indicates if the status shall be created if it
   * does not exist.
   * @return A deed status or <code>null</code>.
   */
  public AchievableStatus get(DeedDescription deed, boolean createIfNecessary)
  {
    Integer key=Integer.valueOf(deed.getIdentifier());
    AchievableStatus ret=_status.get(key);
    if ((ret==null) && (createIfNecessary))
    {
      ret=new AchievableStatus(deed);
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
      AchievableStatus deedStatus=_status.get(key);
      if (deedStatus.isEmpty())
      {
        _status.remove(key);
      }
    }
  }

  /**
   * Get all managed deed statuses.
   * @return A list of deed statuses, ordered by deed key.
   */
  public List<AchievableStatus> getAll()
  {
    List<Integer> deedKeys=new ArrayList<Integer>(_status.keySet());
    Collections.sort(deedKeys);
    List<AchievableStatus> ret=new ArrayList<AchievableStatus>();
    for(Integer deedKey : deedKeys)
    {
      ret.add(_status.get(deedKey));
    }
    return ret;
  }
}
