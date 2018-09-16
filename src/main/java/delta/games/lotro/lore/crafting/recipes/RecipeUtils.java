package delta.games.lotro.lore.crafting.recipes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import delta.common.utils.collections.CompoundComparator;
import delta.games.lotro.lore.crafting.recipes.comparator.RecipeCategoryComparator;
import delta.games.lotro.lore.crafting.recipes.comparator.RecipeNameComparator;
import delta.games.lotro.lore.crafting.recipes.comparator.RecipeProfessionComparator;
import delta.games.lotro.lore.crafting.recipes.comparator.RecipeTierComparator;

/**
 * Utility methods for recipes.
 * @author DAM
 */
public class RecipeUtils
{
  /**
   * Sort some recipes by profession/tier/name.
   * @param recipes Recipes to sort.
   */
  public static void sort(List<Recipe> recipes)
  {
    List<Comparator<Recipe>> comparators=new ArrayList<Comparator<Recipe>>();
    comparators.add(new RecipeProfessionComparator());
    comparators.add(new RecipeTierComparator());
    comparators.add(new RecipeCategoryComparator());
    comparators.add(new RecipeNameComparator());
    CompoundComparator<Recipe> comparator=new CompoundComparator<Recipe>(comparators);
    Collections.sort(recipes,comparator);
  }

  /**
   * Indicates if 2 recipes are considered equal.
   * @param recipe1 First recipe.
   * @param recipe2 Second recipe.
   * @return <code>true</code> if they are, <code>false</code> otherwise.
   */
  public static boolean equals(Recipe recipe1, Recipe recipe2)
  {
    boolean ret=true;
    if (recipe1.getIdentifier()!=recipe2.getIdentifier())
    {
      ret=false;
    }
    if (!recipe1.getProfession().equals(recipe2.getProfession()))
    {
      ret=false;
    }
    if (recipe1.getTier()!=recipe2.getTier())
    {
      ret=false;
    }
    if (!recipe1.getName().equals(recipe2.getName()))
    {
      ret=false;
    }
    if (!recipe1.getCategory().equals(recipe2.getCategory()))
    {
      //System.out.println(context + ": category differ: "+recipe1.getCategory()+" != "+recipe2.getCategory());
      ret=false;
    }
    if (!recipe1.getKey().equals(recipe2.getKey()))
    {
      //System.out.println(context + ": key differ: "+recipe1.getKey()+" != "+recipe2.getKey());
      ret=false;
    }
    // Ingredients
    List<Ingredient> ingredients1=recipe1.getIngredients();
    List<Ingredient> ingredients2=recipe2.getIngredients();
    int nbIngredients1=ingredients1.size();
    int nbIngredients2=ingredients2.size();
    if (nbIngredients1!=nbIngredients2)
    {
      ret=false;
    }
    else
    {
      for(int i=0;i<nbIngredients1;i++)
      {
        Ingredient ingredient1=ingredients1.get(i);
        Ingredient ingredient2=ingredients2.get(i);
        if (!equals(ingredient1,ingredient2))
        {
          ret=false;
        }
      }
    }
    if (ret==true)
    {
      //System.out.println("Same: "+getContext(recipe1)+" and "+getContext(recipe2));
    }
    return ret;
  }

  private static boolean equals(Ingredient ingredient1, Ingredient ingredient2)
  {
    int count1=ingredient1.getQuantity();
    int count2=ingredient2.getQuantity();
    if (count1!=count2)
    {
      return false;
    }
    String name1=ingredient1.getName();
    String name2=ingredient2.getName();
    if (name1==null)
    {
      if (name2!=null) return false;
    }
    else
    {
      if ((name2==null) || (!name1.equals(name2)))
      {
        return false;
      }
    }
    return true;
  }

  /**
   * Get a context string for a recipe.
   * @param recipe Recipe to use.
   * @return A displayable context string.
   */
  public static String getContext(Recipe recipe)
  {
    return recipe.getProfession()+":Tier "+recipe.getTier()+":"+recipe.getName();
  }
}
