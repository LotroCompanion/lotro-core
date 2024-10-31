package delta.games.lotro.common.effects.display;

import java.util.List;

import delta.common.utils.l10n.L10n;
import delta.games.lotro.common.Duration;
import delta.games.lotro.common.effects.InduceCombatStateEffect;
import delta.games.lotro.common.enums.CombatState;
import delta.games.lotro.common.math.LinearFunction;
import delta.games.lotro.common.stats.StatModifiersComputer;

/**
 * Renderer for 'InduceCombatStateEffect' effects.
 * @author DAM
 */
public class InduceCombatStateEffectRenderer extends AbstractSingleEffectRenderer implements SingleEffectRenderer<InduceCombatStateEffect>
{
  @Override
  public void render(List<String> storage, InduceCombatStateEffect effect)
  {
    // Duration
    float duration=computeDuration(effect);
    CombatState state=effect.getCombatState();
    String stateStr="?";
    if (state!=null)
    {
      stateStr=EffectDisplayUtils.getStateLabel(state);
    }
    String text=L10n.getString(duration,1)+"s "+stateStr;
    storage.add(text);
    // Break-out
    Float gracePeriod=effect.getGracePeriod();
    if (gracePeriod!=null)
    {
      float totalPeriod=gracePeriod.floatValue();
      StatModifiersComputer statModsComputer=getContext().getStatModifiersComputer();
      if (statModsComputer!=null)
      {
        totalPeriod+=statModsComputer.computeAdditiveModifiers(effect.getGracePeriodModifiers());
      }
      String gracePeriodText="100% break chance on damage after "+Duration.getShortDurationString(totalPeriod);
      storage.add(gracePeriodText);
    }
  }

  private float computeDuration(InduceCombatStateEffect effect)
  {
    float duration=effect.getDuration();
    LinearFunction durationFunction=effect.getDurationFunction();
    if (durationFunction!=null)
    {
      int level=getLevel();
      Float computedDuration=durationFunction.getValue(level);
      if (computedDuration!=null)
      {
        duration=computedDuration.floatValue();
      }
    }
    duration+=computeAdditiveModifiers(effect.getDurationModifiers());
    return duration;
  }
}
