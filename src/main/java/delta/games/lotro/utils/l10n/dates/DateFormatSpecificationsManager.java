package delta.games.lotro.utils.l10n.dates;

import java.util.HashMap;
import java.util.Map;

/**
 * Manager for date format specifications.
 * @author DAM
 */
public class DateFormatSpecificationsManager
{
  private Map<String,DateFormatSpecification> _dateSpecs;
  private Map<String,DateFormatSpecification> _dateTimeSpecs;

  /**
   * Constructor.
   */
  public DateFormatSpecificationsManager()
  {
    _dateSpecs=new HashMap<String,DateFormatSpecification>();
    _dateTimeSpecs=new HashMap<String,DateFormatSpecification>();
    initSpecifications();
  }

  /**
   * Get a date format specification using its internal identifier.
   * @param id An identifier.
   * @return A specification or <code>null</code> if not found.
   */
  public DateFormatSpecification getDateFormatSpecification(String id)
  {
    return _dateSpecs.get(id);
  }

  /**
   * Get a date/time format specification using its internal identifier.
   * @param id An identifier.
   * @return A specification or <code>null</code> if not found.
   */
  public DateFormatSpecification getDateTimeFormatSpecification(String id)
  {
    return _dateTimeSpecs.get(id);
  }

  private void initSpecifications()
  {
    initUS();
    initISO8601();
    initDMY();
  }

  private void initUS()
  {
    DateFormatSpecification dateSpec=new DateFormatSpecification(DateFormatID.US,"M/d/yyyy",8,10,75);
    _dateSpecs.put(dateSpec.getId(),dateSpec);
    DateFormatSpecification dateTimeSpec=new DateFormatSpecification(DateFormatID.US,"M/d/yyyy h:mm a",16,19,130);
    _dateTimeSpecs.put(dateTimeSpec.getId(),dateTimeSpec);
  }

  private void initISO8601()
  {
    DateFormatSpecification dateSpec=new DateFormatSpecification(DateFormatID.ISO8601,"yyyy-MM-dd",10,10,75);
    _dateSpecs.put(dateSpec.getId(),dateSpec);
    DateFormatSpecification dateTimeSpec=new DateFormatSpecification(DateFormatID.ISO8601,"yyyy-MM-dd HH:mm",16,16,110);
    _dateTimeSpecs.put(dateTimeSpec.getId(),dateTimeSpec);
  }

  private void initDMY()
  {
    DateFormatSpecification dateSpec=new DateFormatSpecification(DateFormatID.DMY,"dd/MM/yyyy",10,10,75);
    _dateSpecs.put(dateSpec.getId(),dateSpec);
    DateFormatSpecification dateTimeSpec=new DateFormatSpecification(DateFormatID.DMY,"dd/MM/yyyy HH:mm",16,16,110);
    _dateTimeSpecs.put(dateTimeSpec.getId(),dateTimeSpec);
  }
}
