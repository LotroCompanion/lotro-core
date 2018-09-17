package delta.games.lotro.lore.crafting.recipes;

/**
 * Version of a recipe.
 * @author DAM
 */
public class RecipeVersion
{
  private CraftingResult _regular;
  private CraftingResult _critical;
  private Integer _baseCriticalChance;

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

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
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

