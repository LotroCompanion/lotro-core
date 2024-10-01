package delta.games.lotro.character.skills;

import delta.games.lotro.character.skills.attack.CharacterDataForSkills;
import delta.games.lotro.common.inductions.Induction;

/**
 * Utility methods related to skill details.
 * @author DAM
 */
public class SkillDetailsUtils
{
  /**
   * Get the induction duration for a skill.
   * @param details Skill details.
   * @param character Character to use.
   * @return A duration (seconds).
   */
  public static float getInductionDuration(SkillDetails details, CharacterDataForSkills character)
  {
    Induction induction=details.getInduction();
    if (induction==null)
    {
      return 0;
    }
    float baseInductionDuration=induction.getDuration();
    float inductionAddMods=character.computeAdditiveModifiers(induction.getAddMods());
    float inductionDuration=baseInductionDuration+inductionAddMods;
    float inductionMultiplyMods=character.computeMultiplicativeModifiers(induction.getMultiplyMods());
    inductionDuration*=inductionMultiplyMods;
    System.out.println("Induction: "+inductionDuration);
    return inductionDuration;
  }
}
