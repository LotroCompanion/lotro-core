package delta.games.lotro.lore.titles.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.lore.titles.TitleDescription;

/**
 * Filter for titles of a given category.
 * @author DAM
 */
public class TitleCategoryFilter implements Filter<TitleDescription>
{
  private String _category;

  /**
   * Constructor.
   * @param category Category to select (may be <code>null</code>).
   */
  public TitleCategoryFilter(String category)
  {
    _category=category;
  }

  /**
   * Get the category to use.
   * @return A category or <code>null</code>.
   */
  public String getCategory()
  {
    return _category;
  }

  /**
   * Set the category to select.
   * @param category Category to use, may be <code>null</code>.
   */
  public void setCategory(String category)
  {
    _category=category;
  }

  public boolean accept(TitleDescription title)
  {
    if (_category==null)
    {
      return true;
    }
    String category=title.getCategory();
    if (_category.equals(category))
    {
      return true;
    }
    return false;
  }
}
