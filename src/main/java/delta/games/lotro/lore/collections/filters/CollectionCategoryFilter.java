package delta.games.lotro.lore.collections.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.common.enums.CollectionCategory;
import delta.games.lotro.lore.collections.CollectionDescription;

/**
 * Filter for collections of a given category.
 * @author DAM
 */
public class CollectionCategoryFilter implements Filter<CollectionDescription>
{
  private CollectionCategory _category;

  /**
   * Constructor.
   * @param category Class to select (may be <code>null</code>).
   */
  public CollectionCategoryFilter(CollectionCategory category)
  {
    _category=category;
  }

  /**
   * Get the category to use.
   * @return A category or <code>null</code>.
   */
  public CollectionCategory getCategory()
  {
    return _category;
  }

  /**
   * Set the category to select.
   * @param category Category to use, may be <code>null</code>.
   */
  public void setCategory(CollectionCategory category)
  {
    _category=category;
  }

  @Override
  public boolean accept(CollectionDescription collection)
  {
    if (_category==null)
    {
      return true;
    }
    return collection.getCategory()==_category;
  }
}
