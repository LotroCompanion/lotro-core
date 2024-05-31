package delta.games.lotro.utils.style;

/**
 * Font style.
 * @author DAM
 */
public class FontStyle implements StyleElement
{
  private String _name;
  private Integer _size;
  private String _color;

  /**
   * Constructor.
   * @param name Font name (may be <code>null</code>).
   * @param size Font size (may be <code>null</code>).
   * @param color Color (may be <code>null</code>).
   */
  public FontStyle(String name, Integer size, String color)
  {
    _name=name;
    _size=size;
    _color=color;
  }

  /**
   * Get the font name.
   * @return A font name or <code>null</code>.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Get the font size.
   * @return the font size or <code>null</code>.
   */
  public Integer getSize()
  {
    return _size;
  }

  /**
   * Get the color.
   * @return a color or <code>null<:code>.
   */
  public String getColor()
  {
    return _color;
  }

  @Override
  public String toString()
  {
    return "Font style: name="+_name+", size="+_size+", color="+_color;
  }
}
