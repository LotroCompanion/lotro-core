package delta.games.lotro.common.effects.display;

import java.util.List;

import delta.games.lotro.common.effects.EffectGenerator;
import delta.games.lotro.common.effects.TieredEffect;

/**
 * Renderer for 'TieredEffect' effects.
 * @author DAM
 */
public class TieredEffectRenderer extends AbstractSingleEffectRenderer implements SingleEffectRenderer<TieredEffect>
{
  @Override
  public void render(List<String> storage, TieredEffect tieredEffect)
  {
    EffectGenerator firstTier=tieredEffect.getTiers().get(0);
    displayEffect(storage,firstTier.getEffect());
  }
}
