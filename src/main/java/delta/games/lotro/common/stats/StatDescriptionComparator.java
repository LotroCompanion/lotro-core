package delta.games.lotro.common.stats;

import java.util.Comparator;
import java.util.List;

/**
 * Comparator for stat descriptions, using their 'natural' order.
 * @author DAM
 */
public class StatDescriptionComparator implements Comparator<StatDescription>
{
  private List<StatDescription> _stats=StatsRegistry.getInstance().getAll();
  public int compare(StatDescription o1, StatDescription o2)
  {
    int id1=_stats.indexOf(o1);
    int id2=_stats.indexOf(o2);
    return id1-id2;
  }
}
