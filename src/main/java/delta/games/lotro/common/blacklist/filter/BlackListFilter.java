package delta.games.lotro.common.blacklist.filter;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.blacklist.Blacklist;

/**
 * Filters identifiables using the given blacklist.
 * @author DAM
 */
public class BlackListFilter implements Filter<Identifiable>
{
  private boolean _enabled;
  private Blacklist _blackList;

  /**
   * Constructor.
   * @param blacklist Blacklist to use.
   */
  public BlackListFilter(Blacklist blacklist)
  {
    _enabled=true;
    _blackList=blacklist;
  }

  /**
   * Indicates if this filter is enabled or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isEnabled()
  {
    return _enabled;
  }

  /**
   * Set the 'enabled' state of this filter.
   * @param enabled State to set.
   */
  public void setEnabled(boolean enabled)
  {
    _enabled=enabled;
  }

  @Override
  public boolean accept(Identifiable achievable)
  {
    if (!_enabled)
    {
      return true;
    }
    boolean blacklisted=_blackList.isBlacklisted(achievable.getIdentifier());
    return !blacklisted;
  }
}
