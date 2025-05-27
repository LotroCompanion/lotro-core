package delta.games.lotro.lore.travels;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.Named;
import delta.games.lotro.common.requirements.LevelRangeRequirement;
import delta.games.lotro.common.requirements.QuestRequirement;
import delta.games.lotro.common.requirements.Requirements;

/**
 * Travel route.
 * @author DAM
 */
public class TravelRoute implements Identifiable,Named
{
  private int _id;
  private String _name;
  private TravelDestination _destination;
  private List<TravelRouteAction> _routeActions;
  private TravelMode _mode;
  private Requirements _requirements;

  /**
   * Constructor.
   * @param id Identifier.
   * @param name Route name.
   * @param mode Travel mode.
   * @param destination Destination.
   */
  public TravelRoute(int id, String name, TravelMode mode, TravelDestination destination)
  {
    _id=id;
    _name=name;
    _mode=mode;
    _destination=destination;
    _routeActions=new ArrayList<TravelRouteAction>();
    _requirements=new Requirements();
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
  public List<TravelRouteAction> getRouteActions()
  {
    return _routeActions;
  }

  /**
   * Add a route action.
   * @param routeAction Action to add.
   */
  public void addRouteAction(TravelRouteAction routeAction)
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
    LevelRangeRequirement requirement=_requirements.getRequirement(LevelRangeRequirement.class);
    if (requirement!=null)
    {
      return requirement.getMinLevel();
    }
    return null;
  }

  /**
   * Get the quests/deeds requirement.
   * @return A requirement or <code>null</code>.
   */
  public QuestRequirement getQuestRequirement()
  {
    return _requirements.getRequirement(QuestRequirement.class);
  }

  /**
   * Get the requirements.
   * @return the requirements (that may be empty).
   */
  public Requirements getRequirements()
  {
    return _requirements;
  }

  @Override
  public String toString()
  {
    return "Route: ID="+_id+", name="+_name+", to="+_destination+", requirements="+_requirements;
  }
}
