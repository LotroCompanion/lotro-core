package delta.games.lotro.character.comparators;

import java.util.Comparator;

import delta.games.lotro.character.BaseCharacterSummary;

/**
 * Comparator for character summaries using the character account name.
 * @author DAM
 * @param <T> Actual type of compared objects.
 */
public class CharacterAccountNameComparator<T extends BaseCharacterSummary> implements Comparator<T>
{
  @Override
  public int compare(T o1, T o2)
  {
    String account1=o1.getAccountName();
    String account2=o2.getAccountName();
    return account1.compareTo(account2);
  }
}
