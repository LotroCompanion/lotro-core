package delta.games.lotro.lore.items.filters;

import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.lore.items.Item;

/**
 * Filter items that can be used by a character class.
 * @author DAM
 */
public class ItemRequiredClassFilter implements ItemFilter
{
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
  }

  public boolean accept(Item item)
  {
    CharacterClass required=item.getRequiredClass();
    if (((!_strict) && (required==null)) || (required==_characterClass))
    {
      return true;
    }
    return false;
  }
}
