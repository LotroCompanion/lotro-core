package delta.games.lotro.lore.items.comparators;

import java.util.Comparator;

import delta.games.lotro.lore.items.Item;

/**
 * Comparator for items, using their tier.
 * @author DAM
 */
public class ItemTierComparator implements Comparator<Item>
{
  public int compare(Item item1, Item item2)
  {
    Integer tier1=item1.getTier();
    Integer tier2=item2.getTier();
    if (tier1!=null)
    {
      if (tier2!=null)
      {
        return tier1.compareTo(tier2);
      }
      return 1;
    }
    if (tier2!=null)
    {
      return -1;
    }
    return 0;
  }
}
