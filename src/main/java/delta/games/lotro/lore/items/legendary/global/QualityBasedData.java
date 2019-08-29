package delta.games.lotro.lore.items.legendary.global;

import java.util.HashMap;
import java.util.Map;

import delta.games.lotro.lore.items.EquipmentLocation;
import delta.games.lotro.lore.items.ItemQuality;

/**
 * Legendary data associated to an item quality (age).
 * @author DAM
 */
public class QualityBasedData
{
  private ItemQuality _quality;
  private Map<Integer,Integer> _startProgressionLevels;
  private Map<EquipmentLocation,int[]> _pointsTables;
  private int[] _xpTable;

  /**
   * Constructor.
   * @param quality Associated item quality.
   */
  public QualityBasedData(ItemQuality quality)
  {
    _quality=quality;
    _startProgressionLevels=new HashMap<Integer,Integer>();
    _pointsTables=new HashMap<EquipmentLocation,int[]>();
  }

  /**
   * Get the associated quality.
   * @return a quality.
   */
  public ItemQuality getQuality()
  {
   return _quality;
  }

  /**
   * Add a 'start progression level'.
   * @param itemLevel Item level.
   * @param startLevel Start progression level.
   */
  public void addStartProgressionLevel(int itemLevel, int startLevel)
  {
    _startProgressionLevels.put(Integer.valueOf(itemLevel),Integer.valueOf(startLevel));
  }

  /**
   * Get all start levels.
   * @return a map of item level to start progression levels.
   */
  public Map<Integer,Integer> getStartLevels()
  {
    return _startProgressionLevels;
  }

  /**
   * Get the start progression level for an item level.
   * @param itemLevel Item level to use.
   * @return A start progression level or <code>null</code> if not found.
   */
  public Integer getStartLevel(int itemLevel)
  {
    return _startProgressionLevels.get(Integer.valueOf(itemLevel));
  }

  /**
   * Set the points table for a given slot.
   * @param location Slot.
   * @param points Points table.
   */
  public void setPointsTable(EquipmentLocation location, int[] points)
  {
    _pointsTables.put(location,points);
  }

  /**
   * Get the points table for a slot.
   * @param location Slot.
   * @return A points table or <code>null</code> if not found.
   */
  public int[] getPointsTable(EquipmentLocation location)
  {
    return _pointsTables.get(location);
  }

  /**
   * Get the legendary points for a legendary items.
   * @param location Slot.
   * @param level Legendary level.
   * @return A number of points.
   */
  public int getPoints(EquipmentLocation location, int level)
  {
    int ret=0;
    int[] levels=_pointsTables.get(location);
    if (levels!=null)
    {
      if ((level>=0) && (level<levels.length))
      {
        ret=levels[level];
      }
    }
    return ret;
  }

  /**
   * Get the XP table.
   * @return the XP table.
   */
  public int[] getXpTable()
  {
    return _xpTable;
  }

  /**
   * Set the XP table.
   * @param xpTable XP table to set.
   */
  public void setXpTable(int[] xpTable)
  {
    _xpTable=xpTable;
  }

  /**
   * Get the XP for a legendary level.
   * @param level Level to use.
   * @return an XP value.
   */
  public int getXp(int level)
  {
    if ((level>=0) && (level<_xpTable.length))
    {
      return _xpTable[level];
    }
    return 0;
  }
}
