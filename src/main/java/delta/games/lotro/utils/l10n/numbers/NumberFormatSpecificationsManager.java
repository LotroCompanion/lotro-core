package delta.games.lotro.utils.l10n.numbers;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Manager for number format specifications.
 * @author DAM
 */
public class NumberFormatSpecificationsManager
{
  private Map<String,NumberFormatSpecification> _integerSpecs;

  /**
   * Constructor.
   */
  public NumberFormatSpecificationsManager()
  {
    _integerSpecs=new HashMap<String,NumberFormatSpecification>();
    initSpecifications();
  }

  /**
   * Get a number format specification for integers, using its internal identifier.
   * @param id An identifier.
   * @return A specification or <code>null</code> if not found.
   */
  public NumberFormatSpecification getIntegerFormatSpecification(String id)
  {
    return _integerSpecs.get(id);
  }

  private void initSpecifications()
  {
    initUS();
    initEuropean();
  }

  private void initUS()
  {
    NumberFormat format=NumberFormat.getIntegerInstance(Locale.US);
    NumberFormatSpecification spec=new NumberFormatSpecification(NumberFormatID.US,format);
    _integerSpecs.put(spec.getId(),spec);
  }

  private void initEuropean()
  {
    NumberFormat format=NumberFormat.getIntegerInstance(Locale.GERMANY);
    NumberFormatSpecification spec=new NumberFormatSpecification(NumberFormatID.EUROPEAN,format);
    _integerSpecs.put(spec.getId(),spec);
  }
}
