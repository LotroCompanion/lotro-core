package delta.games.lotro.common.effects;

import java.util.ArrayList;
import java.util.List;

/**
 * Tiered effect.
 * @author DAM
 */
public class TieredEffect implements EffectAspect
{
  //(see DNT - Burning Embers Tierup)
  // From Effect_TierUp_EffectList:
  /*
    #1: EffectGenerator_EffectStruct 
      EffectGenerator_EffectID: 1879449300
      EffectGenerator_EffectSpellcraft: -1.0
    #2: EffectGenerator_EffectStruct 
      EffectGenerator_EffectID: 1879449302
      EffectGenerator_EffectSpellcraft: -1.0
    #3: EffectGenerator_EffectStruct 
      EffectGenerator_EffectID: 1879449297
      EffectGenerator_EffectSpellcraft: -1.0
    #4: EffectGenerator_EffectStruct 
      EffectGenerator_EffectID: 1879449290
      EffectGenerator_EffectSpellcraft: -1.0
    #5: EffectGenerator_EffectStruct 
      EffectGenerator_EffectID: 1879449315
      EffectGenerator_EffectSpellcraft: -1.0
  */
  private List<EffectGenerator> _tiers;
  // From Effect_TierUp_FinalEffect:
  /*
  #1: EffectGenerator_EffectStruct 
    EffectGenerator_EffectID: 1879449315
    EffectGenerator_EffectSpellcraft: -1.0
  */
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
