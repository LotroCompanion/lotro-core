package delta.games.lotro.lore.quests.objectives;

import delta.games.lotro.common.enums.MobDivision;
import delta.games.lotro.lore.geo.landmarks.LandmarkDescription;
import delta.games.lotro.lore.maps.LandDivision;

/**
 * Structured mob location definition.
 * @author DAM
 */
public class MobLocation
{
  private MobDivision _mobDivision;
  private LandDivision _landDivision;
  private LandmarkDescription _landmark;

  /**
   * Constructor.
   * @param mobDivision Mob division.
   * @param landDivision Land division.
   * @param landmark Landmark.
   */
  public MobLocation(MobDivision mobDivision, LandDivision landDivision, LandmarkDescription landmark)
  {
    _mobDivision=mobDivision;
    _landDivision=landDivision;
    _landmark=landmark;
  }

  /**
   * Get the mob division.
   * @return the mob division.
   */
  public MobDivision getMobDivision()
  {
    return _mobDivision;
  }

  /**
   * Get the land division.
   * @return the land division.
   */
  public LandDivision getLandDivision()
  {
    return _landDivision;
  }

  /**
   * Get the landmark.
   * @return the landmark.
   */
  public LandmarkDescription getLandmark()
  {
    return _landmark;
  }
}
