package delta.games.lotro.lore.items.filters;

import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.common.requirements.ClassRequirement;
import delta.games.lotro.common.requirements.UsageRequirement;
import delta.games.lotro.lore.items.Item;

/**
 * Filter items that can be used by a character class.
 * @author DAM
 */
public class ItemRequiredClassFilter implements ItemFilter
{
  private boolean _enabled;
  private ClassDescription _characterClass;
  private boolean _strict;

  /**
   * Constructor.
   * @param characterClass Character class to use.
   * @param strict Allow only class items, or allow all non class restricted items.
   */
  public ItemRequiredClassFilter(ClassDescription characterClass, boolean strict)
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

  /**
   * Get the character class.
   * @return A character class or <code>null</code>.
   */
  public ClassDescription getCharacterClass()
  {
    return _characterClass;
  }

  /**
   * Set the character class to use.
   * @param characterClass Character class (may be <code>null</code>).
   */
  public void setCharacterClass(ClassDescription characterClass)
  {
    _characterClass=characterClass;
  }

  @Override
  public boolean accept(Item item)
  {
    if (!_enabled)
    {
      return true;
    }
    if (_characterClass==null)
    {
      return true;
    }
    UsageRequirement requirements=item.getUsageRequirements();
    ClassRequirement classRequirement=requirements.getClassRequirement();
    if (classRequirement==null)
    {
      return !_strict;
    }
    return classRequirement.accept(_characterClass);
  }
}
