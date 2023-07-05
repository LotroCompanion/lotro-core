package delta.games.lotro.lore.quests.objectives;

import delta.games.lotro.lore.geo.landmarks.LandmarkDescription;

/**
 * Landmark detected condition.
 * @author DAM
 */
public class LandmarkDetectionCondition extends ObjectiveCondition
{
  private LandmarkDescription _landmark;

  /**
   * Constructor.
   */
  public LandmarkDetectionCondition()
  {
    _landmark=null;
  }

  @Override
  public ConditionType getType()
  {
    return ConditionType.LANDMARK_DETECTION;
  }

  /**
   * Get the landmark.
   * @return a landmark or <code>null</code>.
   */
  public LandmarkDescription getLandmark()
  {
    return _landmark;
  }

  /**
   * Set the landmark.
   * @param landmark the landmark to set (may be <code>null</code>).
   */
  public void setLandmark(LandmarkDescription landmark)
  {
    _landmark=landmark;
  }

  @Override
  public String toString()
  {
    return "LandarkDetectedCondition: "+_landmark;
  }
}
