package delta.games.lotro.common.enums.comparator;

import java.util.Comparator;

import delta.games.lotro.common.enums.LotroEnumEntry;

/**
 * Comparator for LOTRO enum entries, using their code.
 * @param <T> Type of entries.
 * @author DAM
 */
public class LotroEnumEntryCodeComparator<T extends LotroEnumEntry> implements Comparator<T>
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
    return Integer.compare(o1.getCode(),o2.getCode());
  }
}
