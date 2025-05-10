package delta.games.lotro.common.requirements;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.classes.AbstractClassDescription;
import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.races.RaceDescription;

/**
 * Usage requirement (for quests, deeds, items, relics...).
 * @author DAM
 */
public class UsageRequirement extends Requirements
{
  /**
   * Get the managed requirement classes, ordered.
   */
  public static final List<Class<? extends Requirement>> REQUIREMENT_CLASSES=buildRequirementClasses();

  private static List<Class<? extends Requirement>> buildRequirementClasses()
  {
    List<Class<? extends Requirement>> ret=new ArrayList<Class<? extends Requirement>>();
    ret.add(LevelRangeRequirement.class);
    ret.add(ClassRequirement.class);
    ret.add(RaceRequirement.class);
    ret.add(FactionRequirement.class);
    ret.add(QuestRequirement.class);
    ret.add(ProfessionRequirement.class);
    ret.add(GloryRankRequirement.class);
    ret.add(EffectRequirement.class);
    ret.add(TraitRequirement.class);
    ret.add(DifficultyRequirement.class);
    ret.add(LevelCapRequirement.class);
    ret.add(WorldEventRequirement.class);
    return ret;
  }

  /**
   * Constructor.
   */
  public UsageRequirement()
  {
    super();
  }

  /**
   * Get the minimum level requirement.
   * @return A level or <code>null</code> if no restriction.
   */
  public Integer getMinLevel()
  {
    LevelRangeRequirement levelRequirement=getRequirement(LevelRangeRequirement.class);
    return (levelRequirement!=null)?levelRequirement.getMinLevel():null;
  }

  /**
   * Get the maximum level requirement.
   * @return A level or <code>null</code> if no restriction.
   */
  public Integer getMaxLevel()
  {
    LevelRangeRequirement levelRequirement=getRequirement(LevelRangeRequirement.class);
    return (levelRequirement!=null)?levelRequirement.getMaxLevel():null;
  }

  /**
   * Get the level requirement.
   * @return A level requirement or <code>null</code>.
   */
  public LevelRangeRequirement getLevelRequirement()
  {
    return getRequirement(LevelRangeRequirement.class);
  }

  /**
   * Set the level range requirement.
   * @param minLevel Minimum level (may be <code>null</code>).
   * @param maxLevel Maximum level (may be <code>null</code>).
   */
  public void setLevelRange(Integer minLevel, Integer maxLevel)
  {
    LevelRangeRequirement levelRequirement=null;
    if ((minLevel!=null) || (maxLevel!=null))
    {
      levelRequirement=new LevelRangeRequirement(minLevel,maxLevel);
    }
    setRequirement(LevelRangeRequirement.class,levelRequirement);
  }

  /**
   * Get the class requirement.
   * @return A class requirement or <code>null</code>.
   */
  public ClassRequirement getClassRequirement()
  {
    return getRequirement(ClassRequirement.class);
  }

  /**
   * Set the class requirement.
   * @param classRequirement Requirement to set, may be <code>null</code>.
   */
  public void setClassRequirement(ClassRequirement classRequirement)
  {
    setRequirement(ClassRequirement.class,classRequirement);
  }

  /**
   * Get the required class for this deed.
   * @return a character class or <code>null</code>.
   */
  public AbstractClassDescription getRequiredClass()
  {
    ClassRequirement classRequirement=getClassRequirement();
    if (classRequirement!=null)
    {
      List<AbstractClassDescription> classes=classRequirement.getAllowedClasses();
      if (!classes.isEmpty())
      {
        return classes.get(0);
      }
    }
    return null;
  }

  /**
   * Set the required class.
   * @param characterClass a character class or <code>null</code> for no restriction.
   */
  public void setRequiredClass(ClassDescription characterClass)
  {
    ClassRequirement classRequirement=null;
    if (characterClass!=null)
    {
      classRequirement=new ClassRequirement(characterClass);
    }
    setClassRequirement(classRequirement);
  }

  /**
   * Get the race requirement.
   * @return A race requirement or <code>null</code>.
   */
  public RaceRequirement getRaceRequirement()
  {
    return getRequirement(RaceRequirement.class);
  }

  /**
   * Set the race requirement.
   * @param raceRequirement Requirement to set, may be <code>null</code>.
   */
  public void setRaceRequirement(RaceRequirement raceRequirement)
  {
    setRequirement(RaceRequirement.class,raceRequirement);
  }

