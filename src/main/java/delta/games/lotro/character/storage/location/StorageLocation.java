package delta.games.lotro.character.storage.location;

/**
 * Base class for storage locations.
 * @author DAM
 */
public abstract class StorageLocation
{
  /**
   * Get a displayable label for this location.
   * @return a displayable label.
   */
  public abstract String getLabel();

  @Override
  public String toString()
  {
    return getLabel();
  }
}
