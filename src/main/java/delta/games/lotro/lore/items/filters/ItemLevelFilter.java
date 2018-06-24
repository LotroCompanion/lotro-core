package delta.games.lotro.lore.items.filters;

import delta.games.lotro.lore.items.Item;

/**
 * Filter items whose item level is in the specified range.
 * @author DAM
 */
public class ItemLevelFilter implements ItemFilter
{
  private Integer _minItemLevel;
  private Integer _maxItemLevel;

  /**
   * Constructor.
   */
  public ItemLevelFilter()
  {
    _minItemLevel=null;
    _maxItemLevel=null;
  }

  /**
   * Set the range to use.
   * @param min Min value (may be <code>null</code>).
   * @param max Max value (may be <code>null</code>).
   */
  public void setRange(Integer min, Integer max)
  {
    _minItemLevel=min;
    _maxItemLevel=max;
  }

  public boolean accept(Item item)
  {
    boolean ret=true;
    Integer itemLevel=item.getItemLevel();
    if (itemLevel!=null)
    {
      if (_minItemLevel!=null)
      {
        if (itemLevel.intValue()<_minItemLevel.intValue())
        {
          ret=false;
        }
      }
      if (_maxItemLevel!=null)
      {
        if (itemLevel.intValue()>_maxItemLevel.intValue())
        {
          ret=false;
        }
      }
    }
    return ret;
  }
}
