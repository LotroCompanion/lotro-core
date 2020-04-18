package delta.games.lotro.lore.travels;

import delta.games.lotro.common.money.Money;

/**
 * Instance of travel route.
 * @author DAM
 */
public class TravelRouteInstance
{
  private Money _cost;
  private TravelRoute _route;

  /**
   * Constructor.
   * @param cost Reference cost for this route (silver coins).
   * @param route Associated route.
   */
  public TravelRouteInstance(int cost, TravelRoute route)
  {
    _cost=new Money();
    _cost.setRawValue(cost);
    _route=route;
  }

  /**
   * Get the route cost.
   * @return a cost.
   */
  public Money getCost()
  {
    return _cost;
  }

  /**
   * Get the associated route.
   * @return the associated route.
   */
  public TravelRoute getRoute()
  {
    return _route;
  }

  @Override
  public String toString()
  {
    return "Route: "+_route.getName()+": "+_cost;
  }
}
