package delta.games.lotro.character.storage;

import java.util.ArrayList;
import java.util.List;

/**
 * Storage vault (own vault, shared vault or bags).
 * @author DAM
 */
public class Vault
{
  private int _used;
  private int _capacity;
  private List<Chest> _chests;

  /**
   * Constructor.
   */
  public Vault()
  {
    _used=0;
    _capacity=0;
    _chests=new ArrayList<Chest>();
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
   * Get a chest by its index.
   * @param index A chest index, starting at 0.
   * @return a chest.
   */
  public Chest getChest(int index)
  {
    int size=_chests.size();
    for(int i=size;i<=index;i++)
    {
      _chests.add(new Chest());
    }
    return _chests.get(index);
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
   * Dump contents.
   * @param level Indentation level.
   */
  public void dump(int level)
  {
    for(int i=0;i<level;i++) System.out.print('\t');
    System.out.println("Capacity: "+_used+'/'+_capacity);
    for(Chest chest : _chests)
    {
      chest.dump(level);
    }
  }
}
