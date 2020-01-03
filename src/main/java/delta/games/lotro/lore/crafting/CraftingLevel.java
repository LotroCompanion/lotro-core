package delta.games.lotro.lore.crafting;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a level in a crafting profession.
 * @author DAM
 */
public final class CraftingLevel
{
  private Profession _profession;
  private int _tier;
  private String _name;
  /**
   * Icon name: iconID-backgroundIconID.
   */
  private String _icon;
  private CraftingLevelTier _proficiency;
  private CraftingLevelTier _mastery;
  private List<Integer> _recipes;

  /**
   * Constructor.
   * @param profession Profession.
   * @param tier Tier.
   */
  public CraftingLevel(Profession profession, int tier)
  {
    _profession=profession;
    _tier=tier;
    _name="";
    _icon="";
    _proficiency=new CraftingLevelTier();
    _mastery=new CraftingLevelTier();
    _recipes=new ArrayList<Integer>();
  }

  /**
   * Get the associated profession.
   * @return the associated profession.
   */
  public Profession getProfession()
  {
    return _profession;
  }

  /**
   * Get the associated tier value.
   * @return A tier number.
   */
  public int getTier()
  {
    return _tier;
  }

  /**
   * Get the name of this level.
   * @return a displayable label.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Set the name of this tier.
   * @param name Name to set.
   */
  public void setName(String name)
  {
    _name=name;
  }

  /**
   * Get the icon of this level.
   * @return an icon path.
   */
  public String getIcon()
  {
    return _icon;
  }

  /**
   * Set the icon of this tier.
   * @param icon Icon to set.
   */
  public void setIcon(String icon)
  {
    _icon=icon;
  }

  /**
   * Get the proficiency tier.
   * @return the proficiency tier.
   */
  public CraftingLevelTier getProficiency()
  {
    return _proficiency;
  }

  /**
   * Get the mastery tier.
   * @return the mastery tier.
   */
  public CraftingLevelTier getMastery()
  {
    return _mastery;
  }

  /**
   * Add a recipe.
   * @param recipeId Recipe identifier.
   */
  public void addRecipe(int recipeId)
  {
    _recipes.add(Integer.valueOf(recipeId));
  }

  /**
   * Indicates if the given recipe is auto-bestowed or not.
   * @param recipeId Identifier of the recipe to use.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isAutobestowed(int recipeId)
  {
    return _recipes.contains(Integer.valueOf(recipeId));
  }

  /**
   * Get the identifiers of the automatic recipes for the level.
   * @return an array of recipe identifiers.
   */
  public int[] getRecipes()
  {
    int nbRecipes=_recipes.size();
    int[] ret=new int[nbRecipes];
    for(int i=0;i<nbRecipes;i++)
    {
      ret[i]=_recipes.get(i).intValue();
    }
    return ret;
  }

  @Override
  public String toString()
  {
    return _proficiency.getLabel();
  }
}
