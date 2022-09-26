package delta.games.lotro.lore.deeds.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.lore.deeds.DeedDescription;

/**
 * Filter for deed of a given category.
 * @author DAM
 */
public class DeedCategoryFilter implements Filter<DeedDescription>
{
  private String _category;

  /**
   * Constructor.
   * @param category Category to select (may be <code>null</code>).
   */
  public DeedCategoryFilter(String category)
  {
    _category=category;
  }

  /**
   * Get the category to use.
   * @return A category or <code>null</code>.
   */
  public String getDeedCategory()
  {
    return _category;
  }

  /**
   * Set the category to select.
   * @param category Category to use, may be <code>null</code>.
   */
  public void setDeedCategory(String category)
  {
    _category=category;
  }

  @Override
  public boolean accept(DeedDescription deed)
  {
    if (_category==null)
    {
      return true;
    }
    String deedCategory=deed.getCategory();
    if (_category.equals(deedCategory))
    {
      return true;
    }
    if ((deedCategory!=null) && (deedCategory.startsWith(_category+":")))
    {
      return true;
    }
    return false;
  }
}
