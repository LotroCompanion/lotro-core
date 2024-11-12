package delta.games.lotro.common.effects.display;

import java.util.List;

import delta.games.lotro.common.effects.InstantVitalEffect;
import delta.games.lotro.common.effects.VitalChangeDescription;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.lore.items.DamageType;

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
    StatDescription stat=effect.getStat();
    boolean multiplicative=effect.isMultiplicative();
    if (!multiplicative)
    {
      VitalEffectsUtils utils=new VitalEffectsUtils(getContext());
      float[] minMax=utils.getMinMaxValue(stat,effect,description,true);
      // Handle optional multiplier
      Float multiplier=effect.getInitialChangeMultiplier();
      if (multiplier!=null)
      {
        minMax[0]*=(1+multiplier.floatValue());
        minMax[1]*=(1+multiplier.floatValue());
      }
      int initialMinInt=Math.round(minMax[0]);
      int initialMaxInt=Math.round(minMax[1]);
      DamageType damageType=effect.getDamageType();
      String line=VitalChangeUtils.buildFullChange(false,initialMinInt,initialMaxInt,stat,damageType);
      storage.add(line);
    }
    else
    {
      handleMultiplicativeChange(storage,description,stat);
    }
  }

  private void handleMultiplicativeChange(List<String> storage, VitalChangeDescription description, StatDescription stat)
  {
    VitalEffectsUtils utils=new VitalEffectsUtils(getContext());
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
