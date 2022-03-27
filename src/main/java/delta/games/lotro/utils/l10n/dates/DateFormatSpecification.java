package delta.games.lotro.utils.l10n.dates;

import java.text.SimpleDateFormat;

/**
 * Specification of a date format.
 * @author DAM
 */
public class DateFormatSpecification
{
  private String _id;
  private String _datePattern;
  private SimpleDateFormat _format;
  private int _minSize;
  private int _maxSize;
  private int _columnSize;

  /**
   * Constructor.
   * @param id Format identifier.
   * @param datePattern Format pattern.
   * @param minSize Minimum size of a valid value (in characters).
   * @param maxSize Maximul size of a valid value (in characters).
   * @param columnSize Size of columns (in pixels)
   */
  public DateFormatSpecification(String id, String datePattern, int minSize, int maxSize, int columnSize)
  {
    _id=id;
    _datePattern=datePattern;
    _format=new SimpleDateFormat(datePattern);
    _minSize=minSize;
    _maxSize=maxSize;
    _columnSize=columnSize;
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
   * Get the date pattern.
   * @return the date pattern.
   */
  public String getDatePattern()
  {
    return _datePattern;
  }

  /**
   * Get the date format.
   * @return the date format.
   */
  public SimpleDateFormat getFormat()
  {
    return _format;
  }

  /**
   * Get the minimum length of valid values.
   * @return a length (in characters).
   */
  public int getMinLength()
  {
    return _minSize;
  }

  /**
   * Get the maximum length of valid values.
   * @return a length (in characters).
   */
  public int getMaxLength()
  {
    return _maxSize;
  }

  /**
   * Get the size to use in columns.
   * @return a size in pixels.
   */
  public int getColumnSize()
  {
    return _columnSize;
  }
}
