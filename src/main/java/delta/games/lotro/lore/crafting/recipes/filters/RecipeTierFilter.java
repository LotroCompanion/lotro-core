package delta.games.lotro.lore.crafting.recipes.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.lore.crafting.recipes.Recipe;

/**
 * Filter for recipes of a given tier.
 * @author DAM
 */
public class RecipeTierFilter implements Filter<Recipe>
{
  private Integer _tier;

  /**
   * Constructor.
   * @param tier Tier to select (may be <code>null</code>).
   */
  public RecipeTierFilter(Integer tier)
  {
    _tier=tier;
  }

  /**
   * Get the tier to use.
   * @return A tier or <code>null</code>.
   */
  public Integer getTier()
  {
    return _tier;
  }

  /**
   * Set the tier to select.
   * @param tier Tier to use, may be <code>null</code>.
   */
  public void setTier(Integer tier)
  {
    _tier=tier;
  }

  public boolean accept(Recipe recipe)
  {
    if (_tier==null)
    {
      return true;
    }
    int tier=recipe.getTier();
    if (_tier.intValue()==tier)
    {
      return true;
    }
    return false;
  }
}
