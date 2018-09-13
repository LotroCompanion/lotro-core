package delta.games.lotro.lore.crafting.recipes;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.lore.crafting.recipes.io.xml.RecipeXMLParser;
import delta.games.lotro.lore.crafting.recipes.io.xml.RecipeXMLWriter;

/**
 * Facade for recipes access.
 * @author DAM
 */
public final class RecipesManager
{
  private static final Logger LOGGER=Logger.getLogger(RecipesManager.class);

  private static RecipesManager _instance=new RecipesManager();

  private Map<String,Map<Integer,Map<String,Recipe>>> _recipes;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static RecipesManager getInstance()
  {
    return _instance;
  }

  /**
   * Constructor.
   */
  public RecipesManager()
  {
    _recipes=new HashMap<String,Map<Integer,Map<String,Recipe>>>();
  }

  /**
   * Load all recipes from a file.
   * @param inputFile Input file.
   */
  public void loadRecipesFromFile(File inputFile)
  {
    RecipeXMLParser parser=new RecipeXMLParser();
    List<Recipe> recipes=parser.loadRecipes(inputFile);
    _recipes.clear();
    for(Recipe recipe : recipes)
    {
      registerRecipe(recipe);
    }
  }

  /**
   * Register a new recipe.
   * @param recipe Recipe to add.
   */
  public void registerRecipe(Recipe recipe)
  {
    String profession=recipe.getProfession();
    Map<Integer,Map<String,Recipe>> recipesForProfession=_recipes.get(profession);
    if (recipesForProfession==null)
    {
      recipesForProfession=new HashMap<Integer,Map<String,Recipe>>();
      _recipes.put(recipe.getProfession(),recipesForProfession);
    }
    Integer tier=Integer.valueOf(recipe.getTier());
    Map<String,Recipe> recipesForTier=recipesForProfession.get(tier);
    if (recipesForTier==null)
    {
      recipesForTier=new HashMap<String,Recipe>();
      recipesForProfession.put(tier,recipesForTier);
    }
    String name=recipe.getName();
    Recipe old=recipesForTier.get(name);
    if (old!=null)
    {
      if (recipe.getTier()!=old.getTier())
      {
        LOGGER.warn("Recipes with the same profession [" + profession + "], tier [" + tier + "] and name [" + name +"]");
      }
    }
    else
    {
      recipesForTier.put(name,recipe);
    }
  }

  /**
   * Get all recipes.
   * @return a list of all recipes.
   */
  public List<Recipe> getAll()
  {
    List<Recipe> ret=new ArrayList<Recipe>();
    for(String profession : _recipes.keySet())
    {
      for(Integer tier : _recipes.get(profession).keySet())
      {
        ret.addAll(_recipes.get(profession).get(tier).values());
      }
    }
    return ret;
  }

  /**
   * Write the managed recipes to the specified file.
   * @param toFile File to write to.
   */
  public void writeToFile(File toFile)
  {
    List<Recipe> recipes=getAll();
    System.out.println(recipes.size());
    RecipeUtils.sort(recipes);
    RecipeXMLWriter writer=new RecipeXMLWriter();
    writer.write(toFile,recipes,EncodingNames.UTF_8);
  }
}
