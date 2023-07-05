package delta.games.lotro.lore.geo.landmarks;

import delta.games.lotro.common.Identifiable;

/**
 * Landmark description.
 * @author DAM
 */
public class LandmarkDescription implements Identifiable
{
  private int _identifier;
  private String _name;

  /**
   * Constructor.
   * @param id Identifier.
   * @param name Name.
   */
  public LandmarkDescription(int id, String name)
  {
    _identifier=id;
    _name=name;
  }

  /**
   * Get the identifier.
   * @return an identifier.
   */
  public int getIdentifier()
  {
    return _identifier;
  }

  /**
   * Get the landmark name.
   * @return a name.
   */
  public String getName()
  {
    return _name;
  }
}
