package delta.games.lotro.common.id;

import java.util.Comparator;

/**
 * Comparator for internal game IDs.
 * @author DAM
 */
public class InternalGameIdComparator implements Comparator<InternalGameId>
{
  @Override
  public int compare(InternalGameId o1, InternalGameId o2)
  {
    if (o1==null)
    {
      return (o2==null)?0:-1;
    }
    if (o2==null)
    {
      return 1;
    }
    return Long.compare(o1.asLong(),o2.asLong());
  }
}
