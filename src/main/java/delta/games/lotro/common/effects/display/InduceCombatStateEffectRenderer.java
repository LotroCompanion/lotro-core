package delta.games.lotro.common.effects.display;

import java.util.List;

import delta.games.lotro.common.effects.InduceCombatStateEffect;
import delta.games.lotro.common.enums.CombatState;
import delta.games.lotro.common.math.LinearFunction;

/**
 * Renderer for 'InduceCombatStateEffect' effects.
 * @author DAM
 */
public class InduceCombatStateEffectRenderer extends AbstractSingleEffectRenderer implements SingleEffectRenderer<InduceCombatStateEffect>
{
  public void render(List<String> storage, InduceCombatStateEffect effect)
  {
    float duration=effect.getDuration();
    LinearFunction durationFunction=effect.getDurationFunction();
    if (durationFunction!=null)
    {
      Float computedDuration=durationFunction.getValue(getLevel());
      if (computedDuration!=null)
      {
        duration=computedDuration.floatValue();
      }
    }
    CombatState state=effect.getCombatState();
    String stateStr="?";
    if (state!=null)
    {
      stateStr=EffectDisplayUtils.getStateLabel(state);
    }
    String text=duration+"s "+stateStr;
    storage.add(text);
  }
}
