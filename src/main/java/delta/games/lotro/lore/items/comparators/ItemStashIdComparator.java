package delta.games.lotro.lore.items.comparators;

import java.util.Comparator;

import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemInstance;

/**
 * Comparator for item instances, using their stash identifier.
 * @author DAM
 */
public class ItemStashIdComparator implements Comparator<ItemInstance<? extends Item>>
{
  public int compare(ItemInstance<? extends Item> o1, ItemInstance<? extends Item> o2)
  {
    Integer id1=o1.getStashIdentifier();
    Integer id2=o2.getStashIdentifier();
    if (id1!=null)
    {
      if (id2!=null)
      {
        return id1.compareTo(id2);
      }
      return 1;
    }
    if (id2!=null)
    {
      return -1;
    }
    return 0;
  }
}
