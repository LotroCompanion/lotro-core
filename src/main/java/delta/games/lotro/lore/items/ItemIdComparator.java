package delta.games.lotro.lore.items;

import java.util.Comparator;

/**
 * @author dm
 */
public class ItemIdComparator implements Comparator<Item>
{
  public int compare(Item o1, Item o2)
  {
    int id1=o1.getIdentifier();
    int id2=o2.getIdentifier();
    return id1-id2;
  }
}
