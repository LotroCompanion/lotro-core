package delta.games.lotro.common.stats;

import delta.games.lotro.values.codec.ValueWriter;

/**
 * A constant stat provider that uses a generic type for value.
 * @param <T> Type of managed values.
 * @author DAM
 */
public class GenericConstantStatProvider<T> extends AbstractStatProvider
{
  protected T _value;

  /**
   * Constructor.
   * @param stat Targeted stat.
   */
  public GenericConstantStatProvider(StatDescription stat)
  {
    super(stat);
  }

  /**
   * Get the raw value.
   * @return the stored value or <code>null</code>.
   */
  public T getRawValue()
  {
    return _value;
  }

  /**
   * Set the raw value.
   * @param value Value to set.
   */
  public void setRawValue(T value)
  {
    _value=value;
  }

  /**
   * Get a string representation of the managed value.
   * @return A string or <code>null</code>.
   */
  public String asPersistentString()
  {
    if (_value==null)
    {
      return null;
    }
    return ValueWriter.write(_value);
  }

  @Override
  public Float getStatValue(int tier, int level)
  {
    return null;
  }
}
