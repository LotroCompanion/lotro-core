package delta.games.lotro.character.status.collections.filters;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.collections.filters.CompoundFilter;
import delta.common.utils.collections.filters.Filter;
import delta.common.utils.collections.filters.Operator;
import delta.common.utils.collections.filters.ProxyFilter;
import delta.common.utils.collections.filters.ProxyValueResolver;
import delta.games.lotro.character.status.collections.CollectionStatus;
import delta.games.lotro.common.filters.NamedFilter;
import delta.games.lotro.lore.collections.CollectionDescription;

/**
 * Collection status filter.
 * @author DAM
 */
public class CollectionsStatusFilter implements Filter<CollectionStatus>
{
  private Filter<CollectionStatus> _filter;
  private NamedFilter<CollectionDescription> _nameFilter;
  private CollectionStateFilter _stateFilter;

  /**
   * Constructor.
   */
  public CollectionsStatusFilter()
  {
    List<Filter<CollectionStatus>> filters=new ArrayList<Filter<CollectionStatus>>();
    // Name
    _nameFilter=new NamedFilter<CollectionDescription>();
    ProxyValueResolver<CollectionStatus,CollectionDescription> proxySolver=new ProxyValueResolver<CollectionStatus,CollectionDescription>()
    {
      @Override
      public CollectionDescription getValue(CollectionStatus pojo)
      {
        return pojo.getCollection();
      }
    };
    ProxyFilter<CollectionStatus,CollectionDescription> nameFilter=new ProxyFilter<CollectionStatus,CollectionDescription>(proxySolver,_nameFilter);
    filters.add(nameFilter);
    // State
    _stateFilter=new CollectionStateFilter(null);
    filters.add(_stateFilter);
    _filter=new CompoundFilter<CollectionStatus>(Operator.AND,filters);
  }

  /**
   * Get the filter on collection name.
   * @return a collection name filter.
   */
  public NamedFilter<CollectionDescription> getNameFilter()
  {
    return _nameFilter;
  }

  /**
   * Get the filter on collection state.
   * @return a state filter.
   */
  public CollectionStateFilter getStateFilter()
  {
    return _stateFilter;
  }

  @Override
  public boolean accept(CollectionStatus item)
  {
    return _filter.accept(item);
  }
}
