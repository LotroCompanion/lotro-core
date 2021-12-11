package delta.games.lotro.lore.items.carryalls;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.lore.items.CountedItem;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemInstance;

/**
 * Carry-all contents.
 * @author DAM
 */
public class CarryAllInstance extends ItemInstance<CarryAll>
{
  private List<CountedItem<Item>> _items;

  /**
   * Constructor.
   */
  public CarryAllInstance()
  {
    super();
    _items=new ArrayList<CountedItem<Item>>();
  }

  /**
   * Copy item instance data from a source.
   * @param itemInstance Source item instance.
   */
  @Override
  public void copyFrom(ItemInstance<?> itemInstance)
  {
    super.copyFrom(itemInstance);
    _items.clear();
    CarryAllInstance source=(CarryAllInstance)itemInstance;
    for(CountedItem<Item> item : source._items)
    {
      addItem(item.getItem(),item.getQuantity());
    }
  }

  /**
   * Add an item in this carry-all.
   * @param item Item to add.
   * @param count Item count.
   */
  public void addItem(Item item, int count)
  {
    CountedItem<Item> newItem=new CountedItem<Item>(item,count);
    _items.add(newItem);
  }

  /**
   * Get the managed items.
   * @return a list of items.
   */
  public List<CountedItem<Item>> getItems()
  {
    return new ArrayList<CountedItem<Item>>(_items);
  }
}