  /**
   * Get the required race for this deed.
   * @return a race or <code>null</code>.
   */
  public RaceDescription getRequiredRace()
  {
    RaceRequirement raceRequirement=getRaceRequirement();
    if (raceRequirement!=null)
    {
      List<RaceDescription> races=raceRequirement.getAllowedRaces();
      if (!races.isEmpty())
      {
        return races.get(0);
      }
    }
    return null;
  }

  /**
   * Set the required race.
   * @param race the race to set (or <code>null</code>).
   */
  public void setRequiredRace(RaceDescription race)
  {
    RaceRequirement raceRequirement=null;
    if (race!=null)
    {
      raceRequirement=new RaceRequirement(race);
    }
    setRaceRequirement(raceRequirement);
  }

  /**
   * Get the faction requirement.
   * @return A faction requirement or <code>null</code>.
   */
  public FactionRequirement getFactionRequirement()
  {
    return getRequirement(FactionRequirement.class);
  }

  /**
   * Set the faction requirement.
   * @param factionRequirement Requirement to set, may be <code>null</code>.
   */
  public void setFactionRequirement(FactionRequirement factionRequirement)
  {
    setRequirement(FactionRequirement.class,factionRequirement);
  }

  /**
   * Get the quest requirement.
   * @return A quest requirement or <code>null</code>.
   */
  public QuestRequirement getQuestRequirement()
  {
    return getRequirement(QuestRequirement.class);
  }

  /**
   * Set the quest requirement.
   * @param questRequirement Requirement to set, may be <code>null</code>.
   */
  public void setQuestRequirement(QuestRequirement questRequirement)
  {
    setRequirement(QuestRequirement.class,questRequirement);
  }

  /**
   * Get the profession requirement.
   * @return A profession requirement or <code>null</code>.
   */
  public ProfessionRequirement getProfessionRequirement()
  {
    return getRequirement(ProfessionRequirement.class);
  }

  /**
   * Set the profession requirement.
   * @param professionRequirement Requirement to set, may be <code>null</code>.
   */
  public void setProfessionRequirement(ProfessionRequirement professionRequirement)
  {
    setRequirement(ProfessionRequirement.class,professionRequirement);
  }

  /**
   * Get the glory rank requirement.
   * @return A glory rank requirement or <code>null</code>.
   */
  public GloryRankRequirement getGloryRankRequirement()
  {
    return getRequirement(GloryRankRequirement.class);
  }

  /**
   * Set the glory rank requirement.
   * @param gloryRankRequirement Requirement to set, may be <code>null</code>.
   */
  public void setGloryRankRequirement(GloryRankRequirement gloryRankRequirement)
  {
    setRequirement(GloryRankRequirement.class,gloryRankRequirement);
  }

  /**
   * Get the effect requirement.
   * @return An effect requirement or <code>null</code>.
   */
  public EffectRequirement getEffectRequirement()
  {
    return getRequirement(EffectRequirement.class);
  }

  /**
   * Set the effect requirement.
   * @param effectRequirement Requirement to set, may be <code>null</code>.
   */
  public void setEffectRequirement(EffectRequirement effectRequirement)
  {
    setRequirement(EffectRequirement.class,effectRequirement);
  }

  /**
   * Get the trait requirement.
   * @return A trait requirement or <code>null</code>.
   */
  public TraitRequirement getTraitRequirement()
  {
    return getRequirement(TraitRequirement.class);
  }

  /**
   * Set the trait requirement.
   * @param traitRequirement Requirement to set, may be <code>null</code>.
   */
  public void setTraitRequirement(TraitRequirement traitRequirement)
  {
    setRequirement(TraitRequirement.class,traitRequirement);
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
    LevelRangeRequirement levelRequirement=getLevelRequirement();
    if (levelRequirement!=null)
    {
      Integer minLevel=levelRequirement.getMinLevel();
      if ((minLevel!=null) && (level<minLevel.intValue()))
      {
        return false;
      }
      Integer maxLevel=levelRequirement.getMaxLevel();
      if ((maxLevel!=null) && (level>maxLevel.intValue()))
      {
        return false;
      }
    }
    ClassRequirement classRequirement=getClassRequirement();
    if (classRequirement!=null)
    {
      if (!classRequirement.accept(characterClass))
      {
        return false;
      }
    }
    RaceRequirement raceRequirement=getRaceRequirement();
    if (raceRequirement!=null)
    {
      if (!raceRequirement.accept(race))
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
    for(Class<? extends Requirement> requirementClass : REQUIREMENT_CLASSES)
    {
      Requirement requirement=getRequirement(requirementClass);
      if (requirement!=null)
      {
        sb.append(' ').append(requirement);
      }
    }
    return sb.toString().trim();
  }
}
