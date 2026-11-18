package delta.games.lotro.character.storage;

import delta.games.lotro.character.storage.location.StorageLocation;
import delta.games.lotro.common.owner.Owner;
import delta.games.lotro.lore.items.BaseItem;
import delta.games.lotro.lore.items.CountedItem;

/**
 * Stored item.
 * @author DAM
 */
public class StoredItem
{
  private StorageLocation _location;
  private Owner _owner;
  private CountedItem<BaseItem> _item;

  /**
   * Constructor.
   * @param item Wrapped item.
   */
  public StoredItem(CountedItem<BaseItem> item)
  {
    _item=item;
  }

  /**
   * Get the location of this item.
   * @return a location.
   */
  public StorageLocation getLocation() 
  {
    return _location;
  }

  /**
   * Set the item location.
   * @param location Location to set.
   */
  public void setLocation(StorageLocation location)
  {
    _location=location;
  }

  /**
   * Get the owner of this item.
   * @return a owner.
   */
  public Owner getOwner()
  {
    return _owner;
  }

  /**
   * Set the item owner.
   * @param owner Owner to set.
   */
  public void setOwner(Owner owner)
  {
    _owner=owner;
  }

  /**
   * Get the managed item.
   * @return the managed item.
   */
  public CountedItem<BaseItem> getItem()
  {
    return _item;
  }
}
