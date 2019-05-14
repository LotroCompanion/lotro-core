package delta.games.lotro.lore.quests.filter;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.lore.quests.QuestDescription;

/**
 * Filter for quests that are auto-bestowed, or that are NOT auto-bestowed.
 * @author DAM
 */
public class AutoBestowedQuestFilter implements Filter<QuestDescription>
{
  private Boolean _isAutoBestowed;

  /**
   * Constructor.
   * @param isAutoBestowed Indicates if this filter shall select quests
   * that are auto-bestowed, or not (<code>null</code> means no filter).
   */
  public AutoBestowedQuestFilter(Boolean isAutoBestowed)
  {
    _isAutoBestowed=isAutoBestowed;
  }

  /**
   * Get the value of the 'is auto-bestowed' flag.
   * @return A boolean value or <code>null</code>.
   */
  public Boolean getIsAutoBestowedFlag()
  {
    return _isAutoBestowed;
  }

  /**
   * Set the value of the 'is auto-bestowed' flag.
   * @param isAutoBestowed Flag to set, may be <code>null</code>.
   */
  public void setIsAutoBestowedFlag(Boolean isAutoBestowed)
  {
    _isAutoBestowed=isAutoBestowed;
  }

  @Override
  public boolean accept(QuestDescription quest)
  {
    if (_isAutoBestowed==null)
    {
      return true;
    }
    boolean autoBestowed=quest.isAutoBestowed();
    return (_isAutoBestowed.booleanValue()==autoBestowed);
  }
}
