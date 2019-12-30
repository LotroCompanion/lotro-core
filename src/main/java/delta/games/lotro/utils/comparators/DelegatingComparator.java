package delta.games.lotro.utils.comparators;

import java.util.Comparator;

import delta.games.lotro.utils.DataProvider;

/**
 * Delegating comparator.
 * @param <SOURCE_POJO> Source POJO type.
 * @param <POJO> Intermediate POJO type.
 * @author DAM
 */
public class DelegatingComparator<SOURCE_POJO,POJO> implements Comparator<SOURCE_POJO>
{
  private DataProvider<SOURCE_POJO,POJO> _dataProvider;
  private Comparator<POJO> _comparator;

  /**
   * Constructor.
   * @param dataProvider Provider to resolve a POJO from a source POJO.
   * @param comparator Wrapped comparator.
   */
  public DelegatingComparator(final DataProvider<SOURCE_POJO,POJO> dataProvider, Comparator<POJO> comparator)
  {
    _dataProvider=dataProvider;
    _comparator=comparator;
  }

  @Override
  public int compare(SOURCE_POJO o1, SOURCE_POJO o2)
  {
    POJO p1=_dataProvider.getData(o1);
    POJO p2=_dataProvider.getData(o2);
    return _comparator.compare(p1,p2);
  }
}
