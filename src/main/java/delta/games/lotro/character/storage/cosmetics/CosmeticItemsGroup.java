package delta.games.lotro.character.storage.cosmetics;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.storage.StoredItem;

/**
 * Group of items with the same cosmetics.
 * @author DAM
 */
public class CosmeticItemsGroup
{
  private int _cosmeticID;
  private List<StoredItem> _items;

  /**
   * Constructor.
   * @param cosmeticID Cosmetic ID.
   */
  public CosmeticItemsGroup(int cosmeticID)
  {
    _cosmeticID=cosmeticID;
    _items=new ArrayList<StoredItem>();
  }

  /**
   * Get the cosmetic ID.
   * @return A cosmetic ID.
   */
  public int getCosmeticID()
  {
    return _cosmeticID;
  }

  /**
   * Add an item to the group.
   * @param item Item to add.
   */
  public void addItem(StoredItem item)
  {
    _items.add(item);
  }

  /**
   * Get the items in the group.
   * @return A list of items.
   */
  public List<StoredItem> getItems()
  {
    return _items;
  }
}
