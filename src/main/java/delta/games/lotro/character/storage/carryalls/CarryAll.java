package delta.games.lotro.character.storage.carryalls;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.id.InternalGameId;
import delta.games.lotro.lore.items.CountedItem;
import delta.games.lotro.lore.items.Item;

/**
 * Carry-all contents.
 * @author DAM
 */
public class CarryAll
{
  private InternalGameId _id;
  private CarryAllDefinition _carryAllDefinition;
  private List<CountedItem<Item>> _items;

  /**
   * Constructor.
   * @param definition Definition.
   */
  public CarryAll(CarryAllDefinition definition)
  {
    _id=null;
    _carryAllDefinition=definition;
    _items=new ArrayList<CountedItem<Item>>();
  }

  /**
   * Get the instance ID.
   * @return an instance ID or <code>null</code> if not set.
   */
  public InternalGameId getInstanceId()
  {
    return _id;
  }

  /**
   * Set the instance ID.
   * @param id Instance identifier to set.
   */
  public void setInstanceId(InternalGameId id)
  {
    _id=id;
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
   * Get the carry-all definition.
   * @return the carry-all definition.
   */
  public CarryAllDefinition getDefinition()
  {
    return _carryAllDefinition;
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
