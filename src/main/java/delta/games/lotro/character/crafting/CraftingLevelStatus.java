package delta.games.lotro.character.crafting;

import delta.games.lotro.crafting.CraftingLevel;

/**
 * Crafting status for a given level on a given profession.
 * @author DAM
 */
public class CraftingLevelStatus
{
  private CraftingLevel _level;
  private CraftingLevelTierStatus _proficiency;
  private CraftingLevelTierStatus _mastery;

  /**
   * Constructor.
   * @param level Associated level.
   */
  public CraftingLevelStatus(CraftingLevel level)
  {
    _level=level;
    _proficiency=new CraftingLevelTierStatus(_level.getProficiency());
    _mastery=new CraftingLevelTierStatus(_level.getMastery());
  }

  /**
   * Get the associated level.
   * @return the associated level.
   */
  public CraftingLevel getLevel()
  {
    return _level;
  }

  /**
   * Get the proficiency status.
   * @return the proficiency status.
   */
  public CraftingLevelTierStatus getProficiency()
  {
    return _proficiency;
  }

  /**
   * Get the mastery status.
   * @return the mastery status.
   */
  public CraftingLevelTierStatus getMastery()
  {
    return _mastery;
  }
}
