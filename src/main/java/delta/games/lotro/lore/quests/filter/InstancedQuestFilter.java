package delta.games.lotro.lore.quests.filter;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.lore.quests.QuestDescription;

/**
 * Filter for quests that are instanced, or that are NOT instanced.
 * @author DAM
 */
public class InstancedQuestFilter implements Filter<QuestDescription>
{
  private Boolean _isInstanced;

  /**
   * Constructor.
   * @param isInstanced Indicates if this filter shall select quests
   * that are instanced, or not (<code>null</code> means no filter).
   */
  public InstancedQuestFilter(Boolean isInstanced)
  {
    _isInstanced=isInstanced;
  }

  /**
   * Get the value of the 'is instanced' flag.
   * @return A boolean value or <code>null</code>.
   */
  public Boolean getIsInstancedFlag()
  {
    return _isInstanced;
  }

  /**
   * Set the value of the 'is instanced' flag.
   * @param isInstanced Flag to set, may be <code>null</code>.
   */
  public void setIsInstancedFlag(Boolean isInstanced)
  {
    _isInstanced=isInstanced;
  }

  @Override
  public boolean accept(QuestDescription quest)
  {
    if (_isInstanced==null)
    {
      return true;
    }
    boolean instanced=quest.isInstanced();
    return (_isInstanced.booleanValue()==instanced);
  }
}
