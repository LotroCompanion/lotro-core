package delta.games.lotro.lore.items.stats;

/**
 * Progression of item levels (mapping level to item level).
 * @author DAM
 */
public class ItemLevelProgression
{
  private int[] _xValues;
  private int[] _yValues;

  /**
   * Constructor.
   * @param size Number of supported points.
   */
  public ItemLevelProgression(int size)
  {
    _xValues=new int[size];
    _yValues=new int[size];
  }

  /**
   * Set the value of a single point.
   * @param index Index of the point to set.
   * @param x X value to set.
   * @param y Y value to set.
   */
  public void set(int index, int x, int y)
  {
    _xValues[index]=x;
    _yValues[index]=y;
  }

  /**
   * Get the item level value for a given level value. 
   * @param level Level value.
   * @return An item level value or <code>null</code> if not supported.
   */
  public Integer getValue(int level)
  {
    for(int i=0;i<_xValues.length;i++)
    {
      if (level==_xValues[i]) return Integer.valueOf(_yValues[i]);
    }
    return null;
  }
}
