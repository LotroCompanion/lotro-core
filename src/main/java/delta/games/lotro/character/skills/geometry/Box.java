package delta.games.lotro.character.skills.geometry;

/**
 * Box.
 * @author DAM
 */
public class Box implements Shape
{
  private float _length;
  private float _width;

  /**
   * Constructor.
   */
  public Box()
  {
    _length=1;
    _width=1;
  }

  /**
   * @return the length
   */
  public float getLength()
  {
    return _length;
  }

  /**
   * @param length the length to set
   */
  public void setLength(float length)
  {
    _length=length;
  }

  /**
   * @return the width
   */
  public float getWidth()
  {
    return _width;
  }

  /**
   * @param width the width to set
   */
  public void setWidth(float width)
  {
    _width=width;
  }

  @Override
  public String toString()
  {
    return "Box: width="+_width+", length="+_length;
  }
}
