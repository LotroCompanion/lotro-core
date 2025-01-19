package delta.games.lotro.character.status.housing;

/**
 * House address.
 * @author DAM
 */
public class HouseAddress
{
  private int _neighborhoodID;
  private int _houseID;

  /**
   * Constructor.
   * @param neighborhoodID Neighborhood identifier.
   * @param houseID House identifier.
   */
  public HouseAddress(int neighborhoodID, int houseID)
  {
    _neighborhoodID=neighborhoodID;
    _houseID=houseID;
  }

  /**
   * Get the neighborhood identifier.
   * @return the neighborhood identifier.
   */
  public int getNeighborhoodID()
  {
    return _neighborhoodID;
  }

  /**
   * Get the house identifier.
   * @return the house identifier.
   */
  public int getHouseID()
  {
    return _houseID;
  }
}
