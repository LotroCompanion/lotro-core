package delta.games.lotro.lore.items.details;

/**
 * Item decay.
 * @author DAM
 */
public class ItemDecay extends ItemDetail
{
  private float _duration;

  /**
   * Constructor.
   * @param duration Duration (seconds).
   */
  public ItemDecay(float duration)
  {
    _duration=duration;
  }

  /**
   * Get the decay duration.
   * @return a duration (seconds).
   */
  public float getDuration()
  {
    return _duration;
  }
}
