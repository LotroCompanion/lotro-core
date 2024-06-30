package delta.games.lotro.tools.crafting;

import delta.games.lotro.lore.crafting.recipes.Recipe;
import delta.games.lotro.lore.items.Item;

/**
 * Stores a couplpe recipe/item.
 * @author DAM
 */
public class RecipeSharingElement
{
  private Item _item;
  private Recipe _recipe;

  /**
   * Constructor.
   * @param item Item.
   * @param recipe Recipe.
   */
  public RecipeSharingElement(Item item, Recipe recipe)
  {
    _item=item;
    _recipe=recipe;
  }

  /**
   * Get the item.
   * @return the item.
   */
  public Item getItem()
  {
    return _item;
  }

  /**
   * Get the recipe.
   * @return the recipe.
   */
  public Recipe getRecipe()
  {
    return _recipe;
  }
}
