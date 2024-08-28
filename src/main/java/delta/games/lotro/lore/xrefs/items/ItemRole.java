package delta.games.lotro.lore.xrefs.items;

/**
 * Roles in items cross-references.
 * @author DAM
 */
public enum ItemRole
{
  /**
   * Item found as ingredient in recipe.
   */
  RECIPE_INGREDIENT,
  /**
   * Item found as critical ingredient in recipe.
   */
  RECIPE_CRITICAL_INGREDIENT,
  /**
   * Item found as result in recipe.
   */
  RECIPE_RESULT,
  /**
   * Item found as critical result in recipe.
   */
  RECIPE_CRITICAL_RESULT,
  /**
   * Item provides a recipe.
   */
  RECIPE_PROVIDES_RECIPE,
  /**
   * Item is a member of set.
   */
  SET_MEMBER_OF_SET,
  /**
   * Item is given by barterer (received by the character).
   */
  BARTERER_GIVEN,
  /**
   * Item is received by barterer (given by the character).
   */
  BARTERER_RECEIVED,
  /**
   * Item is sold by vendor.
   */
  VENDOR_SOLD_BY,
  /**
   * Item is involved in quest/deed.
   */
  ACHIEVABLE_INVOLVED,
  /**
   * Item is a reward of quest/deed.
   */
  ACHIEVABLE_REWARD,
  /**
   * Item is required in a task.
   */
  TASK_ITEM,
  /**
   * Item may come from container.
   */
  CONTAINED_IN,
  /**
   * Has same cosmetics as.
   */
  SAME_COSMETICS,
  /**
   * Item comes from a web store item.
   */
  WEB_STORE_ITEM
}
