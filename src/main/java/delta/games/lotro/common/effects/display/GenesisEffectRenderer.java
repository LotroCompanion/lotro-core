package delta.games.lotro.common.effects.display;

import java.util.List;

import delta.games.lotro.common.Duration;
import delta.games.lotro.common.effects.GenesisEffect;
import delta.games.lotro.common.effects.Hotspot;

/**
 * Renderer for 'GenesisEffect' effects.
 * @author DAM
 */
public class GenesisEffectRenderer extends AbstractSingleEffectRenderer implements SingleEffectRenderer<GenesisEffect>
{
  public void render(List<String> storage, GenesisEffect effect)
  {
    boolean permanent=effect.isPermanent();
    if (permanent)
    {
      storage.add("Permanent");
    }
    else
    {
      float duration=effect.getSummonDuration();
      String durationStr=Duration.getDurationString((int)duration);
      storage.add("Duration: "+durationStr);
    }
    getState().setDurationDisplayed();
    Hotspot hotspot=effect.getHotspot();
    if (hotspot!=null)
    {
      showEffectGenerators(storage,hotspot.getEffects());
    }
  }
}
