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
   * Week duration in seconds.
   */
  public static final int WEEK=DAY*7;
  /**
   * Month duration in seconds.
   */
  public static final int MONTH=DAY*30;
  /**
   * Year duration in seconds.
   */
  public static final int YEAR=MONTH*12;

  private static final int SHOW_SECONDS=1<<0;
  private static final int SHOW_MINUTES=1<<1;
  private static final int SHOW_HOURS=1<<2;
  private static final int SHOW_DAYS=1<<3;
  private static final int SHOW_WEEKS=1<<5;
  private static final int SHOW_MONTHS=1<<6;
  private static final int SHOW_YEARS=1<<7;
  private static final int SHOW_ALL=SHOW_YEARS+SHOW_MONTHS+SHOW_DAYS+SHOW_HOURS+SHOW_MINUTES+SHOW_SECONDS;

  /**
   * Get a duration string from a duration input.
   * @param duration Duration in seconds.
   * @return A displayable string.
   */
  public static String getDurationString(int duration)
  {
    return getDurationString(duration,SHOW_ALL);
  }

  private static String getDurationString(int duration, int showFlags)
  {
    StringBuilder sb=new StringBuilder();
    // Years
    if ((showFlags&SHOW_YEARS)!=0)
    {
      int years=duration/YEAR;
      if (years>0)
      {
        sb.append(years).append('y');
      }
      duration=duration%YEAR;
    }
    // Months
    if ((showFlags&SHOW_MONTHS)!=0)
    {
      int months=duration/MONTH;
      if (months>0)
      {
        sb.append(months).append('m');
      }
      duration=duration%MONTH;
    }
    // Weeks
    if ((showFlags&SHOW_WEEKS)!=0)
    {
      int weeks=duration/WEEK;
      if (weeks>0)
      {
        sb.append(weeks).append('w');
      }
      duration=duration%WEEK;
    }
    // Days
    if ((showFlags&SHOW_DAYS)!=0)
    {
      int days=duration/DAY;
      if (days>0)
      {
        sb.append(days).append('d');
      }
      duration=duration%DAY;
    }
    // Hours
    if ((showFlags&SHOW_HOURS)!=0)
    {
      int hours=duration/HOUR;
      if (hours>0)
      {
        sb.append(hours).append('h');
      }
      duration=duration%HOUR;
    }
    // Minutes
    if ((showFlags&SHOW_MINUTES)!=0)
    {
      int minutes=duration/MINUTE;
      if (minutes>0)
      {
        sb.append(minutes).append('m');
      }
    }
    // Seconds
    if ((showFlags&SHOW_SECONDS)!=0)
    {
      int seconds=duration%MINUTE;
      if ((seconds>0) || (sb.length()==0))
      {
        sb.append(seconds).append('s');
      }
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
    }
    return Integer.valueOf(days*DAY+hours*HOUR+minutes*MINUTE+seconds);
  }

  /**
   * Get a smart display for durations.
   * @param duration Duration in seconds.
   * @return A duration string.
   */
  public static String getSmartDurationString(int duration)
  {
    if (duration<HOUR)
    {
      // Display minutes and seconds
      return getDurationString(duration,SHOW_MINUTES|SHOW_SECONDS);
    }
    else if (duration<DAY)
    {
      // Display hours and minutes
      return getDurationString(duration,SHOW_HOURS|SHOW_MINUTES);
    }
    else if (duration<MONTH)
    {
      // Display days and hours
      return getDurationString(duration,SHOW_DAYS|SHOW_HOURS);
    }
    else if (duration<YEAR)
    {
      // Display months and days
      return getDurationString(duration,SHOW_MONTHS|SHOW_DAYS);
    }
    // Display years and months
    return getDurationString(duration,SHOW_YEARS|SHOW_MONTHS);
  }
}
