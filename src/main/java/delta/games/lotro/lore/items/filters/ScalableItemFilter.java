package delta.games.lotro.lore.items.filters;

import delta.games.lotro.lore.items.Item;

/**
 * Filter scalable/non scalable items .
 * @author DAM
 */
public class ScalableItemFilter implements ItemFilter
{
  private Boolean _scalable;

  /**
   * Constructor.
   * @param scalable Use scalable or non scalable or both.
   */
  public ScalableItemFilter(Boolean scalable)
  {
    _scalable=scalable;
  }

  /**
   * Set the 'scalable' attribute.
   * @param scalable
   */
  public void setScalable(Boolean scalable)
  {
    _scalable=scalable;
  }

  /**
   * Get the 'scalable' attribute.
   * @return a Boolean value (true, false or null).
   */
  public Boolean getScalable()
  {
    return _scalable;
  }

  @Override
  public boolean accept(Item item)
  {
    if (_scalable!=null)
    {
      boolean isScalable=item.isScalable();
      return (_scalable.booleanValue()==isScalable);
    }
    return true;
  }
}
