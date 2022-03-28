package delta.games.lotro.utils.l10n;

import delta.games.lotro.utils.l10n.dates.DateFormatID;
import delta.games.lotro.utils.l10n.numbers.NumberFormatID;

/**
 * Configuration of the localization system.
 * @author DAM
 */
public class L10nConfiguration
{
  private String _dateFormatID;
  private String _dateTimeFormatID;
  private String _integerFormatID;

  /**
   * Constructor.
   */
  public L10nConfiguration()
  {
    _dateFormatID=DateFormatID.AUTO;
    _dateTimeFormatID=DateFormatID.AUTO;
    _integerFormatID=NumberFormatID.AUTO;
  }

  /**
   * Get the identifier of the date format to use.
   * @return a date format identifier.
   */
  public String getDateFormatID()
  {
    return _dateFormatID;
  }

  /**
   * Set the identifier of the date format to use.
   * @param dateFormatID the identifier of the date format to use.
   */
  public void setDateFormatID(String dateFormatID)
  {
    _dateFormatID=dateFormatID;
  }

  /**
   * Get the identifier of the date/time format to use.
   * @return a date/time format identifier.
   */
  public String getDateTimeFormatID()
  {
    return _dateTimeFormatID;
  }

  /**
   * Set the identifier of the date/time format to use.
   * @param dateTimeFormatID the identifier of the date/time format to use.
   */
  public void setDateTimeFormatID(String dateTimeFormatID)
  {
    _dateTimeFormatID=dateTimeFormatID;
  }

  /**
   * Get the identifier of the integer format to use.
   * @return an integer format identifier.
   */
  public String getIntegerFormatID()
  {
    return _integerFormatID;
  }

  /**
   * Set the identifier of the integer format to use.
   * @param integerFormatID the identifier of the integer format to use.
   */
  public void setIntegerFormatID(String integerFormatID)
  {
    _integerFormatID=integerFormatID;
  }
}

