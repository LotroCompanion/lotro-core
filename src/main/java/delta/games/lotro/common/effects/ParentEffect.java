package delta.games.lotro.common.effects;

import java.util.Set;

/**
 * Interface of effect that have 'child' effects.
 * @author DAM
 */
public interface ParentEffect
{
  /**
   * Get the child effects.
   * @return The child effects.
   */
  Set<Effect> getChildEffects();
}
