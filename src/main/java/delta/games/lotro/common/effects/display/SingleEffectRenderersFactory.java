package delta.games.lotro.common.effects.display;

import delta.games.lotro.common.effects.DispelByResistEffect;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.effects.GenesisEffect;
import delta.games.lotro.common.effects.InduceCombatStateEffect;
import delta.games.lotro.common.effects.InstantFellowshipEffect;
import delta.games.lotro.common.effects.InstantVitalEffect;
import delta.games.lotro.common.effects.ProcEffect;
import delta.games.lotro.common.effects.PropertyModificationEffect;
import delta.games.lotro.common.effects.ReactiveVitalEffect;
import delta.games.lotro.common.effects.VitalOverTimeEffect;

/**
 * Factory for effect renderers.
 * @author DAM
 */
public class SingleEffectRenderersFactory
{
  /**
   * Build a renderer for the given effect.
   * @param effect Effect to use.
   * @return A renderer.
   */
  public SingleEffectRenderer<? extends Effect> buildRenderer(Effect effect)
  {
    if (effect instanceof InstantVitalEffect)
    {
      return new InstantVitalEffectRenderer();
    }
    else if (effect instanceof ProcEffect)
    {
      return new ProcEffectRenderer();
    }
    else if (effect instanceof ReactiveVitalEffect)
    {
      return new ReactiveVitalEffectRenderer();
    }
    else if (effect instanceof PropertyModificationEffect)
    {
      return new PropertyModificationEffectRenderer<PropertyModificationEffect>();
    }
    else if (effect instanceof InstantFellowshipEffect)
    {
      return new InstantFellowshipEffectRenderer();
    }
    else if (effect instanceof GenesisEffect)
    {
      return new GenesisEffectRenderer();
    }
    else if (effect instanceof VitalOverTimeEffect)
    {
      return new VitalOverTimeEffectRenderer();
    }
    else if (effect instanceof InduceCombatStateEffect)
    {
      return new InduceCombatStateEffectRenderer();
    }
    else if (effect instanceof DispelByResistEffect)
    {
      return new DisplayByResistEffectRenderer();
    }
    return new DefaultEffectRenderer();
  }
}
