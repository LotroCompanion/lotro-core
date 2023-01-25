package delta.games.lotro.lore.items.filters;

import delta.games.lotro.character.classes.AbstractClassDescription;
import delta.games.lotro.common.requirements.ClassRequirement;
import delta.games.lotro.common.requirements.UsageRequirement;
import delta.games.lotro.lore.items.Item;

/**
 * Filter items that can be used by a character/monster class.
 * @author DAM
 */
public class ItemRequiredClassFilter implements ItemFilter
{
  private boolean _enabled;
  private AbstractClassDescription _class;
  private boolean _strict;

  /**
   * Constructor.
   * @param clazz Class to use.
   * @param strict Allow only class items, or allow all non class restricted items.
   */
  public ItemRequiredClassFilter(AbstractClassDescription clazz, boolean strict)
  {
    _class=clazz;
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
   * Get the class.
   * @return A class or <code>null</code>.
   */
  public AbstractClassDescription getCharacterClass()
  {
    return _class;
  }

  /**
   * Set the class to use.
   * @param clazz Class (may be <code>null</code>).
   */
  public void setClass(AbstractClassDescription clazz)
  {
    _class=clazz;
  }

  @Override
  public boolean accept(Item item)
  {
    if (!_enabled)
    {
      return true;
    }
    if (_class==null)
    {
      return true;
    }
    UsageRequirement requirements=item.getUsageRequirements();
    ClassRequirement classRequirement=requirements.getClassRequirement();
    if (classRequirement==null)
    {
      return !_strict;
    }
    return classRequirement.accept(_class);
  }
}
