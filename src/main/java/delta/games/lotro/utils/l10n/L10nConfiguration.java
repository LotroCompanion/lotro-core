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
  private String _numberFormatID;

  /**
   * Constructor.
   */
  public L10nConfiguration()
  {
    _dateFormatID=DateFormatID.AUTO;
    _dateTimeFormatID=DateFormatID.AUTO;
    _numberFormatID=NumberFormatID.AUTO;
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
   * Get the identifier of the number format to use.
   * @return a number format identifier.
   */
  public String getNumberFormatID()
  {
    return _numberFormatID;
  }

  /**
   * Set the identifier of the number format to use.
   * @param numberFormatID the identifier of the number format to use.
   */
  public void setNumberFormatID(String numberFormatID)
  {
    _numberFormatID=numberFormatID;
  }
}

