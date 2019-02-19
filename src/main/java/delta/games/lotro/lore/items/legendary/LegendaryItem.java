package delta.games.lotro.lore.items.legendary;

import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemCategory;

/**
 * Legendary item description.
 * @author DAM
 */
public class LegendaryItem extends Item implements Legendary
{
  /**
   * Constructor.
   */
  public LegendaryItem()
  {
    setCategory(ItemCategory.LEGENDARY_ITEM);
  }

  /**
   * Copy constructor.
   * @param item Source.
   */
  public LegendaryItem(LegendaryItem item)
  {
    this();
    copyFrom(item);
  }
}
