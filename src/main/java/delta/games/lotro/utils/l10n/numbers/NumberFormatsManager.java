package delta.games.lotro.utils.l10n.numbers;

import delta.games.lotro.utils.l10n.L10nConfiguration;

/**
 * Entry point for number formats management.
 * @author DAM
 */
public class NumberFormatsManager
{
  private LocalizedNumberFormatsManager _numberFormatSpecificationsManager;
  private SingleLocaleNumberFormatsManager _numberFormatSpec;

  /**
   * Constructor.
   */
  public NumberFormatsManager()
  {
    _numberFormatSpecificationsManager=new LocalizedNumberFormatsManager();
    init(new L10nConfiguration());
  }

  /**
   * Get the number format specification to use.
   * @return a number format specification.
   */
  public SingleLocaleNumberFormatsManager getNumberFormatSpecification()
  {
    return _numberFormatSpec;
  }

  /**
   * Initialize this manager using the given configuration.
   * @param config Configuration to apply.
   */
  public void init(L10nConfiguration config)
  {
    String formatID=config.getNumberFormatID();
    SingleLocaleNumberFormatsManager spec=_numberFormatSpecificationsManager.getNumberFormatsManager(formatID);
    if (spec==null)
    {
      spec=_numberFormatSpecificationsManager.getNumberFormatsManager(NumberFormatID.EUROPEAN);
    }
    _numberFormatSpec=spec;
  }
}
