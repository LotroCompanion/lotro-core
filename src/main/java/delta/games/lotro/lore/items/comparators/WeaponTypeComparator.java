package delta.games.lotro.lore.items.comparators;

import java.util.Comparator;

import delta.games.lotro.lore.items.WeaponType;

/**
 * Comparator for weapon types.
 * @author DAM
 */
public class WeaponTypeComparator implements Comparator<WeaponType>
{
  public int compare(WeaponType type1, WeaponType type2)
  {
    if (type1!=null)
    {
      if (type2!=null)
      {
        return type1.getKey().compareTo(type2.getKey());
      }
      return 1;
    }
    return (type2!=null)?-1:0;
  }
}
