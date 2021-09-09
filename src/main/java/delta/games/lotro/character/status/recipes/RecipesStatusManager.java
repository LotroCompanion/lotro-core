package delta.games.lotro.character.status.recipes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.character.status.crafting.CraftingStatus;
import delta.games.lotro.character.status.crafting.KnownRecipes;
import delta.games.lotro.character.status.crafting.ProfessionStatus;
import delta.games.lotro.character.status.recipes.comparators.RecipeStatusSortUtils;
import delta.games.lotro.lore.crafting.CraftingLevel;
import delta.games.lotro.lore.crafting.Profession;
import delta.games.lotro.lore.crafting.Vocation;
import delta.games.lotro.lore.crafting.recipes.Recipe;
import delta.games.lotro.lore.crafting.recipes.RecipeUtils;
import delta.games.lotro.lore.crafting.recipes.RecipesManager;

/**
 * Storage for all recipes for a single character.
 * @author DAM
 */
public class RecipesStatusManager
{
  private Map<Integer,RecipeStatus> _statuses;
  private RecipesStatistics _statistics;

  /**
   * Constructor.
   */
  public RecipesStatusManager()
  {
    _statuses=new HashMap<Integer,RecipeStatus>();
    _statistics=new RecipesStatistics();
  }

  /**
   * Initialize from a crafting status.
   * @param craftingStatus Crafting status.
   */
  public void init(CraftingStatus craftingStatus)
  {
    _statuses.clear();
    Vocation vocation=craftingStatus.getVocation();
    if (vocation==null)
    {
      return;
    }
    RecipesManager recipesMgr=RecipesManager.getInstance();
    for(Profession profession : vocation.getProfessions())
    {
      Set<Integer> knownRecipeIds=new HashSet<Integer>();
      ProfessionStatus professionStatus=craftingStatus.getProfessionStatus(profession);
      if (professionStatus!=null)
      {
        KnownRecipes knownRecipes=professionStatus.getKnownRecipes();
        if (knownRecipes!=null)
        {
          knownRecipeIds.addAll(knownRecipes.getKnownRecipes());
        }
      }
      List<Integer> tiers=recipesMgr.getTiers(profession);
      for(Integer tier : tiers)
      {
        CraftingLevel level=profession.getByTier(tier.intValue());
        List<Recipe> recipes=recipesMgr.getRecipes(profession,tier.intValue());
        for(Recipe recipe : recipes)
        {
          int recipeId=recipe.getIdentifier();
          RecipeState state=RecipeState.NOT_KNOWN;
          boolean auto=level.isAutobestowed(recipeId);
          if (auto)
          {
            state=RecipeState.AUTO;
          }
          else if (knownRecipeIds.contains(Integer.valueOf(recipeId)))
          {
            state=RecipeState.LEARNT;
          }
          RecipeStatus status=new RecipeStatus(recipe,state);
          _statuses.put(Integer.valueOf(recipeId),status);
        }
      }
    }
  }

  /**
   * Get the list of managed recipes.
   * @return A list of recipes.
   */
  public List<Recipe> getRecipes()
  {
    List<Recipe> ret=new ArrayList<Recipe>();
    for(RecipeStatus status : _statuses.values())
    {
      ret.add(status.getRecipe());
    }
    Comparator<Recipe> recipesComparator=RecipeUtils.buildRecipeComparator();
    Collections.sort(ret,recipesComparator);
    return ret;
  }

  /**
   * Get the managed statuses.
   * @return a list of recipe statuses.
   */
  public List<RecipeStatus> getRecipeStatuses()
  {
    List<RecipeStatus> ret=new ArrayList<RecipeStatus>(_statuses.values());
    RecipeStatusSortUtils.sortByRecipeStatuses(ret);
    return ret;
  }

  /**
   * Get the status for a recipe.
   * @param recipeId Recipe identifier.
   * @return A recipe status or <code>null</code> if not found.
   */
  public RecipeStatus getStatus(int recipeId)
  {
    return _statuses.get(Integer.valueOf(recipeId));
  }

  /**
   * Get the managed statistics.
   * @return some statistics.
   */
  public RecipesStatistics getStatistics()
  {
    return _statistics;
  }

  /**
   * Update the statistics.
   * @param recipeFilter Filter to use.
   */
  public void update(Filter<Recipe> recipeFilter)
  {
    _statistics.reset();
    for(RecipeStatus status : _statuses.values())
    {
      if (recipeFilter.accept(status.getRecipe()))
      {
        _statistics.add(status.getState());
      }
    }
  }
}
