package delta.games.lotro.lore.maps;

import delta.games.lotro.common.Identifiable;

/**
 * Area.
 * @author DAM
 */
public class Area implements Identifiable
{
  private int _identifier;
  private String _name;
  private Territory _parentTerritory;
  private Integer _iconId;

  /**
   * Constructor.
   * @param identifier Internal identifier.
   * @param name Area's name.
   * @param parentTerritory Parent territory.
   */
  public Area(int identifier, String name, Territory parentTerritory)
  {
    _identifier=identifier;
    _name=name;
    _parentTerritory=parentTerritory;
    _iconId=null;
  }

  /**
   * Get the internal identifier.
   * @return the internal identifier.
   */
  public int getIdentifier()
  {
    return _identifier;
  }

  /**
   * Get the area's name.
   * @return the area's name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Get the parent territory.
   * @return the parent territory.
   */
  public Territory getParentTerritory()
  {
    return _parentTerritory;
  }

  /**
   * Get the icon for this area.
   * @return an icon identifier or <code>null</code>.
   */
  public Integer getIconId()
  {
    return _iconId;
  }

  /**
   * Set the icon identifier for this area.
   * @param iconId Icon identifier to set.
   */
  public void setIconId(Integer iconId)
  {
    _iconId=iconId;
  }

  /**
   * Dump the contents of this area as a string.
   * @return A readable string.
   */
  public String dump()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Area: ").append(_name);
    sb.append(" (").append(_identifier).append(')');
    return sb.toString();
  }

  @Override
  public String toString()
  {
    return "Area: "+_name;
  }
}
