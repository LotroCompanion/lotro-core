package delta.games.lotro.utils.style;

/**
 * Style definitions.
 * @author DAM
 */
public class Style
{
  /**
   * Bold.
   */
  public static final StyleElement BOLD=new BasicStyleElement();
  /**
   * Italic.
   */
  public static final StyleElement ITALIC=new BasicStyleElement();
  /**
   * Underline.
   */
  public static final StyleElement UNDERLINE=new BasicStyleElement();

  /**
   * Get a title style.
   * @param level Level.
   * @return A title style.
   */
  public static TitleStyle getTitleStyle(int level)
  {
    return new TitleStyle(level);
  }

  /**
   * Get a color style.
   * @param color Color spec.
   * @return A color style.
   */
  public static FontStyle getColorStyle(String color)
  {
    return new FontStyle(null,null,color);
  }
}
