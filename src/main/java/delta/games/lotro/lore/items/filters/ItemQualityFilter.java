package delta.games.lotro.lore.items.filters;

import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemQuality;

/**
 * Filter items with a given quality.
 * @author DAM
 */
public class ItemQualityFilter implements ItemFilter
{
  private ItemQuality _quality;

  /**
   * Constructor.
   * @param quality Quality to search.
   */
  public ItemQualityFilter(ItemQuality quality)
  {
    _quality=quality;
  }

  /**
   * Get the quality to select.
   * @return A quality or <code>null</code> to select all.
   */
  public ItemQuality getQuality()
  {
    return _quality;
  }

  /**
   * Set the quality to search.
   * @param quality Quality to search.
   */
  public void setQuality(ItemQuality quality)
  {
    _quality=quality;
  }

  public boolean accept(Item item)
  {
    if (_quality!=null)
    {
      ItemQuality quality=(item!=null)?item.getQuality():null;
      return quality==_quality;
    }
    return true;
  }
}
