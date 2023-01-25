package delta.games.lotro.character.stats.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.computer.StatsStorage;
import delta.games.lotro.character.stats.contribs.StatsContribution;
import delta.games.lotro.common.comparators.NamedComparator;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatDescriptionComparator;
import delta.games.lotro.utils.NumericUtils;

/**
 * Manager for derived statistics contributions.
 * @author DAM
 */
public final class DerivedStatsContributionsMgr
{
  private static final Logger LOGGER=Logger.getLogger(DerivedStatsContributionsMgr.class);

  /**
   * Contribution for a derived stat.
   * @author DAM
   */
  public static class DerivedStatContribution
  {
    private StatDescription _contributedStat;
    private Number _factor;

    /**
     * Get the target stat.
     * @return the target stat.
     */
    public StatDescription getTargetStat()
    {
      return _contributedStat;
    }

    /**
     * Get the factor.
     * @return the factor.
     */
    public Number getFactor()
    {
      return _factor;
    }

    @Override
    public String toString()
    {
      return _contributedStat.getName()+"x"+_factor;
    }
  }

  /**
   * Contributions for a single source stat.
   * @author DAM
   */
  public static final class StatContributions
  {
    private List<DerivedStatContribution> _factors;

    private StatContributions()
    {
      _factors=new ArrayList<DerivedStatContribution>();
    }

    private void addStatContribution(StatDescription contributedStat, Number factor)
    {
      DerivedStatContribution contrib=new DerivedStatContribution();
      contrib._contributedStat=contributedStat;
      contrib._factor=factor;
      _factors.add(contrib);
    }

    /**
     * Get contributions.
     * @return a list of contributions.
     */
    public List<DerivedStatContribution> getFactors()
    {
      return _factors;
    }
  }

  /**
   * Derived stats contributions for a single class.
   * @author DAM
   */
  public static final class ClassDerivedStats
  {
    private HashMap<StatDescription,StatContributions> _contributions;
    private ClassDerivedStats()
    {
      _contributions=new HashMap<StatDescription,StatContributions>();
    }

    private void addStatContribution(StatDescription sourceStat, StatDescription contributedStat, Number factor)
    {
      StatContributions contribs=_contributions.get(sourceStat);
      if (contribs==null)
      {
        contribs=new StatContributions();
        _contributions.put(sourceStat,contribs);
      }
      contribs.addStatContribution(contributedStat,factor);
    }

    /**
     * Get a list of source stats.
     * @return a list of source stats.
     */
    public List<StatDescription> getSourceStats()
    {
      List<StatDescription> stats=new ArrayList<StatDescription>(_contributions.keySet());
      Collections.sort(stats,new StatDescriptionComparator());
      return stats;
    }

    /**
     * Get the contributions for a single source stat.
     * @param sourceStat Source stat.
     * @return Stat contributions.
     */
    public StatContributions getContribsForStat(StatDescription sourceStat)
    {
      return _contributions.get(sourceStat);
    }

    @Override
    public String toString()
    {
      return _contributions.toString();
    }
  }

  private HashMap<ClassDescription,ClassDerivedStats> _allContribs;

  /**
   * Constructor.
   */
  public DerivedStatsContributionsMgr()
  {
    _allContribs=new HashMap<ClassDescription,ClassDerivedStats>();
  }

  /**
   * Get the managed classes, sorted by name.
   * @return A list of classes.
   */
  public List<ClassDescription> getClasses()
  {
    List<ClassDescription> ret=new ArrayList<ClassDescription>();
    ret.addAll(_allContribs.keySet());
    Collections.sort(ret,new NamedComparator());
    return ret;
  }

  /**
   * Get the derived stats contributions for a single class.
   * @param characterClass Targeted class.
   * @return some derived stats contributions.
   */
  public ClassDerivedStats getDerivatedStats(ClassDescription characterClass)
  {
    return _allContribs.get(characterClass);
  }

  /**
   * Set factor.
   * @param primaryStat Primary stat.
   * @param contributedStat Contributed stat.
   * @param characterClass Character class;
   * @param factor Factor.
   */
  public void setFactor(StatDescription primaryStat, StatDescription contributedStat, ClassDescription characterClass, Number factor)
  {
    ClassDerivedStats derivedStats=_allContribs.get(characterClass);
    if (derivedStats==null)
    {
      derivedStats=new ClassDerivedStats();
      _allContribs.put(characterClass,derivedStats);
    }
    if ((primaryStat!=null) && (contributedStat!=null))
    {
      derivedStats.addStatContribution(primaryStat,contributedStat,factor);
    }
    else
    {
      LOGGER.warn("Could not register factor: source="+primaryStat+", target="+contributedStat);
    }
  }

  /**
   * Get stats contribution for a set of stats.
   * @param cClass Targeted character class.
   * @param set Set of raw stats.
   * @return Stats contributions.
   */
  public BasicStatsSet getContribution(ClassDescription cClass, BasicStatsSet set)
  {
    List<StatsContribution> contribs=getContributions(cClass,set);
    StatsStorage storage=new StatsStorage();
    for(StatsContribution contrib : contribs)
    {
      storage.addContrib(contrib);
    }
    return storage.aggregate();
  }

  /**
   * Get stats contribution for a set of stats.
   * @param characterClass Targeted character class.
   * @param set Set of raw stats.
   * @return Some stat contributions.
   */
  public List<StatsContribution> getContributions(ClassDescription characterClass, BasicStatsSet set)
  {
    List<StatsContribution> ret=new ArrayList<StatsContribution>();
    ClassDerivedStats classDerivedStats=_allContribs.get(characterClass);
    for(StatDescription stat : set.getStats())
    {
      Number statValue=set.getStat(stat);
      StatContributions contrib=classDerivedStats.getContribsForStat(stat);
      if (contrib!=null)
      {
        for(DerivedStatContribution factor : contrib.getFactors())
        {
          Number toAdd=NumericUtils.multiply(statValue,factor._factor);
          BasicStatsSet stats=new BasicStatsSet();
          stats.addStat(factor._contributedStat,toAdd);
          StatsContribution statContrib=StatsContribution.getStatContrib(stat,factor._factor,stats);
          ret.add(statContrib);
        }
      }
    }
    return ret;
  }
}
