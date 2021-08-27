package delta.games.lotro.character.status.relics.filter;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.character.status.relics.RelicsInventoryEntry;
import delta.games.lotro.lore.items.legendary.relics.RelicFilter;

/**
 * Filter for relic inventory items.
 * @author DAM
 */
public class RelicInventoryEntryFilter implements Filter<RelicsInventoryEntry>
{
  private RelicFilter _relicFilter;

  /**
   * Constructor.
   */
  public RelicInventoryEntryFilter()
  {
    _relicFilter=new RelicFilter();
  }

  /**
   * Get the managed relic filter.
   * @return the managed relic filter.
   */
  public RelicFilter getRelicFilter()
  {
    return _relicFilter;
  }

  @Override
  public boolean accept(RelicsInventoryEntry item)
  {
    return _relicFilter.accept(item.getRelic());
  }
}
