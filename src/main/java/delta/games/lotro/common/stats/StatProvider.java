package delta.games.lotro.common.stats;

/**
 * Interface of a stat provider.
 * @author DAM
 */
public interface StatProvider extends StatsProviderEntry
{
  /**
   * Get the targeted stat.
   * @return the targeted stat.
   */
  StatDescription getStat();

  /**
   * Set stat.
   * @param stat Stat to set.
   */
  void setStat(StatDescription stat);

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

  /**
   * Get an optional description override.
   * @return a description override or <code>null</code>.
   */
  String getDescriptionOverride();

  /**
   * Set the description override.
   * @param descriptionOverride Value to set.
   */
  void setDescriptionOverride(String descriptionOverride);
}

