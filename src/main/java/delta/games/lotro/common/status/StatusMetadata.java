package delta.games.lotro.common.status;

/**
 * Metadata for a status object.
 * @author DAM
 */
public class StatusMetadata
{
  private long _timestamp;

  /**
   * Constructor.
   */
  public StatusMetadata()
  {
    // Nothing!
  }

  /**
   * Get the validity timestamp.
   * @return A timestamp or 0 if not defined.
   */
  public long getTimeStamp()
  {
    return _timestamp;
  }

  /**
   * Set the validity timestamp.
   * @param timestamp Validity timestamp to set.
   */
  public void setTimeStamp(long timestamp)
  {
    _timestamp=timestamp; 
  }

  /**
   * Indicates if this object contains interesting data or not.
   * @return <code>true</code> if it does not, <code>false</code> if it does.
   */
  public boolean isEmpty()
  {
    return _timestamp==0;
  }
}
