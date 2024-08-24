package delta.games.lotro.common.effects;

import delta.games.lotro.common.geo.Position;

/**
 * Travel effect.
 * @author DAM
 */
public class TravelEffect extends InstantEffect
{
  private int _sceneID;
  private Position _destination;
  private Integer _privateEncounterID;
  private boolean _removeFromInstance;

  /**
   * Constructor.
   */
  public TravelEffect()
  {
    super();
    _destination=null;
    _removeFromInstance=true;
  }

  /**
   * Get the scene identifier.
   * @return A scene identifier.
   */
  public int getSceneID()
  {
    return _sceneID;
  }

  /**
   * Set the scene identifier.
   * @param sceneID Scene identifier.
   */
  public void setSceneID(int sceneID)
  {
    _sceneID=sceneID;
  }

  /**
   * Get the destination position.
   * @return a position.
   */
  public Position getDestination()
  {
    return _destination;
  }

  /**
   * Set the destination position.
   * @param destination Position to set.
   */
  public void setDestination(Position destination)
  {
    _destination=destination;
  }

  /**
   * Get the private encounter identifier.
   * @return An identifier or <code>null</code>.
   */
  public Integer getPrivateEncounterID()
  {
    return _privateEncounterID;
  }

  /**
   * Set the private encounter identifier.
   * @param privateEncounterID Private encounter identifier to set.
   */
  public void setPrivateEncounterID(Integer privateEncounterID)
  {
    _privateEncounterID=privateEncounterID;
  }

  /**
   * Indicates if the travel effect removes the character from the current instance, if any.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean isRemoveFromInstance()
  {
    return _removeFromInstance;
  }

  /**
   *  Set the 'remove from instance' flag.
   * @param removeFromInstance Value to set.
   */
  public void setRemoveFromInstance(boolean removeFromInstance)
  {
    _removeFromInstance=removeFromInstance;
  }
}
