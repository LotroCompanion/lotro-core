package delta.games.lotro.lore.items.comparators;

import java.util.Comparator;

import delta.games.lotro.lore.items.Item;

/**
 * Comparator for items, using their stash identifier.
 * @author DAM
 */
public class ItemStashIdComparator implements Comparator<Item>
{
  public int compare(Item o1, Item o2)
  {
    int id1=o1.getStashIdentifier();
    int id2=o2.getStashIdentifier();
    return id1-id2;
  }
}
