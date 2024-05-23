package delta.games.lotro.character.storage.carryAlls;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.id.InternalGameId;
import delta.games.lotro.common.status.StatusMetadata;
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
  private String _name;
  private List<CountedItem<Item>> _items;
  private StatusMetadata _statusMetadata;

  /**
   * Constructor.
   */
  public CarryAllInstance()
  {
    super();
    _id=null;
    _reference=null;
    _name="";
    _items=new ArrayList<CountedItem<Item>>();
    _statusMetadata=new StatusMetadata();
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
   * Get the name.
   * @return a name (may be empty but never <code>null</code>).
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Set the name.
   * @param name Name to set.
   */
  public void setName(String name)
  {
    if (name==null)
    {
      name="";
    }
    _name=name;
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

  /**
   * Get the status metadata.
   * @return the status metadata.
   */
  public StatusMetadata getStatusMetadata()
  {
    return _statusMetadata;
  }
}
