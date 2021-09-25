package delta.games.lotro.common.enums.filter;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.common.enums.LotroEnumEntry;
import delta.games.lotro.utils.DataProvider;

/**
 * Filter for POJOs that yield a given enum value.
 * @author DAM
 * @param <T> Type of enum entries.
 * @param <POJO> Type of source object.
 */
public class LotroEnumValueFilter<T extends LotroEnumEntry,POJO> implements Filter<POJO>
{
  private DataProvider<POJO,T> _provider;
  private T _selected;

  /**
   * Constructor.
   * @param provider Data provider.
   * @param value Value to select (may be <code>null</code>).
   */
  public LotroEnumValueFilter(DataProvider<POJO,T> provider, T value)
  {
    _provider=provider;
    _selected=value;
  }

  /**
   * Get the selected value.
   * @return A value or <code>null</code>.
   */
  public T getValue()
  {
    return _selected;
  }

  /**
   * Set the value to select.
   * @param value Value to select, may be <code>null</code>.
   */
  public void setValue(T value)
  {
    _selected=value;
  }

  @Override
  public boolean accept(POJO source)
  {
    if (_selected==null)
    {
      return true;
    }
    T value=_provider.getData(source);
    return (value==_selected);
  }
}
