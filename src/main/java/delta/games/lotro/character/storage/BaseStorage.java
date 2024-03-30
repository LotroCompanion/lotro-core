package delta.games.lotro.character.storage;

import delta.games.lotro.common.status.StatusMetadata;

/**
 * Base class for storage containers.
 * @author DAM
 */
public abstract class BaseStorage
{
  private StatusMetadata _statusMetadata;

  /**
   * Constructor.
   */
  protected BaseStorage()
  {
    _statusMetadata=new StatusMetadata();
  }

  /**
   * Get the number of slots used.
   * @return a slots count.
   */
  public abstract int getUsed();

  /**
   * Get the total number of slots.
   * @return a slots count.
   */
  public abstract int getCapacity();

  /**
   * Get the status metadata.
   * @return the status metadata.
   */
  public StatusMetadata getStatusMetadata()
  {
    return _statusMetadata;
  }
}
