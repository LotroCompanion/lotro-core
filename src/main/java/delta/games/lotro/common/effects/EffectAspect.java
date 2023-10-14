package delta.games.lotro.common.effects;

/**
 * Base interface for effect aspects.
 * @author DAM
 */
public interface EffectAspect
{
  /**
   * Resolve a variable defined by its name.
   * @param variableName Name of the variable to resolve.
   * @return A resolved value or <code>null</code> if not supported.
   */
  public default String resolveVariable(String variableName)
  {
    return null;
  }
}
