package delta.games.lotro.lore.items.comparators;

import java.util.Comparator;

import delta.games.lotro.lore.items.ItemQuality;

/**
 * Comparator for item qualities.
 * @author DAM
 */
public class ItemQualityComparator implements Comparator<ItemQuality>
{
  public int compare(ItemQuality quality1, ItemQuality quality2)
  {
    if (quality1!=null)
    {
      if (quality2!=null)
      {
        return quality1.getCode()-quality2.getCode();
      }
      return 1;
    }
    return (quality2!=null)?-1:0;
  }
}
