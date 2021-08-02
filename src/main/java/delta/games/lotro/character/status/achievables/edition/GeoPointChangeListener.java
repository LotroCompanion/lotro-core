package delta.games.lotro.character.status.achievables.edition;

/**
 * Interface of a listener for point state changes.
 * @author DAM
 */
public interface GeoPointChangeListener
{
  /**
   * Handle a point state change.
   * @param point Targeted point.
   * @param completed New state.
   */
  void handlePointChange(AchievableStatusGeoItem point, boolean completed);
}
