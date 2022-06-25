package delta.games.lotro.character.stats.computer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.StatsSetElement;
import delta.games.lotro.character.stats.contribs.StatsContribution;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatOperator;

/**
 * Computes absolute values for 'multiply' contribs.
 * @author DAM
 */
public class MultiplyContribsComputer
{
  /**
   * Update multiply contribs to use 'add' contribs.
   * @param statsNoMultiply Stats before multiply contribs.
   * @param contribs Contribs to update.
   */
  public void handleMultiplyContribs(BasicStatsSet statsNoMultiply, List<StatsContribution> contribs)
  {
    Map<Integer,MultiplyContribsForStat> map=new HashMap<Integer,MultiplyContribsForStat>();
    for(StatsContribution contrib : contribs)
    {
      for(StatsSetElement element : contrib.getStats().getStatElements())
      {
        StatOperator operator=element.getOperator();
        if (operator==StatOperator.MULTIPLY)
        {
          StatDescription stat=element.getStat();
          Integer key=Integer.valueOf(stat.getIdentifier());
          MultiplyContribsForStat contribsForStat=map.get(key);
          if (contribsForStat==null)
          {
            contribsForStat=new MultiplyContribsForStat(stat);
            map.put(key,contribsForStat);
          }
          contribsForStat.addContrib(contrib);
        }
      }
    }
    for(MultiplyContribsForStat contribsForStat : map.values())
    {
      StatDescription stat=contribsForStat.getStat();
      Number statValue=statsNoMultiply.getStat(stat);
      if (statValue!=null)
      {
        contribsForStat.compute(statValue.floatValue());
      }
    }
  }
}
