package delta.games.lotro.common.effects;

import java.util.List;

/**
 * @author dm
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
}
