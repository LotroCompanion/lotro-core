package delta.games.lotro.lore.xrefs.items;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.games.lotro.common.Identifiable;

/**
 * Item reference.
 * @author DAM
 * @param <T> 
 */
public class ItemReference<T extends Identifiable>
{
  private T _source;
  private HashSet<ItemRole> _roles;

  /**
   * Constructor.
   * @param source Source.
   * @param role Role.
   */
  public ItemReference(T source, ItemRole role)
  {
    _source=source;
    _roles=new HashSet<ItemRole>();
    _roles.add(role);
  }

  /**
   * Constructor.
   * @param source Source.
   * @param roles Roles.
   */
  public ItemReference(T source, Set<ItemRole> roles)
  {
    _source=source;
    _roles=new HashSet<ItemRole>(roles);
  }

  /**
   * Get the source.
   * @return the source.
   */
  public T getSource()
  {
    return _source;
  }

  /**
   * Get the roles of the item for the source.
   * @return a list of roles.
   */
  public List<ItemRole> getRoles()
  {
    List<ItemRole> ret=new ArrayList<ItemRole>(_roles);
    return ret;
  }
}
