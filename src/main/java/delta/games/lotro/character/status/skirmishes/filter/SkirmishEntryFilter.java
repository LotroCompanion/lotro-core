package delta.games.lotro.character.status.skirmishes.filter;

import java.util.HashSet;

import delta.common.utils.collections.filters.CompoundFilter;
import delta.common.utils.collections.filters.Filter;
import delta.common.utils.collections.filters.Operator;
import delta.games.lotro.character.status.skirmishes.SkirmishEntry;
import delta.games.lotro.character.status.skirmishes.SkirmishLevel;
import delta.games.lotro.common.groupSize.GroupSize;

/**
 * Filter for skirmish entries.
 * @author DAM
 */
public class SkirmishEntryFilter implements Filter<SkirmishEntry>
{
  private SkirmishEntrySkirmishFilter _skirmish;
  private SkirmishEntrySizeFilter _groupSize;
  private SkirmishEntryLevelFilter _level;
  private CompoundFilter<SkirmishEntry> _filter;

  /**
   * Constructor.
   */
  public SkirmishEntryFilter()
  {
    _skirmish=new SkirmishEntrySkirmishFilter(null);
    _groupSize=new SkirmishEntrySizeFilter(new HashSet<GroupSize>());
    _level=new SkirmishEntryLevelFilter(new HashSet<SkirmishLevel>());
    _filter=new CompoundFilter<SkirmishEntry>(Operator.AND);
    _filter.addFilter(_skirmish);
    _filter.addFilter(_groupSize);
    _filter.addFilter(_level);
  }

  /**
   * Get the skirmish filter.
   * @return a skirmish filter.
   */
  public SkirmishEntrySkirmishFilter getSkirmishFilter()
  {
    return _skirmish;
  }

  /**
   * Get the group size filter.
   * @return a group size filter.
   */
  public SkirmishEntrySizeFilter getGroupSizeFilter()
  {
    return _groupSize;
  }

  /**
   * Get the level filter.
   * @return a level filter.
   */
  public SkirmishEntryLevelFilter getLevelFilter()
  {
    return _level;
  }

  @Override
  public boolean accept(SkirmishEntry item)
  {
    return _filter.accept(item);
  }
}
