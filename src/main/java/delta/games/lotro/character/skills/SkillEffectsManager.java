package delta.games.lotro.character.skills;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Stores the effects for a skill.
 * @author DAM
 */
public class SkillEffectsManager
{
  private Map<SkillEffectType,SingleTypeSkillEffectsManager> _map;

  /**
   * Constructor.
   */
  public SkillEffectsManager()
  {
    _map=new EnumMap<SkillEffectType,SingleTypeSkillEffectsManager>(SkillEffectType.class);
  }

  /**
   * Get all the managed effects.
   * @return A possibly empty but never <code>null</code> list of effect generators.
   */
  public List<SkillEffectGenerator> getEffects()
  {
    List<SkillEffectGenerator> ret=new ArrayList<SkillEffectGenerator>();
    for(SingleTypeSkillEffectsManager mgr : getAll())
    {
      ret.addAll(mgr.getEffects());
    }
    return ret;
  }

  /**
   * Get all the effect managers.
   * @return A possibly empty but never <code>null</code> list of effect manages.
   */
  public List<SingleTypeSkillEffectsManager> getAll()
  {
    List<SingleTypeSkillEffectsManager> ret=new ArrayList<SingleTypeSkillEffectsManager>();
    for(SkillEffectType type : SkillEffectType.values())
    {
      SingleTypeSkillEffectsManager mgr=getEffects(type);
      if (mgr!=null)
      {
        ret.add(mgr);
      }
    }
    return ret;
  }

  /**
   * Set the effects managed for a type. 
   * @param type Type to use.
   * @param mgr Effects manager to set.
   */
  public void setEffects(SkillEffectType type, SingleTypeSkillEffectsManager mgr)
  {
    _map.put(type,mgr);
  }

  /**
   * Get the effects manager for the given effect type.
   * @param type Type to use.
   * @return An effects manager or <code>null</code>.
   */
  public SingleTypeSkillEffectsManager getEffects(SkillEffectType type)
  {
    return _map.get(type);
  }
}
