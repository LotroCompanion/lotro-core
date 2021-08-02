package delta.games.lotro.character.status.titles.comparators;

import java.util.Comparator;

import delta.games.lotro.character.status.titles.TitleStatus;

/**
 * Comparator for title statuses, using their acquisition timestamp.
 * @author DAM
 */
public class TitleAcquisitionTimestampComparator implements Comparator<TitleStatus>
{
  @Override
  public int compare(TitleStatus o1, TitleStatus o2)
  {
    Double ts1=o1.getAcquisitionTimeStamp();
    Double ts2=o2.getAcquisitionTimeStamp();
    if (ts1==null)
    {
      return (ts2==null)?0:-1;
    }
    return (ts2!=null)?Double.compare(ts1.doubleValue(),ts2.doubleValue()):1;
  }
}
