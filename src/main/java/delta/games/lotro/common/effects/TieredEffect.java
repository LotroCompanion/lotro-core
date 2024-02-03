package delta.games.lotro.common.effects;

import java.util.ArrayList;
import java.util.List;

/**
 * Tiered effect.
 * @author DAM
 */
public class TieredEffect extends Effect2
{
  private List<EffectGenerator> _tiers;
  private EffectGenerator _finalTier;
  // Effect_TierUp_ShowInExamination: 1

  /**
   * Constructor.
   */
  public TieredEffect()
  {
    _tiers=new ArrayList<EffectGenerator>();
  }

  /**
   * Get the managed tiers.
   * @return A list of effect generators.
   */
  public List<EffectGenerator> getTiers()
  {
    return _tiers;
  }

  /**
   * Add a generator.
   * @param generator Generator to add.
   */
  public void addTierEffect(EffectGenerator generator)
  {
    _tiers.add(generator);
  }

  /**
   * Get the effect generator for the final tier.
   * @return An effect generator.
   */
  public EffectGenerator getFinalTier()
  {
    return _finalTier;
  }

  /**
   * Set the final tier effect.
   * @param generator Effect generator to set.
   */
  public void setFinalTier(EffectGenerator generator)
  {
    _finalTier=generator;
  }
}
