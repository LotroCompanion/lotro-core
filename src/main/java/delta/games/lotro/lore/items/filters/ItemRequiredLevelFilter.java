package delta.games.lotro.lore.items.filters;

import delta.games.lotro.lore.items.Item;

/**
 * Filter items that can be used by a character, because of its level.
 * @author DAM
 */
public class ItemRequiredLevelFilter implements ItemFilter
{
  private boolean _enabled;
  private int _level;

  /**
   * Constructor.
   * @param level Level to use.
   */
  public ItemRequiredLevelFilter(int level)
  {
    _level=level;
    _enabled=true;
  }

  /**
   * Indicates if this filter is enabled or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isEnabled()
  {
    return _enabled;
  }

  /**
   * Enable this filter or not.
   * @param enabled <code>true</code> to enable filtering, <code>false</code> to disable filtering.
   */
  public void setEnabled(boolean enabled)
  {
    _enabled=enabled;
  }

  public boolean accept(Item item)
  {
    if (!_enabled)
    {
      return true;
    }
    Integer itemMinLevel=item.getMinLevel();
    if (itemMinLevel!=null)
    {
      return (itemMinLevel.intValue()<=_level);
    }
    return true;
  }
}
