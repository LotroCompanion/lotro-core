package delta.games.lotro.character.skills;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.games.lotro.common.inductions.Induction;
import delta.games.lotro.common.stats.StatModifiersComputer;

/**
 * Utility methods related to skill details.
 * @author DAM
 */
public class SkillDetailsUtils
{
  private static final Logger LOGGER=LoggerFactory.getLogger(SkillDetailsUtils.class);

  /**
   * Get the induction duration for a skill.
   * @param details Skill details.
   * @param statModsComputer Stat modifiers computer.
   * @return A duration (seconds).
   */
  public static float getInductionDuration(SkillDetails details, StatModifiersComputer statModsComputer)
  {
    Induction induction=details.getInduction();
    if (induction==null)
    {
      return 0;
    }
    float baseInductionDuration=induction.getDuration();
    LOGGER.debug("Induction (base): {}",Float.valueOf(baseInductionDuration));
    float inductionAddMods=statModsComputer.computeAdditiveModifiers(induction.getAddMods());
    float inductionDuration=baseInductionDuration+inductionAddMods;
    LOGGER.debug("Induction (with + mods): {}",Float.valueOf(inductionDuration));
    float inductionMultiplyMods=statModsComputer.computeMultiplicativeModifiers(induction.getMultiplyMods());
    inductionDuration*=inductionMultiplyMods;
    LOGGER.debug("Induction (with x mods): {}",Float.valueOf(inductionDuration));
    return inductionDuration;
  }
}
