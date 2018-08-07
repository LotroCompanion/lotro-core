package delta.games.lotro.character.storage.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.character.storage.StoredItem;
import delta.games.lotro.character.storage.location.StorageLocation;

/**
 * Filter on the location of stored items.
 * @author DAM
 */
public class StoredItemLocationFilter implements Filter<StoredItem>
{
  private StorageLocation _location;

  /**
   * Constructor.
   * @param location Location to select (may be <code>null</code>).
   */
  public StoredItemLocationFilter(StorageLocation location)
  {
    _location=location;
  }

  /**
   * Get the location to use.
   * @return A location or <code>null</code>.
   */
  public StorageLocation getLocation()
  {
    return _location;
  }

  /**
   * Set the location to select.
   * @param location Location to use, may be <code>null</code>.
   */
  public void setLocation(StorageLocation location)
  {
    _location=location;
  }

  public boolean accept(StoredItem item)
  {
    if (_location==null)
    {
      return true;
    }
    return item.getLocation().equals(_location);
  }
}
