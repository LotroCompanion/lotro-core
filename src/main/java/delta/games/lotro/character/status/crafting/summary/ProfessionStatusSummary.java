package delta.games.lotro.character.status.crafting.summary;

import delta.games.lotro.common.enums.CraftTier;
import delta.games.lotro.lore.crafting.Profession;
import delta.games.lotro.lore.reputation.FactionLevel;

/**
 * Summary of profession status for a single character.
 * @author DAM
 */
public class ProfessionStatusSummary
{
  private Profession _profession;
  private CraftTier _proficiency;
  private CraftTier _mastery;
  private FactionLevel _guildLevel;

  /**
   * Constructor.
   * @param profession Managed profession.
   */
  public ProfessionStatusSummary(Profession profession)
  {
    _profession=profession;
  }

  /**
   * Get the managed profession.
   * @return a profession.
   */
  public Profession getProfession()
  {
    return _profession;
  }

  /**
   * Get the crafting proficiency.
   * @return the crafting proficiency.
   */
  public CraftTier getProficiency()
  {
    return _proficiency;
  }

  /**
   * Set the crafting proficiency.
   * @param proficiency the proficiency to set.
   */
  public void setProficiency(CraftTier proficiency)
  {
    _proficiency=proficiency;
  }

  /**
   * Get the crafting mastery.
   * @return the crafting mastery.
   */
  public CraftTier getMastery()
  {
    return _mastery;
  }

  /**
   * Set the crafting mastery.
   * @param mastery the mastery to set
   */
  public void setMastery(CraftTier mastery)
  {
    _mastery=mastery;
  }

  /**
   * Get the guild level.
   * @return a guild level (may be <code>null</code> if no guild).
   */
  public FactionLevel getGuildLevel()
  {
    return _guildLevel;
  }

  /**
   * Set the guild level.
   * @param guildLevel the guild level to set.
   */
  public void setGuildLevel(FactionLevel guildLevel)
  {
    _guildLevel=guildLevel;
  }
}
