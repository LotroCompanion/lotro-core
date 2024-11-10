package delta.games.lotro.common.effects;

import delta.games.lotro.common.enums.VitalType;
import delta.games.lotro.common.properties.ModPropertyList;

/**
 * Revive vital data.
 * @author DAM
 */
public class ReviveVitalData
{
  private VitalType _vital;
  private float _percentage;
  private ModPropertyList _modifiers;

  /**
   * Constructor.
   * @param vital Involved vital.
   * @param percentage Percentage of vital.
   */
  public ReviveVitalData(VitalType vital, float percentage)
  {
    _vital=vital;
    _percentage=percentage;
  }

  /**
   * Get the involved vital
   * @return the involved vital.
   */
  public VitalType getVital()
  {
    return _vital;
  }

  /**
   * Get the vital percentage.
   * @return A percentage (0 to 1).
   */
  public float getPercentage()
  {
    return _percentage;
  }

  /**
   * Get the modifiers for the vital percentage.
   * @return some modifiers or <code>null</code>.
   */
  public ModPropertyList getModifiers()
  {
    return _modifiers;
  }

  /**
   * Set the modifiers for the vital percentage.
   * @param modifiers the modifiers to set (may be <code>null</code>).
   */
  public void setModifiers(ModPropertyList modifiers)
  {
    _modifiers=modifiers;
  }
}
