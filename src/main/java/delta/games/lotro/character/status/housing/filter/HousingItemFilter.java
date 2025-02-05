package delta.games.lotro.character.status.housing.filter;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.collections.filters.CompoundFilter;
import delta.common.utils.collections.filters.Filter;
import delta.common.utils.collections.filters.Operator;
import delta.common.utils.collections.filters.ProxyFilter;
import delta.common.utils.collections.filters.ProxyValueResolver;
import delta.games.lotro.character.status.housing.HousingItem;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.filters.ItemClassFilter;
import delta.games.lotro.lore.items.filters.ItemNameFilter;

/**
 * Housing item filter.
 * @author DAM
 */
public class HousingItemFilter implements Filter<HousingItem>
{
  private Filter<HousingItem> _filter;

  // Hook ID
  private HousingItemHookFilter _hookFilter;
  // Item filter:
  // - name
  private ItemNameFilter _nameFilter;
  // - category
  private ItemClassFilter _categoryFilter;

  /**
   * Constructor.
   */
  public HousingItemFilter()
  {
    List<Filter<HousingItem>> filters=new ArrayList<Filter<HousingItem>>();

    // Item filters
    ProxyValueResolver<HousingItem,Item> resolver=new ProxyValueResolver<HousingItem,Item>()
    {
      public Item getValue(HousingItem pojo)
      {
        return pojo.getItem();
      }
    };
    // - name
    _nameFilter=new ItemNameFilter();
    ProxyFilter<HousingItem,Item> nameFilter=new ProxyFilter<HousingItem,Item>(resolver,_nameFilter);
    filters.add(nameFilter);
    // - category
    _categoryFilter=new ItemClassFilter(null);
    ProxyFilter<HousingItem,Item> categoryFilter=new ProxyFilter<HousingItem,Item>(resolver,_categoryFilter);
    filters.add(categoryFilter);

    // Hook ID filter
    _hookFilter=new HousingItemHookFilter(null);
    filters.add(_hookFilter);
    _filter=new CompoundFilter<HousingItem>(Operator.AND,filters);
  }

  /**
   * Get the filter on item name.
   * @return a name filter.
   */
  public ItemNameFilter getNameFilter()
  {
    return _nameFilter;
  }

  /**
   * Get the filter on item category.
   * @return a name filter.
   */
  public ItemClassFilter getCategoryFilter()
  {
    return _categoryFilter;
  }

  /**
   * Get the filter on rank.
   * @return a rank filter.
   */
  public HousingItemHookFilter getHookFilter()
  {
    return _hookFilter;
  }

  @Override
  public boolean accept(HousingItem item)
  {
    return _filter.accept(item);
  }
}
