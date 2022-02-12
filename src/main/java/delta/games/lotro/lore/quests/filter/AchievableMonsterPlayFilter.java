package delta.games.lotro.lore.quests.filter;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.lore.quests.Achievable;

/**
 * Filter for achievables that are 'monster play', or that are NOT 'monster play'.
 * @param <T> Type of managed achievables.
 * @author DAM
 */
public class AchievableMonsterPlayFilter<T extends Achievable> implements Filter<T>
{
  private Boolean _isMonsterPlay;

  /**
   * Constructor.
   * @param isMonsterPlay Indicates if this filter shall select achievables
   * that are 'monster play', or not (<code>null</code> means no filter).
   */
  public AchievableMonsterPlayFilter(Boolean isMonsterPlay)
  {
    _isMonsterPlay=isMonsterPlay;
  }

  /**
   * Get the value of the 'is monster play' flag.
   * @return A boolean value or <code>null</code>.
   */
  public Boolean getIsMonsterPlayFlag()
  {
    return _isMonsterPlay;
  }

  /**
   * Set the value of the 'is monster play' flag.
   * @param isMonsterPlay Flag to set, may be <code>null</code>.
   */
  public void setIsMonsterPlayFlag(Boolean isMonsterPlay)
  {
    _isMonsterPlay=isMonsterPlay;
  }

  @Override
  public boolean accept(T achievable)
  {
    if (_isMonsterPlay==null)
    {
      return true;
    }
    boolean monsterPlay=achievable.isMonsterPlay();
    return (_isMonsterPlay.booleanValue()==monsterPlay);
  }
}
