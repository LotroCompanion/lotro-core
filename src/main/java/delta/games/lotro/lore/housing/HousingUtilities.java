package delta.games.lotro.lore.housing;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.geo.Position;

/**
 * Utility methods related to housing.
 * @author DAM
 */
public class HousingUtilities
{
  /**
   * Get the nearest house from a position.
   * @param neighborhoodID Neighborhood identifier.
   * @param position Position to use.
   * @return A house or <code>null</code> if none.
   */
  public static HouseDefinition findNearestHouse(int neighborhoodID, Position position)
  {
    HouseDefinition ret=null;
    float bestDistance=Float.MAX_VALUE;
    for(HouseDefinition house : getHouses(neighborhoodID))
    {
      float distance=getSquareDistance(house,position);
      if (distance<bestDistance)
      {
        ret=house;
        bestDistance=distance;
      }
    }
    return ret;
  }

  private static float getSquareDistance(HouseDefinition house, Position position)
  {
    Position housePosition=house.getPosition();
    int region=position.getRegion();
    int houseRegion=housePosition.getRegion();
    if (region!=houseRegion)
    {
      return Float.MAX_VALUE;
    }
    float latitude=position.getLatitude();
    float houseLatitude=housePosition.getLatitude();
    float latitudeDiff=Math.abs(houseLatitude-latitude);
    float longitude=position.getLongitude();
    float houseLongitude=housePosition.getLongitude();
    float longitudeDiff=Math.abs(houseLongitude-longitude);
    float ret=latitudeDiff*latitudeDiff+longitudeDiff*longitudeDiff;
    return ret;
  }

  private static List<HouseDefinition> getHouses(int neighborhoodID)
  {
    List<HouseDefinition> ret=new ArrayList<HouseDefinition>();
    HousingManager mgr=HousingSystem.getInstance().getData();
    Neighborhood neighborhood=mgr.getNeighborhood(neighborhoodID);
    if (neighborhood!=null)
    {
      NeighborhoodTemplate template=neighborhood.getTemplate();
      if (template!=null)
      {
        ret.addAll(template.getHouses());
      }
    }
    return ret;
  }
}
