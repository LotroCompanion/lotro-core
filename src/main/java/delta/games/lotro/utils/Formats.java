package delta.games.lotro.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Formats.
 * @author DAM
 */
public class Formats
{
  /**
   * Date pattern.
   */
  public static final String DATE_PATTERN="dd/MM/yyyy";

  /**
   * Date pattern with time.
   */
  public static final String DATE_TIME_PATTERN="dd/MM/yyyy HH:mm";

  /**
   * File date pattern.
   */
  public static final String FILE_DATE_PATTERN="yyyy-MM-dd HHmm";

  private static SimpleDateFormat _dateFormatter=new SimpleDateFormat(DATE_PATTERN);
  private static SimpleDateFormat _dateTimeFormatter=new SimpleDateFormat(DATE_TIME_PATTERN);

  /**
   * Get the dates formatter.
   * @return a dates formatter.
   */
  public static SimpleDateFormat getDateFormatter()
  {
    if (_dateFormatter==null)
    {
      _dateFormatter=new SimpleDateFormat(DATE_PATTERN);
      _dateFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
    }
    return _dateFormatter;
  }

  /**
   * Format a date.
   * @param date Date to format.
   * @return A string or <code>null</code> if <code>date</code> is <code>null</code>.
   */
  public static String getDateString(Date date)
  {
    String ret=null;
    if (date!=null)
    {
      ret=_dateFormatter.format(date);
    }
    return ret;
  }

  /**
   * Format a date.
   * @param date Date to format.
   * @return A string or <code>null</code> if <code>date</code> is <code>null</code>.
   */
  public static String getDateTimeString(Date date)
  {
    String ret=null;
    if (date!=null)
    {
      ret=_dateTimeFormatter.format(date);
    }
    return ret;
  }

  /**
   * Format a date.
   * @param date Date to format.
   * @return A string or <code>null</code> if <code>date</code> is <code>null</code>.
   */
  public static String getDateString(Long date)
  {
    String ret=null;
    if (date!=null)
    {
      ret=getDateString(new Date(date.longValue()));
    }
    return ret;
  }

  /**
   * Parse a date string.
   * @param dateStr Input string.
   * @return A date or <code>null</code> if bad format.
   */
  public static Date parseDate(String dateStr)
  {
    Date date=null;
    try
    {
      date=_dateTimeFormatter.parse(dateStr);
    }
    catch(ParseException parseException)
    {
      // Ignored
    }
    if (date==null)
    {
      try
      {
        date=_dateFormatter.parse(dateStr);
      }
      catch(ParseException parseException)
      {
        // Ignored
      }
    }
    return date;
  }

  /**
   * Format a position.
   * @param longitude Longitude (degrees).
   * @param latitude Latitude (degrees).
   * @return A formatted string.
   */
  public static String formatPosition(float longitude, float latitude)
  {
      StringBuilder sb=new StringBuilder();
      // Latitude
      int lat10=(int)(latitude*10);
      boolean south=lat10<0;
      if (lat10<0) lat10=-lat10;
      sb.append(lat10/10);
      sb.append('.');
      sb.append(lat10%10);
      sb.append(south?'S':'N');
      // Separator
      sb.append(' ');
      // Longitude
      int lon10=(int)(longitude*10);
      boolean west=lon10<0;
      if (lon10<0) lon10=-lon10;
      sb.append(lon10/10);
      sb.append('.');
      sb.append(lon10%10);
      sb.append(west?'W':'E');
      return sb.toString();
  }
}
