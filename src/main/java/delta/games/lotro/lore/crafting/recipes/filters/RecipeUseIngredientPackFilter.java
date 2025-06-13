package delta.games.lotro.lore.crafting.recipes.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.lore.crafting.recipes.Recipe;

/**
 * Filter recipes that can use ingredient packs or not.
 * @author DAM
 */
public class RecipeUseIngredientPackFilter implements Filter<Recipe>
{
  private Boolean _useIngredientPack;

  /**
   * Constructor.
   * @param useIngredientPack Indicates if this filter shall select recipes
   * which can use ingredient packs or not (<code>null</code> means no filter).
   */
  public RecipeUseIngredientPackFilter(Boolean useIngredientPack)
  {
    _useIngredientPack=useIngredientPack;
  }

  /**
   * Get the value of the 'use ingredient pack' flag.
   * @return A boolean value or <code>null</code>.
   */
  public Boolean getUseIngredientPackFlag()
  {
    return _useIngredientPack;
  }

  /**
   * Set the value of the 'use ingredient pack' flag.
   * @param useIngredientPack Flag to set, may be <code>null</code>.
   */
  public void setUseIngredientPackFlag(Boolean useIngredientPack)
  {
    _useIngredientPack=useIngredientPack;
  }

  @Override
  public boolean accept(Recipe recipe)
  {
    if (_useIngredientPack==null)
    {
      return true;
    }
    boolean useIngredientPack=(recipe.getIngredientPack()!=null);
    return _useIngredientPack.booleanValue()==useIngredientPack;
  }
}
