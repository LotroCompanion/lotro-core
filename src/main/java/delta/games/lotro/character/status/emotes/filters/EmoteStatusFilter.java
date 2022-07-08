package delta.games.lotro.character.status.emotes.filters;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.common.utils.collections.filters.CompoundFilter;
import delta.common.utils.collections.filters.Filter;
import delta.common.utils.collections.filters.Operator;
import delta.common.utils.collections.filters.ProxyFilter;
import delta.common.utils.collections.filters.ProxyValueResolver;
import delta.games.lotro.character.status.emotes.EmoteState;
import delta.games.lotro.character.status.emotes.EmoteStatus;
import delta.games.lotro.common.filters.NamedFilter;
import delta.games.lotro.lore.emotes.EmoteDescription;

/**
 * Emote status filter.
 * @author DAM
 */
public class EmoteStatusFilter implements Filter<EmoteStatus>
{
  private Filter<EmoteStatus> _filter;
  private NamedFilter<EmoteDescription> _nameFilter;
  private EmoteStateFilter _stateFilter;

  /**
   * Constructor.
   */
  public EmoteStatusFilter()
  {
    List<Filter<EmoteStatus>> filters=new ArrayList<Filter<EmoteStatus>>();
    // Name
    _nameFilter=new NamedFilter<EmoteDescription>();
    ProxyValueResolver<EmoteStatus,EmoteDescription> proxySolver=new ProxyValueResolver<EmoteStatus,EmoteDescription>()
    {
      @Override
      public EmoteDescription getValue(EmoteStatus pojo)
      {
        return pojo.getEmote();
      }
    };
    ProxyFilter<EmoteStatus,EmoteDescription> nameFilter=new ProxyFilter<EmoteStatus,EmoteDescription>(proxySolver,_nameFilter);
    filters.add(nameFilter);
    // State
    Set<EmoteState> states=new HashSet<EmoteState>();
    for(EmoteState state : EmoteState.values())
    {
      states.add(state);
    }
    _stateFilter=new EmoteStateFilter(states);
    filters.add(_stateFilter);
    _filter=new CompoundFilter<EmoteStatus>(Operator.AND,filters);
  }

  /**
   * Get the filter on emote name.
   * @return an emote name filter.
   */
  public NamedFilter<EmoteDescription> getNameFilter()
  {
    return _nameFilter;
  }

  /**
   * Get the filter on emote state.
   * @return an emote state filter.
   */
  public EmoteStateFilter getStateFilter()
  {
    return _stateFilter;
  }

  @Override
  public boolean accept(EmoteStatus item)
  {
    return _filter.accept(item);
  }
}
