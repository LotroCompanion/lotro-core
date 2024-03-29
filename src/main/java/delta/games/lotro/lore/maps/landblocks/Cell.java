package delta.games.lotro.lore.maps.landblocks;

import delta.common.utils.math.geometry.Vector3D;

/**
 * Cell data (restricted to usage in LotroCompanion tools).
 * @author DAM
 */
public class Cell
{
  private int _index;
  private Integer _dungeonId;
  private Vector3D _position;

  /**
   * Constructor.
   * @param index Cell index.
   * @param dungeonId Dungeon identifier.
   */
  public Cell(int index, Integer dungeonId)
  {
    _index=index;
    _dungeonId=dungeonId;
  }

  /**
   * Get the cell index.
   * @return the cell index.
   */
  public int getIndex()
  {
    return _index;
  }

  /**
   * Get the dungeon identifier.
   * @return the dungeon identifier.
   */
  public Integer getDungeonId()
  {
    return _dungeonId;
  }

  /**
   * Get the position of this cell.
   * @return the position of this cell.
   */
  public Vector3D getPosition()
  {
    return _position;
  }

  /**
   * Set the position of this cell.
   * @param position Position to set.
   */
  public void setPosition(Vector3D position)
  {
    _position=position;
  }

  @Override
  public String toString()
  {
    return "Cell: ID="+_index+", dungeon="+_dungeonId+", position="+_position;
  }
}
