package delta.games.lotro.lore.deeds.comparators;

import java.util.Comparator;

import delta.games.lotro.lore.deeds.DeedDescription;

/**
 * Comparator for deeds, using their name.
 * @author DAM
 */
public class DeedNameComparator implements Comparator<DeedDescription>
{
  public int compare(DeedDescription deed1, DeedDescription deed2)
  {
    String name1=deed1.getName();
    String name2=deed2.getName();
    return name1.compareTo(name2);
  }
}
