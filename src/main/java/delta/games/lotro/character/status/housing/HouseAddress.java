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

  @Override
  public int hashCode()
  {
    return 31*_houseID+_neighborhoodID;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (this==obj) return true;
    if (obj==null) return false;
    if (getClass()!=obj.getClass()) return false;
    HouseAddress other=(HouseAddress)obj;
    return _houseID==other._houseID&&_neighborhoodID==other._neighborhoodID;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder("(house id=");
    sb.append(_houseID);
    sb.append(", neighborhood id=");
    sb.append(_neighborhoodID);
    return sb.toString();
  }
}
