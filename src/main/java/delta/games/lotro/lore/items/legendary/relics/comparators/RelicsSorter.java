package delta.games.lotro.lore.items.legendary.relics.comparators;

import java.util.Collections;
import java.util.List;

import delta.games.lotro.common.comparators.NamedComparator;
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
    Collections.sort(relics,new NamedComparator());
    Collections.sort(relics,new RelicTypeComparator());
  }
}
