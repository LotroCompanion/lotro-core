package delta.games.lotro.character.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.character.BaseCharacterSummary;
import delta.games.lotro.character.races.RaceDescription;

/**
 * Filter for characters of a given race.
 * @author DAM
 */
public class RaceFilter implements Filter<BaseCharacterSummary>
{
  private RaceDescription _race;

  /**
   * Constructor.
   * @param race Race to select (may be <code>null</code>).
   */
  public RaceFilter(RaceDescription race)
  {
    _race=race;
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

  @Override
  public boolean accept(BaseCharacterSummary summary)
  {
    if (_race==null)
    {
      return true;
    }
    return summary.getRace()==_race;
  }
}
