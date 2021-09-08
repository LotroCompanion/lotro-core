package delta.games.lotro.lore.crafting.recipes.filters;

import java.util.List;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.lore.crafting.recipes.Ingredient;
import delta.games.lotro.lore.crafting.recipes.Recipe;
import delta.games.lotro.lore.crafting.recipes.RecipeVersion;
import delta.games.lotro.lore.items.Item;

/**
 * Filter for recipes using the given item as ingredient.
 * @author DAM
 */
public class RecipeIngredientFilter implements Filter<Recipe>
{
  private Integer _itemId;

  /**
   * Constructor.
   * @param itemId ID of item to select (may be <code>null</code>).
   */
  public RecipeIngredientFilter(Integer itemId)
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
      List<Ingredient> ingredients=version.getIngredients();
      for(Ingredient ingredient : ingredients)
      {
        Item item=ingredient.getItem();
        if (accept(item))
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
