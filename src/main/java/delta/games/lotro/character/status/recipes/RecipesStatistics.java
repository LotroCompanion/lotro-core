package delta.games.lotro.character.status.recipes;

/**
 * Statistics about the status of recipes.
 * @author DAM
 */
public class RecipesStatistics
{
  private int _autoRecipes;
  private int _learntRecipes;
  private int _notKnownRecipes;
  private int _blacklisted;

  /**
   * Constructor.
   */
  public RecipesStatistics()
  {
    reset();
  }

  /**
   * Reset values.
   */
  public void reset()
  {
    _autoRecipes=0;
    _learntRecipes=0;
    _notKnownRecipes=0;
    _blacklisted=0;
  }

  /**
   * Get the number of auto recipes.
   * @return a count.
   */
  public int getAutoRecipesCount()
  {
    return _autoRecipes;
  }

  /**
   * Get the number of learnt recipes.
   * @return a count.
   */
  public int getLearntRecipesCount()
  {
    return _learntRecipes;
  }

  /**
   * Get the number of not known recipes.
   * @return a count.
   */
  public int getNotKnownRecipesCount()
  {
    return _notKnownRecipes;
  }

  /**
   * Get the number of blacklisted recipes.
   * @return A count.
   */
  public int getBloacklistedRecipesCount()
  {
    return _blacklisted;
  }

  /**
   * Add a recipe.
   * @param state State of recipe to add.
   */
  public void add(RecipeState state)
  {
    if (state==RecipeState.AUTO) _autoRecipes++;
    else if (state==RecipeState.LEARNT) _learntRecipes++;
    else if (state==RecipeState.NOT_KNOWN) _notKnownRecipes++;
  }

  /**
   * Add a blacklisted recipe.
   */
  public void addBlacklisted()
  {
    _blacklisted++;
  }
}
