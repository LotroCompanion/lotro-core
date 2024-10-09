package delta.games.lotro.common.effects;

import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.lore.items.DamageType;

/**
 * Base vital effect.
 * @author DAM
 */
public abstract class BaseVitalEffect extends Effect
{
  /**
   * Get the involved stat.
   * @return the involved stat.
   */
  public abstract StatDescription getStat();

  /**
   * Get the damage type.
   * @return the damage type.
   */
  public abstract DamageType getDamageType();
}
