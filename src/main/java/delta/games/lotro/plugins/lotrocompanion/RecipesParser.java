package delta.games.lotro.plugins.lotrocompanion;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import delta.games.lotro.lore.crafting.recipes.CraftingResult;
import delta.games.lotro.lore.crafting.recipes.Ingredient;
import delta.games.lotro.lore.crafting.recipes.ItemReference;
import delta.games.lotro.lore.crafting.recipes.Recipe;
import delta.games.lotro.lore.crafting.recipes.RecipeVersion;
import delta.games.lotro.plugins.LuaParser;
import delta.games.lotro.plugins.PluginConstants;

/**
 * Parser for the recipes as found in LotroCompanion plugin data.
 * @author DAM
 */
public class RecipesParser
{
  private static final Logger LOGGER=Logger.getLogger(RecipesParser.class);

  /**
   * Parse/use data from the "Recipes" file of the LotroCompanion plugin.
   * @param f Input file.
   * @return A possibly empty list of recipes.
   * @throws Exception If an error occurs.
   */
  public List<Recipe> doIt(File f) throws Exception
  {
    LuaParser parser=new LuaParser();
    Map<String,Object> data=parser.read(f);
    List<Recipe> ret=useData(data);
    return ret;
  }

  @SuppressWarnings("unchecked")
  private List<Recipe> useData(Map<String,Object> data)
  {
    List<Recipe> ret=new ArrayList<Recipe>();
    Map<String,Object> recipesMap=(Map<String,Object>)data.get("Recipes");
    if (recipesMap!=null)
    {
      Set<String> professions=recipesMap.keySet();
      List<String> sortedProfessions=new ArrayList<String>(professions);
      Collections.sort(sortedProfessions);
      for(String profession : sortedProfessions)
      {
        Map<String,Object> recipesForProfessionMap=(Map<String,Object>)recipesMap.get(profession);
        List<Map<String,Object>> recipeDatas=getOrderedRecipes(recipesForProfessionMap);
        for(Map<String,Object> recipeData : recipeDatas)
        {
          Recipe recipe=parseRecipe(recipeData,profession);
          ret.add(recipe);
        }
      }
    }
    return ret;
  }

  private List<Map<String,Object>> getOrderedRecipes(Map<String,Object> recipesForProfessionMap)
  {
    List<Map<String,Object>> ret=new ArrayList<Map<String,Object>>();
    int nb=recipesForProfessionMap.size();
    for(int i=1;i<=nb;i++)
    {
      String key=i+".0";
      @SuppressWarnings("unchecked")
      Map<String,Object> data=(Map<String,Object>)recipesForProfessionMap.get(key);
      ret.add(data);
    }
    return ret;
  }

