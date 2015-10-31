package delta.games.lotro.character.stats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import delta.games.lotro.character.CharacterStat.STAT;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Manager for derived statistics contributions.
 * @author DAM
 */
public class DerivatedStatsContributionsMgr
{
  private static class StatContributionFactor
  {
    private STAT _contributedStat;
    private FixedDecimalsInteger _factor;
  }

  private static class DerivedStatContributions
  {
    private List<StatContributionFactor> _factors;

    private DerivedStatContributions()
    {
      _factors=new ArrayList<StatContributionFactor>();
    }

    private void addStatContribution(STAT contributedStat, FixedDecimalsInteger factor)
    {
      StatContributionFactor contrib=new StatContributionFactor();
      contrib._contributedStat=contributedStat;
      contrib._factor=factor;
      _factors.add(contrib);
    }

    private List<StatContributionFactor> getFactors()
    {
      return _factors;
    }
  }

  private static class ClassDerivedStats
  {
    private HashMap<STAT,DerivedStatContributions> _contributions;
    private ClassDerivedStats()
    {
      _contributions=new HashMap<STAT,DerivedStatContributions>();
    }

    private void addStatContribution(STAT primaryStat, STAT contributedStat, FixedDecimalsInteger factor)
    {
      DerivedStatContributions contribs=_contributions.get(primaryStat);
      if (contribs==null)
      {
        contribs=new DerivedStatContributions();
        _contributions.put(primaryStat,contribs);
      }
      contribs.addStatContribution(contributedStat,factor);
    }

    private DerivedStatContributions getContrib(STAT primaryStat)
    {
      return _contributions.get(primaryStat);
    }
  }

  private HashMap<CharacterClass,ClassDerivedStats> _allContribs;

  /**
   * Constructor.
   */
  public DerivatedStatsContributionsMgr()
  {
    _allContribs=new HashMap<CharacterClass,ClassDerivedStats>();
  }

  /**
   * Set factor.
   * @param primaryStat Primary stat.
   * @param contributedStat Contributed stat.
   * @param cClass Character class;
   * @param factor Factor.
   */
  public void setFactor(STAT primaryStat, STAT contributedStat, CharacterClass cClass, FixedDecimalsInteger factor)
  {
    ClassDerivedStats derivedStats=_allContribs.get(cClass);
    if (derivedStats==null)
    {
      derivedStats=new ClassDerivedStats();
      _allContribs.put(cClass,derivedStats);
    }
    derivedStats.addStatContribution(primaryStat,contributedStat,factor);
  }

  /**
   * Get stats contribution for a set of stats.
   * @param cClass Targeted character class.
   * @param set Set of raw stats.
   * @return A set of stat contributions.
   */
  public BasicStatsSet getContribution(CharacterClass cClass, BasicStatsSet set)
  {
    ClassDerivedStats derivedStats=_allContribs.get(cClass);
    BasicStatsSet result=new BasicStatsSet();
    for(STAT stat : STAT.values())
    {
      FixedDecimalsInteger statValue=set.getStat(stat);
      if (statValue!=null)
      {
        DerivedStatContributions contrib=derivedStats.getContrib(stat);
        if (contrib!=null)
        {
          for(StatContributionFactor factor : contrib.getFactors())
          {
            FixedDecimalsInteger toAdd=new FixedDecimalsInteger(statValue);
            toAdd.multiply(factor._factor);
            result.addStat(factor._contributedStat,toAdd);
          }
        }
      }
    }
    return result;
  }
}
