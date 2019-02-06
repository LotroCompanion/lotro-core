package delta.games.lotro.common.colors;

/**
 * LOTRO color description.
 * @author DAM
 */
public class ColorDescription
{
  private String _name;
  private float _code;

  /**
   * Constructor.
   */
  public ColorDescription()
  {
    _name=null;
    _code=0.0f;
  }

  /**
   * Get the name of this color.
   * @return the name of this color.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Set the name of this color.
   * @param name the name to set.
   */
  public void setName(String name)
  {
    _name=name;
  }

  /**
   * Get the color code.
   * @return a code.
   */
  public float getCode()
  {
    return _code;
  }

  /**
   * Set the color code.
   * @param code the code to set.
   */
  public void setCode(float code)
  {
    _code=code;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Color code=").append(_code);
    sb.append(", name=").append(_name);
    return sb.toString();
  }
}
