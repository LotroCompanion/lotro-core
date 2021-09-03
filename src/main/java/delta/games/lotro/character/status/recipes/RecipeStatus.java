package delta.games.lotro.character.status.recipes;

import delta.games.lotro.lore.crafting.recipes.Recipe;

/**
 * Status of a single recipe.
 * @author DAM
 */
public class RecipeStatus
{
  private Recipe _recipe;
  private RecipeState _state;

  /**
   * Constructor.
   * @param recipe Associated recipe.
   * @param state Recipe state.
   */
  public RecipeStatus(Recipe recipe, RecipeState state)
  {
    _recipe=recipe;
    _state=state;
  }

  /**
   * Get the associated recipe.
   * @return a recipe.
   */
  public Recipe getRecipe()
  {
    return _recipe;
  }

  /**
   * Get the recipe state.
   * @return a state.
   */
  public RecipeState getState()
  {
    return _state;
  }
}
