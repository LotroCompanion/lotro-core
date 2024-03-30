package delta.games.lotro.lore.travels;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.Identifiable;

/**
 * Travel node.
 * <p>
 * A travel node has:
 * <ul>
 * <li>one or several destinations (locations) that represent this node,
 * <li>a collection of routes. Each route has:
 * <ul>
 * <li>a cost,
 * <li>a destination.
 * </ul>
 * </ul>
 * @author DAM
 */
public class TravelNode implements Identifiable
{
  private int _id;
  private List<TravelDestination> _locations;
  private List<TravelRouteInstance> _routes;

  /**
   * Constructor.
   * @param id Identifier.
   */
  public TravelNode(int id)
  {
    _id=id;
    _locations=new ArrayList<TravelDestination>();
    _routes=new ArrayList<TravelRouteInstance>();
  }

  @Override
  public int getIdentifier()
  {
    return _id;
  }

  /**
   * Get the name of this node.
   * @return a name.
   */
  public String getName()
  {
    if (!_locations.isEmpty())
    {
      return _locations.get(0).getName();
    }
    return "???";
  }

  /**
   * Get the associated locations.
   * @return a list of one or more locations.
   */
  public List<TravelDestination> getLocations()
  {
    return _locations;
  }

  /**
   * Add a location.
   * @param location Location to add.
   */
  public void addLocation(TravelDestination location)
  {
    _locations.add(location);
  }

  /**
   * Get the available routes.
   * @return a list of routes.
   */
  public List<TravelRouteInstance> getRoutes()
  {
    return _routes;
  }

  /**
   * Add a route.
   * @param route Route to add.
   */
  public void addRoute(TravelRouteInstance route)
  {
    _routes.add(route);
  }

  @Override
  public String toString()
  {
    return "Travel node: ID="+_id;
  }
}
