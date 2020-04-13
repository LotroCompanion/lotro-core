package delta.games.lotro.character.stats.tomes;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.character.stats.tomes.io.xml.StatTomesXMLParser;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatDescriptionIndexComparator;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;

/**
 * Stat tomes manager.
 * @author DAM
 */
public class StatTomesManager
{
  private static StatTomesManager _instance=load();
  private Map<StatDescription,List<StatTome>> _tomes;

  /**
   * Constructor.
   */
  public StatTomesManager()
  {
    _tomes=new HashMap<StatDescription,List<StatTome>>();
  }

  private static StatTomesManager load()
  {
    File input=LotroCoreConfig.getInstance().getFile(DataFiles.STAT_TOMES);
    StatTomesManager tomesManager=StatTomesXMLParser.parseStatTomesFile(input);
    return tomesManager;
  }

  /**
   * Get the main instance of this class.
   * @return the main instance of this class.
   */
  public static StatTomesManager getInstance()
  {
    return _instance;
  }

  /**
   * Get the stat tome for a given stat and rank.
   * @param stat Targeted stat.
   * @param rank Targeted rank.
   * @return A stat tome or <code>null</code> if not found.
   */
  public StatTome getStatTome(StatDescription stat, int rank)
  {
    List<StatTome> tomes=_tomes.get(stat);
    if (tomes!=null)
    {
      int nbTomes=tomes.size();
      if ((rank>=1) && (rank<=nbTomes))
      {
        return tomes.get(rank-1);
      }
    }
    return null;
  }

  /**
   * Get a stat tome using a trait identifier.
   * @param traitId Trait identifier.
   * @return A stat tome or <code>null</code> if not found.
   */
  public StatTome getStatTomeFromTraitId(int traitId)
  {
    for(List<StatTome> tomes : _tomes.values())
    {
      for(StatTome tome : tomes)
      {
        if ((tome!=null) && (tome.getTraitId()==traitId))
        {
          return tome;
        }
      }
    }
    return null;
  }

  /**
   * Register a stat tome.
   * @param tome Tome to register.
   */
  public void registerStatTome(StatTome tome)
  {
    StatDescription stat=tome.getStat();
    List<StatTome> tomes=_tomes.get(stat);
    if (tomes==null)
    {
      tomes=new ArrayList<StatTome>();
      _tomes.put(stat,tomes);
    }
    int rank=tome.getRank();
    while (rank>tomes.size())
    {
      tomes.add(null);
    }
    tomes.set(rank-1,tome);
  }

  /**
   * Get the available stats.
   * @return A list of available stats.
   */
  public List<StatDescription> getStats()
  {
    List<StatDescription> stats=new ArrayList<StatDescription>(_tomes.keySet());
    Collections.sort(stats,new StatDescriptionIndexComparator());
    return stats;
  }

  /**
   * Get the number of ranks for the given stat.
   * @param stat Targeted stat.
   * @return A ranks count.
   */
  public int getNbOfRanks(StatDescription stat)
  {
    List<StatTome> tomes=_tomes.get(stat);
    if (tomes!=null)
    {
      return tomes.size();
    }
    return 0;
  }
}
