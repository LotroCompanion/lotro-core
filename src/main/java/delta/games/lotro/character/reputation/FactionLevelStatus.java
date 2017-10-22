package delta.games.lotro.character.reputation;

import java.util.Date;

import delta.games.lotro.lore.reputation.FactionLevel;

/**
 * Crafting status for a tier of a level of a profession.
 * @author DAM
 */
public class FactionLevelStatus
{
  private FactionLevel _level;
  private int _acquiredXP;
  private boolean _completed;
  private long _completionDate;

  /**
   * Constructor.
   * @param level Associated level.
   */
  public FactionLevelStatus(FactionLevel level)
  {
    _level=level;
    _acquiredXP=0;
    _completed=false;
    _completionDate=0;
  }

  /**
   * Copy constructor.
   * @param source Source status.
   */
  public FactionLevelStatus(FactionLevelStatus source)
  {
    _level=source._level;
    _acquiredXP=source._acquiredXP;
    _completed=source._completed;
    _completionDate=source._completionDate;
  }

  /**
   * Get the associated level.
   * @return the associated level.
   */
  public FactionLevel getLevel()
  {
    return _level;
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
   * Get the completion date.
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
    setAcquiredXP(_level.getRequiredXp());
    setCompleted(true);
    setCompletionDate(date);
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append(_level.getName());
    sb.append(": xp=").append(_acquiredXP).append('/').append(_level.getRequiredXp());
    sb.append(", completed=").append(_completed);
    if (_completed)
    {
      if (_completionDate!=0)
      {
        sb.append(", date=").append(_completionDate);
        sb.append(" (").append(new Date(_completionDate)).append(')');
      }
    }
    return sb.toString();
  }
}
