package delta.games.lotro.common.requirements.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.character.races.RaceDescription;
import delta.games.lotro.common.requirements.RaceRequirement;

/**
 * Filter for a race requirement.
 * @author DAM
 */
public class RaceRequirementFilter implements Filter<RaceRequirement>
{
  private RaceDescription _race;

  /**
   * Constructor.
   */
  public RaceRequirementFilter()
  {
    _race=null;
  }

  /**
   * Get the race to use.
   * @return A race or <code>null</code>.
   */
  public RaceDescription getRace()
  {
    return _race;
  }

  /**
   * Set the race to select.
   * @param race Race to use, may be <code>null</code>.
   */
  public void setRace(RaceDescription race)
  {
    _race=race;
  }

  public boolean accept(RaceRequirement raceRequirement)
  {
    if (_race==null)
    {
      return true;
    }
    if (raceRequirement==null)
    {
      return true;
    }
    return raceRequirement.accept(_race);
  }
}
