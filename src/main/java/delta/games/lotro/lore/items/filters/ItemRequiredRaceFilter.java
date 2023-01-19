package delta.games.lotro.lore.items.filters;

import delta.games.lotro.character.races.RaceDescription;
import delta.games.lotro.common.requirements.RaceRequirement;
import delta.games.lotro.common.requirements.UsageRequirement;
import delta.games.lotro.lore.items.Item;

/**
 * Filter items that can be used by a character race.
 * @author DAM
 */
public class ItemRequiredRaceFilter implements ItemFilter
{
  private boolean _enabled;
  private RaceDescription _race;
  private boolean _strict;

  /**
   * Constructor.
   * @param race Character race to use.
   * @param strict Allow only class items, or allow all non class restricted items.
   */
  public ItemRequiredRaceFilter(RaceDescription race, boolean strict)
  {
    _race=race;
    _strict=strict;
    _enabled=true;
  }

  /**
   * Indicates if this filter is enabled or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isEnabled()
  {
    return _enabled;
  }

  /**
   * Enable this filter or not.
   * @param enabled <code>true</code> to enable filtering, <code>false</code> to disable filtering.
   */
  public void setEnabled(boolean enabled)
  {
    _enabled=enabled;
  }

  /**
   * Get the character race.
   * @return A character race or <code>null</code>.
   */
  public RaceDescription getRace()
  {
    return _race;
  }

  /**
   * Set the character race to use.
   * @param race Character race (may be <code>null</code>).
   */
  public void setRace(RaceDescription race)
  {
    _race=race;
  }

  @Override
  public boolean accept(Item item)
  {
    if (!_enabled)
    {
      return true;
    }
    if (_race==null)
    {
      return true;
    }
    UsageRequirement requirements=item.getUsageRequirements();
    RaceRequirement raceRequirement=requirements.getRaceRequirement();
    if (raceRequirement==null)
    {
      return !_strict;
    }
    return raceRequirement.accept(_race);
  }
}
