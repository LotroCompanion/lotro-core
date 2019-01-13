package delta.games.lotro.common.stats;

/**
 * Base class for stat providers.
 * @author DAM
 */
public abstract class AbstractStatProvider implements StatProvider
{
  private StatOperator _operator;
  private StatDescription _stat;

  /**
   * Constructor.
   * @param stat Targeted stat.
   */
  public AbstractStatProvider(StatDescription stat)
  {
    _stat=stat;
    _operator=StatOperator.ADD;
  }

  @Override
  public StatDescription getStat()
  {
    return _stat;
  }

  @Override
  public StatOperator getOperator()
  {
    return _operator;
  }

  @Override
  public void setOperator(StatOperator operator)
  {
    _operator=operator;
  }
}
