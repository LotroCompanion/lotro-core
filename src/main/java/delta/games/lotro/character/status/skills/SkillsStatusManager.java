package delta.games.lotro.character.status.skills;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.character.skills.SkillDescription;

/**
 * Storage for all skill status data for a single character.
 * @author DAM
 */
public class SkillsStatusManager
{
  private Map<Integer,SkillStatus> _status;

  /**
   * Constructor.
   */
  public SkillsStatusManager()
  {
    _status=new HashMap<Integer,SkillStatus>();
  }

  /**
   * Clear data.
   */
  public void clear()
  {
    _status.clear();
  }

  /**
   * Get the status of a skill.
   * @param skill Targeted skill.
   * @param createIfNecessary Indicates if the status shall be created if it
   * does not exist.
   * @return A skill status or <code>null</code>.
   */
  public SkillStatus get(SkillDescription skill, boolean createIfNecessary)
  {
    Integer key=Integer.valueOf(skill.getIdentifier());
    SkillStatus ret=_status.get(key);
    if ((ret==null) && (createIfNecessary))
    {
      ret=new SkillStatus(skill);
      _status.put(key,ret);
    }
    return ret;
  }

  /**
   * Get all managed statuses.
   * @return A list of statuses, ordered by title ID.
   */
  public List<SkillStatus> getAll()
  {
    List<Integer> ids=new ArrayList<Integer>(_status.keySet());
    Collections.sort(ids);
    List<SkillStatus> ret=new ArrayList<SkillStatus>();
    for(Integer id : ids)
    {
      ret.add(_status.get(id));
    }
    return ret;
  }
}
