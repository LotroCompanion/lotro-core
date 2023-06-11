package delta.games.lotro.lore.items.legendary.relics.comparators;

import java.util.Collections;
import java.util.List;

import delta.games.lotro.common.comparators.NamedComparator;
import delta.games.lotro.lore.items.legendary.relics.Relic;
import delta.games.lotro.lore.items.legendary.relics.RelicType;
import delta.games.lotro.utils.DataProvider;
import delta.games.lotro.utils.comparators.DelegatingComparator;

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
  public static void sortRelicsForInternalUse(List<Relic> relics)
  {
    Collections.sort(relics,new NamedComparator());
    DataProvider<Relic,RelicType> p=new DataProvider<Relic,RelicType>()
    {
      @Override
      public RelicType getData(Relic r)
      {
        return r.getTypes().get(0);
      }
    };
    DelegatingComparator<Relic,RelicType> c=new DelegatingComparator<Relic,RelicType>(p,new RelicTypeComparator());
    Collections.sort(relics,c);
  }
}
