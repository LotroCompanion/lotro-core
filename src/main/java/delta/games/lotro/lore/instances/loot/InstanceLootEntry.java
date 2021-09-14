package delta.games.lotro.lore.instances.loot;

import delta.games.lotro.common.treasure.TrophyList;

/**
 * Entry of an instance loot table.
 * @author DAM
 */
public class InstanceLootEntry
{
  private InstanceLootParameters _parameters;
  private TrophyList _trophyList;

  /**
   * Constructor.
   * @param parameters Associated instance parameters.
   */
  public InstanceLootEntry(InstanceLootParameters parameters)
  {
    _parameters=parameters;
    _trophyList=null;
  }

  /**
   * Get the associated instance parameters.
   * @return some instance parameters.
   */
  public InstanceLootParameters getParameters()
  {
    return _parameters;
  }

  /**
   * Get the managed trophy list.
   * @return a trophy list.
   */
  public TrophyList getTrophyList()
  {
    return _trophyList;
  }

  /**
   * Set the managed trophy list.
   * @param trophyList Trophy list to set.
   */
  public void setTrophyList(TrophyList trophyList)
  {
    _trophyList=trophyList;
  }
}
