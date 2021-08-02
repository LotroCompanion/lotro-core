package delta.games.lotro.character.status.crafting;

import delta.games.lotro.lore.crafting.CraftingLevel;

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

  /**
   * Indicates if this level has been started or not.
   * @return <code>true</code> if it has, <code>false</code> otherwise.
   */
  public boolean isStarted()
  {
    if (_proficiency.isCompleted()) return true;
    if (_proficiency.getAcquiredXP()>0) return true;
    if (_mastery.isCompleted()) return true;
    if (_mastery.getAcquiredXP()>0) return true;
    return false;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Level: ").append(_level);
    sb.append(", proficiency: ").append(_proficiency);
    sb.append(", mastery: ").append(_mastery);
    return sb.toString();
  }
}
