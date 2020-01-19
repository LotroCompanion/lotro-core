package delta.games.lotro.lore.items;

/**
 * Item property names.
 * @author DAM
 */
public class ItemPropertyNames
{
  /**
   * Munging.
   */
  public static final String MUNGING="Munging";

  /**
   * User comment.
   */
  public static final String USER_COMMENT="UserComment";

  /**
   * Stash ID.
   */
  public static final String STASH_ID="stashId";

  /**
   * Indicates if the given property name is used for an item reference or not.
   * @param key Key to use.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public static boolean isItemReferenceProperty(String key) 
  {
    if (MUNGING.equals(key)) return true;
    return false;
  }

  /**
   * Indicates if the given property name is used for an item instance or not.
   * @param key Key to use.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public static boolean isItemInstanceProperty(String key) 
  {
    if (USER_COMMENT.equals(key)) return true;
    if (STASH_ID.equals(key)) return true;
    return false;
  }
}
