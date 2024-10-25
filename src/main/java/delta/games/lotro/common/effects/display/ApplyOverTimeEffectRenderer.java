package delta.games.lotro.common.effects.display;

import java.util.List;

import delta.common.utils.l10n.L10n;
import delta.games.lotro.common.effects.ApplyOverTimeEffect;
import delta.games.lotro.common.effects.EffectGenerator;
import delta.games.lotro.common.stats.StatModifiersComputer;

/**
 * Renderer for 'ApplyOverTimeEffect' effects.
 * @author DAM
 */
public class ApplyOverTimeEffectRenderer extends AbstractSingleEffectRenderer implements SingleEffectRenderer<ApplyOverTimeEffect>
{
  @Override
  public void render(List<String> storage, ApplyOverTimeEffect applyOverTimeEffect)
  {
    if (!applyOverTimeEffect.getAppliedEffects().isEmpty())
    {
      StatModifiersComputer statModsComputer=getContext().getStatModifiersComputer();
      float interval=EffectDisplayUtils.getDuration(applyOverTimeEffect,statModsComputer);
      String seconds=(interval>1.0f)?" seconds:":" second:";
      String line="Every "+L10n.getString(interval,1)+seconds;
      storage.add(line);
      for(EffectGenerator childGenerator : applyOverTimeEffect.getAppliedEffects())
      {
        displayEffect(storage,childGenerator.getEffect());
      }
    }
  }
}
