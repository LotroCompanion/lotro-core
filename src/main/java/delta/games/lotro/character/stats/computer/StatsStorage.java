package delta.games.lotro.character.stats.computer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.StatsSetElement;
import delta.games.lotro.character.stats.contribs.StatsContribution;
import delta.games.lotro.common.stats.StatOperator;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Stats storage.
 * @author DAM
 */
public class StatsStorage
{
  private List<StatsContribution> _contribs;

  /**
   * Constructor.
   */
  public StatsStorage()
  {
    _contribs=new ArrayList<StatsContribution>();
  }

  /**
   * Add a contribution from a source.
   * @param contrib Contribution to add.
   */
  public void addContrib(StatsContribution contrib)
  {
    _contribs.add(contrib);
  }

  /**
   * Aggregate stats.
   * @return the aggregated stats.
   */
  public BasicStatsSet aggregate()
  {
    List<StatsSetElement> adds=getStats(StatOperator.ADD);
    BasicStatsSet ret=sumStats(adds);
    //System.out.println("Add stats: "+addStats);
    // Multiply
    List<StatsSetElement> multiply=getStats(StatOperator.MULTIPLY);
    Map<Integer,Float> multiplyFactors=multiplyStats(multiply);
    //System.out.println("Multiply stats: "+multiplyStats);
    applyMultiply(ret,multiplyFactors);
    // Set
    List<StatsSetElement> set=getStats(StatOperator.SET);
    BasicStatsSet setStats=sumStats(set);
    ret=applySet(ret,setStats);
    //System.out.println("Result stats: "+ret);
    return ret;
  }

  private void applyMultiply(BasicStatsSet source, Map<Integer,Float> multiplyFactors)
  {
    for(StatsSetElement element : source.getStatElements())
    {
      Integer key=Integer.valueOf(element.getStat().getIdentifier());
      Float factor=multiplyFactors.get(key);
      if (factor!=null)
      {
        float value=element.getValue().floatValue();
        element.setValue(new FixedDecimalsInteger(value*factor.floatValue()));
      }
    }
  }

  private BasicStatsSet applySet(BasicStatsSet source, BasicStatsSet set)
  {
    BasicStatsSet ret=new BasicStatsSet(source);
    for(StatsSetElement element : set.getStatElements())
    {
      ret.setStat(element.getStat(),element.getValue());
    }
    return ret;
  }

  private BasicStatsSet sumStats(List<StatsSetElement> elements)
  {
    BasicStatsSet ret=new BasicStatsSet();
    for(StatsSetElement element : elements)
    {
      ret.addStat(element);
    }
    return ret;
  }

  private Map<Integer,Float> multiplyStats(List<StatsSetElement> elements)
  {
    Map<Integer,Float> ret=new HashMap<Integer,Float>();
    for(StatsSetElement element : elements)
    {
      float value=element.getValue().floatValue();
      Integer key=Integer.valueOf(element.getStat().getIdentifier());
      Float oldValue=ret.get(key);
      if (oldValue!=null)
      {
        ret.put(key,Float.valueOf(oldValue.floatValue()*value));
      }
      else
      {
        ret.put(key,Float.valueOf(value));
      }
    }
    return ret;
  }

  private List<StatsSetElement> getStats(StatOperator operator)
  {
    List<StatsSetElement> ret=new ArrayList<StatsSetElement>();
    for(StatsContribution contrib : _contribs)
    {
      BasicStatsSet stats=contrib.getStats();
      for(StatsSetElement element : stats.getStatElements())
      {
        if (element.getOperator()==operator)
        {
          ret.add(element);
        }
      }
    }
    return ret;
  }
}
