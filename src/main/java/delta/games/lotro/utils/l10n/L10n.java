package delta.games.lotro.utils.l10n;

import java.text.NumberFormat;

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

  /**
   * Get a localized string for a number (double).
   * @param number Value to use.
   * @param nbDigits Maximum number of digits.
   * @return A localized string.
   */
  public static String getString(double number, int nbDigits)
  {
    NumberFormat format=LocalizedFormats.getRealNumberFormat(0,nbDigits);
    return format.format(number);
  }
}
