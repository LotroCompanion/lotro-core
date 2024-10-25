package delta.games.lotro.common.effects.display;

import delta.games.lotro.common.enums.DamageQualifier;
import delta.games.lotro.common.enums.ImplementUsageType;
import delta.games.lotro.common.stats.StatComputerContext;
import delta.games.lotro.common.stats.StatModifiersComputer;
import delta.games.lotro.common.stats.StatValueProvider;

/**
 * Context for effect rendering.
 * @author DAM
 */
public class EffectRenderingContext implements StatComputerContext
{
  private int _level;
  private StatModifiersComputer _statModifiersComputer;
  private DamageQualifier _damageQualifier;
  private ImplementUsageType _implementUsage;

  /**
   * Constructor.
   * @param level Level for computations.
   */
  public EffectRenderingContext(int level)
  {
    _level=level;
  }

  @Override
  public int getTier()
  {
    return 1;
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

  /**
   * Get the damage qualifier.
   * @return a damage qualifier or <code>null</code>.
   */
  public DamageQualifier getDamageQualifier()
  {
    return _damageQualifier;
  }

  /**
   * Set the damage qualifier.
   * @param damageQualifier the damage qualifier to set.
   */
  public void setDamageQualifier(DamageQualifier damageQualifier)
  {
    _damageQualifier=damageQualifier;
  }

  /**
   * Get the implement usage.
   * @return an implement usage or <code>null</code>.
   */
  public ImplementUsageType getImplementUsage()
  {
    return _implementUsage;
  }

  /**
   * Set the implement usage.
   * @param implementUsage the implement usage to set.
   */
  public void setImplementUsage(ImplementUsageType implementUsage)
  {
    _implementUsage=implementUsage;
  }
}
