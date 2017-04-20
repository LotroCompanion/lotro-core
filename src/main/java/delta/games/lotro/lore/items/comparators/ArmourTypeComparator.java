package delta.games.lotro.lore.items.comparators;

import java.util.Comparator;

import delta.games.lotro.lore.items.ArmourType;

/**
 * Comparator for armour types.
 * @author DAM
 */
public class ArmourTypeComparator implements Comparator<ArmourType>
{
  public int compare(ArmourType type1, ArmourType type2)
  {
    if (type1!=null)
    {
      if (type2!=null)
      {
        return type1.getCode()-type2.getCode();
      }
      return 1;
    }
    return (type2!=null)?-1:0;
  }
}
