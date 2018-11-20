package delta.games.lotro.common;

import java.util.Comparator;

/**
 * Comparator for identifiable items, using their... identifier.
 * @param <T> Type of managed data.
 * @author DAM
 */
public class IdentifiableComparator<T extends Identifiable> implements Comparator<T>
{
  public int compare(T o1, T o2)
  {
    int id1=o1.getIdentifier();
    int id2=o2.getIdentifier();
    return id1-id2;
  }
}
