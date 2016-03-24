package delta.games.lotro.lore.items.essences;

import delta.games.lotro.character.stats.BasicStatsSet;

/**
 * Essence.
 * @author DAM
 */
public class Essence
{
  private String _name;
  private BasicStatsSet _stats;

  /**
   * Constructor.
   * @param name Essence name.
   */
  public Essence(String name)
  {
    _name=name;
    _stats=new BasicStatsSet();
  }

  /**
   * Get the essence name.
   * @return the essence name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Get the stats for this essence.
   * @return the stats of this essence.
   */
  public BasicStatsSet getStats()
  {
    return _stats;
  }

  @Override
  public String toString()
  {
    return _name + ": " + _stats;
  }
}
