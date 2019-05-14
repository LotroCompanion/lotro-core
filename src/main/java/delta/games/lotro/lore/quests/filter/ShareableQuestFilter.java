package delta.games.lotro.lore.quests.filter;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.lore.quests.QuestDescription;

/**
 * Filter for quests that are shareable, or that are NOT shareable.
 * @author DAM
 */
public class ShareableQuestFilter implements Filter<QuestDescription>
{
  private Boolean _isShareable;

  /**
   * Constructor.
   * @param isShareable Indicates if this filter shall select quests
   * that are shareable, or not (<code>null</code> means no filter).
   */
  public ShareableQuestFilter(Boolean isShareable)
  {
    _isShareable=isShareable;
  }

  /**
   * Get the value of the 'is shareable' flag.
   * @return A boolean value or <code>null</code>.
   */
  public Boolean getIsShareableFlag()
  {
    return _isShareable;
  }

  /**
   * Set the value of the 'is shareable' flag.
   * @param isShareable Flag to set, may be <code>null</code>.
   */
  public void setIsShareableFlag(Boolean isShareable)
  {
    _isShareable=isShareable;
  }

  @Override
  public boolean accept(QuestDescription quest)
  {
    if (_isShareable==null)
    {
      return true;
    }
    boolean shareable=quest.isShareable();
    return (_isShareable.booleanValue()==shareable);
  }
}
