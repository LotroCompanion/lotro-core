package delta.games.lotro.lore.items.comparators;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import delta.games.lotro.lore.items.ItemQualities;
import delta.games.lotro.lore.items.ItemQuality;

/**
 * Comparator for item qualities.
 * @author DAM
 */
public class ItemQualityComparator implements Comparator<ItemQuality>
{
  private List<ItemQuality> _orderedQualities;

  /**
   * Constructor.
   */
  public ItemQualityComparator()
  {
    _orderedQualities=Arrays.asList(ItemQualities.ALL);
  }

  public int compare(ItemQuality quality1, ItemQuality quality2)
  {
    if (quality1!=null)
    {
      if (quality2!=null)
      {
        int code1=_orderedQualities.indexOf(quality1);
        int code2=_orderedQualities.indexOf(quality2);
        return Integer.compare(code1,code2);
      }
      return 1;
    }
    return (quality2!=null)?-1:0;
  }
}
