package delta.games.lotro.utils.l10n.numbers;

import java.text.NumberFormat;

/**
 * Specification of a number format.
 * @author DAM
 */
public class NumberFormatSpecification
{
  private String _id;
  private NumberFormat _format;

  /**
   * Constructor.
   * @param id Format identifier.
   * @param format Format to use.
   */
  public NumberFormatSpecification(String id, NumberFormat format)
  {
    _id=id;
    _format=format;
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
  public NumberFormat getFormat()
  {
    return _format;
  }
}
