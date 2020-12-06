package delta.games.lotro.lore.maps.comparators;

import java.util.Comparator;

import delta.games.lotro.lore.maps.AbstractMap;

/**
 * Comparator for abstract maps, using their name.
 * @author DAM
 * @param <T> Type of maps.
 */
public class AbstractMapNameComparator<T extends AbstractMap> implements Comparator<T>
{
  @Override
  public int compare(T o1, T o2)
  {
    String name1=o1.getName();
    String name2=o2.getName();
    return name1.compareTo(name2);
  }
}
