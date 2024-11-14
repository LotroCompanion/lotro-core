package delta.games.lotro.common.effects.display;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.Duration;
import delta.games.lotro.common.effects.EffectFlags;
import delta.games.lotro.common.effects.PropertyModificationEffect;
import delta.games.lotro.common.stats.StatModifiersComputer;
import delta.games.lotro.common.stats.StatUtils;
import delta.games.lotro.common.stats.StatsProvider;

/**
 * Renderer for 'property modification' effects.
 * @author DAM
 * @param <T> Actual type of effect.
 */
public class PropertyModificationEffectRenderer<T extends PropertyModificationEffect> extends AbstractSingleEffectRenderer<T>
{
  @Override
  public void render(List<String> storage, T effect)
  {
    List<String> childStorage=new ArrayList<String>();
    renderSpecifics(childStorage,effect);
    if (!childStorage.isEmpty())
    {
      renderDuration(childStorage,effect);
    }
    renderAfterDuration(childStorage,effect);
    storage.addAll(childStorage);
  }

  protected void renderStats(List<String> storage, T effect)
  {
    EffectRenderingState state=getState();
    if (state.hidePropertyModificationStats())
    {
      return;
    }
    StatsProvider provider=effect.getStatsProvider();
    if (provider!=null)
    {
      EffectRenderingContext context=getContext(); 
      List<String> lines=StatUtils.getFullStatsForDisplay(provider,context);
      storage.addAll(lines);
    }
    boolean expiresOutOfCombat=effect.getBaseFlag(EffectFlags.DURATION_COMBAT_ONLY);
    if (expiresOutOfCombat)
    {
      // TODO Sometimes "Expires if out of combat for a short amount of time."
      storage.add("Expires if out of combat for 9 seconds.");
    }
  }

  protected void renderSpecifics(List<String> storage, T effect)
  {
    renderStats(storage,effect);
  }

  private void renderDuration(List<String> storage, T effect)
  {
    StatModifiersComputer statModsComputer=getContext().getStatModifiersComputer();
    float duration=EffectDisplayUtils.getDuration(effect,statModsComputer);
    if (duration>0)
    {
      String line="Duration: "+Duration.getShortDurationString(duration);
      storage.add(line);
      getState().setDurationDisplayed();
    }
  }

  protected void renderAfterDuration(List<String> storage, T effect)
  {
    // Nothing!
  }
}
