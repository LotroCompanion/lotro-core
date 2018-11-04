package delta.games.lotro.lore.crafting.recipes;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.text.EndOfLine;

/**
 * Version of a recipe.
 * @author DAM
 */
public class RecipeVersion
{
  private List<Ingredient> _ingredients;
  private CraftingResult _regular;
  private CraftingResult _critical;
  private Integer _baseCriticalChance;

  /**
   * Constructor.
   */
  public RecipeVersion()
  {
    _ingredients=new ArrayList<Ingredient>();
  }

  /**
   * Get the ingredients for this recipes.
   * @return a list of ingredients.
   */
  public List<Ingredient> getIngredients()
  {
    return _ingredients;
  }

  /**
   * Set the ingredients for this recipe.
   * @param ingredients the ingredients to set.
   */
  public void setIngredients(List<Ingredient> ingredients)
  {
    _ingredients.clear();
    if (ingredients!=null)
    {
      _ingredients.addAll(ingredients);
    }
  }

  /**
   * Get the regular result of this recipe version.
   * @return A result.
   */
  public CraftingResult getRegular()
  {
    return _regular;
  }

  /**
   * Set the regular result for this recipe version.
   * @param regular Result to set.
   */
  public void setRegular(CraftingResult regular)
  {
    _regular=regular;
  }

  /**
   * Get the critical result of this recipe version.
   * @return A result or <code>null</code> if there's none.
   */
  public CraftingResult getCritical()
  {
    return _critical;
  }

  /**
   * Set the critical result for this recipe version.
   * @param critical Result to set (may be <code>null</code>).
   */
  public void setCritical(CraftingResult critical)
  {
    _critical=critical;
  }

  /**
   * Get the base percentage for critical success.
   * @return a percentage value (1..100) or <code>null</code>.
   */
  public Integer getBaseCriticalChance()
  {
    return _baseCriticalChance;
  }

  /**
   * Set the base percentage for critical success.
   * @param baseCriticalChance a percentage value (1..100) or <code>null</code>.
   */
  public void setBaseCriticalChance(Integer baseCriticalChance)
  {
    _baseCriticalChance=baseCriticalChance;
  }

  /**
   * Clone data.
   * @return a cloned instance.
   */
  public RecipeVersion cloneData()
  {
    RecipeVersion ret=new RecipeVersion();
    ret.setBaseCriticalChance(_baseCriticalChance);
    for(Ingredient ingredient : _ingredients)
    {
      Ingredient newIngredient=ingredient.cloneData();
      ret._ingredients.add(newIngredient);
    }
    if (_regular!=null)
    {
      ret._regular=_regular.cloneData();
    }
    if (_critical!=null)
    {
      ret._critical=_critical.cloneData();
    }
    return ret;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Ingredients:");
    sb.append(EndOfLine.NATIVE_EOL);
    for(Ingredient ingredient : _ingredients)
    {
      sb.append('\t').append(ingredient);
      sb.append(EndOfLine.NATIVE_EOL);
    }
    if (_regular!=null)
    {
      sb.append("Regular: ").append(_regular);
    }
    if (_critical!=null)
    {
      if (sb.length()>0) sb.append(" / ");
      sb.append("Critical: ").append(_critical);
      if (_baseCriticalChance!=null)
      {
        sb.append(" (").append(_baseCriticalChance).append("%)");
      }
    }
    return sb.toString();
  }
}

