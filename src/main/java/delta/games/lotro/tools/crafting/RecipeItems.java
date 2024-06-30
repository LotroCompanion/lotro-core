package delta.games.lotro.tools.crafting;

import java.util.HashMap;
import java.util.Map;

import delta.games.lotro.lore.crafting.recipes.Recipe;
import delta.games.lotro.lore.crafting.recipes.RecipesManager;
import delta.games.lotro.lore.items.Item;

/**
 * Tool to get recipes from items.
 * @author DAM
 */
public class RecipeItems
{
  private Map<Integer,Recipe> _recipesMap;

  /**
   * Constructor.
   */
  public RecipeItems()
  {
    _recipesMap=new HashMap<Integer,Recipe>();
    load();
  }

  private void load()
  {
    for(Recipe recipe : RecipesManager.getInstance().getAll())
    {
      Item scroll=recipe.getRecipeScroll();
      if (scroll!=null)
      {
        Integer key=Integer.valueOf(scroll.getIdentifier());
        _recipesMap.put(key,recipe);
      }
    }
  }

  /**
   * Get the recipe from the given item.
   * @param item Item to use.
   * @return A recipe or <code>null</code> if the item bears no recipe.
   */
  public Recipe getRecipeForItem(Item item)
  {
    Integer key=Integer.valueOf(item.getIdentifier());
    return _recipesMap.get(key);
  }
}
