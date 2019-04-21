package delta.games.lotro.lore.quests.filter;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.lore.quests.QuestDescription;

/**
 * Filter for quests of a given category.
 * @author DAM
 */
public class QuestCategoryFilter implements Filter<QuestDescription>
{
  private String _category;

  /**
   * Constructor.
   * @param category Category to select (may be <code>null</code>).
   */
  public QuestCategoryFilter(String category)
  {
    _category=category;
  }

  /**
   * Get the category to use.
   * @return A category or <code>null</code>.
   */
  public String getQuestCategory()
  {
    return _category;
  }

  /**
   * Set the category to select.
   * @param category Category to use, may be <code>null</code>.
   */
  public void setQuestCategory(String category)
  {
    _category=category;
  }

  public boolean accept(QuestDescription quest)
  {
    if (_category==null)
    {
      return true;
    }
    String questCategory=quest.getCategory();
    if (_category.equals(questCategory))
    {
      return true;
    }
    return false;
  }
}
