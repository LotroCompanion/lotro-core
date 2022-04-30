package delta.games.lotro.lore.xrefs.titles;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Title reference.
 * @author DAM
 * @param <T> 
 */
public class TitleReference<T>
{
  private T _source;
  private HashSet<TitleRole> _roles;

  /**
   * Constructor.
   * @param source Source.
   * @param role Role.
   */
  public TitleReference(T source, TitleRole role)
  {
    _source=source;
    _roles=new HashSet<TitleRole>();
    _roles.add(role);
  }

  /**
   * Constructor.
   * @param source Source.
   * @param roles Roles.
   */
  public TitleReference(T source, Set<TitleRole> roles)
  {
    _source=source;
    _roles=new HashSet<TitleRole>(roles);
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
   * Get the roles of the title for the source.
   * @return a list of roles.
   */
  public List<TitleRole> getRoles()
  {
    List<TitleRole> ret=new ArrayList<TitleRole>(_roles);
    return ret;
  }
}
