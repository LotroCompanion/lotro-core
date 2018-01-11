package delta.games.lotro.lore.deeds.comparators;

import java.util.Comparator;

import delta.games.lotro.lore.deeds.DeedDescription;

/**
 * Comparator for deeds, using their identifier.
 * @author DAM
 */
public class DeedIdComparator implements Comparator<DeedDescription>
{
  public int compare(DeedDescription o1, DeedDescription o2)
  {
    int id1=o1.getIdentifier();
    int id2=o2.getIdentifier();
    return id1-id2;
  }
}
