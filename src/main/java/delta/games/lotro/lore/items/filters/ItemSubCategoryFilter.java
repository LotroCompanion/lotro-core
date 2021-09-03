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

  /**
   * Get the managed subcategory.
   * @return A name or <code>null</code>.
   */
  public String getSubCategory()
  {
    return _subCategory;
  }

  /**
   * Set the subcategory to use.
   * @param subCategory A name or <code>null</code>.
   */
  public void setSubCategory(String subCategory)
  {
    _subCategory=subCategory;
  }

  @Override
  public boolean accept(Item item)
  {
    if (item==null)
    {
      return false;
    }
    String subCategory=item.getSubCategory();
    return ((_subCategory==null) || (subCategory.equals(_subCategory)));
  }
}
