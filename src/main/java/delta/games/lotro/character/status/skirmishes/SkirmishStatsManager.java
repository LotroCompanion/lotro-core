package delta.games.lotro.character.status.skirmishes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.lore.instances.SkirmishPrivateEncounter;
import delta.games.lotro.utils.DataProvider;
import delta.games.lotro.utils.comparators.DelegatingComparator;

/**
 * Manager for all the skirmish stats for a single character.
 * @author DAM
 */
public class SkirmishStatsManager
{
  private Map<Integer,SingleSkirmishStats> _stats;

  /**
   * Constructor.
   */
  public SkirmishStatsManager()
  {
    _stats=new HashMap<Integer,SingleSkirmishStats>();
  }

  /**
   * Add some stats.
   * @param stats Stats to add.
   */
  public void add(SingleSkirmishStats stats)
  {
    int id=stats.getSkirmish().getIdentifier();
    _stats.put(Integer.valueOf(id),stats);
  }

  /**
   * Get all known stats, sorted by skirmish ID.
   * @return A list of single skirmish stats.
   */
  public List<SingleSkirmishStats> getStats()
  {
    List<SingleSkirmishStats> ret=new ArrayList<SingleSkirmishStats>(_stats.values());
    Comparator<SkirmishPrivateEncounter> idComparator=new IdentifiableComparator<SkirmishPrivateEncounter>();
    DataProvider<SingleSkirmishStats,SkirmishPrivateEncounter> dataProvider=new DataProvider<SingleSkirmishStats,SkirmishPrivateEncounter>()
    {
      @Override
      public SkirmishPrivateEncounter getData(SingleSkirmishStats p)
      {
        return p.getSkirmish();
      }
    };
    DelegatingComparator<SingleSkirmishStats,SkirmishPrivateEncounter> c=new DelegatingComparator<>(dataProvider,idComparator);
    Collections.sort(ret,c);
    return ret;
  }
}
