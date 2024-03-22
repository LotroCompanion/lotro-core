package delta.games.lotro.character.status.achievables.filter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.character.status.achievables.AchievableElementState;
import delta.games.lotro.character.status.achievables.AchievableStatus;
import delta.games.lotro.common.blacklist.Blacklist;
import delta.games.lotro.common.blacklist.filter.BlackListFilter;

/**
 * Filter for achievable statuses.
 * @author DAM
 */
public abstract class AchievableStatusFilter implements Filter<AchievableStatus>
{
  // State filter
  private AchievableElementStateFilter _stateFilter;
  // Blacklist
  private BlackListFilter _blacklist;

  /**
   * Constructor.
   */
  protected AchievableStatusFilter()
  {
    Set<AchievableElementState> states=new HashSet<AchievableElementState>();
    Collections.addAll(states,AchievableElementState.values());
    _stateFilter=new AchievableElementStateFilter(states);
    _blacklist=null;
  }

  /**
   * Get the state filter.
   * @return the state filter.
   */
  public AchievableElementStateFilter getStateFilter()
  {
    return _stateFilter;
  }

  /**
   * Get the blacklist filter.
   * @return the blacklist filter.
   */
  public BlackListFilter getBlacklistFilter()
  {
    return _blacklist;
  }

  /**
   * Set the blacklist.
   * @param blacklist Blacklist to use.
   */
  public void setBlacklist(Blacklist blacklist)
  {
    _blacklist=new BlackListFilter(blacklist);
  }

  @Override
  public boolean accept(AchievableStatus item)
  {
    boolean ok=_stateFilter.accept(item);
    if ((ok) && (_blacklist!=null))
    {
      ok=_blacklist.accept(item.getAchievable());
    }
    return ok;
  }
}
