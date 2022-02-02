package delta.games.lotro.common.statistics;

import delta.games.lotro.lore.reputation.Faction;

/**
 * Statistics for a single faction.
 * @author DAM
 */
public class FactionStats
{
  private Faction _faction;
  protected int _points;

  /**
   * Constructor.
   * @param faction Managed faction.
   */
  public FactionStats(Faction faction)
  {
    _faction=faction;
  }

  /**
   * Get the managed faction.
   * @return a faction.
   */
  public Faction getFaction()
  {
    return _faction;
  }

  /**
   * Get the total reputation points for this faction
   * @return A points count.
   */
  public int getPoints()
  {
    return _points;
  }
}
