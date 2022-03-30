package delta.games.lotro.utils.l10n.numbers;

import delta.games.lotro.utils.l10n.L10nConfiguration;

/**
 * Manager for number formats.
 * @author DAM
 */
public class NumberFormatsManager
{
  private NumberFormatSpecificationsManager _numberFormatSpecificationsManager;
  private LocalizedNumberFormatsManager _numberFormatSpec;

  /**
   * Constructor.
   */
  public NumberFormatsManager()
  {
    _numberFormatSpecificationsManager=new NumberFormatSpecificationsManager();
    init(new L10nConfiguration());
  }

  /**
   * Get the number format specification to use.
   * @return a number format specification.
   */
  public LocalizedNumberFormatsManager getNumberFormatSpecification()
  {
    return _numberFormatSpec;
  }

  /**
   * Initialize this manager using the given configuration.
   * @param config Configuration to apply.
   */
  public void init(L10nConfiguration config)
  {
    initIntegerFormat(config.getNumberFormatID());
  }

  private void initIntegerFormat(String formatID)
  {
    LocalizedNumberFormatsManager spec=_numberFormatSpecificationsManager.getIntegerFormatSpecification(formatID);
    if (spec==null)
    {
      spec=_numberFormatSpecificationsManager.getIntegerFormatSpecification(NumberFormatID.EUROPEAN);
    }
    _numberFormatSpec=spec;
  }
}
