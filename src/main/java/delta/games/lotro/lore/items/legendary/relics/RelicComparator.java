package delta.games.lotro.lore.items.legendary.relics;

import java.util.Comparator;

/**
 * Relic comparator.
 * Uses type then name.
 * @author DAM
 */
public class RelicComparator implements Comparator<Relic>
{
  public int compare(Relic r1, Relic r2)
  {
    RelicType type1=r1.getType();
    RelicType type2=r2.getType();
    int typeDiff=type1.ordinal()-type2.ordinal();
    if (typeDiff!=0)
    {
      return typeDiff;
    }
    return r1.getName().compareTo(r2.getName());
  }
}
