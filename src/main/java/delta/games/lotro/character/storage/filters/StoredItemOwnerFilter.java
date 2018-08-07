package delta.games.lotro.character.storage.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.character.storage.StoredItem;
import delta.games.lotro.common.owner.Owner;

/**
 * Filter on the owner of stored items.
 * @author DAM
 */
public class StoredItemOwnerFilter implements Filter<StoredItem>
{
  private Owner _owner;

  /**
   * Constructor.
   * @param owner Owner to select (may be <code>null</code>).
   */
  public StoredItemOwnerFilter(Owner owner)
  {
    _owner=owner;
  }

  /**
   * Get the owner to use.
   * @return A owner or <code>null</code>.
   */
  public Owner getOwner()
  {
    return _owner;
  }

  /**
   * Set the owner to select.
   * @param owner Owner to use, may be <code>null</code>.
   */
  public void setOwner(Owner owner)
  {
    _owner=owner;
  }

  public boolean accept(StoredItem item)
  {
    if (_owner==null)
    {
      return true;
    }
    return item.getOwner().equals(_owner);
  }
}
