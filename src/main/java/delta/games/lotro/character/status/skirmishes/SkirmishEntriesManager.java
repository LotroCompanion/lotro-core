package delta.games.lotro.character.status.skirmishes;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.status.skirmishes.cfg.SkirmishEntriesPolicy;
import delta.games.lotro.character.status.skirmishes.filter.SkirmishEntryFilter;

/**
 * Manages the skirmish entries related to a {@link SkirmishStatsManager}.
 * @author DAM
 */
public class SkirmishEntriesManager
{
  // Source
  private SkirmishStatsManager _stats;
  // Entries
  private List<SkirmishEntry> _entries;
  // Filter
  private SkirmishEntryFilter _filter;
  // Config
  private SkirmishEntriesPolicy _config;
  // Totals
  private SkirmishStats _totals;

  /**
   * Constructor.
   * @param stats Stats.
   */
  public SkirmishEntriesManager(SkirmishStatsManager stats)
  {
    _stats=stats;
    _entries=new ArrayList<SkirmishEntry>();
    _filter=new SkirmishEntryFilter();
    _config=new SkirmishEntriesPolicy(false,false);
    _totals=new SkirmishStats();
    update();
  }

  /**
   * Update entries.
   */
  public void update()
  {
    _entries=SkirmishEntriesUtils.getEntries(_stats,_filter,_config);
    updateTotals();
  }

  /**
   * Get the managed entries.
   * @return a list of skirmish entries.
   */
  public List<SkirmishEntry> getEntries()
  {
    return _entries;
  }

  /**
   * Get the managed filter.
   * @return a filter.
   */
  public SkirmishEntryFilter getFilter()
  {
    return _filter;
  }

  /**
   * Get the managed configuration.
   * @return a configuration.
   */
  public SkirmishEntriesPolicy getConfig()
  {
    return _config;
  }

  /**
   * Get the managed totals.
   * @return total stats.
   */
  public SkirmishStats getTotals()
  {
    return _totals;
  }

  private void updateTotals()
  {
    _totals=new SkirmishStats();
    for(SkirmishEntry entry : _entries)
    {
      _totals.add(entry.getStats());
    }
  }
}
