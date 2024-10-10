package delta.games.lotro.common.effects;

import java.util.ArrayList;
import java.util.List;

/**
 * Effect that triggers other effects on expiration and/or removal.
 * @author DAM
 */
public class CountDownEffect extends PropertyModificationEffect
{
  /**
   * 'On expire' effects.
   */
  private List<EffectGenerator> _onExpireEffects;
  private EffectGenerator _onRemoval; 

  /**
   * Constructor.
   */
  public CountDownEffect()
  {
    super();
    _onExpireEffects=new ArrayList<EffectGenerator>();
    _onRemoval=null;
  }

  /**
   * Add a 'on expire' effect.
   * @param generator Effect generator.
   */
  public void addOnExpireEffect(EffectGenerator generator)
  {
    _onExpireEffects.add(generator);
  }

  /**
   * Get the 'on expire' effects.
   * @return A list of effect generators.
   */
  public List<EffectGenerator> getOnExpireEffects()
  {
    return _onExpireEffects;
  }

  /**
   * Get the 'on removal' effect (if any).
   * @return An effect generator or <code>null</code>.
   */
  public EffectGenerator getOnRemovalEffect()
  {
    return _onRemoval;
  }

  /**
   * Set the 'on removal' effect.
   * @param generator Generator to set.
   */
  public void setOnRemovalEffect(EffectGenerator generator)
  {
    _onRemoval=generator;
  }
}
