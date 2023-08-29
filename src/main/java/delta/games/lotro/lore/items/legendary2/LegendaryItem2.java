package delta.games.lotro.lore.items.legendary2;

import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemCategory;

/**
 * Legendary item description (reloaded).
 * @author DAM
 */
public class LegendaryItem2 extends Item implements Legendary2
{
  private LegendaryAttrs2 _attrs;

  /**
   * Constructor.
   */
  public LegendaryItem2()
  {
    _attrs=new LegendaryAttrs2();
  }

  @Override
  public ItemCategory getCategory()
  {
    return ItemCategory.LEGENDARY_ITEM2;
  }

  @Override
  public LegendaryAttrs2 getLegendaryAttrs()
  {
    return _attrs;
  }

  @Override
  public boolean isScalable()
  {
    return false;
  }
}
