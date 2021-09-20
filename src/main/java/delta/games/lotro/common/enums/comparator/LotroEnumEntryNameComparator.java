package delta.games.lotro.common.enums.comparator;

import java.util.Comparator;

import delta.games.lotro.common.enums.LotroEnumEntry;

/**
 * Comparator for LOTRO enum entries, using their name/label.
 * @param <T> Type of entries.
 * @author DAM
 */
public class LotroEnumEntryNameComparator<T extends LotroEnumEntry> implements Comparator<T>
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
    String name1=o1.getLabel();
    String name2=o2.getLabel();
    return name1.compareTo(name2);
  }
}
