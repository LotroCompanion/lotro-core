package delta.games.lotro.utils.l10n.dates;

import java.util.Locale;

import delta.games.lotro.utils.l10n.L10nConfiguration;

/**
 * Manager for date formats.
 * @author DAM
 */
public class DateFormatsManager
{
  private DateFormatSpecificationsManager _dateFormatSpecificationsManager;
  private DateFormatSpecification _dateFormat;
  private DateFormatSpecification _dateTimeFormat;

  /**
   * Constructor.
   */
  public DateFormatsManager()
  {
    _dateFormatSpecificationsManager=new DateFormatSpecificationsManager();
    init(new L10nConfiguration());
  }

  /**
   * Get the date format specification to use.
   * @return a date format specification.
   */
  public DateFormatSpecification getDateFormatSpecification()
  {
    return _dateFormat;
  }

  /**
   * Get the date/time format specification to use.
   * @return a date/time format specification.
   */
  public DateFormatSpecification getDateTimeFormatSpecification()
  {
    return _dateTimeFormat;
  }

  /**
   * Initialize this manager using the given configuration.
   * @param config Configuration to apply.
   */
  public void init(L10nConfiguration config)
  {
    initDateFormat(config.getDateFormatID());
    initDateTimeFormat(config.getDateTimeFormatID());
  }

  private void initDateFormat(String formatID)
  {
    formatID=resolveDateFormat(formatID);
    DateFormatSpecification spec=_dateFormatSpecificationsManager.getDateFormatSpecification(formatID);
    if (spec==null)
    {
      spec=_dateFormatSpecificationsManager.getDateFormatSpecification(DateFormatID.DMY);
    }
    _dateFormat=spec;
  }

  private void initDateTimeFormat(String formatID)
  {
    formatID=resolveDateFormat(formatID);
    DateFormatSpecification spec=_dateFormatSpecificationsManager.getDateTimeFormatSpecification(formatID);
    if (spec==null)
    {
      spec=_dateFormatSpecificationsManager.getDateTimeFormatSpecification(DateFormatID.DMY);
    }
    _dateTimeFormat=spec;
  }

  private String resolveDateFormat(String formatID)
  {
    if (DateFormatID.AUTO.equals(formatID))
    {
      Locale locale=Locale.getDefault();
      if ("US".equals(locale.getCountry()))
      {
        formatID=DateFormatID.US;
      }
      else
      {
        formatID=DateFormatID.DMY;
      }
    }
    return formatID;
  }
}
