package delta.games.lotro.common.effects;

import delta.games.lotro.common.properties.ModPropertyList;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.utils.maths.Progression;

/**
 * Bubble effect.
 * @author DAM
 */
public class BubbleEffect extends CountDownEffect
{
  private StatDescription _vital;
  private Float _value;
  private Float _percentage;
  private Progression _progression;
  private ModPropertyList _modifiers;

  /**
   * Constructor.
   */
  public BubbleEffect()
  {
    super();
  }

  /**
   * Get the vital to use.
   * @return a stat.
   */
  public StatDescription getVital()
  {
    return _vital;
  }

  /**
   * Set the vital to use.
   * @param vital the vital to set.
   */
  public void setVital(StatDescription vital)
  {
    _vital=vital;
  }

  /**
   * Get the bubble value.
   * @return A value or <code>null</code>.
   */
  public Float getValue()
  {
    return _value;
  }

  /**
   * Set the bubble value.
   * @param value the value to set (may be <code>null</code>).
   */
  public void setValue(Float value)
  {
    _value=value;
  }

  /**
   * Get the bubble percentage.
   * @return A percentage or <code>null</code>.
   */
  public Float getPercentage()
  {
    return _percentage;
  }

  /**
   * Set the bubble percentage.
   * @param percentage the value to set (may be <code>null</code>).
   */
  public void setPercentage(Float percentage)
  {
    _percentage=percentage;
  }

  /**
   * Get the progression to compute the bubble size.
   * @return A progression (may be <code>null</code>).
   */
  public Progression getProgression()
  {
    return _progression;
  }

  /**
   * Set the progression to use.
   * @param progression the progression to set (may be <code>null</code>).
   */
  public void setProgression(Progression progression)
  {
    _progression=progression;
  }

  /**
   * Get the modifiers for the bubble size.
   * @return some modifiers or <code>null</code>.
   */
  public ModPropertyList getModifiers()
  {
    return _modifiers;
  }

  /**
   * Set the modifiers to use.
   * @param modifiers the modifiers to set (may be <code>null</code>).
   */
  public void setModifiers(ModPropertyList modifiers)
  {
    _modifiers=modifiers;
  }
}
