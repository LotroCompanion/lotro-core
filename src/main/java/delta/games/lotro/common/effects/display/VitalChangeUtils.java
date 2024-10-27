package delta.games.lotro.common.effects.display;

import delta.common.utils.l10n.L10n;
import delta.games.lotro.common.effects.AbstractVitalChange;
import delta.games.lotro.common.effects.VitalChangeDescription;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.WellKnownStat;
import delta.games.lotro.lore.items.DamageType;
import delta.games.lotro.utils.maths.Progression;

/**
 * Utility methods related to vital changes.
 * @author DAM
 */
public class VitalChangeUtils
{
  private EffectRenderingContext _context;

  /**
   * Constructor.
   * @param context
   */
  public VitalChangeUtils(EffectRenderingContext context)
  {
    _context=context;
  }

  /**
   * Get the value for a vital change.
   * @param change Input vital change.
   * @return A value (may be <code>null</code>).
   */
  public Float getValue(AbstractVitalChange change)
  {
    Float value=change.getConstant();
    if (value!=null)
    {
      return value;
    }
    Progression progression=change.getProgression();
    if (progression!=null)
    {
      int level=_context.getLevel();
      value=progression.getValue(level);
    }
    return value;
  }

  /**
   * Get the min/max value for a vital change.
   * @param description Vital change description.
   * @return An array of min and max values.
   */
  public int[] getMinMaxValue(VitalChangeDescription description)
  {
    Float value=getValue(description);
    if (value!=null)
    {
      float maxValueFloat=value.floatValue();
      int maxValue=(int)maxValueFloat;
      Float variance=description.getVariance();
      if (variance!=null)
      {
        float minValueFloat=maxValueFloat*(1-variance.floatValue());
        int minValue=(int)minValueFloat;
        return new int[] {minValue, maxValue};
      }
      return new int[] {maxValue, maxValue};
    }
    Float minValueFloat=description.getMinValue();
    Float maxValueFloat=description.getMaxValue();
    if ((minValueFloat!=null) && (maxValueFloat!=null))
    {
      int minValue=(int)minValueFloat.floatValue();
      int maxValue=(int)maxValueFloat.floatValue();
      return new int[] {minValue, maxValue};
    }
    return new int[] {0,0};
  }

  /**
   * Build a full change line.
   * @param min Minimum value.
   * @param max Maximum value.
   * @param stat Involved stat.
   * @param damageType Damage type (may be <code>null</code>).
   * @return A displayable line.
   */
  public static String buildFullChange(int min, int max, StatDescription stat, DamageType damageType)
  {
    boolean negative=false;
    if ((min<0) || (max<0))
    {
      negative=true;
      min=Math.abs(min);
      max=Math.abs(max);
    }
    String changeStr=buildChangeStr(min,max);
    String fullChange="";
    if (stat==WellKnownStat.MORALE)
    {
      if (negative)
      {
        String damageTypeStr="";
        if (damageType!=null)
        {
          damageTypeStr=" "+damageType.getLabel();
        }
        fullChange=changeStr+damageTypeStr+" Damage";
      }
      else
      {
        fullChange="+"+changeStr+" Morale"; // TODO "+" or "Heals "!!
      }
    }
    else if (stat==WellKnownStat.POWER)
    {
      fullChange=(negative?"Drains ":"+")+changeStr+" Power"; // TODO "+" or "Restores "!!
    }
    return fullChange;
  }

  private static String buildChangeStr(int min, int max)
  {
    String ret="";
    if (min==max)
    {
      ret=L10n.getString(min);
    }
    else
    {
      ret=L10n.getString(min)+" - "+L10n.getString(max);
    }
    return ret;
  }
}
