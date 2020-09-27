package delta.games.lotro.lore.instances;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.lore.geo.BlockReference;

/**
 * Description of an instance map.
 * @author DAM
 */
public class InstanceMapDescription
{
  private Integer _mapId;
  private List<Integer> _zoneIds;
  private List<BlockReference> _blocks;

  /**
   * Constructor.
   * @param mapId Basemap identifier (optional).
   */
  public InstanceMapDescription(Integer mapId)
  {
    _mapId=mapId;
    _zoneIds=new ArrayList<Integer>();
    _blocks=new ArrayList<BlockReference>();
  }

  /**
   * Get the basemap identifier.
   * @return a basemap identifier (may be <code>null</code>).
   */
  public Integer getMapId()
  {
    return _mapId;
  }

  /**
   * Add a zone ID for this map.
   * @param zoneId Zone identifier (dungeon or area).
   */
  public void addZoneId(int zoneId)
  {
    _zoneIds.add(Integer.valueOf(zoneId));
  }

  /**
   * Get all zone identifiers for this map.
   * @return a list of zone identifiers. 
   */
  public List<Integer> getZoneIds()
  {
    return _zoneIds;
  }

  /**
   * Add a block reference.
   * @param block Block reference to add.
   */
  public void addBlock(BlockReference block)
  {
    _blocks.add(block);
  }

  /**
   * Get the blocks for this map.
   * @return a list of block references.
   */
  public List<BlockReference> getBlocks()
  {
    return _blocks;
  }

  @Override
  public String toString()
  {
    return "Instance map: basemap="+_mapId+", zones="+_zoneIds+", blocks="+_blocks;
  }
}
