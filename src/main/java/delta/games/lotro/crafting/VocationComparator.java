package delta.games.lotro.crafting;

import java.util.Comparator;

/**
 * Vocations comparator.
 * @author DAM
 */
public class VocationComparator implements Comparator<Vocation>
{
  /**
   * Compare 2 vocations using their label.
   * @param vocation1 First vocation.
   * @param vocation2 Second vocation.
   * @return Result of the comparison of the names of the given vocations.
   */
  public int compare(Vocation vocation1, Vocation vocation2)
  {
    return vocation1.getName().compareTo(vocation2.getName());
  }
}
