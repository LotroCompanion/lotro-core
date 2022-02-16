package delta.games.lotro.lore.maps.landblocks;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import delta.common.utils.math.geometry.Vector3D;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.geo.BlockReference;
import delta.games.lotro.lore.maps.landblocks.io.xml.LandblocksXMLParser;

/**
 * Manager for landblocks.
 * <ul>
 * <li>Gives the parent zone for positions.
 * </ul>
 * @author DAM
 */
public class LandblocksManager
{
  private static final Logger LOGGER=Logger.getLogger(LandblocksManager.class);

  private static LandblocksManager _instance=null;
  private Map<String,Landblock> _index;

  /**
   * Get the reference instance of this class.
   * @return the reference instance of this class.
   */
  public static LandblocksManager getInstance()
  {
    if (_instance==null)
    {
      _instance=load();
    }
    return _instance;
  }

  private static LandblocksManager load()
  {
    File from=LotroCoreConfig.getInstance().getFile(DataFiles.LANDBLOCKS);
    return new LandblocksXMLParser().parseXML(from);
  }

  /**
   * Constructor.
   */
  public LandblocksManager()
  {
    _index=new HashMap<String,Landblock>();
  }

  private String getKey(int region, int blockX, int blockY)
  {
    return region+"#"+blockX+"#"+blockY;
  }

  /**
   * Get the managed landblocks.
   * @return a list of landblocks.
   */
  public List<Landblock> getLandblocks()
  {
    return new ArrayList<Landblock>(_index.values());
  }

  /**
   * Get a landblock.
   * @param region Region.
   * @param blockX Block X.
   * @param blockY Block Y.
   * @return A landblock or <code>null</code> if not found.
   */
  public Landblock getLandblock(int region, int blockX, int blockY)
  {
    String key=getKey(region,blockX,blockY);
    Landblock data=_index.get(key);
    return data;
  }

  /**
   * Add a landblock.
   * @param landblock Landblock to add.
   */
  public void addLandblock(Landblock landblock)
  {
    BlockReference blockId=landblock.getBlockId();
    String key=getKey(blockId.getRegion(),blockId.getBlockX(),blockId.getBlockY());
    _index.put(key,landblock);
  }

  /**
   * Get the parent zone (area or dungeon) for a position.
   * @param region Region.
   * @param blockX Block X.
   * @param blockY Block Y.
   * @param cell Cell.
   * @param position Position.
   * @return A parent zone identifier or <code>null</code>.
   */
  public Integer getParentZone(int region, int blockX, int blockY, int cell, Vector3D position)
  {
    Landblock data=getLandblock(region,blockX,blockY);
    Integer ret=null;
    if (data!=null)
    {
      ret=data.getParentData(cell,position);
    }
    else
    {
      LOGGER.warn("No parent data for: "+position);
    }
    return ret;
  }
}
