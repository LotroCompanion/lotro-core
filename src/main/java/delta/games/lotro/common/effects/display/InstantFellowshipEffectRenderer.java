package delta.games.lotro.common.effects.display;

import java.util.List;

import delta.games.lotro.common.effects.EffectGenerator;
import delta.games.lotro.common.effects.InstantFellowshipEffect;

/**
 * Renderer for 'InstantFellowshipEffect' effects.
 * @author DAM
 */
public class InstantFellowshipEffectRenderer extends AbstractSingleEffectRenderer implements SingleEffectRenderer<InstantFellowshipEffect>
{
  public void render(List<String> storage, InstantFellowshipEffect effect)
  {
    Float range=effect.getRange();
    List<EffectGenerator> effects=effect.getEffects();
    //boolean appliesToTarget=effect.appliesToTarget();
    int rangeInt=(int)range.floatValue();
    String text="Effects applied to the Fellowship within "+rangeInt+" metres:";
    storage.add(text);
    showEffectGenerators(storage,effects);
  }
}
