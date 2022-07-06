package delta.games.lotro.character.status.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages a set of collection statuses.
 * @author DAM
 */
public class CollectionsStatusManager
{
  private Map<Integer,CollectionStatus> _map;

  /**
   * Constructor.
   */
  public CollectionsStatusManager()
  {
    _map=new HashMap<Integer,CollectionStatus>();
  }

  /**
   * Add a status.
   * @param status Status to add.
   */
  public void addStatus(CollectionStatus status)
  {
    Integer key=Integer.valueOf(status.getCollection().getIdentifier());
    _map.put(key,status);
  }

  /**
   * Get the status for a collection.
   * @param collectionID Identifier of the collection to use.
   * @return A status or <code>null</code> if not managed.
   */
  public CollectionStatus get(int collectionID)
  {
    return _map.get(Integer.valueOf(collectionID));
  }

  /**
   * Get a all managed statuses.
   * @return A list of statuses, sorted by collection ID.
   */
  public List<CollectionStatus> getAll()
  {
    List<CollectionStatus> ret=new ArrayList<CollectionStatus>();
    List<Integer> ids=new ArrayList<Integer>(_map.keySet());
    Collections.sort(ids);
    for(Integer id : ids)
    {
      ret.add(_map.get(id));
    }
    return ret;
  }
}
