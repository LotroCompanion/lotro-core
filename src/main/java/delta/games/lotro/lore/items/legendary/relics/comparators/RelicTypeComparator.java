package delta.games.lotro.lore.items.legendary.relics.comparators;

import java.util.Comparator;

import delta.games.lotro.lore.items.legendary.relics.Relic;
import delta.games.lotro.lore.items.legendary.relics.RelicType;

/**
 * Relic comparator using their first type.
 * @author DAM
 */
public class RelicTypeComparator implements Comparator<Relic>
{
  public int compare(Relic r1, Relic r2)
  {
    RelicType type1=r1.getTypes().get(0);
    RelicType type2=r2.getTypes().get(0);
    int typeDiff=type1.ordinal()-type2.ordinal();
    return typeDiff;
  }
}
