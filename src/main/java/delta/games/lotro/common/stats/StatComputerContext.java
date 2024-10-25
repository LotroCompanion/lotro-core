package delta.games.lotro.common.stats;

/**
 * Interface of a stat computer context.
 * @author DAM
 */
public interface StatComputerContext
{
  /**
   * Get the level to use (item or character).
   * @return A level.
   */
  int getLevel();
  /**
   * Get the tier to use.
   * @return A tier (usually 1).
   */
  int getTier();
  /**
   * Get the stat modifier computer.
   * @return A stat modifier computer or <code>null</code>.
   */
  StatModifiersComputer getStatModifiersComputer();
}
