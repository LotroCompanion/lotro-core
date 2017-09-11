package delta.games.lotro.crafting;

import java.util.Comparator;

/**
 * Professions comparator.
 * @author DAM
 */
public class ProfessionComparator implements Comparator<Profession>
{
  /**
   * Compare 2 professions using their label. 
   */
  public int compare(Profession p1, Profession p2)
  {
    return p1.getLabel().compareTo(p2.getLabel());
  }
}
