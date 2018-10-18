package delta.games.lotro.lore.items.filters;

import delta.games.lotro.lore.items.Item;

/**
 * Filter for item sub category.
 * @author DAM
 */
public class ItemSubCategoryFilter implements ItemFilter
{
  private String _subCategory;

  /**
   * Constructor.
   * @param subCategory Subcategory to select.
   */
  public ItemSubCategoryFilter(String subCategory)
  {
    _subCategory=subCategory;
  }

  public boolean accept(Item item)
  {
    if (item==null)
    {
      return false;
    }
    String subCategory=item.getSubCategory();
    if (subCategory!=null)
    {
      return subCategory.equals(_subCategory);
    }
    return false;
  }
}
