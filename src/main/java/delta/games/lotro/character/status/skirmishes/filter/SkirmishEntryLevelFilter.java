package delta.games.lotro.character.status.skirmishes.filter;

import java.util.Set;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.character.status.skirmishes.SkirmishEntry;
import delta.games.lotro.character.status.skirmishes.SkirmishLevel;

/**
 * Filter for a skirmish entries for a given level.
 * @author DAM
 */
public class SkirmishEntryLevelFilter implements Filter<SkirmishEntry>
{
  private Set<SkirmishLevel> _levels;

  /**
   * Constructor.
   * @param levels Levels to select.
   */
  public SkirmishEntryLevelFilter(Set<SkirmishLevel> levels)
  {
    _levels=levels;
  }

  /**
   * Get the selected levels.
   * @return A possibly empty but never <code>null</code> set of levels.
   */
  public Set<SkirmishLevel> getSelectedLevels()
  {
    return _levels;
  }

  /**
   * Set the levels to select.
   * @param levels Levels to select.
   */
  public void setLevels(Set<SkirmishLevel> levels)
  {
    _levels=levels;
  }

  @Override
  public boolean accept(SkirmishEntry entry)
  {
    return _levels.contains(entry.getLevel());
  }
}
