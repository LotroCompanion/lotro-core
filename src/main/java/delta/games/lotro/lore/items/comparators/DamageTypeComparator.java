package delta.games.lotro.lore.items.comparators;

import java.util.Comparator;

import delta.games.lotro.lore.items.DamageType;

/**
 * Comparator for damage types.
 * @author DAM
 */
public class DamageTypeComparator implements Comparator<DamageType>
{
  public int compare(DamageType type1, DamageType type2)
  {
    return type1.getName().compareTo(type2.getName());
  }
}
