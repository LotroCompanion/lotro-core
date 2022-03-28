package delta.games.lotro.utils.l10n.numbers;

import java.util.Locale;

import delta.games.lotro.utils.l10n.L10nConfiguration;

/**
 * Manager for number formats.
 * @author DAM
 */
public class NumberFormatsManager
{
  private NumberFormatSpecificationsManager _numberFormatSpecificationsManager;
  private NumberFormatSpecification _integerFormat;

  /**
   * Constructor.
   */
  public NumberFormatsManager()
  {
    _numberFormatSpecificationsManager=new NumberFormatSpecificationsManager();
    init(new L10nConfiguration());
  }

  /**
   * Get the integer format specification to use.
   * @return an integer format specification.
   */
  public NumberFormatSpecification getIntegerFormatSpecification()
  {
    return _integerFormat;
  }

  /**
   * Initialize this manager using the given configuration.
   * @param config Configuration to apply.
   */
  public void init(L10nConfiguration config)
  {
    initIntegerFormat(config.getIntegerFormatID());
  }

  private void initIntegerFormat(String formatID)
  {
    formatID=resolveNumberFormat(formatID);
    NumberFormatSpecification spec=_numberFormatSpecificationsManager.getIntegerFormatSpecification(formatID);
    if (spec==null)
    {
      spec=_numberFormatSpecificationsManager.getIntegerFormatSpecification(NumberFormatID.EUROPEAN);
    }
    _integerFormat=spec;
  }

  private String resolveNumberFormat(String formatID)
  {
    if (NumberFormatID.AUTO.equals(formatID))
    {
      Locale locale=Locale.getDefault();
      if ("US".equals(locale.getCountry()))
      {
        formatID=NumberFormatID.US;
      }
      else
      {
        formatID=NumberFormatID.EUROPEAN;
      }
    }
    return formatID;
  }
}
