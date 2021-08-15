package delta.games.lotro.character.status.skirmishes.filter;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.character.status.skirmishes.SkirmishEntry;
import delta.games.lotro.lore.instances.SkirmishPrivateEncounter;

/**
 * Filter for skirmish entries for a given skirmish.
 * @author DAM
 */
public class SkirmishEntrySkirmishFilter implements Filter<SkirmishEntry>
{
  private SkirmishPrivateEncounter _skirmish;

  /**
   * Constructor.
   * @param skirmish Skirmish to select (may be <code>null</code>).
   */
  public SkirmishEntrySkirmishFilter(SkirmishPrivateEncounter skirmish)
  {
    _skirmish=skirmish;
  }

  /**
   * Get the skirmish to use.
   * @return A skirmish or <code>null</code>.
   */
  public SkirmishPrivateEncounter getSkirmish()
  {
    return _skirmish;
  }

  /**
   * Set the skirmish to select.
   * @param skirmish Skirmish to use, may be <code>null</code>.
   */
  public void setSex(SkirmishPrivateEncounter skirmish)
  {
    _skirmish=skirmish;
  }

  @Override
  public boolean accept(SkirmishEntry entry)
  {
    if (_skirmish==null)
    {
      return true;
    }
    return entry.getSkirmish()==_skirmish;
  }
}
