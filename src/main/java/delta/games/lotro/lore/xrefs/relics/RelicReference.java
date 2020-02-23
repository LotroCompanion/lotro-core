package delta.games.lotro.lore.xrefs.relics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.games.lotro.common.Identifiable;

/**
 * Relic reference.
 * @author DAM
 * @param <T> Type of referenced object.
 */
public class RelicReference<T extends Identifiable>
{
  private T _source;
  private HashSet<RelicRole> _roles;

  /**
   * Constructor.
   * @param source Source.
   * @param role Role.
   */
  public RelicReference(T source, RelicRole role)
  {
    _source=source;
    _roles=new HashSet<RelicRole>();
    _roles.add(role);
  }

  /**
   * Constructor.
   * @param source Source.
   * @param roles Roles.
   */
  public RelicReference(T source, Set<RelicRole> roles)
  {
    _source=source;
    _roles=new HashSet<RelicRole>(roles);
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
  public List<RelicRole> getRoles()
  {
    List<RelicRole> ret=new ArrayList<RelicRole>(_roles);
    return ret;
  }

  @Override
  public String toString()
  {
    return _roles.toString()+" "+_source;
  }
}
