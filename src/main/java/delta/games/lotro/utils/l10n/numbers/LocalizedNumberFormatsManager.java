package delta.games.lotro.utils.l10n.numbers;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Specification of a number format.
 * @author DAM
 */
public class LocalizedNumberFormatsManager
{
  private String _id;
  private Locale _locale;
  private NumberFormat _format;
  private Map<String,NumberFormat> _formats;

  /**
   * Constructor.
   * @param id Format identifier.
   * @param locale Locale to use.
   */
  public LocalizedNumberFormatsManager(String id, Locale locale)
  {
    _id=id;
    _locale=locale;
    _format=NumberFormat.getIntegerInstance(locale);
    _formats=new HashMap<String,NumberFormat>();
  }

  /**
   * Get the identifier of the managed format.
   * @return An identifier.
   */
  public String getId()
  {
    return _id;
  }

  /**
   * Get the number format.
   * @return the number format.
   */
  public NumberFormat getIntegerFormat()
  {
    return _format;
  }

  private String getRealKey(int minDigits, int maxDigits)
  {
    return minDigits+"/"+maxDigits;
  }

  /**
   * Get a format for a real number.
   * @param minDigits Minimum number of fractional digits.
   * @param maxDigits Maximum number of fractional digits.
   * @return A number format.
   */
  public NumberFormat getRealNumberFormat(int minDigits, int maxDigits)
  {
    String key=getRealKey(minDigits,maxDigits);
    NumberFormat ret=_formats.get(key);
    if (ret==null)
    {
      ret=NumberFormat.getNumberInstance(_locale);
      ret.setMinimumFractionDigits(minDigits);
      ret.setMaximumFractionDigits(maxDigits);
      _formats.put(key,ret);
    }
    return ret;
  }
}
