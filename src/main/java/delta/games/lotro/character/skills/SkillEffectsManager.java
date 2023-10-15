package delta.games.lotro.character.skills;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores the effects for a skill.
 * @author DAM
 */
public class SkillEffectsManager
{
  private List<SkillEffectGenerator> _effects;

  /**
   * Constructor.
   */
  public SkillEffectsManager()
  {
    // Nothing!
  }

  /**
   * Add an effect generator.
   * @param generator Effect generator to add.
   */
  public void addEffect(SkillEffectGenerator generator)
  {
    if (_effects==null)
    {
      _effects=new ArrayList<SkillEffectGenerator>();
    }
    _effects.add(generator);
  }

  /**
   * Indicates if this manager has effects.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean hasEffects()
  {
    return (_effects!=null);
  }

  /**
   * Get the managed effects.
   * @return A array of effect generators, possibly empty but never <code>null</code>.
   */
  public SkillEffectGenerator[] getEffects()
  {
    if (_effects==null)
    {
      return new SkillEffectGenerator[0];
    }
    SkillEffectGenerator[] ret=new SkillEffectGenerator[_effects.size()];
    return _effects.toArray(ret);
  }
}
