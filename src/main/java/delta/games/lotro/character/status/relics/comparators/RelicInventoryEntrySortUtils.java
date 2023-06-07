package delta.games.lotro.character.status.relics.comparators;

import java.util.Collections;
import java.util.List;

import delta.games.lotro.character.status.relics.RelicsInventoryEntry;
import delta.games.lotro.common.Named;
import delta.games.lotro.common.comparators.NamedComparator;
import delta.games.lotro.lore.items.legendary.relics.Relic;
import delta.games.lotro.utils.DataProvider;
import delta.games.lotro.utils.comparators.DelegatingComparator;

/**
 * Utility methods to sort relic inventory entries.
 * @author DAM
 */
public class RelicInventoryEntrySortUtils
{
  /**
   * Sort relic inventory entries using the relic name.
   * @param entries Elements to sort.
   */
  public static void sortByName(List<RelicsInventoryEntry> entries)
  {
    DataProvider<RelicsInventoryEntry,Named> p=new DataProvider<RelicsInventoryEntry,Named>()
    {
      public Relic getData(RelicsInventoryEntry entry)
      {
        return entry.getRelic();
      }
    };
    DelegatingComparator<RelicsInventoryEntry,Named> c=new DelegatingComparator<RelicsInventoryEntry,Named>(p,new NamedComparator());
    Collections.sort(entries,c);
  }
}
