package delta.games.lotro.character.status.achievables;

import java.util.Comparator;

/**
 * Comparator for achievable statuses, using their date.
 * @author DAM
 */
public class AchievableStatusDateComparator implements Comparator<AchievableStatus>
{
  @Override
  public int compare(AchievableStatus o1, AchievableStatus o2)
  {
    Long date1=o1.getCompletionDate();
    Long date2=o2.getCompletionDate();
    if (date1==null)
    {
      return (date2!=null)?-1:0;
    }
    if (date2==null)
    {
      return 1;
    }
    return date1.compareTo(date2);
  }
}
