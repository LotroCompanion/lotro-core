package delta.games.lotro.lore.items.legendary.relics.comparators;

import java.util.Comparator;

import delta.games.lotro.lore.items.legendary.relics.RelicsCategory;

/**
 * Comparator for relic categories using their category code.
 * @author DAM
 */
public class RelicCategoryCodeComparator implements Comparator<RelicsCategory>
{
  public int compare(RelicsCategory rc1, RelicsCategory rc2)
  {
    return Integer.compare(rc1.getCategoryCode(),rc2.getCategoryCode());
  }
}
