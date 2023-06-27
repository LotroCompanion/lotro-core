package delta.games.lotro.lore.xrefs;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Generic reference.
 * @author DAM
 * @param <T> Source type.
 * @param <E> Role type.
 */
public class Reference<T, E extends Enum<?>>
{
  private T _source;
  private TreeSet<E> _roles;

  /**
   * Constructor.
   * @param source Source.
   * @param role Role.
   */
  public Reference(T source, E role)
  {
    _source=source;
    _roles=new TreeSet<E>();
    _roles.add(role);
  }

  /**
   * Constructor.
   * @param source Source.
   * @param roles Roles.
   */
  public Reference(T source, Set<E> roles)
  {
    _source=source;
    _roles=new TreeSet<E>(roles);
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
   * Get the roles for the source.
   * @return a list of roles.
   */
  public List<E> getRoles()
  {
    List<E> ret=new ArrayList<E>(_roles);
    return ret;
  }
}
