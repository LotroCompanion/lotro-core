package delta.games.lotro.lore.travels;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.requirements.AbstractAchievableRequirement;
import delta.games.lotro.common.requirements.UsageRequirement;

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
  private AbstractAchievableRequirement _questRequirement;
  private UsageRequirement _usageRequirements;

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
    _routeActions=new ArrayList<String>();
    _usageRequirements=new UsageRequirement();
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
    return _usageRequirements.getMinLevel();
  }

  /**
   * Get the quests/deeds requirement.
   * @return A requirement or <code>null</code>.
   */
  public AbstractAchievableRequirement getQuestRequirement()
  {
    return _questRequirement;
  }

  /**
   * Set the quests/deeds requirement.
   * @param questRequirement Requirement to use.
   */
  public void setQuestRequirement(AbstractAchievableRequirement questRequirement)
  {
    _questRequirement=questRequirement;
  }

  /**
   * Get the usage requirement.
   * @return A usage requirement (that may be empty).
   */
  public UsageRequirement getUsageRequirement()
  {
    return _usageRequirements;
  }

  @Override
  public String toString()
  {
    return "Route: ID="+_id+", name="+_name+", to="+_destination+", requirements="+_usageRequirements;
  }
}
