package delta.games.lotro.common.effects.display;

/**
 * State for effect rendering.
 * @author DAM
 */
public class EffectRenderingState
{
  private boolean _skipRawStats;
  private boolean _isRootEffect;
  private boolean _durationDisplayed;

  /**
   * Constructor.
   */
  public EffectRenderingState()
  {
    _durationDisplayed=false;
    _isRootEffect=true;
  }

  /**
   * Build a rendering state for a child effect.
   * @return A new rendering state.
   */
  public EffectRenderingState buildChildState()
  {
    EffectRenderingState ret=new EffectRenderingState();
    ret._skipRawStats=_skipRawStats;
    ret._isRootEffect=false;
    ret._durationDisplayed=_durationDisplayed;
    return ret;
  }

  /**
   * Set the "skip raw stats" flag.
   * @param skipRawStats Value to set.
   */
  public void skipRawStats(boolean skipRawStats)
  {
    _skipRawStats=skipRawStats;
  }

  /**
   * Indicates if stats for property modification effects shall be hidden or not.
   * @return <code>true</code> to hide, <code>false</code> to show.
   */
  public boolean hidePropertyModificationStats()
  {
    return ((_skipRawStats) && (_isRootEffect));
  }

  /**
   * Indicates if duration has been displayed or not.
   * @return <code>true</code> if it has been displayed, <code>false</code> otherwise.
   */
  public boolean isDurationDisplayed()
  {
    return _durationDisplayed;
  }

  /**
   * Mark the duration as displayed.
   */
  public void setDurationDisplayed()
  {
    _durationDisplayed=true;
  }
}
