package delta.games.lotro.lore.crafting;

import java.util.Comparator;

/**
 * Professions comparator.
 * @author DAM
 */
public class ProfessionComparator implements Comparator<Profession>
{
  /**
   * Compare 2 professions using their name.
   * @param profession1 First profession.
   * @param profession2 Second profession.
   * @return Result of the comparison of the names of the given professions.
   */
  public int compare(Profession profession1, Profession profession2)
  {
    if (profession1!=null)
    {
      if (profession2!=null)
      {
        return profession1.getName().compareTo(profession2.getName());
      }
      return 1;
    }
    return (profession2!=null)?-1:0;
  }
}
