package delta.games.lotro.lore.quests.filter;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.lore.quests.Achievable;

/**
 * Filter for achievables that are obsolete/hidden, or that are NOT obsolete/hidden.
 * @param <T> Type of managed achievables.
 * @author DAM
 */
public class HiddenAchievableFilter<T extends Achievable> implements Filter<T>
{
  private Boolean _isHidden;

  /**
   * Constructor.
   * @param isHidden Indicates if this filter shall select quests
   * that are obsolete/hidden, or not (<code>null</code> means no filter).
   */
  public HiddenAchievableFilter(Boolean isHidden)
  {
    _isHidden=isHidden;
  }

  /**
   * Get the value of the 'is hidden' flag.
   * @return A boolean value or <code>null</code>.
   */
  public Boolean getIsHiddenFlag()
  {
    return _isHidden;
  }

  /**
   * Set the value of the 'is hidden' flag.
   * @param isHidden Flag to set, may be <code>null</code>.
   */
  public void setIsHiddenFlag(Boolean isHidden)
  {
    _isHidden=isHidden;
  }

  @Override
  public boolean accept(T achievable)
  {
    if (_isHidden==null)
    {
      return true;
    }
    boolean hidden=achievable.isHidden();
    return (_isHidden.booleanValue()==hidden);
  }
}
