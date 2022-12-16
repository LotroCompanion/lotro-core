package delta.games.lotro.character.storage.vaults;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import delta.games.lotro.character.storage.BaseStorage;
import delta.games.lotro.common.id.InternalGameId;
import delta.games.lotro.lore.items.CountedItem;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemInstance;
import delta.games.lotro.lore.items.carryalls.CarryAll;

/**
 * Storage vault (own vault or shared vault).
 * @author DAM
 */
public class Vault extends BaseStorage
{
  private int _used;
  private int _capacity;
  private Map<Integer,Chest> _chests;

  /**
   * Constructor.
   */
  public Vault()
  {
    super();
    _used=0;
    _capacity=0;
    _chests=new HashMap<Integer,Chest>();
  }

  /**
   * Get all chest identifiers.
   * @return a list of chest identifiers.
   */
  public List<Integer> getChestIds()
  {
    List<Integer> ret=new ArrayList<Integer>(_chests.keySet());
    Collections.sort(ret);
    return ret;
  }

  /**
   * Get the number of chests in this vault.
   * @return A chest count.
   */
  public int getChestCount()
  {
    return _chests.size();
  }

  /**
   * Get a chest by its identifier.
   * @param chestId A chest identifier.
   * @return a chest or <code>null</code> if not found.
   */
  public Chest getChest(int chestId)
  {
    return _chests.get(Integer.valueOf(chestId));
  }

  /**
   * Add a chest.
   * @param chest
   */
  public void addChest(Chest chest)
  {
    Integer key=Integer.valueOf(chest.getChestId());
    _chests.put(key,chest);
  }

  /**
   * Get the number of slots used in this vault.
   * @return a slot count.
   */
  public int getUsed()
  {
    return _used;
  }

  /**
   * Set the number of slots used in this vault.
   * @param used Number of used slots.
   */
  public void setUsed(int used)
  {
    _used=used;
  }

  /**
   * Get the total number of slots in this vault.
   * @return a slot count.
   */
  public int getCapacity()
  {
    return _capacity;
  }

  /**
   * Set the total number of slots in this vault.
   * @param capacity Number of slots.
   */
  public void setCapacity(int capacity)
  {
    _capacity=capacity;
  }

  /**
   * Get the IDs of the carry-alls from this vault.
   * @return A set of carry-all IDs.
   */
  public Set<InternalGameId> getCarryAllIDs()
  {
    Set<InternalGameId> ret=new HashSet<InternalGameId>();
    for(Chest chest : _chests.values())
    {
      List<CountedItem<ItemInstance<? extends Item>>> countedItems=chest.getAllItemInstances();
      for(CountedItem<ItemInstance<? extends Item>> countedItem : countedItems)
      {
        ItemInstance<? extends Item> itemInstance=countedItem.getManagedItem();
        Item item=itemInstance.getItem();
        if (item instanceof CarryAll)
        {
          ret.add(itemInstance.getInstanceId());
        }
      }
    }
    return ret;
  }

  /**
   * Dump contents.
   * @param level Indentation level.
   */
  public void dump(int level)
  {
    for(int i=0;i<level;i++) System.out.print('\t');
    System.out.println("Capacity: "+_used+'/'+_capacity);
    for(Integer chestId : getChestIds())
    {
      Chest chest=getChest(chestId.intValue());
      chest.dump(level);
    }
  }
}
