package delta.games.lotro.lore.items.stats;

import java.util.Comparator;

import delta.games.lotro.character.stats.STAT;

/**
 * Data for a slice based formula for a single item stat.
 * @author DAM
 */
public class ItemStatSliceData
{
  private STAT _stat;
  private Float _sliceCount;
  private String _additionalParameter;

  /**
   * Constructor.
   * @param stat Targeted stat.
   * @param sliceCount Slice count.
   * @param additionalParameter Additional parameter.
   */
  public ItemStatSliceData(STAT stat, Float sliceCount, String additionalParameter)
  {
    _stat=stat;
    _sliceCount=sliceCount;
    _additionalParameter=additionalParameter;
  }

  /**
   * Get the targeted stat.
   * @return A stat.
   */
  public STAT getStat()
  {
    return _stat;
  }

  /**
   * Get the slice count.
   * @return the slice count.
   */
  public Float getSliceCount()
  {
    return _sliceCount;
  }

  /**
   * Get the additional parameter, if any.
   * @return a string value or <code>null</code>.
   */
  public String getAdditionalParameter()
  {
    return _additionalParameter;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    toString(sb);
    return sb.toString();
  }

  /**
   * Persist this object into the given string builder.
   * @param sb Storage to write to.
   */
  public void toString(StringBuilder sb)
  {
    sb.append(_stat.name());
    if (_additionalParameter!=null)
    {
      sb.append('(').append(_additionalParameter).append(')');
    }
    if (_sliceCount!=null)
    {
      sb.append(':');
      sb.append(_sliceCount);
    }
  }

  /**
   * Comparator for slices.
   * @author DAM
   */
  public static class SliceComparator implements Comparator<ItemStatSliceData>
  {
    public int compare(ItemStatSliceData slice1, ItemStatSliceData slice2)
    {
      return slice1.getStat().compareTo(slice2.getStat());
    }
  }
}
