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
  private long _completionDate;

  /**
   * Constructor.
   * @param level Associated level.
   */
  public FactionLevelStatus(FactionLevel level)
  {
    _level=level;
    _completionDate=0;
  }

  /**
   * Copy constructor.
   * @param source Source status.
   */
  public FactionLevelStatus(FactionLevelStatus source)
  {
    _level=source._level;
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

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append(_level.getName());
    if (_completionDate!=0)
    {
      sb.append(", date=").append(_completionDate);
      sb.append(" (").append(new Date(_completionDate)).append(')');
    }
    return sb.toString();
  }
}
