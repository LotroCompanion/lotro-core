package delta.games.lotro.common.effects;

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
  // Expires in real world time, or in gaming time
  private boolean _expiresInRealTime;

  /**
   * Constructor.
   */
  public EffectDuration()
  {
    _duration=null;
    _pulseCount=0;
    _expiresInRealTime=false;
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
   * Indicates if the duration are evaluated in real time (or game time).
   * @return <code>true</code> for real time, <code>false</code> for game time.
   */
  public boolean expiresInRealTime()
  {
    return _expiresInRealTime;
  }

  /**
   * Set the 'expire in real time' flag.
   * @param expiresInRealTime Value to set.
   */
  public void setExpiresInRealTime(boolean expiresInRealTime)
  {
    _expiresInRealTime=expiresInRealTime;
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
    if (_expiresInRealTime)
    {
      sb.append(" (expires in real time)");
    }
    return sb.toString();
  }
}
