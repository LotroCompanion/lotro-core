package delta.games.lotro.lore.items.details;

/**
 * Usage cooldown for an item.
 * @author DAM
 */
public class ItemUsageCooldown extends ItemDetail
{
  private float _duration;
  private Integer _channelID;

  /**
   * Constructor.
   * @param duration Duration (seconds).
   * @param channelID Channel identifier (optional).
   */
  public ItemUsageCooldown(float duration, Integer channelID)
  {
    _duration=duration;
    _channelID=channelID;
  }

  /**
   * Get the cooldown duration.
   * @return the duration in seconds.
   */
  public float getDuration()
  {
    return _duration;
  }

  /**
   * Get the cooldown channel, if any.
   * @return A cooldown channel ID, or <code>null</code> if none.
   */
  public Integer getChannelID()
  {
    return _channelID;
  }
}
