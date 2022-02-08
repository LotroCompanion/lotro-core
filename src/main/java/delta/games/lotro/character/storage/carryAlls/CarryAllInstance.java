package delta.games.lotro.character.storage.carryAlls;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.id.InternalGameId;
import delta.games.lotro.lore.items.CountedItem;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.carryalls.CarryAll;

/**
 * Carry-all contents.
 * @author DAM
 */
public class CarryAllInstance
{
  private InternalGameId _id;
  private CarryAll _reference;
  private List<CountedItem<Item>> _items;

  /**
   * Constructor.
   */
  public CarryAllInstance()
  {
    super();
    _id=null;
    _reference=null;
    _items=new ArrayList<CountedItem<Item>>();
  }

  /**
   * Get the carry-all instance identifier.
   * @return An identifier.
   */
  public InternalGameId getId()
  {
    return _id;
  }

  /**
   * Set the carry-all instance identifier.
   * @param id Identifier to set.
   */
  public void setId(InternalGameId id)
  {
    _id=id;
  }

  /**
   * Get the reference carry-all item.
   * @return A carry-all item.
   */
  public CarryAll getReference()
  {
    return _reference;
  }

  /**
   * Set the reference carry-all item.
   * @param reference Reference to set.
   */
  public void setReference(CarryAll reference)
  {
    _reference=reference;
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
