package delta.games.lotro.common.effects.display;

import java.util.List;

import delta.games.lotro.common.effects.VitalOverTimeEffect;

/**
 * Renderer for 'VitalOverTimeEffect' effects.
 * @author DAM
 */
public class VitalOverTimeEffectRenderer extends AbstractSingleEffectRenderer<VitalOverTimeEffect>
{
  @Override
  public void render(List<String> storage, VitalOverTimeEffect effect)
  {
    VitalEffectsUtils utils=new VitalEffectsUtils(getContext());
    utils.getVitalOverTimeEffectDisplay(effect,storage);
    getState().setDurationDisplayed();
  }
}
