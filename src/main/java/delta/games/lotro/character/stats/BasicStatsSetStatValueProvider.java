package delta.games.lotro.character.stats;

import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatValueProvider;

/**
 * A <code>StatValueProvider</code> that is ruled by a <code>BasicStatsSet</code>.
 * @author DAM
 */
public class BasicStatsSetStatValueProvider implements StatValueProvider
{
  private BasicStatsSet _stats;

  /**
   * Constructor.
   * @param stats Stats to use.
   */
  public BasicStatsSetStatValueProvider(BasicStatsSet stats)
  {
    _stats=stats;
  }

  @Override
  public float getStat(StatDescription stat)
  {
    Number value=_stats.getStat(stat);
    return (value!=null)?value.floatValue():0;
  }
}
