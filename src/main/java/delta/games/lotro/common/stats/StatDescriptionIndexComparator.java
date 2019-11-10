package delta.games.lotro.common.stats;

import java.util.Comparator;

/**
 * Comparator for stat descriptions, using their 'natural' order.
 * @author DAM
 */
public class StatDescriptionIndexComparator implements Comparator<StatDescription>
{
  @Override
  public int compare(StatDescription o1, StatDescription o2)
  {
    Integer index1=o1.getIndex();
    Integer index2=o2.getIndex();
    if (index1!=null)
    {
      if (index2!=null)
      {
        return Integer.compare(index1.intValue(),index2.intValue());
      }
      return -1;
    }
    if (index2!=null)
    {
      return 1;
    }
    return 0;
  }
}
