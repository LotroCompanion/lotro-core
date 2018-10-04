package delta.games.lotro.common;

import delta.common.utils.NumericTools;

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

  /**
   * Parse a duration string (DdHhMmSs).
   * @param input Input string.
   * @return A duration value or <code>null</code> if parsing failed.
   */
  public static Integer parseDurationString(String input)
  {
    int days=0;
    int hours=0;
    int minutes=0;
    int seconds=0;
    input=input.replace(" ","");
    // Days
    int daysIndex=input.indexOf('d');
    if (daysIndex!=-1)
    {
      days=NumericTools.parseInt(input.substring(0,daysIndex),-1);
      if (days==-1) return null;
      input=input.substring(daysIndex+1);
    }
    // Hours
    int hoursIndex=input.indexOf('h');
    if (hoursIndex!=-1)
    {
      hours=NumericTools.parseInt(input.substring(0,hoursIndex),-1);
      if (hours==-1) return null;
      input=input.substring(hoursIndex+1);
    }
    // Minutes
    int minutesIndex=input.indexOf('m');
    if (minutesIndex!=-1)
    {
      minutes=NumericTools.parseInt(input.substring(0,minutesIndex),-1);
      if (minutes==-1) return null;
      input=input.substring(minutesIndex+1);
    }
    // Seconds
    int secondsIndex=input.indexOf('s');
    if (secondsIndex!=-1)
    {
      seconds=NumericTools.parseInt(input.substring(0,secondsIndex),-1);
      if (seconds==-1) return null;
      input=input.substring(secondsIndex+1);
    }
    return Integer.valueOf(days*DAY+hours*HOUR+minutes*MINUTE+seconds);
  }
}
