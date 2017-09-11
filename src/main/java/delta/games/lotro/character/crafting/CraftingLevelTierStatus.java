package delta.games.lotro.character.crafting;

import java.util.Date;

import delta.games.lotro.crafting.CraftingLevelTier;

/**
 * Crafting status for a tier of a level of a profession.
 * @author DAM
 */
public class CraftingLevelTierStatus
{
  private CraftingLevelTier _tier;
  private int _acquiredXP;
  private boolean _completed;
  private long _completionDate;

  /**
   * Constructor.
   * @param tier Associated tier.
   */
  public CraftingLevelTierStatus(CraftingLevelTier tier)
  {
    _tier=tier;
    _acquiredXP=0;
    _completed=false;
    _completionDate=0;
  }

  /**
   * Get the associated level tier.
   * @return the associated level tier.
   */
  public CraftingLevelTier getLevelTier()
  {
    return _tier;
  }

  /**
   * Get the acquired XP amount.
   * @return the acquired XP amount.
   */
  public int getAcquiredXP()
  {
    return _acquiredXP;
  }

  /**
   * Set the acquired XP amount.
   * @param acquiredXP XP amount to set.
   */
  public void setAcquiredXP(int acquiredXP)
  {
    _acquiredXP=acquiredXP;
  }

  /**
   * Indicates if this tier is completed or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isCompleted()
  {
    return _completed;
  }

  /**
   * Set the 'completed' flag.
   * @param completed Value to set.
   */
  public void setCompleted(boolean completed)
  {
    _completed=completed;
  }

  /**
   * Get the tier completion date.
   * @return the completion date.
   */
  public long getCompletionDate()
  {
    return _completionDate;
  }

  /**
   * Set the completion date.
   * @param completionDate date to set.
   */
  public void setCompletionDate(long completionDate)
  {
    _completionDate=completionDate;
  }

  /**
   * Set tier as completed.
   * @param date Completion date.
   */
  public void setCompleted(long date)
  {
    setAcquiredXP(_tier.getXP());
    setCompleted(true);
    setCompletionDate(date);
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append(_tier.getLabel());
    sb.append(": xp=").append(_acquiredXP).append('/').append(_tier.getXP());
    sb.append(", completed=").append(_completed);
    if (_completed)
    {
      sb.append(", date=").append(_completionDate);
      sb.append(" (").append(new Date(_completionDate)).append(')');
    }
    return sb.toString();
  }
}
