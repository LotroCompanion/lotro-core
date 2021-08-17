package delta.games.lotro.character.status.skirmishes.cfg;

/**
 * Policy to use to handle skirmish entries.
 * @author DAM
 */
public class SkirmishEntriesPolicy
{
  private boolean _mergeSizes;
  private boolean _mergeLevels;

  /**
   * Constructor.
   * @param mergeSizes Merge sizes or not.
   * @param mergeLevels Merge levels or not.
   */
  public SkirmishEntriesPolicy(boolean mergeSizes, boolean mergeLevels)
  {
    _mergeSizes=mergeSizes;
    _mergeLevels=mergeLevels;
  }

  /**
   * Indicates if sizes shall be merged or not.
   * @return <code>true</code> to perform merge, <code>false</code> otherwise.
   */
  public boolean isMergeSizes()
  {
    return _mergeSizes;
  }

  /**
   * Set the 'merge sizes' flag.
   * @param mergeSizes Value to set.
   */
  public void setMergeSizes(boolean mergeSizes)
  {
    _mergeSizes=mergeSizes;
  }

  /**
   * Indicates if levels shall be merged or not.
   * @return <code>true</code> to perform merge, <code>false</code> otherwise.
   */
  public boolean isMergeLevels()
  {
    return _mergeLevels;
  }

  /**
   * Set the 'merge levels' flag.
   * @param mergeLevels Value to set.
   */
  public void setMergeLevels(boolean mergeLevels)
  {
    _mergeLevels=mergeLevels;
  }
}
