package delta.games.lotro.common.effects.display;

/**
 * Context for effect rendering.
 * @author DAM
 */
public class EffectRenderingContext
{
  private int _level;

  /**
   * Constructor.
   * @param level Level for computations.
   */
  public EffectRenderingContext(int level)
  {
    _level=level;
  }

  /**
   * Get the level.
   * @return A level.
   */
  public int getLevel()
  {
    return _level;
  }
}
