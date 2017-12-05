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
   * @param profession1 First profession.
   * @param profession2 Second profession.
   * @return Result of the comparison of the labels of the given professions.
   */
  public int compare(Profession profession1, Profession profession2)
  {
    return profession1.getLabel().compareTo(profession2.getLabel());
  }
}
