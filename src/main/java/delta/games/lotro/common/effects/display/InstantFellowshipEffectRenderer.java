package delta.games.lotro.common.effects.display;

import java.util.List;

import delta.common.utils.l10n.L10n;
import delta.games.lotro.common.effects.EffectGenerator;
import delta.games.lotro.common.effects.InstantFellowshipEffect;

/**
 * Renderer for 'InstantFellowshipEffect' effects.
 * @author DAM
 */
public class InstantFellowshipEffectRenderer extends AbstractSingleEffectRenderer<InstantFellowshipEffect>
{
  @Override
  public void render(List<String> storage, InstantFellowshipEffect effect)
  {
    Float range=effect.getRange();
    String override=effect.getFellowshipStringOverride();
    String target=(override!=null)?override:"the Fellowship";
    String line="Effects applied to "+target;
    if (range!=null)
    {
      line=line+" within "+L10n.getString(range.doubleValue(),0)+" metres";
    }
    line=line+":";
    storage.add(line);
    // Child effects
    List<EffectGenerator> effects=effect.getEffects();
    showEffectGenerators(storage,effects);
  }
}
