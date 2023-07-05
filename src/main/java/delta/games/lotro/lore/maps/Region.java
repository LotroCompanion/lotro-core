package delta.games.lotro.lore.maps;

/**
 * Region.
 * @author DAM
 */
public class Region implements LandDivision
{
  private int _identifier;
  private int _code;
  private String _name;

  /**
   * Constructor.
   * @param identifier Internal identifier.
   * @param code Internal code.
   * @param name Region's name.
   */
  public Region(int identifier, int code, String name)
  {
    _identifier=identifier;
    _code=code;
    _name=name;
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
   * Get the internal code for this region.
   * @return a code (1-4).
   */
  public int getCode()
  {
    return _code;
  }

  /**
   * Get the region's name.
   * @return the region's name.
   */
  public String getName()
  {
    return _name;
  }

  @Override
  public String toString()
  {
    return _name;
  }
}
