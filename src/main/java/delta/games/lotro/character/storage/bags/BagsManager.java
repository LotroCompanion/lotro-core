package delta.games.lotro.character.storage.bags;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.character.storage.BaseStorage;
import delta.games.lotro.common.id.InternalGameId;
import delta.games.lotro.lore.items.CountedItem;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemInstance;
import delta.games.lotro.lore.items.carryalls.CarryAllInstance;

/**
 * Bags contents manager.
 * @author DAM
 */
public class BagsManager extends BaseStorage
{
  private int _capacity;
  private Map<Integer,CountedItem<ItemInstance<? extends Item>>> _bag;
  private BagsSetup _setup;

  /**
   * Constructor.
   */
  public BagsManager()
  {
    _bag=new HashMap<Integer,CountedItem<ItemInstance<? extends Item>>>();
    _setup=new BagsSetup();
  }

  /**
   * Add a bag item.
   * @param itemInstance Item to add in bag.
   * @param index Position in bag.
   */
  public void addBagItem(CountedItem<ItemInstance<? extends Item>> itemInstance, int index)
  {
    _bag.put(Integer.valueOf(index),itemInstance);
  }

  /**
   * Get the occupied indexes.
   * @return a sorted list of indexes.
   */
  public List<Integer> getIndexes()
  {
    List<Integer> indexes=new ArrayList<Integer>(_bag.keySet());
    Collections.sort(indexes);
    return indexes;
  }

  /**
   * Get the counted item instance at the given slot.
   * @param index Index of the targeted slot.
   * @return A counted item instance or <code>null</code>.
   */
  public CountedItem<ItemInstance<? extends Item>> getSlotContent(int index)
  {
    return _bag.get(Integer.valueOf(index));
  }

  /**
   * Get a list of all managed item instances.
   * @return a list of item instances.
   */
  public List<ItemInstance<? extends Item>> getAllItemInstances()
  {
    List<ItemInstance<? extends Item>> ret=new ArrayList<ItemInstance<? extends Item>>();
    for(Integer index : getIndexes())
    {
      CountedItem<ItemInstance<? extends Item>> countedItemInstance=getSlotContent(index.intValue());
      ret.add(countedItemInstance.getManagedItem());
    }
    return ret;
  }

  /**
   * Get a list of all managed item instances.
   * @return a list of item instances.
   */
  public List<CountedItem<ItemInstance<? extends Item>>> getAll()
  {
    List<CountedItem<ItemInstance<? extends Item>>> ret=new ArrayList<CountedItem<ItemInstance<? extends Item>>>();
    for(Integer index : getIndexes())
    {
      CountedItem<ItemInstance<? extends Item>> countedItemInstance=getSlotContent(index.intValue());
      ret.add(countedItemInstance);
    }
    return ret;
  }

  /**
   * Find an item using its Instance Identifier.
   * @param itemIid Identifier to use.
   * @return An item instance or <code>null</code>.
   */
  public ItemInstance<? extends Item> findItemByIid(long itemIid)
  {
    for(CountedItem<ItemInstance<? extends Item>> countedItemInstance : _bag.values())
    {
      ItemInstance<? extends Item> itemInstance=countedItemInstance.getManagedItem();
      InternalGameId instanceId=itemInstance.getInstanceId();
      if (instanceId!=null)
      {
        if (InternalGameId.lightMatch(instanceId.asLong(),itemIid))
        {
          return itemInstance;
        }
      }
    }
    return null;
  }

  @Override
  public int getUsed()
  {
    return _bag.size();
  }

  /**
   * Set the bags setup.
   * @param setup Setup to set.
   */
  public void setBagsSetup(BagsSetup setup)
  {
    _setup=setup;
    _capacity=setup.getCapacity();
  }

  /**
   * Get the total number of slots in this bag.
   * @return a slot count.
   */
  public int getCapacity()
  {
    return _capacity;
  }

  /**
   * Get the bags setup.
   * @return the bags setup.
   */
  public BagsSetup getBagsSetup()
  {
    return _setup;
  }

  /**
   * Get the carry-alls from this vault.
   * @return A list of carry-alls.
   */
  public List<CarryAllInstance> getCarryAlls()
  {
    List<CarryAllInstance> ret=new ArrayList<CarryAllInstance>();
    for(CountedItem<ItemInstance<? extends Item>> item : _bag.values())
    {
      ItemInstance<? extends Item> itemInstance=item.getManagedItem();
      if (itemInstance instanceof CarryAllInstance)
      {
        ret.add((CarryAllInstance)itemInstance);
      }
    }
    return ret;
  }

  /**
   * Dump the contents of this aggregator.
   */
  public void dumpContents()
  {
    System.out.println("Bags:");
    List<Integer> positions=new ArrayList<Integer>(_bag.keySet());
    Collections.sort(positions);
    for(Integer position : positions)
    {
      CountedItem<ItemInstance<? extends Item>> itemInstance=_bag.get(position);
      System.out.println("\t"+position+" => "+itemInstance);
    }
  }
}
