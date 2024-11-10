package delta.games.lotro.common.effects.display;

import java.util.List;

import delta.common.utils.l10n.L10n;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.effects.ReviveEffect;
import delta.games.lotro.common.effects.ReviveVitalData;
import delta.games.lotro.common.enums.VitalType;
import delta.games.lotro.utils.Proxy;

/**
 * Renderer for 'ReviveEffect' effects.
 * @author DAM
 */
public class ReviveEffectRenderer extends AbstractSingleEffectRenderer implements SingleEffectRenderer<ReviveEffect>
{
  @Override
  public void render(List<String> storage, ReviveEffect reviveEffect)
  {
    for(ReviveVitalData vitalData : reviveEffect.getReviveVitalData())
    {
      float percentage=vitalData.getPercentage();
      float totalPercentage=(percentage*100)+computeAdditiveModifiers(vitalData.getModifiers());
      VitalType vital=vitalData.getVital();
      String text="Target revives with "+L10n.getString(Math.round(totalPercentage))+"% "+vital.getLabel();
      storage.add(text);
    }

    List<Proxy<Effect>> onRevivalEffects=reviveEffect.getReviveEffects();
    if (!onRevivalEffects.isEmpty())
    {
      storage.add("Effects to apply on revival:");
      for(Proxy<Effect> onRevivalEffect : onRevivalEffects)
      {
        displayEffect(storage,onRevivalEffect.getObject());
      }
    }
  }
}
