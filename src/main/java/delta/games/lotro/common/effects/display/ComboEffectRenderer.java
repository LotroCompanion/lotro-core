package delta.games.lotro.common.effects.display;

import java.util.List;

import delta.games.lotro.common.effects.ComboEffect;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.utils.Proxy;

/**
 * Renderer for 'ComboEffect' effects.
 * @author DAM
 */
public class ComboEffectRenderer extends AbstractSingleEffectRenderer implements SingleEffectRenderer<ComboEffect>
{
  @Override
  public void render(List<String> storage, ComboEffect comboEffect)
  {
    Proxy<Effect> toExamine=comboEffect.getToExamine();
    if (toExamine!=null)
    {
      displayEffect(storage,toExamine.getObject());
    }
  }
}
