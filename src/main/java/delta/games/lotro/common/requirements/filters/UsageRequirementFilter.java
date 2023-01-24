package delta.games.lotro.common.requirements.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.races.RaceDescription;
import delta.games.lotro.common.requirements.ClassRequirement;
import delta.games.lotro.common.requirements.RaceRequirement;
import delta.games.lotro.common.requirements.UsageRequirement;

/**
 * Filter for a usage requirement using the race/class requirements.
 * @author DAM
 */
public class UsageRequirementFilter implements Filter<UsageRequirement>
{
  private ClassDescription _characterClass;
  private RaceDescription _race;

  /**
   * Constructor.
   * @param characterClass Class to select (may be <code>null</code>).
   * @param race Race to select (may be <code>null</code>).
   */
  public UsageRequirementFilter(ClassDescription characterClass, RaceDescription race)
  {
    _characterClass=characterClass;
    _race=race;
  }

  /**
   * Get the character class to use.
   * @return A character class or <code>null</code>.
   */
  public ClassDescription getCharacterClass()
  {
    return _characterClass;
  }

  /**
   * Set the character class to select.
   * @param characterClass Character class to use, may be <code>null</code>.
   */
  public void setCharacterClass(ClassDescription characterClass)
  {
    _characterClass=characterClass;
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
  public boolean accept(UsageRequirement requirement)
  {
    return (acceptCharacterClass(requirement) && acceptRace(requirement));
  }

  private boolean acceptCharacterClass(UsageRequirement requirement)
  {
    if (_characterClass==null)
    {
      return true;
    }
    ClassRequirement classRequirement=requirement.getClassRequirement();
    if (classRequirement==null)
    {
      return true;
    }
    return classRequirement.accept(_characterClass);
  }

  private boolean acceptRace(UsageRequirement requirement)
  {
    if (_race==null)
    {
      return true;
    }
    RaceRequirement raceRequirement=requirement.getRaceRequirement();
    if (raceRequirement==null)
    {
      return true;
    }
    return raceRequirement.accept(_race);
  }
}
