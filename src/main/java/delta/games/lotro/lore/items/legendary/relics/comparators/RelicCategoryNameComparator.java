package delta.games.lotro.lore.items.legendary.relics.comparators;

import java.util.Comparator;

import delta.games.lotro.lore.items.legendary.relics.RelicsCategory;

/**
 * Comparator for relic categories using their name.
 * @author DAM
 */
public class RelicCategoryNameComparator implements Comparator<RelicsCategory>
{
  public int compare(RelicsCategory rc1, RelicsCategory rc2)
  {
    return rc1.getName().compareTo(rc2.getName());
  }
}
