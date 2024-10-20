package delta.games.lotro.common.effects.display;

import delta.games.lotro.common.effects.AbstractVitalChange;
import delta.games.lotro.common.effects.VitalChangeDescription;
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
}
