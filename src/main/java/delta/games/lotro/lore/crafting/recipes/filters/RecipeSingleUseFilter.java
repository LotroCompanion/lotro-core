package delta.games.lotro.lore.crafting.recipes.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.lore.crafting.recipes.Recipe;

/**
 * Filter recipes that are single use or not.
 * @author DAM
 */
public class RecipeSingleUseFilter implements Filter<Recipe>
{
  private Boolean _singleUse;

  /**
   * Constructor.
   * @param singleUse Indicates if this filter shall select recipes
   * which are single use or not (<code>null</code> means no filter).
   */
  public RecipeSingleUseFilter(Boolean singleUse)
  {
    _singleUse=singleUse;
  }

  /**
   * Get the value of the 'single use' flag.
   * @return A boolean value or <code>null</code>.
   */
  public Boolean getSingleUseFlag()
  {
    return _singleUse;
  }

  /**
   * Set the value of the 'single use' flag.
   * @param singleUse Flag to set, may be <code>null</code>.
   */
  public void setSingleUseFlag(Boolean singleUse)
  {
    _singleUse=singleUse;
  }

  @Override
  public boolean accept(Recipe recipe)
  {
    if (_singleUse==null)
    {
      return true;
    }
    boolean oneTimeUse=recipe.isOneTimeUse();
    return (_singleUse.booleanValue()==oneTimeUse);
  }
}
