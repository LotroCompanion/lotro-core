package delta.games.lotro.lore.items;

/**
 * Item property names.
 * @author DAM
 */
public class ItemPropertyNames
{
  /**
   * Factory comment.
   */
  public static final String FACTORY_COMMENT="FactoryComment";

  /**
   * Munging.
   */
  public static final String MUNGING="Munging";

  /**
   * User comment.
   */
  public static final String USER_COMMENT="UserComment";

  /**
   * Icon ID.
   */
  public static final String ICON_ID="iconId";

  /**
   * Background icon ID.
   */
  public static final String BACKGROUND_ICON_ID="backgroundIconId";

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
    if (FACTORY_COMMENT.equals(key)) return true;
    if (MUNGING.equals(key)) return true;
    return false;
  }
}
