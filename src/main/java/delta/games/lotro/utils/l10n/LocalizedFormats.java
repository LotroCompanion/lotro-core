package delta.games.lotro.utils.l10n;

import java.text.NumberFormat;

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
}
