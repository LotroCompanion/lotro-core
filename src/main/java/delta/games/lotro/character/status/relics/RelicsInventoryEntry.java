package delta.games.lotro.character.status.relics;

import delta.games.lotro.lore.items.legendary.relics.Relic;

/**
 * Status of a single relic.
 * @author DAM
 */
public class RelicsInventoryEntry
{
  private Relic _relic;
  private int _count;

  /**
   * Constructor.
   * @param relic Associated relic.
   * @param count Count.
   */
  public RelicsInventoryEntry(Relic relic, int count)
  {
    _relic=relic;
    _count=count;
  }

  /**
   * Get the associated relics.
   * @return a relic.
   */
  public Relic getRelic()
  {
    return _relic;
  }

  /**
   * Get the associated count.
   * @return a count.
   */
  public int getCount()
  {
    return _count;
  }
}
