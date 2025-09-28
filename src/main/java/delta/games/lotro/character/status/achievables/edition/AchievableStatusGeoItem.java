package delta.games.lotro.character.status.achievables.edition;

import delta.games.lotro.lore.quests.Achievable;
import delta.games.lotro.lore.quests.geo.AchievableGeoPoint;
import delta.games.lotro.lore.quests.objectives.Objective;
import delta.games.lotro.lore.quests.objectives.ObjectiveCondition;

/**
 * Status of a single geographic point in an achievable.
 * @author DAM
 */
public class AchievableStatusGeoItem
{
  private Achievable _achievable;
  private Objective _objective;
  private ObjectiveCondition _condition;
  private AchievableGeoPoint _point;
  private boolean _completed;

  /**
   * Constructor.
   * @param achievable Parent achievable.
   * @param objective Parent objective.
   * @param condition Parent condition.
   * @param point Point to use.
   */
  public AchievableStatusGeoItem(Achievable achievable, Objective objective, ObjectiveCondition condition, AchievableGeoPoint point)
  {
    _achievable=achievable;
    _objective=objective;
    _condition=condition;
    _point=point;
  }

  /**
   * Get the parent achievable.
   * @return the parent achievable
   */
  public Achievable getAchievable()
  {
    return _achievable;
  }

  /**
   * Get the parent objective.
   * @return the parent objective
   */
  public Objective getObjective()
  {
    return _objective;
  }

  /**
   * Get the association condition.
   * @return the associated condition.
   */
  public ObjectiveCondition getCondition()
  {
    return _condition;
  }

  /**
   * Get the managed point.
   * @return the managed point.
   */
  public AchievableGeoPoint getPoint()
  {
    return _point;
  }

  /**
   * Indicates if the managed point is completed or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isCompleted()
  {
    return _completed;
  }

  /**
   * Set the 'completed' state of the managed point.
   * @param completed State to set.
   */
  public void setCompleted(boolean completed)
  {
    _completed=completed;
  }
}
