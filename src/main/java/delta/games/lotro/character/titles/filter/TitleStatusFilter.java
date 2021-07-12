package delta.games.lotro.character.titles.filter;

import java.util.HashSet;
import java.util.Set;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.character.titles.TitleStatus;
import delta.games.lotro.lore.titles.filters.TitleFilter;

/**
 * Filter for title statuses.
 * @author DAM
 */
public class TitleStatusFilter implements Filter<TitleStatus>
{
  private TitleFilter _titleFilter;
  private TitleStateFilter _stateFilter;

  /**
   * Constructor.
   */
  public TitleStatusFilter()
  {
    _titleFilter=new TitleFilter();
    Set<Boolean> states=new HashSet<Boolean>();
    states.add(Boolean.TRUE);
    states.add(Boolean.FALSE);
    _stateFilter=new TitleStateFilter(states);
  }

  /**
   * Get the managed title filter.
   * @return the managed title filter.
   */
  public TitleFilter getTitleFilter()
  {
    return _titleFilter;
  }

  /**
   * Get the state filter.
   * @return the state filter.
   */
  public TitleStateFilter getStateFilter()
  {
    return _stateFilter;
  }

  @Override
  public boolean accept(TitleStatus item)
  {
    boolean ok=_stateFilter.accept(item);
    if (ok)
    {
      return _titleFilter.accept(item.getTitle());
    }
    return false;
  }
}
