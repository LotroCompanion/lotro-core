package delta.games.lotro.utils;

/**
 * Utilities related to icons.
 * @author DAM
 */
public class IconsUtils
{
  /**
   * Prefix for item icons.
   */
  public static final String ITEM_ICON_PREFIX = "item:";

  /**
   * Build an item icon path.
   * @param iconName Icon name.
   * @return A path.
   */
  public static String buildItemIcon(String iconName)
  {
    return "/items/"+iconName+".png";
  }
}
