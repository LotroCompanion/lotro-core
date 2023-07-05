package delta.games.lotro.lore.quests.objectives;

import delta.games.lotro.lore.maps.LandDivision;

/**
 * Structured mob location definition.
 * @author DAM
 */
public class MobLocation
{
  // TODO Use an enum
  private String _mobDivision;
  private LandDivision _landDivision;
  // TODO Use a landmark POJO
  private String _landmark;

  /**
   * Constructor.
   * @param mobDivision Mob division.
   * @param landDivision Land division.
   * @param landmark Landmark.
   */
  public MobLocation(String mobDivision, LandDivision landDivision, String landmark)
  {
    _mobDivision=mobDivision;
    _landDivision=landDivision;
    _landmark=landmark;
  }

  /**
   * Get the mob division.
   * @return the mob division.
   */
  public String getMobDivision()
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
  public String getLandmark()
  {
    return _landmark;
  }
}
