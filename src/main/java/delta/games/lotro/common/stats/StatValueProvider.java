package delta.games.lotro.common.stats;

/**
 * Interface of an object that can provide a value for a stat.
 * @author DAM
 */
public interface StatValueProvider
{
  /**
   * Get a value for the given stat.
   * @param stat Stat to use.
   * @return A value.
   */
  float getStat(StatDescription stat);
}
