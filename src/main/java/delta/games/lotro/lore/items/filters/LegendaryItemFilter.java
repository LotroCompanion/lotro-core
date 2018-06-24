package delta.games.lotro.lore.items.filters;

import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.legendary.Legendary;

/**
 * Filter legendary/non legendary items.
 * @author DAM
 */
public class LegendaryItemFilter implements ItemFilter
{
  private Boolean _legendary;

  /**
   * Constructor.
   * @param legendary Use legendary or non legendary or both.
   */
  public LegendaryItemFilter(Boolean legendary)
  {
    _legendary=legendary;
  }

  public boolean accept(Item item)
  {
    if (_legendary!=null)
    {
      return (item instanceof Legendary)==_legendary.booleanValue();
    }
    return true;
  }
}
