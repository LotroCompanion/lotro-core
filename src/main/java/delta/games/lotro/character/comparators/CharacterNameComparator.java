package delta.games.lotro.character.comparators;

import java.util.Comparator;

import delta.games.lotro.character.BaseCharacterSummary;

/**
 * Comparator for character summaries using the character name.
 * @author DAM
 * @param <T> Actual type of compared objects.
 */
public class CharacterNameComparator<T extends BaseCharacterSummary> implements Comparator<T>
{
  @Override
  public int compare(T o1, T o2)
  {
    String name1=o1.getName();
    String name2=o2.getName();
    return name1.compareTo(name2);
  }
}
