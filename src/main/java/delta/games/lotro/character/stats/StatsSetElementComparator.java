package delta.games.lotro.character.stats;

import java.util.Comparator;

import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatDescriptionComparator;

/**
 * Comparator for stats set elements, using the natural order of stats.
 * @author DAM
 */
public class StatsSetElementComparator implements Comparator<StatsSetElement>
{
  private StatDescriptionComparator _statComparator;

  /**
   * Constructor.
   */
  public StatsSetElementComparator()
  {
    _statComparator=new StatDescriptionComparator();
  }

  @Override
  public int compare(StatsSetElement o1, StatsSetElement o2)
  {
    StatDescription stat1=o1.getStat();
    StatDescription stat2=o2.getStat();
    return _statComparator.compare(stat1,stat2);
  }
}
