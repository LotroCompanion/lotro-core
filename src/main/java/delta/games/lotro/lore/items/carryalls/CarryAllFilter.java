package delta.games.lotro.lore.items.carryalls;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.collections.filters.CompoundFilter;
import delta.common.utils.collections.filters.Filter;
import delta.common.utils.collections.filters.Operator;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.filters.ItemNameFilter;

/**
 * Filter on items for the carry-all display.
 * @author DAM
 */
public class CarryAllFilter implements Filter<Item>
{
  // Data
  private Filter<Item> _filter;
  private ItemNameFilter _nameFilter;

  /**
   * Constructor.
   */
  public CarryAllFilter()
  {
    List<Filter<Item>> filters=new ArrayList<Filter<Item>>();
    _nameFilter=new ItemNameFilter();
    filters.add(_nameFilter);
    _filter=new CompoundFilter<Item>(Operator.AND,filters);
  }

  /**
   * Get the name filter.
   * @return a name filter.
   */
  public ItemNameFilter getNameFilter()
  {
    return _nameFilter;
  }

  @Override
  public boolean accept(Item item)
  {
    return _filter.accept(item);
  }
}
