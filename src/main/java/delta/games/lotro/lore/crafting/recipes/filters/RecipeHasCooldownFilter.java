package delta.games.lotro.lore.crafting.recipes.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.lore.crafting.recipes.Recipe;

/**
 * Filter recipes that have a cooldown or not.
 * @author DAM
 */
public class RecipeHasCooldownFilter implements Filter<Recipe>
{
  private Boolean _cooldown;

  /**
   * Constructor.
   * @param cooldown Indicates if this filter shall select recipes
   * which have a cooldown or not (<code>null</code> means no filter).
   */
  public RecipeHasCooldownFilter(Boolean cooldown)
  {
    _cooldown=cooldown;
  }

  /**
   * Get the value of the 'cooldown' flag.
   * @return A boolean value or <code>null</code>.
   */
  public Boolean getCooldownFlag()
  {
    return _cooldown;
  }

  /**
   * Set the value of the 'cooldown' flag.
   * @param cooldown Flag to set, may be <code>null</code>.
   */
  public void setCooldownFlag(Boolean cooldown)
  {
    _cooldown=cooldown;
  }

  @Override
  public boolean accept(Recipe recipe)
  {
    if (_cooldown==null)
    {
      return true;
    }
    int cooldown=recipe.getCooldown();
    return (_cooldown.booleanValue()?(cooldown>=0):(cooldown<0));
  }
}
