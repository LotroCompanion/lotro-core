package delta.games.lotro.character.storage.summary;

/**
 * Summary for a single storage.
 * @author DAM
 */
public class SingleStorageSummary
{
  private int _available;
  private int _max;

  /**
   * Constructor.
   */
  public SingleStorageSummary()
  {
    _available=0;
    _max=0;
  }

  /**
   * Get the available space.
   * @return a slots count.
   */
  public int getAvailable()
  {
    return _available;
  }

  /**
   * Set the available space.
   * @param available Available slot counts.
   */
  public void setAvailable(int available)
  {
    _available=available; 
  }

  /**
   * Get the maximum number of slots.
   * @return A slots count.
   */
  public int getMax()
  {
    return _max;
  }

  /**
   * Set the maximum number of slots.
   * @param max Slots count to set.
   */
  public void setMax(int max)
  {
    _max=max;
  }

  /**
   * Get the number of used slots.
   * @return A slots count.
   */
  public int getUsed()
  {
    return _max-_available;
  }

  @Override
  public String toString()
  {
    return "Available: "+_available+", max: "+_max;
  }
}
