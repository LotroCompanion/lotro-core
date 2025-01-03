package delta.games.lotro.common.effects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Random effect.
 * @author DAM
 */
public class RandomEffect extends InstantEffect implements ParentEffect
{
  private List<RandomEffectGenerator> _effects;

  /**
   * Constructor.
   */
  public RandomEffect()
  {
    _effects=new ArrayList<RandomEffectGenerator>();
  }

  /**
   * Add an effect.
   * @param effect Effect to add.
   */
  public void addEffect(RandomEffectGenerator effect)
  {
    _effects.add(effect);
  }

  /**
   * Get the effects.
   * @return A list of effect (may be empty but never <code>null</code>).
   */
  public List<RandomEffectGenerator> getEffects()
  {
    return _effects;
  }

  @Override
  public Set<Effect> getChildEffects()
  {
    HashSet<Effect> ret=new HashSet<Effect>();
    for(EffectGenerator generator : _effects)
    {
      ret.add(generator.getEffect());
    }
    return ret;
  }
}
