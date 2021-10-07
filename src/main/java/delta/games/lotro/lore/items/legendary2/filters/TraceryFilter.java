package delta.games.lotro.lore.items.legendary2.filters;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.collections.filters.CompoundFilter;
import delta.common.utils.collections.filters.Filter;
import delta.common.utils.collections.filters.Operator;
import delta.common.utils.collections.filters.ProxyFilter;
import delta.common.utils.collections.filters.ProxyValueResolver;
import delta.games.lotro.common.enums.SocketType;
import delta.games.lotro.common.enums.filter.LotroEnumValueFilter;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.filters.ItemNameFilter;
import delta.games.lotro.lore.items.filters.ItemQualityFilter;
import delta.games.lotro.lore.items.legendary2.Tracery;
import delta.games.lotro.utils.DataProvider;

/**
 * Tracery filter.
 * @author DAM
 */
public class TraceryFilter implements Filter<Tracery>
{
  private Filter<Tracery> _filter;

  private ItemNameFilter _nameFilter;
  private LotroEnumValueFilter<SocketType,Tracery> _typeFilter;
  private TraceryTierFilter _tierFilter;
  private ItemQualityFilter _qualityFilter;

  /**
   * Constructor.
   */
  public TraceryFilter()
  {
    List<Filter<Tracery>> filters=new ArrayList<Filter<Tracery>>();
    // Item based filters
    ProxyValueResolver<Tracery,Item> r=new ProxyValueResolver<Tracery,Item>()
    {
      public Item getValue(Tracery tracery)
      {
        return tracery.getItem();
      }
    };
    // - Name
    _nameFilter=new ItemNameFilter();
    filters.add(new ProxyFilter<>(r,_nameFilter));
    // - Quality
    _qualityFilter=new ItemQualityFilter(null);
    filters.add(new ProxyFilter<>(r,_qualityFilter));
    // Type
    DataProvider<Tracery,SocketType> provider=new DataProvider<Tracery,SocketType>()
    {
      public SocketType getData(Tracery tracery)
      {
        return tracery.getType();
      }
    };
    _typeFilter=new LotroEnumValueFilter<SocketType,Tracery>(provider,null);
    filters.add(_typeFilter);
    // Tier
    _tierFilter=new TraceryTierFilter();
    filters.add(_tierFilter);
    _filter=new CompoundFilter<Tracery>(Operator.AND,filters);
  }

  /**
   * Get the filter on tracery name.
   * @return a name filter.
   */
  public ItemNameFilter getNameFilter()
  {
    return _nameFilter;
  }

  /**
   * Get the filter on type.
   * @return a classification filter.
   */
  public LotroEnumValueFilter<SocketType,Tracery> getTypeFilter()
  {
    return _typeFilter;
  }

  /**
   * Get the filter on tracery quality.
   * @return a quality filter.
   */
  public ItemQualityFilter getQualityFilter()
  {
    return _qualityFilter;
  }

  /**
   * Get the filter on tracery tier.
   * @return a tier filter.
   */
  public TraceryTierFilter getTierFilter()
  {
    return _tierFilter;
  }

  @Override
  public boolean accept(Tracery item)
  {
    return _filter.accept(item);
  }
}
