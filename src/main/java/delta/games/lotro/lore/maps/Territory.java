package delta.games.lotro.lore.maps;

/**
 * Territory.
 * @author DAM
 */
public class Territory implements LandDivision
{
  private int _identifier;
  private String _name;
  private Region _parentRegion;

  /**
   * Constructor.
   * @param identifier Internal identifier.
   * @param name Territory's name.
   * @param parentRegion Parent region.
   */
  public Territory(int identifier, String name, Region parentRegion)
  {
    _identifier=identifier;
    _name=name;
    _parentRegion=parentRegion;
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
   * Get the territory's name.
   * @return the territory's name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Get the parent region.
   * @return the parent region.
   */
  public Region getParentRegion()
  {
    return _parentRegion;
  }

  /**
   * Dump the contents of this territory as a string.
   * @return A readable string.
   */
  public String dump()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Territory: ").append(_name);
    sb.append(" (").append(_identifier).append(')');
    return sb.toString();
  }

  @Override
  public String toString()
  {
    return _name;
  }
}
