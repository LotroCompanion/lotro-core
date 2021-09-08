package delta.games.lotro.lore.crafting.recipes.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.lore.crafting.recipes.Recipe;

/**
 * Filter recipes that are guild or not.
 * @author DAM
 */
public class RecipeIsGuildFilter implements Filter<Recipe>
{
  private Boolean _guild;

  /**
   * Constructor.
   * @param guild Indicates if this filter shall select recipes
   * which are single use or not (<code>null</code> means no filter).
   */
  public RecipeIsGuildFilter(Boolean guild)
  {
    _guild=guild;
  }

  /**
   * Get the value of the 'guild' flag.
   * @return A boolean value or <code>null</code>.
   */
  public Boolean getGuildFlag()
  {
    return _guild;
  }

  /**
   * Set the value of the 'guild' flag.
   * @param guild Flag to set, may be <code>null</code>.
   */
  public void setGuildFlag(Boolean guild)
  {
    _guild=guild;
  }

  @Override
  public boolean accept(Recipe recipe)
  {
    if (_guild==null)
    {
      return true;
    }
    boolean guildRequired=recipe.isGuildRequired();
    return (_guild.booleanValue()==guildRequired);
  }
}
