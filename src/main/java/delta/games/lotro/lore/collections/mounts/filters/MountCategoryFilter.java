package delta.games.lotro.lore.collections.mounts.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.common.enums.SkillCharacteristicSubCategory;
import delta.games.lotro.lore.collections.mounts.MountDescription;

/**
 * Filter for mounts of a given category.
 * @author DAM
 */
public class MountCategoryFilter implements Filter<MountDescription>
{
  private SkillCharacteristicSubCategory _category;

  /**
   * Constructor.
   * @param category Category to select (may be <code>null</code>).
   */
  public MountCategoryFilter(SkillCharacteristicSubCategory category)
  {
    _category=category;
  }

  /**
   * Get the category to use.
   * @return A category or <code>null</code>.
   */
  public SkillCharacteristicSubCategory getCategory()
  {
    return _category;
  }

  /**
   * Set the category to select.
   * @param category Category to use, may be <code>null</code>.
   */
  public void setCategory(SkillCharacteristicSubCategory category)
  {
    _category=category;
  }

  public boolean accept(MountDescription mount)
  {
    if (_category==null)
    {
      return true;
    }
    SkillCharacteristicSubCategory category=mount.getMountCategory();
    return (_category==category);
  }
}
