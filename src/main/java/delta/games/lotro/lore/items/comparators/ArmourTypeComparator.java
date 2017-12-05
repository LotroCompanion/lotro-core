package delta.games.lotro.lore.items.comparators;

import java.util.Comparator;

import delta.games.lotro.lore.items.ArmourType;

/**
 * Comparator for armour types.
 * @author DAM
 */
public class ArmourTypeComparator implements Comparator<ArmourType>
{
  /**
   * Compare two armour types.
   * @param type1 First type.
   * @param type2 Second type.
   * @return <ul>
   * <li><code>-1</code> if type1 is lighter than type2,
   * <li><code>0</code> if both types are equal or both are <code>null</code>,
   * <li><code>1</code> if type1 is heavier than type2.
   * </ul>
   * Note that shield armour types are considered higher than regular armour types.
   */
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
