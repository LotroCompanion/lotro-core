package delta.games.lotro.utils;

import java.text.ParseException;
import java.util.Date;

import delta.games.lotro.utils.l10n.LocalizedFormats;

/**
 * Formats.
 * @author DAM
 */
public class Formats
{
  /**
   * File date pattern.
   */
  public static final String FILE_DATE_PATTERN="yyyy-MM-dd HHmm";

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
      ret=LocalizedFormats.getDateFormat().format(date);
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
      ret=LocalizedFormats.getDateTimeFormat().format(date);
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
      date=LocalizedFormats.getDateTimeFormat().parse(dateStr);
    }
    catch(ParseException parseException)
    {
      // Ignored
    }
    if (date==null)
    {
      try
      {
        date=LocalizedFormats.getDateFormat().parse(dateStr);
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
