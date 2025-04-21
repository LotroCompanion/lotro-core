package delta.games.lotro.character.status.collections.birds.filters;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.collections.filters.CompoundFilter;
import delta.common.utils.collections.filters.Filter;
import delta.common.utils.collections.filters.Operator;
import delta.common.utils.collections.filters.ProxyFilter;
import delta.common.utils.collections.filters.ProxyValueResolver;
import delta.games.lotro.character.status.collections.birds.BirdStatus;
import delta.games.lotro.common.filters.NamedFilter;
import delta.games.lotro.lore.collections.birds.BirdDescription;

/**
 * Bird status filter.
 * @author DAM
 */
public class BirdStatusFilter implements Filter<BirdStatus>
{
  private Filter<BirdStatus> _filter;
  private NamedFilter<BirdDescription> _nameFilter;
  private KnownBirdFilter _knownFilter;

  /**
   * Constructor.
   */
  public BirdStatusFilter()
  {
    List<Filter<BirdStatus>> filters=new ArrayList<Filter<BirdStatus>>();
    // Name
    _nameFilter=new NamedFilter<BirdDescription>();
    ProxyValueResolver<BirdStatus,BirdDescription> proxySolver=new ProxyValueResolver<BirdStatus,BirdDescription>()
    {
      @Override
      public BirdDescription getValue(BirdStatus pojo)
      {
        return pojo.getBird();
      }
    };
    ProxyFilter<BirdStatus,BirdDescription> nameFilter=new ProxyFilter<BirdStatus,BirdDescription>(proxySolver,_nameFilter);
    filters.add(nameFilter);
    // Known
    _knownFilter=new KnownBirdFilter(null);
    filters.add(_knownFilter);
    _filter=new CompoundFilter<BirdStatus>(Operator.AND,filters);
  }

  /**
   * Get the filter on bird name.
   * @return a bird name filter.
   */
  public NamedFilter<BirdDescription> getNameFilter()
  {
    return _nameFilter;
  }

  /**
   * Get the filter on known/unknown state.
   * @return a known/unknown filter.
   */
  public KnownBirdFilter getKnownFilter()
  {
    return _knownFilter;
  }

  @Override
  public boolean accept(BirdStatus item)
  {
    return _filter.accept(item);
  }
}
