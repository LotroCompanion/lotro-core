package delta.games.lotro.common.stats;

import delta.games.lotro.character.stats.STAT;

/**
 * Base class for stat providers.
 * @author DAM
 */
public abstract class AbstractStatProvider implements StatProvider
{
  private StatOperator _operator;
  private STAT _stat;

  /**
   * Constructor.
   * @param stat Targeted stat.
   */
  public AbstractStatProvider(STAT stat)
  {
    _stat=stat;
    _operator=StatOperator.ADD;
  }

  @Override
  public STAT getStat()
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
