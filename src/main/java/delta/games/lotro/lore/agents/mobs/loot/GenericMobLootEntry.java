package delta.games.lotro.lore.agents.mobs.loot;

import delta.games.lotro.common.treasure.TreasureList;
import delta.games.lotro.common.treasure.TrophyList;

/**
 * Entry in a species loot entry, dedicated to a single level.
 * @author DAM
 */
public class GenericMobLootEntry
{
  private int _level;
  private TreasureList _treasureList;
  private TrophyList _trophyList;

  /**
   * Constructor.
   * @param level Level.
   */
  public GenericMobLootEntry(int level)
  {
    _level=level;
  }

  /**
   * Get the managed level.
   * @return a level.
   */
  public int getLevel()
  {
    return _level;
  }

  /**
   * Get the managed treasure list.
   * @return A treasure list or <code>null</code>.
   */
  public TreasureList getTreasureList()
  {
    return _treasureList;
  }

  /**
   * Set the managed treasure list.
   * @param treasureList Treasure list to set (may be <code>null</code>).
   */
  public void setTreasureList(TreasureList treasureList)
  {
    _treasureList=treasureList;
  }

  /**
   * Get the managed trophy list.
   * @return A trophy list or <code>null</code>.
   */
  public TrophyList getTrophyList()
  {
    return _trophyList;
  }

  /**
   * Set the managed trophy list.
   * @param trophyList Trophy list to set (may be <code>null</code>).
   */
  public void setTrophyList(TrophyList trophyList)
  {
    _trophyList=trophyList;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder("Level=");
    sb.append(_level);
    if (_treasureList!=null)
    {
      sb.append(", treasure list=");
      sb.append(_treasureList.getIdentifier());
    }
    if (_trophyList!=null)
    {
      sb.append(", trophy list=");
      sb.append(_trophyList.getIdentifier());
    }
    return sb.toString();
  }
}
