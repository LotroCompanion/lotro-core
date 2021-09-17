package delta.games.lotro.lore.quests.filter;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.lore.quests.Achievable;

/**
 * Filter for achievables that are obsolete, or that are NOT obsolete.
 * @param <T> Type of managed achievables.
 * @author DAM
 */
public class ObsoleteAchievableFilter<T extends Achievable> implements Filter<T>
{
  private Boolean _isObsolete;

  /**
   * Constructor.
   * @param isObsolete Indicates if this filter shall select quests
   * that are obsolete, or not (<code>null</code> means no filter).
   */
  public ObsoleteAchievableFilter(Boolean isObsolete)
  {
    _isObsolete=isObsolete;
  }

  /**
   * Get the value of the 'is obsolete' flag.
   * @return A boolean value or <code>null</code>.
   */
  public Boolean getIsObsoleteFlag()
  {
    return _isObsolete;
  }

  /**
   * Set the value of the 'is obsolete' flag.
   * @param isObsolete Flag to set, may be <code>null</code>.
   */
  public void setIsObsoleteFlag(Boolean isObsolete)
  {
    _isObsolete=isObsolete;
  }

  @Override
  public boolean accept(T achievable)
  {
    if (_isObsolete==null)
    {
      return true;
    }
    boolean obsolete=achievable.isObsolete();
    return (_isObsolete.booleanValue()==obsolete);
  }
}
