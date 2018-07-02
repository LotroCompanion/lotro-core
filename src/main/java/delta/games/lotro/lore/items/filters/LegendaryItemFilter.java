package delta.games.lotro.lore.items.filters;

import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.legendary.Legendary;

/**
 * Filter legendary/non legendary items or both.
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

  /**
   * Set the 'legendary' attribute.
   * @param legendary
   */
  public void setLegendary(Boolean legendary)
  {
    _legendary=legendary;
  }

  /**
   * Get the 'legendary' attribute.
   * @return a Boolean value (true, false or null).
   */
  public Boolean getLegendary()
  {
    return _legendary;
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
