package delta.games.lotro.lore.items.legendary.relics.comparators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import delta.common.utils.collections.CompoundComparator;
import delta.games.lotro.lore.items.legendary.relics.Relic;

/**
 * Relics sorting utilities.
 * @author DAM
 */
public class RelicsSorter
{
  /**
   * Sort relics for internal use.
   * @param relics Relics to sort.
   */
  public static void sortStatsForInternalUse(List<Relic> relics)
  {
    List<Comparator<Relic>> comparators=new ArrayList<Comparator<Relic>>();
    comparators.add(new RelicTypeComparator());
    comparators.add(new RelicNameComparator());
    CompoundComparator<Relic> c=new CompoundComparator<Relic>(comparators);
    Collections.sort(relics,c);
  }
}
