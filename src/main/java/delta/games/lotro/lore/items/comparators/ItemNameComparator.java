package delta.games.lotro.lore.items.comparators;

import java.util.Comparator;

import delta.games.lotro.lore.items.Item;

/**
 * Comparator for items, using their name.
 * @author DAM
 */
public class ItemNameComparator implements Comparator<Item>
{
  public int compare(Item o1, Item o2)
  {
    String name1=o1.getName();
    String name2=o2.getName();
    if (name1!=null)
    {
      if (name2!=null)
      {
        return name1.compareTo(name2);
      }
      return 1;
    }
    return (name2!=null)?-1:0;
  }
}
