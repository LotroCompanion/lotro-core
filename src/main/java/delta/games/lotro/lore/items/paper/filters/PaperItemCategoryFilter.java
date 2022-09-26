package delta.games.lotro.lore.items.paper.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.lore.items.paper.PaperItem;

/**
 * Filter for paper items, using their category.
 * @author DAM
 */
public class PaperItemCategoryFilter implements Filter<PaperItem>
{
  private String _category;

  /**
   * Get the category to select.
   * @return A category or <code>null</code>.
   */
  public String getCategory()
  {
    return _category;
  }

  /**
   * Set the category to select.
   * @param category A category or <code>null</code>.
   */
  public void setCategory(String category)
  {
    _category=category;
  }

  @Override
  public boolean accept(PaperItem item)
  {
    if (_category==null)
    {
      return true;
    }
    String deedCategory=item.getCategory();
    if (_category.equals(deedCategory))
    {
      return true;
    }
    return false;
  }
}
