package delta.games.lotro.common.effects.display;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import delta.games.lotro.common.Duration;
import delta.games.lotro.common.effects.EffectGenerator;
import delta.games.lotro.common.effects.GenesisEffect;
import delta.games.lotro.common.effects.Hotspot;

/**
 * Renderer for 'GenesisEffect' effects.
 * @author DAM
 */
public class GenesisEffectRenderer extends AbstractSingleEffectRenderer<GenesisEffect>
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
      List<EffectGenerator> effects=new ArrayList<EffectGenerator>(hotspot.getEffects());
      Collections.reverse(effects);
      showEffectGenerators(storage,effects);
    }
  }
}
