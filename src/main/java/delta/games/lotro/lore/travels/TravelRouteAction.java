package delta.games.lotro.lore.travels;

import delta.games.lotro.common.Identifiable;

/**
 * Travel route action.
 * @author DAM
 */
public class TravelRouteAction implements Identifiable
{
  private int _id;
  private String _headname;
  private Float _delay;
  private Integer _sceneID;
  private String _location;

  /**
   * Constructor.
   * @param id Identifier.
   * @param headname Head name.
   * @param delay Delay.
   * @param sceneID Scene ID.
   * @param location Location.
   */
  public TravelRouteAction(int id, String headname, Float delay, Integer sceneID, String location)
  {
    _id=id;
    _headname=headname;
    _delay=delay;
    _sceneID=sceneID;
    _location=location;
  }

  @Override
  public int getIdentifier()
  {
    return _id;
  }

  /**
   * Get the head name.
   * @return the head name.
   */
  public String getHeadname()
  {
    return _headname;
  }

  /**
   * Get the delay.
   * @return the delay.
   */
  public Float getDelay()
  {
    return _delay;
  }

  /**
   * @return the sceneID
   */
  public Integer getSceneID()
  {
    return _sceneID;
  }

  /**
   * Get the location.
   * @return the location.
   */
  public String getLocation()
  {
    return _location;
  }
}
