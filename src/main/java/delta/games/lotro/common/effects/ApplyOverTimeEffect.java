package delta.games.lotro.common.effects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Effect that periodically applies some other effects.
 * @author DAM
 */
public class ApplyOverTimeEffect extends Effect implements ParentEffect
{
  /**
   * Initially applied effects.
   */
  private List<EffectGenerator> _initiallyAppliedEffects;
  /**
   * Applied effects.
   */
  private List<EffectGenerator> _appliedEffects;

  /**
   * Constructor.
   */
  public ApplyOverTimeEffect()
  {
    super();
    _initiallyAppliedEffects=new ArrayList<EffectGenerator>();
    _appliedEffects=new ArrayList<EffectGenerator>();
  }

  /**
   * Add an 'initially applied' effect.
   * @param generator Effect generator.
   */
  public void addInitiallyAppliedEffect(EffectGenerator generator)
  {
    _initiallyAppliedEffects.add(generator);
  }

  /**
   * Get the 'initially applied' effects.
   * @return A list of effect generators.
   */
  public List<EffectGenerator> getInitiallyAppliedEffects()
  {
    return _initiallyAppliedEffects;
  }

  /**
   * Add an 'applied' effect.
   * @param generator Effect generator.
   */
  public void addAppliedEffect(EffectGenerator generator)
  {
    _appliedEffects.add(generator);
  }

  /**
   * Get the 'applied' effects.
   * @return A list of effect generators.
   */
  public List<EffectGenerator> getAppliedEffects()
  {
    return _appliedEffects;
  }

  @Override
  public Set<Effect> getChildEffects()
  {
    HashSet<Effect> ret=new HashSet<Effect>();
    for(EffectGenerator generator : _initiallyAppliedEffects)
    {
      ret.add(generator.getEffect());
    }
    for(EffectGenerator generator : _appliedEffects)
    {
      ret.add(generator.getEffect());
    }
    return ret;
  }
}
