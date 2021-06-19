package delta.games.lotro.character.comparators;

import java.util.Comparator;

import delta.games.lotro.character.BaseCharacterSummary;

/**
 * Comparator for character summaries using the character server.
 * @author DAM
 * @param <T> Actual type of compared objects.
 */
public class CharacterServerComparator<T extends BaseCharacterSummary> implements Comparator<T>
{
  @Override
  public int compare(T o1, T o2)
  {
    String server1=o1.getServer();
    String server2=o2.getServer();
    return server1.compareTo(server2);
  }
}
