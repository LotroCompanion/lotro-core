package delta.games.lotro.common.effects.display;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.common.utils.l10n.L10n;
import delta.games.lotro.common.effects.BubbleEffect;
import delta.games.lotro.common.enums.VitalType;
import delta.games.lotro.common.enums.VitalTypes;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatValueProvider;
import delta.games.lotro.common.stats.WellKnownStat;
import delta.games.lotro.utils.maths.Progression;

/**
 * Renderer for 'BubbleEffect' effects.
 * @author DAM
 */
public class BubbleEffectRenderer extends PropertyModificationEffectRenderer<BubbleEffect>
{
  private static final Logger LOGGER=LoggerFactory.getLogger(BubbleEffectRenderer.class);

  private float getStatValue(StatDescription stat)
  {
    float ret=0;
    StatValueProvider statProvider=getContext().getCharacter();
    if (statProvider!=null)
    {
      // TODO Only use body+vitality contrib!! (no item/virtue contrib!)
      ret=statProvider.getStat(stat);
    }
    return ret;
  }

  @Override
  protected void renderSpecifics(List<String> storage, BubbleEffect effect)
  {
    Float value=null;
    // Stat
    StatDescription stat=effect.getVital();
    float statValue=getStatValue(stat);
    // Percentage?
    Float percentage=effect.getPercentage();
    if (percentage!=null)
    {
      value=Float.valueOf(statValue*percentage.floatValue());
    }
    // Constant?
    Float constant=effect.getValue();
    if (constant!=null)
    {
      value=constant;
    }
    // Progression?
    Progression progression=effect.getProgression();
    if (progression!=null)
    {
      int x=getLevel(); // or spellcraft?
      value=progression.getValue(x);
    }
    if (value!=null)
    {
      int bubbleValue=Math.round(value.floatValue());
      String vitalName=getVital(stat).getLabel();
      String msg="Applies a damage preventing bubble granting "+L10n.getString(bubbleValue)+" temporary "+vitalName+".";
      storage.add(msg);
    }
  }

  private VitalType getVital(StatDescription stat)
  {
    if (stat==WellKnownStat.MORALE) return VitalTypes.MORALE;
    if (stat==WellKnownStat.POWER) return VitalTypes.POWER;
    LOGGER.warn("Unsupported vital type: "+stat);
    return VitalTypes.MORALE;
  }
}
