package delta.games.lotro.common.effects.display;

import java.util.List;

import delta.games.lotro.common.effects.InstantVitalEffect;
import delta.games.lotro.common.effects.VitalChangeDescription;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.WellKnownStat;

/**
 * Renderer for 'InstantVitalEffect' effects.
 * @author DAM
 */
public class InstantVitalEffectRenderer extends AbstractSingleEffectRenderer implements SingleEffectRenderer<InstantVitalEffect>
{

  @Override
  public void render(List<String> storage, InstantVitalEffect effect)
  {
    VitalChangeDescription description=effect.getInstantChangeDescription();
    if (description==null)
    {
      return;
    }
    VitalChangeUtils utils=new VitalChangeUtils(getContext());
    StatDescription stat=effect.getStat();
    boolean isMorale=(stat==WellKnownStat.MORALE);
    boolean multiplicative=effect.isMultiplicative();
    if (!multiplicative)
    {
      int[] minMax=utils.getMinMaxValue(description);
      if (minMax[0]==minMax[1])
      {
        if ((minMax[0]<0) && isMorale)
        {
          String text=(-minMax[0])+" Damage";
          storage.add(text);
        }
        else
        {
          String text=(minMax[0])+" "+stat.getName();
          storage.add(text);
        }
      }
      else
      {
        if ((minMax[0]<0) && (minMax[1]<0) && isMorale)
        {
          String text=(-minMax[0])+" - "+(-minMax[1])+" Damage";
          storage.add(text);
        }
        else
        {
          String text=(minMax[0])+" - "+(minMax[1])+" "+stat.getName();
          storage.add(text);
        }
      }
    }
    else
    {
      Float value=utils.getValue(description);
      if (value!=null)
      {
        float maxPercentageFloat=value.floatValue()*100;
        int maxPercentage=(int)maxPercentageFloat;
        Float variance=description.getVariance();
        StringBuilder sb=new StringBuilder();
        sb.append("Restores ");
        if (variance!=null)
        {
          float minPercentageFloat=maxPercentageFloat*(1-variance.floatValue());
          int minPercentage=(int)minPercentageFloat;
          sb.append(minPercentage).append("% - ").append(maxPercentage).append('%');
        }
        else
        {
          sb.append(maxPercentage).append('%');
        }
        sb.append(" of maximum ").append(stat.getName());
        storage.add(sb.toString());
      }
    }
  }
}
