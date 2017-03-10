package delta.games.lotro.lore.recipes;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.text.EndOfLine;

/**
 * Recipe description.
 * @author DAM
 */
public class Recipe
{
  private int _identifier;
  private String _key;
  private String _name;
  private String _profession;
  private int _tier;
  private boolean _oneTimeUse;
  private List<Ingredient> _ingredients;
  private List<RecipeVersion> _versions;
  private ItemReference _recipeScroll;

  /**
   * Constructor.
   */
  public Recipe()
  {
    _identifier=0;
    _key=null;
    _name=null;
    _profession=null;
    _tier=1;
    _oneTimeUse=false;
    _ingredients=new ArrayList<Ingredient>();
    _versions=new ArrayList<RecipeVersion>();
    _recipeScroll=null;
  }

  /**
   * Get the identifier of this recipe.
   * @return a recipe identifier.
   */
  public int getIdentifier()
  {
    return _identifier;
  }

  /**
   * Set the identifier of this recipe.
   * @param identifier the identifier to set.
   */
  public void setIdentifier(int identifier)
  {
    _identifier=identifier;
  }

  /**
   * Get the key of this recipe.
   * @return a recipe key.
   */
  public String getKey()
  {
    return _key;
  }

  /**
   * Set the key of this recipe.
   * @param key the key to set.
   */
  public void setKey(String key)
  {
    _key=key;
  }

  /**
   * Get the name of this recipe.
   * @return a recipe name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Set the name of this recipe.
   * @param name the name to set.
   */
  public void setName(String name)
  {
    _name=name;
  }

  /**
   * Get the profession of this recipe.
   * @return a profession identifier.
   */
  public String getProfession()
  {
    return _profession;
  }

  /**
   * Set the profession of this recipe.
   * @param profession the profession to set.
   */
  public void setProfession(String profession)
  {
    _profession=profession;
  }

  /**
   * Get the recipe tier.
   * @return A tier.
   */
  public int getTier()
  {
    return _tier;
  }

  /**
   * Set the recipe tier.
   * @param tier the tier to set.
   */
  public void setTier(int tier)
  {
    _tier=tier;
  }

  /**
   * Indicates if this recipe can only be used once or not.
   * @return <code>true</code> if it is used once, <code>false</code> otherwise.
   */
  public boolean isOneTimeUse()
  {
    return _oneTimeUse;
  }

  /**
   * Set the 'one time use' flag for this recipe.
   * @param oneTimeUse the value to set.
   */
  public void setOneTimeUse(boolean oneTimeUse)
  {
    _oneTimeUse=oneTimeUse;
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
   * Get the versions of this recipe.
   * @return a list of recipe versions.
   */
  public List<RecipeVersion> getVersions()
  {
    return _versions;
  }

  /**
   * Set the version of this recipe.
   * @param versions the versions to set.
   */
  public void setVersions(List<RecipeVersion> versions)
  {
    _versions.clear();
    if (versions!=null)
    {
      _versions.addAll(versions);
    }
  }

  /**
   * Get the reference to the recipe scroll item.
   * @return an item reference or <code>null</code>.
   */
  public ItemReference getRecipeScroll()
  {
    return _recipeScroll;
  }

  /**
   * Set the recipe scroll item reference.
   * @param recipeScroll the reference to set.
   */
  public void setRecipeScroll(ItemReference recipeScroll)
  {
    _recipeScroll=recipeScroll;
  }

  /**
   * Dump the contents of this item as a string.
   * @return A readable string.
   */
  public String dump()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Name: ").append(_name);
    if (_identifier!=0)
    {
      sb.append(" (id=");
      sb.append(_identifier);
      sb.append(')');
    }
    if (_key!=null)
    {
      sb.append(" (key=");
      sb.append(_key);
      sb.append(')');
    }
    if (_profession!=null)
    {
      sb.append(" (profession=");
      sb.append(_profession);
      sb.append(')');
    }
    if (_tier!=0)
    {
      sb.append(" (tier=");
      sb.append(_tier);
      sb.append(')');
    }
    if (_oneTimeUse)
    {
      sb.append(" (one time use)");
    }
    if (_recipeScroll!=null)
    {
      sb.append(" (scroll=");
      sb.append(_recipeScroll);
      sb.append(')');
    }
    sb.append(EndOfLine.NATIVE_EOL);
    sb.append("Ingredients:");
    sb.append(EndOfLine.NATIVE_EOL);
    for(Ingredient ingredient : _ingredients)
    {
      sb.append('\t').append(ingredient);
      sb.append(EndOfLine.NATIVE_EOL);
    }
    sb.append("Results:");
    sb.append(EndOfLine.NATIVE_EOL);
    for(RecipeVersion results : _versions)
    {
      sb.append('\t').append(results);
      sb.append(EndOfLine.NATIVE_EOL);
    }
    return sb.toString().trim();
  }

  @Override
  public String toString() {
    return _name;
  }
}
