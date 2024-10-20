package delta.games.lotro.common.effects.display;

import java.util.List;

import delta.games.lotro.common.effects.EffectDuration;
import delta.games.lotro.common.effects.VitalChangeDescription;
import delta.games.lotro.common.effects.VitalOverTimeEffect;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.lore.items.DamageType;

/**
 * Renderer for 'VitalOverTimeEffect' effects.
 * @author DAM
 */
public class VitalOverTimeEffectRenderer extends AbstractSingleEffectRenderer implements SingleEffectRenderer<VitalOverTimeEffect>
{
  public void render(List<String> storage, VitalOverTimeEffect effect)
  {
    VitalChangeDescription initialChange=effect.getInitialChangeDescription();
    VitalChangeDescription overTimeChange=effect.getOverTimeChangeDescription();
    StatDescription stat=effect.getStat();
    EffectDuration duration=effect.getEffectDuration();
    int pulseCount=duration.getPulseCount();
    Float interval=duration.getDuration();
    float totalDuration=interval.floatValue()*pulseCount;
    String overtime=" every "+interval+" seconds for "+(int)totalDuration+" seconds.";

    VitalChangeUtils utils=new VitalChangeUtils(getContext());
    DamageType damageType=effect.getDamageType();
    if (damageType!=null)
    {
      // Damage computation is wrong now (values do depend on character stats=>tactical mastery % ?)
      if (initialChange!=null)
      {
        Float initialValue=utils.getValue(initialChange);
        String text=initialValue+" "+damageType.getLabel()+" Damage initially.";
        storage.add(text);
      }
      if (overTimeChange!=null)
      {
        Float overTimeValue=utils.getValue(overTimeChange);
        String text=overTimeValue+" "+damageType.getLabel()+" Damage"+overtime;
        storage.add(text);
      }
    }
    else
    {
      // Heal values do not seem to be changed by Incoming Healing or Outgoing Healing!
      // TODO Use "Heals" if Morale, "Restores" if Power!
      // TODO Handle variance
      if (initialChange!=null)
      {
        Float initialValue=utils.getValue(initialChange);
        String text="Restores "+initialValue+" "+stat.getName()+" initially.";
        storage.add(text);
      }
      if (overTimeChange!=null)
      {
        Float overTimeValue=utils.getValue(overTimeChange);
        String text="Restores "+overTimeValue+" "+stat.getName()+overtime;
        storage.add(text);
      }
    }
    getState().setDurationDisplayed();
  }
}
