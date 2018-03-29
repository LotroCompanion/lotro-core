package delta.games.lotro.lore.deeds.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.common.Race;
import delta.games.lotro.lore.deeds.DeedDescription;

/**
 * Filter for deed using the race requirement.
 * @author DAM
 */
public class DeedRaceRequirementFilter implements Filter<DeedDescription>
{
  private Race _race;

  /**
   * Constructor.
   * @param race Race to select (may be <code>null</code>).
   */
  public DeedRaceRequirementFilter(Race race)
  {
    _race=race;
  }

  /**
   * Get the race to use.
   * @return A race or <code>null</code>.
   */
  public Race getRace()
  {
    return _race;
  }

  /**
   * Set the race to select.
   * @param race Race to use, may be <code>null</code>.
   */
  public void setRace(Race race)
  {
    _race=race;
  }

  public boolean accept(DeedDescription deed)
  {
    if (_race==null)
    {
      return true;
    }
    return deed.getRequiredRace()==_race;
  }
}
