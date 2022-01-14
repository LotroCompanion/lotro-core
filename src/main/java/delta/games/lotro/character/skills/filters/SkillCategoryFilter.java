package delta.games.lotro.character.skills.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.common.enums.SkillCategory;

/**
 * Filter for skills, using their category.
 * @author DAM
 */
public class SkillCategoryFilter implements Filter<SkillDescription>
{
  private SkillCategory _category;

  /**
   * Set the category code to select.
   * @param categoryCode A category code.
   */
  public void setCategory(int categoryCode)
  {
    LotroEnum<SkillCategory> categoryEnum=LotroEnumsRegistry.getInstance().get(SkillCategory.class);
    _category=categoryEnum.getEntry(categoryCode);
  }

  @Override
  public boolean accept(SkillDescription item)
  {
    return item.getCategory()==_category;
  }
}