  @SuppressWarnings("unchecked")
  private Recipe parseRecipe(Map<String,Object> data, String profession)
  {
    Recipe recipe=new Recipe();
    // Profession
    recipe.setProfession(profession);

    // Name
    String name=(String)data.get("Name");
    recipe.setName(name);
    // Tier
    Double tier=(Double)data.get("Tier");
    recipe.setTier(tier.intValue());
    // Category
    String category=(String)data.get("CategoryName");
    recipe.setCategory(category);
    // XP
    Double xp=(Double)data.get("ExperienceReward");
    recipe.setXP(xp.intValue());
    // Cooldown
    Double cooldown=(Double)data.get("Cooldown");
    recipe.setCooldown(cooldown.intValue());

    // Single use
    Boolean singleUse=(Boolean)data.get("IsSingleUse");
    recipe.setOneTimeUse(singleUse.booleanValue());

    RecipeVersion version=new RecipeVersion();
    // Regular result
    {
      ItemReference regularResultItem=new ItemReference();
      String resultName=(String)data.get("ResultItemName");
      regularResultItem.setName(resultName);
      Double resultIconId=(Double)data.get("ResultItemIconID");
      Double resultBackgroundIconId=(Double)data.get("ResultItemBackgroundIconID");
      regularResultItem.setIcon(resultIconId.intValue()+"-"+resultBackgroundIconId.intValue());
      CraftingResult regularResult=new CraftingResult();
      regularResult.setItem(regularResultItem);
      regularResult.setCriticalResult(false);
      Double quantity=(Double)data.get("ResultItemQuantity");
      regularResult.setQuantity(quantity.intValue());
      version.setRegular(regularResult);
    }

    Boolean hasCriticalResult=(Boolean)data.get("HasCriticalResultItem");
    if ((hasCriticalResult!=null) && (hasCriticalResult.booleanValue()))
    {
      ItemReference criticalResultItem=new ItemReference();
      String resultName=(String)data.get("CriticalResultItemName");
      criticalResultItem.setName(resultName);
      Double resultIconId=(Double)data.get("CriticalResultItemIconId");
      Double resultBackgroundIconId=(Double)data.get("CriticalResultItemBackgroundIconId");
      criticalResultItem.setIcon(resultIconId.intValue()+"-"+resultBackgroundIconId.intValue());
      CraftingResult criticalResult=new CraftingResult();
      criticalResult.setItem(criticalResultItem);
      criticalResult.setCriticalResult(true);
      Double quantity=(Double)data.get("CriticalSuccessItemQuantity");
      criticalResult.setQuantity(quantity.intValue());
      version.setCritical(criticalResult);
      // TODO ["BaseCriticalSuccessChance"] = 0.050000,
    }
    recipe.getVersions().add(version);

    // Ingredients
    {
      Map<String,Object> ingredientsData=(Map<String,Object>)data.get("Ingredients");
      List<String> keys=new ArrayList<String>(ingredientsData.keySet());
      Collections.sort(keys);
      for(String key : keys)
      {
        Map<String,Object> ingredientData=(Map<String,Object>)ingredientsData.get(key);
        Ingredient ingredient=parseIngredient(ingredientData);
        recipe.getIngredients().add(ingredient);
      }
    }
    // Optional ingredients
    {
      Map<String,Object> ingredientsData=(Map<String,Object>)data.get("OptionalIngredient");
      List<String> keys=new ArrayList<String>(ingredientsData.keySet());
      Collections.sort(keys);
      for(String key : keys)
      {
        Map<String,Object> ingredientData=(Map<String,Object>)ingredientsData.get(key);
        Ingredient ingredient=parseIngredient(ingredientData);
        ingredient.setOptional(true);
        // TODO ["CriticaChanceBonus"] = 0.350000,
        recipe.getIngredients().add(ingredient);
      }
    }
    System.out.println(recipe.dump());
    return recipe;
  }

  private Ingredient parseIngredient(Map<String,Object> ingredientData)
  {
    String name=(String)ingredientData.get("Name");
    Ingredient ingredient=new Ingredient();
    ItemReference item=new ItemReference();
    item.setName(name);
    Double resultIconId=(Double)ingredientData.get("IconID");
    Double resultBackgroundIconId=(Double)ingredientData.get("BackgroundIconID");
    item.setIcon(resultIconId.intValue()+"-"+resultBackgroundIconId.intValue());
    ingredient.setItem(item);
    // Quantity
    Double quantity=(Double)ingredientData.get("RequiredQuantity");
    ingredient.setQuantity(quantity.intValue());
    //["CriticalChanceBonus"] = 0.000000,
    return ingredient;
  }

  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    String account="glorfindel666";
    String server="Landroval";
    List<String> characters=PluginConstants.getCharacters(account,server,false);
    for(String character : characters)
    {
      File dataDir=PluginConstants.getCharacterDir("glorfindel666","Landroval",character);
      File dataFile=new File(dataDir,"LotroCompanionData.plugindata");
      if (dataFile.exists())
      {
        try
        {
          System.out.println("Doing: " + character);
          RecipesParser parser=new RecipesParser();
          parser.doIt(dataFile);
        }
        catch(Exception e)
        {
          LOGGER.error("Error when loading recipes from file "+dataFile, e);
        }
      }
      else
      {
        System.out.println("No recipes for: " + character);
      }
    }
  }
}
