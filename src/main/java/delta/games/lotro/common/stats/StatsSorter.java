package delta.games.lotro.common.stats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import delta.common.utils.collections.CompoundComparator;
import delta.games.lotro.common.comparators.TypedNamedComparator;

/**
 * Stats sorting utilities.
 * @author DAM
 */
public class StatsSorter
{
  /**
   * Sort stats for UI use.
   * @param stats Stats to sort.
   */
  public static void sortStatsForUi(List<StatDescription> stats)
  {
    List<Comparator<StatDescription>> comparators=new ArrayList<Comparator<StatDescription>>();
    comparators.add(new StatDescriptionIndexComparator());
    comparators.add(new TypedNamedComparator<StatDescription>());
    CompoundComparator<StatDescription> c=new CompoundComparator<StatDescription>(comparators);
    Collections.sort(stats,c);
  }
}
