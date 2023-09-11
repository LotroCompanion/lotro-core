package delta.games.lotro.common.effects;

import java.util.List;

import delta.games.lotro.lore.items.DamageType;

/**
 * @author dm
 */
public class OnDamageProcEffect implements EffectAspect
{
  // From:  Effect_InterestedIncomingDamageTypes: 
  //        #1: Effect_DamageType 65407 (ALL)
  private List<DamageType> _damageType;
  // From: Effect_ReactiveVital_DefenderEffect_Probability: 0.15
  private float _probability;
  // From:
  //Effect_ReactiveVital_DefenderEffect_Effect: 1879141127
  //Effect_ReactiveVital_DefenderEffect_SpellcraftOverride: -1.0
  private EffectGenerator _generatedEffects;
  /*
  Effect_VitalInterested_VitalTypeList: 
    #1: Effect_VitalInterested_VitalType 1 (Morale)
    */
}
