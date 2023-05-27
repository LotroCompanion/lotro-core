package delta.games.lotro.common.enums.comparator;

import java.util.Comparator;

import delta.games.lotro.common.enums.LotroEnumEntry;

/**
 * Comparator for LOTRO enum entries, using their key.
 * @param <T> Type of entries.
 * @author DAM
 */
public class LotroEnumEntryKeyComparator<T extends LotroEnumEntry> implements Comparator<T>
{
  @Override
  public int compare(T o1, T o2)
  {
    if (o1==null)
    {
      if (o2==null)
      {
        return 0;
      }
      return -1;
    }
    if (o2==null)
    {
      return 1;
    }
    String key1=o1.getKey();
    String key2=o2.getKey();
    if (key1==null)
    {
      if (key2==null)
      {
        return 0;
      }
      return -1;
    }
    if (key2==null)
    {
      return 1;
    }
    return key1.compareTo(key2);
  }
}
