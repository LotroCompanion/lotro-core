package delta.games.lotro.lore.maps;

import delta.games.lotro.common.geo.ExtendedPosition;

/**
 * Dungeon.
 * @author DAM
 */
public class Dungeon extends AbstractMap implements Zone
{
  private int _basemapId;
  // allowed mount types (Dungeon_Allowed_Mount_Types)
  // Music
  // Map position
  private ExtendedPosition _mapPosition;

  /**
   * Constructor.
   * @param identifier Internal identifier.
   * @param name Dungeon's name.
   * @param basemapId Dungeon basemap identifier.
   */
  public Dungeon(int identifier, String name, int basemapId)
  {
    super(identifier,name);
    _basemapId=basemapId;
  }

  /**
   * Get the basemap identifier for this dungeon.
   * @return a basemap identifier.
   */
  public int getBasemapId()
  {
    return _basemapId;
  }

  /**
   * Set the basemap identifier for this dungeon.
   * @param basemapId Basemap identifier to set.
   */
  public void setBasemapId(int basemapId)
  {
    _basemapId=basemapId;
  }

  /**
   * Get the map position.
   * @return A position or <code>null</code>.
   */
  public ExtendedPosition getMapPosition()
  {
    return _mapPosition;
  }

  /**
   * Set the map position.
   * @param mapPosition A position or <code>null</code>.
   */
  public void setMapPosition(ExtendedPosition mapPosition)
  {
    _mapPosition=mapPosition;
  }

  /**
   * Dump the contents of this area as a string.
   * @return A readable string.
   */
  public String dump()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Dungeon: ").append(_name);
    sb.append(" (").append(_identifier).append(')');
    return sb.toString();
  }

  @Override
  public String toString()
  {
    return "Dungeon: "+_name;
  }
}
