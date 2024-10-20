package delta.games.lotro.common.effects.display;

import java.util.List;

import delta.games.lotro.common.effects.Effect;

/**
 * Interface of effect renderers.
 * @author DAM
 * @param <T> Actual type of effect.
 */
public interface SingleEffectRenderer<T extends Effect>
{
  /**
   * Render the given effect.
   * @param storage Storage of output.
   * @param effect Effect to use.
   */
  void render(List<String> storage, T effect);
}
