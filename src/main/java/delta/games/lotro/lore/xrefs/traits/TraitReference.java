package delta.games.lotro.lore.xrefs.traits;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Trait reference.
 * @author DAM
 * @param <T> 
 */
public class TraitReference<T>
{
  private T _source;
  private HashSet<TraitRole> _roles;

  /**
   * Constructor.
   * @param source Source.
   * @param role Role.
   */
  public TraitReference(T source, TraitRole role)
  {
    _source=source;
    _roles=new HashSet<TraitRole>();
    _roles.add(role);
  }

  /**
   * Constructor.
   * @param source Source.
   * @param roles Roles.
   */
  public TraitReference(T source, Set<TraitRole> roles)
  {
    _source=source;
    _roles=new HashSet<TraitRole>(roles);
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
  public List<TraitRole> getRoles()
  {
    List<TraitRole> ret=new ArrayList<TraitRole>(_roles);
    return ret;
  }
}
