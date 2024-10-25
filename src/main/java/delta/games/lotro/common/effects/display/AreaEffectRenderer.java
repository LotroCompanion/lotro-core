package delta.games.lotro.common.effects.display;

import java.util.List;

import delta.common.utils.l10n.L10n;
import delta.games.lotro.common.effects.AreaEffect;
import delta.games.lotro.common.effects.EffectGenerator;

/**
 * Renderer for 'AreaEffect' effects.
 * @author DAM
 */
public class AreaEffectRenderer extends AbstractSingleEffectRenderer implements SingleEffectRenderer<AreaEffect>
{
  @Override
  public void render(List<String> storage, AreaEffect areaEffect)
  {
    float range=areaEffect.getRange();
    if (!areaEffect.getEffects().isEmpty())
    {
      String line="Effects applied to enemies within "+L10n.getString(range,0)+" metres:";
      storage.add(line);
      for(EffectGenerator childGenerator : areaEffect.getEffects())
      {
        displayEffect(storage,childGenerator.getEffect());
      }
    }
  }
}
