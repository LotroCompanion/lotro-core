package delta.games.lotro.lore.deeds.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.common.enums.DeedCategory;
import delta.games.lotro.lore.deeds.DeedDescription;

/**
 * Filter for deed of a given category.
 * @author DAM
 */
public class DeedCategoryFilter implements Filter<DeedDescription>
{
  private DeedCategory _category;

  /**
   * Constructor.
   * @param category Category to select (may be <code>null</code>).
   */
  public DeedCategoryFilter(DeedCategory category)
  {
    _category=category;
  }

  /**
   * Get the category to use.
   * @return A category or <code>null</code>.
   */
  public DeedCategory getDeedCategory()
  {
    return _category;
  }

  /**
   * Set the category to select.
   * @param category Category to use, may be <code>null</code>.
   */
  public void setDeedCategory(DeedCategory category)
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
    DeedCategory deedCategory=deed.getCategory();
    return (_category==deedCategory);
  }
}
