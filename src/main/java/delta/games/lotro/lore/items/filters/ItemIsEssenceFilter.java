package delta.games.lotro.lore.items.filters;

import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.essences.Essence;

/**
 * Filter to select items that are essences.
 * @author DAM
 */
public class ItemIsEssenceFilter implements ItemFilter
{
  public boolean accept(Item item)
  {
    return (item instanceof Essence);
  }
}
