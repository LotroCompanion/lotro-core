package delta.games.lotro.lore.crafting.recipes.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.lore.crafting.Profession;
import delta.games.lotro.lore.crafting.recipes.Recipe;

/**
 * Filter for recipes of a given profession.
 * @author DAM
 */
public class RecipeProfessionFilter implements Filter<Recipe>
{
  private Profession _profession;

  /**
   * Constructor.
   * @param profession Profession to select (may be <code>null</code>).
   */
  public RecipeProfessionFilter(Profession profession)
  {
    _profession=profession;
  }

  /**
   * Get the profession to use.
   * @return A profession or <code>null</code>.
   */
  public Profession getProfession()
  {
    return _profession;
  }

  /**
   * Set the profession to select.
   * @param profession Profession to use, may be <code>null</code>.
   */
  public void setProfession(Profession profession)
  {
    _profession=profession;
  }

  public boolean accept(Recipe recipe)
  {
    if (_profession==null)
    {
      return true;
    }
    Profession profession=recipe.getProfession();
    if (_profession.equals(profession))
    {
      return true;
    }
    return false;
  }
}
