package delta.games.lotro.common;

/**
 * Duration related tools.
 * @author DAM
 */
public class Duration
{
  /**
   * Minute duration in seconds.
   */
  public static final int MINUTE=60;
  /**
   * Hour duration in seconds.
   */
  public static final int HOUR=MINUTE*60;
  /**
   * Day duration in seconds.
   */
  public static final int DAY=HOUR*24;

  /**
   * Get a duration string from a duration input.
   * @param duration Duration in seconds.
   * @return A displayable string.
   */
  public static String getDurationString(int duration)
  {
    StringBuilder sb=new StringBuilder();
    int days=duration/DAY;
    if (days>0)
    {
      sb.append(days).append('d');
    }
    duration=duration%DAY;
    int hours=duration/HOUR;
    if (hours>0)
    {
      sb.append(hours).append('h');
    }
    duration=duration%HOUR;
    int minutes=duration/MINUTE;
    if (minutes>0)
    {
      sb.append(minutes).append('m');
    }
    int seconds=duration%MINUTE;
    if (seconds>0)
    {
      sb.append(seconds).append('s');
    }
    return sb.toString();
  }
}
