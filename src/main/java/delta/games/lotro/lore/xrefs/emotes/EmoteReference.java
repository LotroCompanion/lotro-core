package delta.games.lotro.lore.xrefs.emotes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Emote reference.
 * @author DAM
 * @param <T> 
 */
public class EmoteReference<T>
{
  private T _source;
  private HashSet<EmoteRole> _roles;

  /**
   * Constructor.
   * @param source Source.
   * @param role Role.
   */
  public EmoteReference(T source, EmoteRole role)
  {
    _source=source;
    _roles=new HashSet<EmoteRole>();
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
   * Get the roles of the emote for the source.
   * @return a list of roles.
   */
  public List<EmoteRole> getRoles()
  {
    List<EmoteRole> ret=new ArrayList<EmoteRole>(_roles);
    return ret;
  }
}
