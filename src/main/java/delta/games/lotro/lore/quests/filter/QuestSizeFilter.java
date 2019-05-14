package delta.games.lotro.lore.quests.filter;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.common.Size;
import delta.games.lotro.lore.quests.QuestDescription;

/**
 * Filter for quests of a given size.
 * @author DAM
 */
public class QuestSizeFilter implements Filter<QuestDescription>
{
  private Size _questSize;

  /**
   * Constructor.
   * @param questSize Quest size to select (may be <code>null</code>).
   */
  public QuestSizeFilter(Size questSize)
  {
    _questSize=questSize;
  }

  /**
   * Get the quest size to use.
   * @return A quest size or <code>null</code>.
   */
  public Size getQuestSize()
  {
    return _questSize;
  }

  /**
   * Set the quest size to select.
   * @param questSize Quest size to use, may be <code>null</code>.
   */
  public void setQuestSize(Size questSize)
  {
    _questSize=questSize;
  }

  @Override
  public boolean accept(QuestDescription quest)
  {
    if (_questSize==null)
    {
      return true;
    }
    Size questSize=quest.getSize();
    return (_questSize==questSize);
  }
}
