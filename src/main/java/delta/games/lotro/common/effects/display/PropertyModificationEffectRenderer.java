package delta.games.lotro.common.effects.display;

import java.util.List;

import delta.games.lotro.common.effects.PropertyModificationEffect;
import delta.games.lotro.common.stats.StatUtils;
import delta.games.lotro.common.stats.StatsProvider;

/**
 * Renderer for 'property modification' effects.
 * @author DAM
 * @param <T> Actual type of effect.
 */
public class PropertyModificationEffectRenderer<T extends PropertyModificationEffect> extends AbstractSingleEffectRenderer implements SingleEffectRenderer<T>
{
  public void render(List<String> storage, T effect)
  {
    EffectRenderingState state=getState();
    if (state.hidePropertyModificationStats())
    {
      return;
    }
    StatsProvider provider=effect.getStatsProvider();
    if (provider==null)
    {
      return;
    }
    int level=getLevel();
    List<String> lines=StatUtils.getFullStatsForDisplay(provider,level);
    storage.addAll(lines);
  }
}
