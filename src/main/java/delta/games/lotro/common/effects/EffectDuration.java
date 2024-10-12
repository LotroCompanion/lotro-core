package delta.games.lotro.common.effects;

import delta.games.lotro.common.properties.ModPropertyList;

/**
 * Description of effect duration.
 * @author DAM
 */
public class EffectDuration
{
  // Total duration, or interval duration if pulses (seconds)
  private Float _duration;
  // Pulses count
  private int _pulseCount;
  private ModPropertyList _pulseCountModifiers;

  /**
   * Constructor.
   */
  public EffectDuration()
  {
    _duration=null;
    _pulseCount=0;
  }

  /**
   * Get the total duration (or interval duration if pulses).
   * @return A duration (s) or <code>null</code>.
   */
  public Float getDuration()
  {
    return _duration;
  }

  /**
   * Set duration.
   * @param duration Duration in seconds.
   */
  public void setDuration(Float duration)
  {
    _duration=duration;
  }

  /**
   * Get the pulses count.
   * @return A pulses count.
   */
  public int getPulseCount()
  {
    return _pulseCount;
  }

  /**
   * Set the pulses count.
   * @param pulseCount Count to set.
   */
  public void setPulseCount(int pulseCount)
  {
    _pulseCount=pulseCount;
  }

  /**
   * Get the pulses count modifiers.
   * @return Some modifiers or <code>null</code>.
   */
  public ModPropertyList getPulseCountModifiers()
  {
    return _pulseCountModifiers;
  }

  /**
   * Set the pulses count modifiers.
   * @param pulseCountModifiers The modifiers to set (may be <code>null</code>).
   */
  public void setPulseCountModifiers(ModPropertyList pulseCountModifiers)
  {
    _pulseCountModifiers=pulseCountModifiers;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    if (_pulseCount>1)
    {
      float duration=(_duration!=null)?_duration.floatValue():0.0f;
      float totalDuration=duration*_pulseCount;
      sb.append(_pulseCount).append(" times, interval ");
      sb.append(_duration).append("s (total duration ").append(totalDuration).append("s)");
    }
    else if (_duration!=null)
    {
      sb.append(_duration).append('s');
    }
    return sb.toString();
  }
}
