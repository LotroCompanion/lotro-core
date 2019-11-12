package delta.games.lotro.lore.items.legendary.relics.comparators;

import java.util.Comparator;

import delta.games.lotro.lore.items.legendary.relics.Relic;

/**
 * Relic comparator using their name.
 * @author DAM
 */
public class RelicNameComparator implements Comparator<Relic>
{
  public int compare(Relic r1, Relic r2)
  {
    return r1.getName().compareTo(r2.getName());
  }
}
