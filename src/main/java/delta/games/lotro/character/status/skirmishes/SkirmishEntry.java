package delta.games.lotro.character.status.skirmishes;

import delta.games.lotro.lore.instances.SkirmishGroupSize;
import delta.games.lotro.lore.instances.SkirmishPrivateEncounter;

/**
 * Skirmish entry.
 * <p>
 * Gathers skimirsh statistics with their context (skirmish, group size, level).
 * @author DAM
 */
public class SkirmishEntry
{
  private SkirmishPrivateEncounter _skirmish;
  private SkirmishGroupSize _size;
  private SkirmishLevel _level;
  private SkirmishStats _stats;

  /**
   * Constructor.
   * @param skirmish Skirmish.
   * @param size Size (<code>null</code> means several sizes).
   * @param level Level (<code>null</code> means several level).
   * @param stats Statistics.
   */
  public SkirmishEntry(SkirmishPrivateEncounter skirmish, SkirmishGroupSize size, SkirmishLevel level, SkirmishStats stats)
  {
    _skirmish=skirmish;
    _size=size;
    _level=level;
    _stats=stats;
  }

  /**
   * Get the associated skirmish.
   * @return the associated skirmish.
   */
  public SkirmishPrivateEncounter getSkirmish()
  {
    return _skirmish;
  }

  /**
   * Get the associated size.
   * @return a size (or <code>null</code> if several sizes).
   */
  public SkirmishGroupSize getSize()
  {
    return _size;
  }

  /**
   * Get the associated level.
   * @return a level (or <code>null</code> if several levels).
   */
  public SkirmishLevel getLevel()
  {
    return _level;
  }

  /**
   * Get the statistics.
   * @return some skirmish statistics.
   */
  public SkirmishStats getStats()
  {
    return _stats;
  }
}
