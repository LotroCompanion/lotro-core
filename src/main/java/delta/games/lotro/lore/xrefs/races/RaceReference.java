package delta.games.lotro.lore.xrefs.races;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Race reference.
 * @author DAM
 * @param <T> 
 */
public class RaceReference<T>
{
  private T _source;
  private HashSet<RaceRole> _roles;

  /**
   * Constructor.
   * @param source Source.
   * @param role Role.
   */
  public RaceReference(T source, RaceRole role)
  {
    _source=source;
    _roles=new HashSet<RaceRole>();
    _roles.add(role);
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
  public List<RaceRole> getRoles()
  {
    List<RaceRole> ret=new ArrayList<RaceRole>(_roles);
    return ret;
  }
}
