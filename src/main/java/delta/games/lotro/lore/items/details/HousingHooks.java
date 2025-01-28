package delta.games.lotro.lore.items.details;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.enums.HousingHookCategory;

/**
 * Housing hooks for a decoration item.
 * @author DAM
 */
public class HousingHooks extends ItemDetail
{
  private List<HousingHookCategory> _hookCategories;

  /**
   * Constructor.
   */
  public HousingHooks()
  {
    _hookCategories=new ArrayList<HousingHookCategory>();
  }

  /**
   * Add a hook category.
   * @param hookCategory Hook category to add.
   */
  public void addCategory(HousingHookCategory hookCategory)
  {
    _hookCategories.add(hookCategory);
  }

  /**
   * Get all hook categories.
   * @return A list of hook categories.
   */
  public List<HousingHookCategory> getHookCategories()
  {
    return _hookCategories;
  }

  /**
   * Get a displayable label for this info.
   * @return A displayable label.
   */
  public String getLabel()
  {
    StringBuilder sb=new StringBuilder();
    int index=0;
    for(HousingHookCategory hookCategory : _hookCategories)
    {
      if (index>0)
      {
        sb.append(", ");
      }
      sb.append(hookCategory.getLabel());
      index++;
    }
    String label=sb.toString();
    return label;
  }

  @Override
  public String toString()
  {
    return getLabel();
  }
}
