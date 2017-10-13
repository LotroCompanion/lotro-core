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
   */
  public int compare(Vocation vocation1, Vocation vocation2)
  {
    return vocation1.getName().compareTo(vocation2.getName());
  }
}
