package delta.games.lotro.business.crafting;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import delta.games.lotro.character.crafting.KnownRecipes;
import delta.games.lotro.lore.crafting.CraftingLevel;
import delta.games.lotro.lore.crafting.Profession;
import delta.games.lotro.lore.crafting.recipes.Recipe;
import delta.games.lotro.lore.crafting.recipes.RecipesManager;

/**
 * Computes known recipes for a character.
 * @author DAM
 */
public class KnownRecipesComputer
{
  /**
   * Show known recipes for a profession and tier.
   * @param profession Profession to use.
   * @param tier Tier to use.
   * @param extraRecipes Extra recipes (acquired known recipes).
   */
  public static void showKnownRecipes(Profession profession, int tier, KnownRecipes extraRecipes)
  {
    CraftingLevel level=profession.getByTier(tier);
    int[] autoRecipes=level.getRecipes();
    Set<Integer> extraRecipeIds=extraRecipes.getKnownRecipes();
    List<Recipe> availableRecipes=RecipesManager.getInstance().getRecipes(profession.getName(),tier);
    for(Recipe recipe : availableRecipes)
    {
      boolean known=false;
      int autoIndex=Arrays.binarySearch(autoRecipes,recipe.getIdentifier());
      if (autoIndex>=0)
      {
        known=true;
      }
      else
      {
        Integer recipeId=Integer.valueOf(recipe.getIdentifier());
        boolean hasExtra=extraRecipeIds.contains(recipeId);
        if (hasExtra)
        {
          known=true;
        }
      }
      System.out.println("\t\t"+(known?"Known":"Not known")+": "+recipe.getName());
    }
  }

  /**
   * Show known recipes for a profession.
   * @param profession Profession to use.
   * @param extraRecipes Extra recipes (acquired known recipes).
   */
  public static void showRecipesStatusForProfession(Profession profession, KnownRecipes extraRecipes)
  {
    System.out.println("Profession: "+profession.getName());
    List<CraftingLevel> levels=profession.getLevels();
    for(CraftingLevel level : levels)
    {
      int tier=level.getTier();
      System.out.println("\tTier "+tier);
      showKnownRecipes(profession,tier,extraRecipes);
    }
  }
}
