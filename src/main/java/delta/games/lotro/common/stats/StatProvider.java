package delta.games.lotro.common.stats;

/**
 * Interface of a stat provider.
 * @author DAM
 */
public interface StatProvider
{
  /**
   * Get the targeted stat.
   * @return the targeted stat.
   */
  StatDescription getStat();

  /**
   * Get the stat value for the given tier and level.
   * @param tier Tier to use, starting at 1.
   * @param level Level to use, starting at 1.
   * @return A value or <code>null</code> if not supported.
   */
  Float getStatValue(int tier, int level);

  /**
   * Get the operator.
   * @return the operator.
   */
  StatOperator getOperator();

  /**
   * Set the operator.
   * @param operator Operator to set.
   */
  void setOperator(StatOperator operator);
}
