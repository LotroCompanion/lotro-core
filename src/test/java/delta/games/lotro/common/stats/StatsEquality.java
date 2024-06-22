package delta.games.lotro.common.stats;

import java.util.List;
import java.util.Objects;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.StatsSetElement;

/**
 * Stats equality utilities.
 * @author DAM
 */
public class StatsEquality
{
  /**
   * Check the equality of 2 stats sets.
   * @param stats1 Stats set 1.
   * @param stats2 Stats set 2.
   */
  public static void checkStats(BasicStatsSet stats1, BasicStatsSet stats2)
  {
    if (!equals(stats1,stats2))
    {
      System.out.println("1: "+stats1);
      System.out.println("2: "+stats2);
    }
  }

  /**
   * Check the equality of 2 stats sets.
   * @param stats1 Stats set 1.
   * @param stats2 Stats set 2.
   * @return <code>true</code> if equal, <code>false</code> otherwise.
   */
  private static boolean equals(BasicStatsSet stats1, BasicStatsSet stats2)
  {
    int n1=stats1.getStatsCount();
    List<StatsSetElement> elements1=stats1.getStatElements();
    for(int i=0;i<n1;i++)
    {
      StatsSetElement element1=elements1.get(i);
      StatsSetElement element2=stats2.findElement(element1.getStat());
      float value1=element1.getFloatValue();
      float value2=(element2!=null)?element2.getFloatValue():0;
      if (Math.abs(value1-value2)>0.01)
      {
        System.out.println("Diff is "+Math.abs(value1-value2)+" for stat: "+element1.getStat());
        System.out.println("old="+value1+", new="+value2);
        return false;
      }
      String desc1=element1.getDescriptionOverride();
      String desc2=(element2!=null)?element2.getDescriptionOverride():null;
      if (!Objects.equals(desc1,desc2))
      {
        return false;
      }
    }
    return true;
  }
}
