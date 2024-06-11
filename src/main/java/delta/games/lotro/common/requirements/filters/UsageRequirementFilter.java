package delta.games.lotro.common.requirements.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.common.requirements.UsageRequirement;

/**
 * Filter for a usage requirement using the race/class requirements.
 * @author DAM
 */
public class UsageRequirementFilter implements Filter<UsageRequirement>
{
  private ClassRequirementFilter _classFilter;
  private RaceRequirementFilter _raceFilter;
  private SimpleProfessionRequirementFilter _professionFilter;

  /**
   * Constructor.
   */
  public UsageRequirementFilter()
  {
    _classFilter=new ClassRequirementFilter();
    _raceFilter=new RaceRequirementFilter();
    _professionFilter=new SimpleProfessionRequirementFilter();
  }

  /**
   * Get the character class filter.
   * @return the character class filter.
   */
  public ClassRequirementFilter getCharacterClassFilter()
  {
    return _classFilter;
  }

  /**
   * Get the race filter.
   * @return the race filter.
   */
  public RaceRequirementFilter getRaceFilter()
  {
    return _raceFilter;
  }

  /**
   * Get the profession filter.
   * @return the profession filter.
   */
  public SimpleProfessionRequirementFilter getProfessionFilter()
  {
    return _professionFilter;
  }

  @Override
  public boolean accept(UsageRequirement requirement)
  {
    if (!_classFilter.accept(requirement.getClassRequirement()))
    {
      return false;
    }
    if (!_raceFilter.accept(requirement.getRaceRequirement()))
    {
      return false;
    }
    if (!_professionFilter.accept(requirement.getProfessionRequirement()))
    {
      return false;
    }
    return true;
  }
}
