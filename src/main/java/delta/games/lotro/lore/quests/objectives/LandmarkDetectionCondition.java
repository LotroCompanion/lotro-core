package delta.games.lotro.lore.quests.objectives;

import delta.games.lotro.lore.geo.landmarks.LandmarkDescription;
import delta.games.lotro.utils.Proxy;

/**
 * Landmark detected condition.
 * @author DAM
 */
public class LandmarkDetectionCondition extends ObjectiveCondition
{
  private Proxy<LandmarkDescription> _landmark;

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
   * Get the proxy to a landmark.
   * @return a proxy or <code>null</code>.
   */
  public Proxy<LandmarkDescription> getLandmarkProxy()
  {
    return _landmark;
  }

  /**
   * Set the proxy to a landmark.
   * @param landmark the proxy to set (may be <code>null</code>).
   */
  public void setLandmarkProxy(Proxy<LandmarkDescription> landmark)
  {
    _landmark=landmark;
  }

  @Override
  public String toString()
  {
    return "LandarkDetectedCondition: "+_landmark;
  }
}
