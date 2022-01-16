package delta.games.lotro.lore.xrefs.skills;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Skill reference.
 * @author DAM
 * @param <T> 
 */
public class SkillReference<T>
{
  private T _source;
  private HashSet<SkillRole> _roles;

  /**
   * Constructor.
   * @param source Source.
   * @param role Role.
   */
  public SkillReference(T source, SkillRole role)
  {
    _source=source;
    _roles=new HashSet<SkillRole>();
    _roles.add(role);
  }

  /**
   * Constructor.
   * @param source Source.
   * @param roles Roles.
   */
  public SkillReference(T source, Set<SkillRole> roles)
  {
    _source=source;
    _roles=new HashSet<SkillRole>(roles);
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
  public List<SkillRole> getRoles()
  {
    List<SkillRole> ret=new ArrayList<SkillRole>(_roles);
    return ret;
  }
}
