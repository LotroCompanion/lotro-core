package delta.games.lotro.common.stats;

/**
 * Simple context for the stat computer.
 * @author DAM
 */
public class SimpleStatComputerContext implements StatComputerContext
{
  private int _tier;
  private int _level;
  private StatModifiersComputer _statModifiersComputer;

  /**
   * Constructor.
   * @param tier Tier to use.
   * @param level Level to use.
   */
  public SimpleStatComputerContext(int tier, int level)
  {
    _tier=tier;
    _level=level;
  }

  @Override
  public int getLevel()
  {
    return _level;
  }

  /**
   * Set the level to use.
   * @param level Level to use.
   */
  public void setLevel(int level)
  {
    _level=level;
  }

  @Override
  public int getTier()
  {
    return _tier;
  }

  /**
   * Set the stat value provider.
   * @param statValueProvider A stat value provider.
   */
  public void setStatValueProvider(StatValueProvider statValueProvider)
  {
    _statModifiersComputer=new StatModifiersComputer(statValueProvider);
  }

  @Override
  public StatModifiersComputer getStatModifiersComputer()
  {
    return _statModifiersComputer;
  }
}
