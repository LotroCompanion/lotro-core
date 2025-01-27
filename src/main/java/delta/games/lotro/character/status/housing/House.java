package delta.games.lotro.character.status.housing;

/**
 * House.
 * @author DAM
 */
public class House
{
  private HouseIdentifier _identifier;
  private HouseContents _interior;
  private HouseContents _exterior;

  /**
   * Constructor.
   * @param houseID House identifier.
   */
  public House(HouseIdentifier houseID)
  {
    _identifier=houseID;
  }

  /**
   * Get the house identifier.
   * @return an identifier or <code>null</code> if not set.
   */
  public HouseIdentifier getIdentifier()
  {
    return _identifier;
  }

  /**
   * Get the contents of the interior.
   * @return A contents (may be <code>null</code> if not assessed).
   */
  public HouseContents getInterior()
  {
    return _interior;
  }

  /**
   * Set the interior contents.
   * @param interior the contents to set.
   */
  public void setInterior(HouseContents interior)
  {
    _interior=interior;
  }

  /**
   * Get the contents of the exterior.
   * @return A contents (may be <code>null</code> if not assessed).
   */
  public HouseContents getExterior()
  {
    return _exterior;
  }

  /**
   * Set the exterior contents.
   * @param exterior the contents to set.
   */
  public void setExterior(HouseContents exterior)
  {
    _exterior=exterior;
  }
}
