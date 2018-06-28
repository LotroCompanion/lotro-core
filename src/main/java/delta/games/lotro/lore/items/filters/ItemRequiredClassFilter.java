package delta.games.lotro.lore.items.filters;

import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.lore.items.Item;

/**
 * Filter items that can be used by a character class.
 * @author DAM
 */
public class ItemRequiredClassFilter implements ItemFilter
{
  private boolean _enabled;
  private CharacterClass _characterClass;
  private boolean _strict;

  /**
   * Constructor.
   * @param characterClass Character class to use.
   * @param strict Allow only class items, or allow all non class restricted items.
   */
  public ItemRequiredClassFilter(CharacterClass characterClass, boolean strict)
  {
    _characterClass=characterClass;
    _strict=strict;
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
    CharacterClass required=item.getRequiredClass();
    if (((!_strict) && (required==null)) || (required==_characterClass))
    {
      return true;
    }
    return false;
  }
}
