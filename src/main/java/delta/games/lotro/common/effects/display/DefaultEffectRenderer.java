package delta.games.lotro.common.effects.display;

import java.util.List;

import delta.games.lotro.common.effects.Effect;

/**
 * Default renderer.
 * @author DAM
 */
public class DefaultEffectRenderer implements SingleEffectRenderer<Effect>
{
  @Override
  public void render(List<String> storage, Effect effect)
  {
    // Nothing!
  }
}
