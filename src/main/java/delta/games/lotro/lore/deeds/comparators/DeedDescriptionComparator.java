package delta.games.lotro.lore.deeds.comparators;

import java.util.Comparator;

import delta.games.lotro.lore.deeds.DeedDescription;

/**
 * Comparator for deeds, using their description.
 * @author DAM
 */
public class DeedDescriptionComparator implements Comparator<DeedDescription>
{
  public int compare(DeedDescription deed1, DeedDescription deed2)
  {
    String description1=deed1.getDescription();
    String description2=deed2.getDescription();
    return description1.compareTo(description2);
  }
}
