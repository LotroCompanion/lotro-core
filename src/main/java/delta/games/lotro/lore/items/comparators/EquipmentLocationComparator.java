package delta.games.lotro.lore.items.comparators;

import java.util.Comparator;

import delta.games.lotro.lore.items.EquipmentLocation;

/**
 * Comparator for item qualities.
 * @author DAM
 */
public class EquipmentLocationComparator implements Comparator<EquipmentLocation>
{
  public int compare(EquipmentLocation location1, EquipmentLocation location2)
  {
    if (location1!=null)
    {
      if (location2!=null)
      {
        return location1.getLabel().compareTo(location2.getLabel());
      }
      return 1;
    }
    return (location2!=null)?-1:0;
  }
}
