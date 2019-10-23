package delta.games.lotro.common.stats;

/**
 * Modes useable to merge a custom stats set into a default/automatic stats set.
 * @author DAM
 */
public enum CustomStatsMergeMode
{
  /**
   * Custom stats are ignored.
   */
  AUTO,
  /**
   * Custom stats replace the default/automatic stats.
   */
  SET,
  /**
   * Custom stats are merged with default/automatic stats.
   * Note that a custom stat set to 0 will remove the stat from the result set.
   */
  MERGE,
  /**
   * Custom stats are added to the default/automatic stats.
   */
  ADD
}
