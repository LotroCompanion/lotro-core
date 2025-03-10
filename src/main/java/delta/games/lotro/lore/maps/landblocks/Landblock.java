package delta.games.lotro.lore.maps.landblocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.common.utils.math.geometry.Vector3D;
import delta.games.lotro.lore.geo.BlockReference;

/**
 * Landblock.
 * <p>
 * Contains summary data for a single landblock, as used by tools.
 * @author DAM
 */
public class Landblock
{
  private static final Logger LOGGER=LoggerFactory.getLogger(Landblock.class);

  private BlockReference _id;
  private Map<Integer,Cell> _cells;
  private Integer _parentDungeon;
  private Integer _parentArea;
  private float _centerHeight;

  /**
   * Constructor.
   * @param id Block identifier.
   */
  public Landblock(BlockReference id)
  {
    _id=id;
    _cells=new HashMap<Integer,Cell>();
  }

  /**
   * Get the block identifier.
   * @return the block identifier.
   */
  public BlockReference getBlockId()
  {
    return _id;
  }

  /**
   * Add a cell.
   * @param cell Cell to add.
   */
  public void addCell(Cell cell)
  {
    _cells.put(Integer.valueOf(cell.getIndex()),cell);
  }

  /**
   * Get a cell using its index.
   * @param cellIndex Cell index to use.
   * @return A cell or <code>null<:code> if not found.
   */
  public Cell getCell(int cellIndex)
  {
    Cell cell=_cells.get(Integer.valueOf(cellIndex));
    return cell;
  }

  /**
   * Get the dungeon identifier for the given cell.
   * @param cellIndex Index of cell to use.
   * @return A dungeon identifier or <code>null</code>.
   */
  public Integer getCellDungeon(int cellIndex)
  {
    Cell cell=_cells.get(Integer.valueOf(cellIndex));
    if (cell!=null)
    {
      return cell.getDungeonId();
    }
    return null;
  }

  /**
   * Get the indexes for the managed cells.
   * @return a list of cell indexes.
   */
  public List<Integer> getCellIndexes()
  {
    return new ArrayList<Integer>(_cells.keySet());
  }

  /**
   * Get a list of all the dungeons found in cells.
   * @return A possibly empty but never <code>null</code> list of dungeon IDs.
   */
  public List<Integer> getDungeonsFromCells()
  {
    List<Integer> ret=new ArrayList<Integer>();
    for(Cell cell : _cells.values())
    {
      Integer dungeonId=cell.getDungeonId();
      if ((dungeonId!=null) && (!ret.contains(dungeonId)))
      {
        ret.add(dungeonId);
      }
    }
    return ret;
  }

  /**
   * Get the parent dungeon identifier.
   * @return A dungeon identifier or <code>null</code>.
   */
  public Integer getParentDungeon()
  {
    return _parentDungeon;
  }

  /**
   * Set the parent dungeon identifier.
   * @param dungeonId Dungeon identifier to set.
   */
  public void setParentDungeon(int dungeonId)
  {
    _parentDungeon=Integer.valueOf(dungeonId);
  }

  /**
   * Get the parent area identifier.
   * @return An area identifier or <code>null</code>.
   */
  public Integer getParentArea()
  {
    return _parentArea;
  }

  /**
   * Set the parent area identifier.
   * @param areaId Area identifier to set.
   */
  public void setParentArea(int areaId)
  {
    _parentArea=Integer.valueOf(areaId);
  }

  /**
   * Get the center height for this landblock.
   * @return a height.
   */
  public float getCenterHeight()
  {
    return _centerHeight;
  }

  /**
   * Set the center height.
   * @param centerHeight Center height (unknown unit).
   */
  public void setCenterHeight(float centerHeight)
  {
    _centerHeight=centerHeight;
  }

  /**
   * Get the parent zone identifier.
   * @param cell Cell index to use.
   * @param position Position of point to check.
   * @return A parent zone identifier or <code>null</code>.
   */
  public Integer getParentData(int cell, Vector3D position)
  {
    Integer ret=null;

    if (cell>0)
    {
      // Dungeon
      ret=getCellDungeon(cell);
      if (ret==null)
      {
        ret=getParentDungeon();
      }
      if (ret==null)
      {
        if (LOGGER.isDebugEnabled())
        {
          LOGGER.debug("No dungeon for cell: {} in landblock: {}",Integer.valueOf(cell),_id);
        }
      }
    }
    else if (cell==0)
    {
      // Landscape
      ret=getParentArea();
    }
    else // Cell not set, cell=-1 (entities)
    {
      boolean hasDungeon=hasDungeon();
      if ((hasDungeon) && (position.getZ()<_centerHeight))
      {
        // In dungeon
        int nbCells=_cells.size();
        if (nbCells>0)
        {
          Cell nearestCell=getNearestCell(position);
          if (nearestCell!=null)
          {
            ret=nearestCell.getDungeonId();
          }
          if (ret==null)
          {
            ret=getParentDungeon();
          }
        }
      }
    }
    // Default
    if (ret==null)
    {
      ret=getParentArea();
    }
    return ret;
  }

  private boolean hasDungeon()
  {
    if (_parentDungeon!=null) return true;
    return (!getDungeonsFromCells().isEmpty());
  }

  private Cell getNearestCell(Vector3D position)
  {
    Cell ret=null;
    float min=Float.MAX_VALUE;
    for(Cell cell : _cells.values())
    {
      Vector3D cellPosition=cell.getPosition();
      float dx=Math.abs(position.getX()-cellPosition.getX());
      float dy=Math.abs(position.getY()-cellPosition.getY());
      float dz=Math.abs(position.getZ()-cellPosition.getZ());
      float value=dx*dx+dy*dy+dz*dz;
      if (value<min)
      {
        ret=cell;
        min=value;
      }
    }
    return ret;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    if (_parentArea!=null)
    {
      sb.append("Area=").append(_parentArea);
    }
    if (_parentDungeon!=null)
    {
      sb.append(", dungeon=").append(_parentDungeon);
    }
    if (_cells.size()>0)
    {
      sb.append(", cells=").append(_cells);
    }
    return sb.toString();
  }
}
