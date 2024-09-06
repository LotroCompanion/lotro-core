package delta.games.lotro.character.skills.attack;

import delta.games.lotro.common.stats.StatDescription;

/**
 * Character data for skill details.
 * @author DAM
 */
public class CharacterDataForSkills
{
  private ClassDataForSkills _classData;

  /**
   * Constructor.
   */
  public CharacterDataForSkills()
  {
    _classData=new ClassDataForSkills();
  }

  /**
   * Get the class data.
   * @return the class data.
   */
  public ClassDataForSkills getClassData()
  {
    return _classData;
  }

  /**
   * Get a stat value for this character.
   * @param stat Stat to use.
   * @return the stat value.
   */
  public float getStat(StatDescription stat)
  {
    return 0f;
  }

  /**
   * Get the character level.
   * @return the character level.
   */
  public int getLevel()
  {
    return 1;
  }
}
