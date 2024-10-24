package delta.games.lotro.common.effects;

import java.util.ArrayList;
import java.util.List;

/**
 * Effect that periodically applies some other effects.
 * @author DAM
 */
public class ApplyOverTimeEffect extends Effect
{
  /**
   * Initially applied effects.
   */
  private List<EffectGenerator> _initiallyAppliedEffects;
  /**
   * Applied effects.
   */
  private List<EffectGenerator> _appliedEffects;

  // Effect_Duration_ConstantInterval: 1.0

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
}
