package delta.games.lotro.lore.travels;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.Identifiable;

/**
 * Travel route.
 * @author DAM
 */
public class TravelRoute implements Identifiable
{
  private int _id;
  private String _name;
  private TravelDestination _destination;
  private List<String> _routeActions;
  private TravelMode _mode;
  // Requirements
  private Integer _minLevel;
  // TODO Quest requirements
  // TODO Faction requirements

  /**
   * Constructor.
   * @param id Identifier.
   * @param name Route name.
   * @param mode Travel mode.
   * @param destination Destination.
   * @param minLevel Minimum level (<code>null</code> if none).
   */
  public TravelRoute(int id, String name, TravelMode mode, TravelDestination destination, Integer minLevel)
  {
    _id=id;
    _name=name;
    _mode=mode;
    _destination=destination;
    _routeActions=new ArrayList<String>();
    _minLevel=minLevel;
  }

  @Override
  public int getIdentifier()
  {
    return _id;
  }

  /**
   * Get the name of this route.
   * @return a route name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Get the mode of this route.
   * @return a route mode.
   */
  public TravelMode getMode()
  {
    return _mode;
  }

  /**
   * Get the route actions.
   * @return the route actions.
   */
  public List<String> getRouteActions()
  {
    return _routeActions;
  }

  /**
   * Add a route action.
   * @param routeAction Action to add.
   */
  public void addRouteAction(String routeAction)
  {
    _routeActions.add(routeAction);
  }

  /**
   * Get the destination.
   * @return the destination.
   */
  public TravelDestination getDestination()
  {
    return _destination;
  }

  /**
   * Get the minimum character level to use this route.
   * @return a level or <code>null</code> if no constraint.
   */
  public Integer getMinLevel()
  {
    return _minLevel;
  }

  @Override
  public String toString()
  {
    return "Route: ID="+_id+", name="+_name+", minLevel="+_minLevel+", to="+_destination;
  }
}
