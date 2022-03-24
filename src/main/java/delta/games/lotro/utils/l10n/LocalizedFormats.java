package delta.games.lotro.utils.l10n;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import delta.games.lotro.utils.Formats;

/**
 * Facade for formats access.
 * @author DAM
 */
public class LocalizedFormats
{
  /**
   * Get the number format for integers.
   * @return A number format for integers.
   */
  public static NumberFormat getIntegerNumberFormat()
  {
    return NumberFormat.getIntegerInstance();
  }

  /**
   * Get the format for date/time.
   * @return A format for date/time.
   */
  public static DateFormat getDateTimeFormat()
  {
    //return DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT);
    return new SimpleDateFormat(Formats.DATE_TIME_PATTERN);
  }

  /**
   * Get the format for date.
   * @return A format for date.
   */
  public static DateFormat getDateFormat()
  {
    return new SimpleDateFormat(Formats.DATE_PATTERN);
  }

  /**
   * Get the format for GMT date.
   * @return A format for GMT date.
   */
  public static DateFormat getGMTDateFormat()
  {
    SimpleDateFormat ret=new SimpleDateFormat(Formats.DATE_PATTERN);
    //ret.setTimeZone(TimeZone.getTimeZone("GMT"));
    return ret;
  }
}
