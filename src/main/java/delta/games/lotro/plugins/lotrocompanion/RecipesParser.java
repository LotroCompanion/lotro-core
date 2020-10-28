package delta.games.lotro.plugins.lotrocompanion;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import delta.games.lotro.lore.crafting.CraftingUtils;
import delta.games.lotro.lore.crafting.Profession;
import delta.games.lotro.lore.crafting.recipes.CraftingResult;
import delta.games.lotro.lore.crafting.recipes.Ingredient;
import delta.games.lotro.lore.crafting.recipes.Recipe;
import delta.games.lotro.lore.crafting.recipes.RecipeVersion;
import delta.games.lotro.lore.items.ItemProxy;
import delta.games.lotro.plugins.LuaParser;

/**
 * Parser for the recipes as found in LotroCompanion plugin data.
 * @author DAM
 */
public class RecipesParser
{
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
    List<Recipe> recipes=useData(data);
    return recipes;
  }

  @SuppressWarnings("unchecked")
  private List<Recipe> useData(Map<String,Object> data)
  {
    List<Recipe> ret=new ArrayList<Recipe>();
    Set<String> professions=data.keySet();
    List<String> sortedProfessions=new ArrayList<String>(professions);
    Collections.sort(sortedProfessions);
    for(String professionName : sortedProfessions)
    {
      Profession profession=CraftingUtils.getProfessionByName(professionName);
      Map<String,Object> recipesForProfessionMap=(Map<String,Object>)data.get(profession);
      List<Map<String,Object>> recipeDatas=getOrderedRecipes(recipesForProfessionMap);
      for(Map<String,Object> recipeData : recipeDatas)
      {
        Recipe recipe=parseRecipe(recipeData,profession);
        ret.add(recipe);
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
  private Recipe parseRecipe(Map<String,Object> data, Profession profession)
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
      String resultName=(String)data.get("ResultItemName");
      Double resultIconId=(Double)data.get("ResultItemIconID");
      Double resultBackgroundIconId=(Double)data.get("ResultItemBackgroundIconID");
      CraftingResult regularResult=new CraftingResult();
      ItemProxy regularResultItem=buildProxy(resultName,resultIconId,resultBackgroundIconId);
      regularResult.setItem(regularResultItem);

      regularResult.setCriticalResult(false);
      Double quantity=(Double)data.get("ResultItemQuantity");
      regularResult.setQuantity(quantity.intValue());
      version.setRegular(regularResult);
    }

    Boolean hasCriticalResult=(Boolean)data.get("HasCriticalResultItem");
    if ((hasCriticalResult!=null) && (hasCriticalResult.booleanValue()))
    {
      String resultName=(String)data.get("CriticalResultItemName");
      Double resultIconId=(Double)data.get("CriticalResultItemIconId");
      Double resultBackgroundIconId=(Double)data.get("CriticalResultItemBackgroundIconId");
      CraftingResult criticalResult=new CraftingResult();
      ItemProxy criticalResultItem=buildProxy(resultName,resultIconId,resultBackgroundIconId);
      criticalResult.setItem(criticalResultItem);
      criticalResult.setCriticalResult(true);
      Double quantity=(Double)data.get("CriticalSuccessItemQuantity");
      criticalResult.setQuantity(quantity.intValue());
      version.setCritical(criticalResult);
      // Base critical chance bonus
      Double baseCriticalChanceValue=(Double)data.get("BaseCriticalSuccessChance");
      if (baseCriticalChanceValue!=null)
      {
        int baseCriticalChance=(int)Math.round(baseCriticalChanceValue.doubleValue()*100);
        version.setBaseCriticalChance((baseCriticalChance!=0)?Integer.valueOf(baseCriticalChance):null);
      }
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
        version.getIngredients().add(ingredient);
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
        version.getIngredients().add(ingredient);
      }
    }
    return recipe;
  }

  private Ingredient parseIngredient(Map<String,Object> ingredientData)
  {
    String name=(String)ingredientData.get("Name");
    Ingredient ingredient=new Ingredient();
    Double resultIconId=(Double)ingredientData.get("IconID");
    Double resultBackgroundIconId=(Double)ingredientData.get("BackgroundIconID");
    ItemProxy item=buildProxy(name,resultIconId,resultBackgroundIconId);
    ingredient.setItem(item);
    // Quantity
    Double quantity=(Double)ingredientData.get("RequiredQuantity");
    ingredient.setQuantity(quantity.intValue());
    // Critical chance bonus
    Double criticalChanceBonusValue=(Double)ingredientData.get("CriticalChanceBonus");
    if (criticalChanceBonusValue!=null)
    {
      int criticalChanceBonus=(int)Math.round(criticalChanceBonusValue.doubleValue()*100);
      ingredient.setCriticalChanceBonus((criticalChanceBonus!=0)?Integer.valueOf(criticalChanceBonus):null);
    }
    return ingredient;
  }

  private ItemProxy buildProxy(String name, Double iconId, Double backgroundIconId)
  {
    ItemProxy proxy=new ItemProxy();
    proxy.setName(name);
    String icon=iconId.intValue()+"-"+backgroundIconId.intValue();
    proxy.setIcon(icon);
    return proxy;
  }
}
