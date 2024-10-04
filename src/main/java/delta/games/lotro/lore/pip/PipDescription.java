package delta.games.lotro.lore.pip;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.Named;
import delta.games.lotro.common.enums.PipType;

/**
 * PIP desription.
 * @author DAM
 */
public class PipDescription implements Identifiable,Named
{
  private PipType _type;
  private String _name;
  private int _min;
  private int _max;
  private int _home;
  private Integer _iconMin;
  private Integer _iconMax;
  private Integer _iconHome;

  /**
   * Constructor.
   * @param type Pip type.
   */
  public PipDescription(PipType type)
  {
    _type=type;
  }

  @Override
  public int getIdentifier()
  {
    return _type.getCode();
  }

  @Override
  public String getName()
  {
    return _name;
  }

  /**
   * Set the name.
   * @param name Name to set.
   */
  public void setName(String name)
  {
    _name=name;
  }

  /**
   * Get the pip type.
   * @return the pip type.
   */
  public PipType getType()
  {
    return _type;
  }

  /**
   * Get the minimum value.
   * @return the minimum value.
   */
  public int getMin()
  {
    return _min;
  }

  /**
   * Set the minimum value.
   * @param min the minimum value to set.
   */
  public void setMin(int min)
  {
    _min=min;
  }

  /**
   * Get the maximum value.
   * @return the maximum value to set.
   */
  public int getMax()
  {
    return _max;
  }

  /**
   * Set the maximum value.
   * @param max the maximum value to set.
   */
  public void setMax(int max)
  {
    _max=max;
  }

  /**
   * Get the home value.
   * @return the home value.
   */
  public int getHome()
  {
    return _home;
  }

  /**
   * Set the home value.
   * @param home the home value to set.
   */
  public void setHome(int home)
  {
    _home=home;
  }

  /**
   * Get the min icon ID.
   * @return an icon ID or <code>null</code>.
   */
  public Integer getIconMin()
  {
    return _iconMin;
  }

  /**
   * Set the min icon ID.
   * @param iconMin the iconID to set (may be <code>null</code>).
   */
  public void setIconMin(Integer iconMin)
  {
    _iconMin=iconMin;
  }

  /**
   * Get the max icon ID.
   * @return an icon ID or <code>null</code>.
   */
  public Integer getIconMax()
  {
    return _iconMax;
  }

  /**
   * Set the max icon ID.
   * @param iconMax the iconID to set (may be <code>null</code>).
   */
  public void setIconMax(Integer iconMax)
  {
    _iconMax=iconMax;
  }

  /**
   * Get the home icon ID.
   * @return an icon ID or <code>null</code>.
   */
  public Integer getIconHome()
  {
    return _iconHome;
  }

  /**
   * Set the home icon ID.
   * @param iconHome the iconID to set (may be <code>null</code>).
   */
  public void setIconHome(Integer iconHome)
  {
    _iconHome=iconHome;
  }
}
