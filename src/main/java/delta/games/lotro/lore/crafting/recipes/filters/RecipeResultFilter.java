package delta.games.lotro.lore.crafting.recipes.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.lore.crafting.recipes.CraftingResult;
import delta.games.lotro.lore.crafting.recipes.Recipe;
import delta.games.lotro.lore.crafting.recipes.RecipeVersion;
import delta.games.lotro.lore.items.Item;

/**
 * Filter for recipes using the given item as result.
 * @author DAM
 */
public class RecipeResultFilter implements Filter<Recipe>
{
  private Integer _itemId;

  /**
   * Constructor.
   * @param itemId ID of item to select (may be <code>null</code>).
   */
  public RecipeResultFilter(Integer itemId)
  {
    _itemId=itemId;
  }

  /**
   * Get the item ID to use.
   * @return An item ID or <code>null</code>.
   */
  public Integer getItemId()
  {
    return _itemId;
  }

  /**
   * Set the item ID to select.
   * @param itemId Item ID to use, may be <code>null</code>.
   */
  public void setItemId(Integer itemId)
  {
    _itemId=itemId;
  }

  @Override
  public boolean accept(Recipe recipe)
  {
    if (_itemId==null)
    {
      return true;
    }
    for(RecipeVersion version : recipe.getVersions())
    {
      // Regular
      CraftingResult regular=version.getRegular();
      if (regular!=null)
      {
        Item regularItem=regular.getItem();
        if ((regularItem!=null) && (accept(regularItem)))
        {
          return true;
        }
      }
      // Critical
      CraftingResult critical=version.getCritical();
      if (critical!=null)
      {
        Item criticalItem=critical.getItem();
        if ((criticalItem!=null) && (accept(criticalItem)))
        {
          return true;
        }
      }
    }
    return false;
  }

  
  private boolean accept(Item item)
  {
    return (_itemId.intValue()==item.getIdentifier());
  }
}
