package delta.games.lotro.character.stats.base;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import delta.common.utils.NumericTools;
import delta.common.utils.files.TextFileReader;
import delta.common.utils.text.EncodingNames;
import delta.common.utils.text.TextUtils;
import delta.common.utils.url.URLTools;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.STAT;
import delta.games.lotro.character.stats.contribs.StatsContribution;
import delta.games.lotro.character.stats.contribs.StatsContributionsManager;
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
    init();
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
    return getContribution(cClass,set,null);
  }

  /**
   * Get stats contribution for a set of stats.
   * @param cClass Targeted character class.
   * @param set Set of raw stats.
   * @param contribsMgr Optional contributions manager to store computed contributions.
   * @return A set of stat contributions.
   */
  public BasicStatsSet getContribution(CharacterClass cClass, BasicStatsSet set, StatsContributionsManager contribsMgr)
  {
    ClassDerivedStats classDerivedStats=_allContribs.get(cClass);
    BasicStatsSet result=new BasicStatsSet();
    for(STAT stat : STAT.values())
    {
      FixedDecimalsInteger statValue=set.getStat(stat);
      if (statValue!=null)
      {
        DerivedStatContributions contrib=classDerivedStats.getContrib(stat);
        if (contrib!=null)
        {
          BasicStatsSet derivedStats=new BasicStatsSet();
          for(StatContributionFactor factor : contrib.getFactors())
          {
            FixedDecimalsInteger toAdd=new FixedDecimalsInteger(statValue);
            toAdd.multiply(factor._factor);
            result.addStat(factor._contributedStat,toAdd);
            derivedStats.addStat(factor._contributedStat,toAdd);
            if (contribsMgr!=null)
            {
              BasicStatsSet stats=new BasicStatsSet();
              stats.addStat(factor._contributedStat,toAdd);
              StatsContribution statContrib=StatsContribution.getStatContrib(stat,factor._factor,stats);
              contribsMgr.addContrib(statContrib);
            }
          }
        }
      }
    }
    return result;
  }

  private void init()
  {
    URL url=URLTools.getFromClassPath("derivations.txt",DerivatedStatsContributionsMgr.class.getPackage());
    TextFileReader r=new TextFileReader(url, EncodingNames.ISO8859_1);
    List<String> lines=TextUtils.readAsLines(r);
    CharacterClass[] classes = CharacterClass.ALL_CLASSES;
    for(String line : lines)
    {
      String[] items=line.split("\t");
      //System.out.println(Arrays.toString(items));
      String primaryStatStr=items[0].replace('_',' ');
      STAT primaryStat=STAT.getByName(primaryStatStr);
      String impactedStatStr=items[1].replace('_',' ').trim();
      STAT impactedStat=STAT.getByName(impactedStatStr);
      if (impactedStat==null)
      {
        if (impactedStatStr.endsWith("Rating"))
        {
          impactedStatStr=impactedStatStr.substring(0,impactedStatStr.length()-6).trim();
        }
        impactedStat=STAT.getByName(impactedStatStr);
      }
      //System.out.println(primaryStat+"  =>  "+impactedStat);
      int index=0;
      for(CharacterClass cClass : classes)
      {
        String factorStr = items[2 + index];
        //System.out.println("\t"+cClass+": "+factorStr);
        index++;
        FixedDecimalsInteger factor=getFactor(factorStr);
        if ((factor!=null) && (factor.intValue()!=0))
        {
          setFactor(primaryStat,impactedStat,cClass,factor);
        }
      }
    }
  }

  private static FixedDecimalsInteger getFactor(String factorStr)
  {
    FixedDecimalsInteger ret=null;
    Integer factor=NumericTools.parseInteger(factorStr,false);
    if (factor!=null)
    {
      ret=new FixedDecimalsInteger(factor.intValue());
    }
    else
    {
      Float fFactor=NumericTools.parseFloat(factorStr,false);
      if (fFactor!=null)
      {
        ret=new FixedDecimalsInteger(fFactor.floatValue());
      }
    }
    return ret;
  }
}
