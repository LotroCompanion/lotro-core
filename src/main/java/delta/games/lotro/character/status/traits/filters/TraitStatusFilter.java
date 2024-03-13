package delta.games.lotro.character.status.traits.filters;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.collections.filters.CompoundFilter;
import delta.common.utils.collections.filters.Filter;
import delta.common.utils.collections.filters.Operator;
import delta.common.utils.collections.filters.ProxyFilter;
import delta.common.utils.collections.filters.ProxyValueResolver;
import delta.games.lotro.character.status.traits.TraitStatus;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.character.traits.filters.TraitGroupFilter;
import delta.games.lotro.common.filters.NamedFilter;

/**
 * Trait status filter.
 * @author DAM
 */
public class TraitStatusFilter implements Filter<TraitStatus>
{
  private Filter<TraitStatus> _filter;
  private NamedFilter<TraitDescription> _nameFilter;
  private TraitGroupFilter _groupFilter;
  private KnownTraitFilter _knownFilter;

  /**
   * Constructor.
   */
  public TraitStatusFilter()
  {
    List<Filter<TraitStatus>> filters=new ArrayList<Filter<TraitStatus>>();
    // Name
    _nameFilter=new NamedFilter<TraitDescription>();
    ProxyValueResolver<TraitStatus,TraitDescription> proxySolver=new ProxyValueResolver<TraitStatus,TraitDescription>()
    {
      @Override
      public TraitDescription getValue(TraitStatus pojo)
      {
        return pojo.getTrait();
      }
    };
    ProxyFilter<TraitStatus,TraitDescription> nameFilter=new ProxyFilter<TraitStatus,TraitDescription>(proxySolver,_nameFilter);
    filters.add(nameFilter);
    // Group
    _groupFilter=new TraitGroupFilter(null);
    ProxyFilter<TraitStatus,TraitDescription> groupFilter=new ProxyFilter<TraitStatus,TraitDescription>(proxySolver,_groupFilter);
    filters.add(groupFilter);
    // Known
    _knownFilter=new KnownTraitFilter(null);
    filters.add(_knownFilter);
    _filter=new CompoundFilter<TraitStatus>(Operator.AND,filters);
  }

  /**
   * Get the filter on trait name.
   * @return a trait name filter.
   */
  public NamedFilter<TraitDescription> getNameFilter()
  {
    return _nameFilter;
  }

  /**
   * Get the filter on trait group.
   * @return a trait group filter.
   */
  public TraitGroupFilter getGroupFilter()
  {
    return _groupFilter;
  }

  /**
   * Get the filter on known/unknown state.
   * @return a known/unknown filter.
   */
  public KnownTraitFilter getKnownFilter()
  {
    return _knownFilter;
  }

  @Override
  public boolean accept(TraitStatus item)
  {
    return _filter.accept(item);
  }
}
