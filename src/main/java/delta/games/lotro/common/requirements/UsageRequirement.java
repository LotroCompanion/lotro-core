package delta.games.lotro.common.requirements;

import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.Race;

/**
 * Usage requirement (for quests, deeds and items).
 * @author DAM
 */
public class UsageRequirement
{
  private Integer _minLevel;
  private Integer _maxLevel;
  private ClassRequirement _classRequirement;
  private RaceRequirement _raceRequirement;

  /**
   * Constructor.
   */
  public UsageRequirement()
  {
    _minLevel=null;
    _maxLevel=null;
    _classRequirement=null;
    _raceRequirement=null;
  }

  /**
   * Get the minimum level requirement.
   * @return A level or <code>null</code> if no restriction.
   */
  public Integer getMinLevel()
  {
    return _minLevel;
  }

  /**
   * Set the minimum level requirement.
   * @param minLevel minimum level to set, may be <code>null</code>.
   */
  public void setMinLevel(Integer minLevel)
  {
    _minLevel=minLevel;
  }

  /**
   * Get the maximum level requirement.
   * @return A level or <code>null</code> if no restriction.
   */
  public Integer getMaxLevel()
  {
    return _maxLevel;
  }

  /**
   * Set the maximum level requirement.
   * @param maxLevel maximum level to set, may be <code>null</code>.
   */
  public void setMaxLevel(Integer maxLevel)
  {
    _maxLevel=maxLevel;
  }

  /**
   * Get the class requirement.
   * @return A class requirement or <code>null</code>.
   */
  public ClassRequirement getClassRequirement()
  {
    return _classRequirement;
  }

  /**
   * Set the class requirement.
   * @param classRequirement Requirement to set, may be <code>null</code>.
   */
  public void setClassRequirement(ClassRequirement classRequirement)
  {
    _classRequirement=classRequirement;
  }

  /**
   * Get the race requirement.
   * @return A race requirement or <code>null</code>.
   */
  public RaceRequirement getRaceRequirement()
  {
    return _raceRequirement;
  }

  /**
   * Set the race requirement.
   * @param raceRequirement Requirement to set, may be <code>null</code>.
   */
  public void setRaceRequirement(RaceRequirement raceRequirement)
  {
    _raceRequirement=raceRequirement;
  }

  /**
   * Indicates if the given parameter do pass this requirement.
   * @param level Level to test.
   * @param characterClass Character class to test.
   * @param race Race to test.
   * @return <code>true</code> if requirement is met, <code>false</code> otherwise.
   */
  public boolean accepts(int level, CharacterClass characterClass, Race race)
  {
    if ((_minLevel!=null) && (level<_minLevel.intValue()))
    {
      return false;
    }
    if ((_maxLevel!=null) && (level>_maxLevel.intValue()))
    {
      return false;
    }
    if (_classRequirement!=null)
    {
      if (!_classRequirement.accept(characterClass))
      {
        return false;
      }
    }
    if (_raceRequirement!=null)
    {
      if (!_raceRequirement.accept(race))
      {
        return false;
      }
    }
    return true;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    if (_minLevel!=null)
    {
      sb.append("Min level=").append(_minLevel);
    }
    if (_maxLevel!=null)
    {
      sb.append(" Max level=").append(_maxLevel);
    }
    if (_classRequirement!=null)
    {
      sb.append(" Class=").append(_classRequirement);
    }
    if (_raceRequirement!=null)
    {
      sb.append(" Race=").append(_raceRequirement);
    }
    return sb.toString().trim();
  }
}
