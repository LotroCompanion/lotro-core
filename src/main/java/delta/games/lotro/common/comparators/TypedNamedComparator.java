package delta.games.lotro.common.comparators;

import java.util.Comparator;

import delta.games.lotro.common.Named;

/**
 * Comparator for Named, using their name (typed version).
 * @param <T> Type of managed objects.
 * @author DAM
 */
public class TypedNamedComparator<T extends Named> implements Comparator<T>
{
  @Override
  public int compare(T o1, T o2)
  {
    String name1=(o1!=null)?o1.getName():null;
    String name2=(o2!=null)?o2.getName():null;
    if (name1==null)
    {
      return (name2==null)?0:-1;
    }
    if (name2==null)
    {
      return 1;
    }
    return name1.compareTo(name2);
  }
}
