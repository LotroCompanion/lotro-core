package delta.games.lotro.common.effects.display;

import delta.games.lotro.common.stats.StatModifiersComputer;
import delta.games.lotro.common.stats.StatValueProvider;

/**
 * Context for effect rendering.
 * @author DAM
 */
public class EffectRenderingContext
{
  private int _level;
  private StatModifiersComputer _statModifiersComputer;

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

  /**
   * Set the stat value provider.
   * @param statValueProvider Stat value provider to set.
   */
  public void setStatValueProvider(StatValueProvider statValueProvider)
  {
    if (statValueProvider!=null)
    {
      _statModifiersComputer=new StatModifiersComputer(statValueProvider);
    }
    else
    {
      _statModifiersComputer=null;
    }
  }

  /**
   * Get the stat modifiers computer.
   * @return A stat modifiers computer or <code>null</code> if not set.
   */
  public StatModifiersComputer getStatModifiersComputer()
  {
    return _statModifiersComputer;
  }
}
