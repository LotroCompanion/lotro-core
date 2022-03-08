package delta.games.lotro.utils.l10n;

/**
 * Localization utilities.
 * @author DAM
 */
public class L10n
{
  /**
   * Get a localized string for a number (integer).
   * @param number Value to use.
   * @return A localized string.
   */
  public static String getString(int number)
  {
    return LocalizedFormats.getIntegerNumberFormat().format(number);
  }

  /**
   * Get a localized string for a number (long).
   * @param number Value to use.
   * @return A localized string.
   */
  public static String getString(long number)
  {
    return LocalizedFormats.getIntegerNumberFormat().format(number);
  }
}
