package delta.games.lotro.character.status.achievables.comparators;

import java.util.Comparator;

import delta.games.lotro.character.status.achievables.Progress;

/**
 * Comparator for progress.
 * @author DAM
 */
public class ProgressComparator implements Comparator<Progress>
{
  @Override
  public int compare(Progress o1, Progress o2)
  {
    if (o1==null)
    {
      return (o2==null)?0:-1;
    }
    if (o2==null)
    {
      return 1;
    }
    int percentageDiff=o1.getPercentage()-o2.getPercentage();
    if (percentageDiff!=0)
    {
      return percentageDiff;
    }
    return o1.getMax()-o2.getMax();
  }
}
