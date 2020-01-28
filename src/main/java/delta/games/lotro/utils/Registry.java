package delta.games.lotro.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.IdentifiableComparator;

/**
 * Generic registry for identifiables.
 * @param <T> Type of registered objects
 * @author DAM
 */
public class Registry<T extends Identifiable>
{
  private Map<Integer,T> _items;

  /**
   * Constructor.
   */
  public Registry()
  {
    _items=new HashMap<Integer,T>();
  }

  /**
   * Add a item.
   * @param item Item to add.
   */
  public void add(T item)
  {
    int id=item.getIdentifier();
    _items.put(Integer.valueOf(id),item);
  }

  /**
   * Get an item using its identifier.
   * @param id Identifier to use.
   * @return An item or <code>null</code> if not found.
   */
  public T getItem(int id)
  {
    return _items.get(Integer.valueOf(id));
  }

  /**
   * Get all items.
   * @return a list of items, ordered by identifier.
   */
  public List<T> getItems()
  {
    List<T> ret=new ArrayList<T>();
    ret.addAll(_items.values());
    Collections.sort(ret,new IdentifiableComparator<T>());
    return ret;
  }

  /**
   * Get the size of this registry.
   * @return an items count.
   */
  public int size()
  {
    return _items.size();
  }
}
