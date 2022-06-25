package delta.games.lotro.character.stats.computer;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.StatsSetElement;
import delta.games.lotro.character.stats.contribs.StatsContribution;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatOperator;

/**
 * Manages a set of multiply contribs for a single stat.
 * @author DAM
 */
public class MultiplyContribsForStat
{
  private StatDescription _stat;
  private List<StatsContribution> _contribs;

  /**
   * Constructor.
   * @param stat Targeted stat.
   */
  public MultiplyContribsForStat(StatDescription stat)
  {
    _stat=stat;
    _contribs=new ArrayList<StatsContribution>();
  }

  /**
   * Get the targeted stat.
   * @return the targeted stat.
   */
  public StatDescription getStat()
  {
    return _stat;
  }

  /**
   * Add a stats contrib.
   * @param contrib
   */
  public void addContrib(StatsContribution contrib)
  {
    _contribs.add(contrib);
  }

  /**
   * Compute/replace 'add' contrib for each 'multiply' contrib.
   * @param totalContrib
   */
  public void compute(float totalContrib)
  {
    List<Float> factors=getFactors();
    float globalFactor=getGlobalFactor(factors);
    float totalFactors=getTotalFactors(factors);
    for(StatsContribution contrib : _contribs)
    {
      BasicStatsSet stats=contrib.getStats();
      StatsSetElement element=stats.findElement(_stat);
      float factor=element.getValue().floatValue();
      float contribFactor=((factor-1)*(globalFactor-1))/(totalFactors-factors.size());
      float partialContrib=totalContrib*contribFactor;
      stats.setStat(_stat,partialContrib);
    }
  }

  private float getGlobalFactor(List<Float> factors)
  {
    int nbFactors=factors.size();
    if (nbFactors==1)
    {
      return factors.get(0).floatValue();
    }
    float ret=factors.get(0).floatValue();
    for(int i=1;i<nbFactors;i++)
    {
      float factor=factors.get(i).floatValue();
      ret*=factor;
    }
    return ret;
  }

  private float getTotalFactors(List<Float> factors)
  {
    float ret=0;
    for(Float factor : factors)
    {
      ret+=factor.floatValue();
    }
    return ret;
  }

  private List<Float> getFactors()
  {
    List<Float> ret=new ArrayList<Float>();
    for(StatsContribution contrib : _contribs)
    {
      StatsSetElement element=contrib.getStats().findElement(_stat);
      if (element!=null)
      {
        StatOperator operator=element.getOperator();
        if (operator==StatOperator.MULTIPLY)
        {
          ret.add(Float.valueOf(element.getFloatValue()));
        }
      }
    }
    return ret;
  }
}
