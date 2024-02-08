package delta.games.lotro.common.requirements;

import java.util.List;

import delta.games.lotro.character.classes.AbstractClassDescription;
import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.races.RaceDescription;
import delta.games.lotro.common.effects.Effect;

/**
 * Usage requirement (for quests, deeds, items and relics).
 * @author DAM
 */
public class UsageRequirement
{
  // Minimum level (may be null)
  private Integer _minLevel;
  // Maximum level (may be null)
  private Integer _maxLevel;
  // Class requirement
  private ClassRequirement _classRequirement;
  // Race requirement
  private RaceRequirement _raceRequirement;
  // Faction requirement
  private FactionRequirement _factionRequirement;
  // Quest requirement
  private QuestRequirement _questRequirement;
  // Profession requirement
  private ProfessionRequirement _professionRequirement;
  // Glory rank requirement
  private GloryRankRequirement _gloryRankRequirement;
  // Effect requirement
  private EffectRequirement _effectRequirement;
  // TODO Trait requirement

  /**
   * Constructor.
   */
  public UsageRequirement()
  {
    _minLevel=null;
    _maxLevel=null;
    _classRequirement=null;
    _raceRequirement=null;
    _factionRequirement=null;
    _professionRequirement=null;
    _gloryRankRequirement=null;
    _effectRequirement=null;
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
   * Add an allowed class.
   * @param characterClass Class to add.
   */
  public void addAllowedClass(AbstractClassDescription characterClass)
  {
    if (_classRequirement==null)
    {
      _classRequirement=new ClassRequirement();
    }
    _classRequirement.addAllowedClass(characterClass);
  }

  /**
   * Remove class requirement.
   */
  public void removeClassRequirement()
  {
    if (_classRequirement!=null)
    {
      _classRequirement.removeAll();
    }
  }

  /**
   * Get the required class for this deed.
   * @return a character class or <code>null</code>.
   */
  public AbstractClassDescription getRequiredClass()
  {
    if (_classRequirement!=null)
    {
      List<AbstractClassDescription> classes=_classRequirement.getAllowedClasses();
      if (classes.size()>0)
      {
        return classes.get(0);
      }
    }
    return null;
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
   * Add an allowed race.
   * @param race Race to add.
   */
  public void addAllowedRace(RaceDescription race)
  {
    if (_raceRequirement==null)
    {
      _raceRequirement=new RaceRequirement();
    }
    _raceRequirement.addAllowedRace(race);
  }

  /**
   * Get the required race for this deed.
   * @return a race or <code>null</code>.
   */
  public RaceDescription getRequiredRace()
  {
    if (_raceRequirement!=null)
    {
      List<RaceDescription> races=_raceRequirement.getAllowedRaces();
      if (races.size()>0)
      {
        return races.get(0);
      }
    }
    return null;
  }

  /**
   * Get the faction requirement.
   * @return A faction requirement or <code>null</code>.
   */
  public FactionRequirement getFactionRequirement()
  {
    return _factionRequirement;
  }

  /**
   * Set the faction requirement.
   * @param factionRequirement Requirement to set, may be <code>null</code>.
   */
  public void setFactionRequirement(FactionRequirement factionRequirement)
  {
    _factionRequirement=factionRequirement;
  }

  /**
   * Get the quest requirement.
   * @return A quest requirement or <code>null</code>.
   */
  public QuestRequirement getQuestRequirement()
  {
    return _questRequirement;
  }

  /**
   * Set the quest requirement.
   * @param questRequirement Requirement to set, may be <code>null</code>.
   */
  public void setQuestRequirement(QuestRequirement questRequirement)
  {
    _questRequirement=questRequirement;
  }

  /**
   * Get the profession requirement.
   * @return A profession requirement or <code>null</code>.
   */
  public ProfessionRequirement getProfessionRequirement()
  {
    return _professionRequirement;
  }

  /**
   * Set the profession requirement.
   * @param professionRequirement Requirement to set, may be <code>null</code>.
   */
  public void setProfessionRequirement(ProfessionRequirement professionRequirement)
  {
    _professionRequirement=professionRequirement;
  }

  /**
   * Get the glory rank requirement.
   * @return A glory rank requirement or <code>null</code>.
   */
  public GloryRankRequirement getGloryRankRequirement()
  {
    return _gloryRankRequirement;
  }

  /**
   * Set the glory rank requirement.
   * @param gloryRankRequirement Requirement to set, may be <code>null</code>.
   */
  public void setGloryRankRequirement(GloryRankRequirement gloryRankRequirement)
  {
    _gloryRankRequirement=gloryRankRequirement;
  }

  /**
   * Get the effect requirement.
   * @return An effect requirement or <code>null</code>.
   */
  public EffectRequirement getEffectRequirement()
  {
    return _effectRequirement;
  }

  /**
   * Set the effect requirement.
   * @param effectRequirement Requirement to set, may be <code>null</code>.
   */
  public void setEffectRequirement(EffectRequirement effectRequirement)
  {
    _effectRequirement=effectRequirement;
  }

  /**
   * Indicates if the given parameter do pass this requirement.
   * @param level Level to test.
   * @param characterClass Character class to test.
   * @param race Race to test.
   * @return <code>true</code> if requirement is met, <code>false</code> otherwise.
   */
  public boolean accepts(int level, ClassDescription characterClass, RaceDescription race)
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

  /**
   * Indicates if this requirement is empty or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isEmpty()
  {
    return ((_minLevel==null) && (_maxLevel==null) && (_classRequirement==null) &&
        (_raceRequirement==null) && (_factionRequirement==null) && (_questRequirement==null) &&
        (_professionRequirement==null) && (_gloryRankRequirement==null) &&
        (_effectRequirement==null));
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
    if (_factionRequirement!=null)
    {
      sb.append(" Faction=").append(_factionRequirement);
    }
    if (_questRequirement!=null)
    {
      sb.append(" Quest=").append(_questRequirement);
    }
    if (_professionRequirement!=null)
    {
      sb.append(" Profession=").append(_professionRequirement);
    }
    if (_gloryRankRequirement!=null)
    {
      sb.append(" Glory Rank>=").append(_gloryRankRequirement.getRank());
    }
    if (_effectRequirement!=null)
    {
      Effect effect=_effectRequirement.getEffect();
      sb.append(" Effect=").append(effect.getName());
    }
    return sb.toString().trim();
  }
}
