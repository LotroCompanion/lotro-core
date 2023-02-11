package delta.games.lotro.lore.quests.filter;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.common.enums.QuestCategory;
import delta.games.lotro.lore.quests.QuestDescription;

/**
 * Filter for quests of a given category.
 * @author DAM
 */
public class QuestCategoryFilter implements Filter<QuestDescription>
{
  private QuestCategory _category;

  /**
   * Constructor.
   * @param category Category to select (may be <code>null</code>).
   */
  public QuestCategoryFilter(QuestCategory category)
  {
    _category=category;
  }

  /**
   * Get the category to use.
   * @return A category or <code>null</code>.
   */
  public QuestCategory getQuestCategory()
  {
    return _category;
  }

  /**
   * Set the category to select.
   * @param category Category to use, may be <code>null</code>.
   */
  public void setQuestCategory(QuestCategory category)
  {
    _category=category;
  }

  public boolean accept(QuestDescription quest)
  {
    if (_category==null)
    {
      return true;
    }
    QuestCategory questCategory=quest.getCategory();
    return (_category==questCategory);
  }
}
