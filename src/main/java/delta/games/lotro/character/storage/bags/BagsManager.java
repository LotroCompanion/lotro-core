package delta.games.lotro.character.storage.bags;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.common.id.InternalGameId;
import delta.games.lotro.lore.items.CountedItemInstance;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemInstance;

/**
 * Bags contents manager.
 * @author DAM
 */
public class BagsManager
{
  private Map<Integer,CountedItemInstance> _bag;

  /**
   * Constructor.
   */
  public BagsManager()
  {
    _bag=new HashMap<Integer,CountedItemInstance>();
  }

  /**
   * Add a bag item.
   * @param itemInstance Item to add in bag.
   * @param index Position in bag.
   */
  public void addBagItem(CountedItemInstance itemInstance, int index)
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
  public CountedItemInstance getSlotContent(int index)
  {
    return _bag.get(Integer.valueOf(index));
  }

  /**
   * Find an item using its Instance Identifier.
   * @param itemIid Identifier to use.
   * @return An item instance or <code>null</code>.
   */
  public ItemInstance<? extends Item> findItemByIid(long itemIid)
  {
    for(CountedItemInstance countedItemInstance : _bag.values())
    {
      ItemInstance<? extends Item> itemInstance=countedItemInstance.getItemInstance();
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
      CountedItemInstance itemInstance=_bag.get(position);
      System.out.println("\t"+position+" => "+itemInstance);
    }
  }
}
