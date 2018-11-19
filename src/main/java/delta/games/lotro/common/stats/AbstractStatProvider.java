package delta.games.lotro.common.stats;

import delta.games.lotro.character.stats.STAT;

/**
 * Base class for stat providers.
 * @author DAM
 */
public abstract class AbstractStatProvider implements StatProvider
{
  private STAT _stat;

  /**
   * Constructor.
   * @param stat Targeted stat.
   */
  public AbstractStatProvider(STAT stat)
  {
    _stat=stat;
  }

  @Override
  public STAT getStat()
  {
    return _stat;
  }
}
