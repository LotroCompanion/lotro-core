package delta.games.lotro.character.status.collections.birds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.lore.collections.birds.BirdDescription;
import delta.games.lotro.lore.collections.birds.BirdsManager;

/**
 * Storage for known birds status data for a single character.
 * @author DAM
 */
public class BirdsStatusManager
{
  private Map<Integer,BirdStatus> _status;

  /**
   * Constructor.
   */
  public BirdsStatusManager()
  {
    _status=new HashMap<Integer,BirdStatus>();
  }

  /**
   * Set the given bird as known.
   * @param birdID Bird identifier.
   */
  public void setKnown(int birdID)
  {
    BirdDescription bird=BirdsManager.getInstance().getBird(birdID);
    if (bird!=null)
    {
      BirdStatus status=get(bird,true);
      status.setKnown(true);
    }
  }

  /**
   * Get the status of a bird.
   * @param bird Targeted bird.
   * @param createIfNecessary Indicates if the status shall be created if it
   * does not exist.
   * @return A bird status or <code>null</code>.
   */
  public BirdStatus get(BirdDescription bird, boolean createIfNecessary)
  {
    Integer key=Integer.valueOf(bird.getIdentifier());
    BirdStatus ret=_status.get(key);
    if ((ret==null) && (createIfNecessary))
    {
      ret=new BirdStatus(bird);
      _status.put(key,ret);
    }
    return ret;
  }

  /**
   * Get the status of a bird.
   * @param birdID Bird identifier.
   * @return <code>true</code> if it is known, <code>false</code> otherwise.
   */
  public boolean isKnown(int birdID)
  {
    Integer key=Integer.valueOf(birdID);
    BirdStatus status=_status.get(key);
    return ((status!=null) && (status.isKnown()));
  }

  /**
   * Get all managed statuses.
   * @return A list of statuses, ordered by bird ID.
   */
  public List<BirdStatus> getAll()
  {
    List<Integer> ids=new ArrayList<Integer>(_status.keySet());
    Collections.sort(ids);
    List<BirdStatus> ret=new ArrayList<BirdStatus>();
    for(Integer id : ids)
    {
      ret.add(_status.get(id));
    }
    return ret;
  }
}
