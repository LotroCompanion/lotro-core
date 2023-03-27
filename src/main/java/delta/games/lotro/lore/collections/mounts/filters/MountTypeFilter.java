package delta.games.lotro.lore.collections.mounts.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.common.enums.MountType;
import delta.games.lotro.lore.collections.mounts.MountDescription;

/**
 * Filter for mounts of a given type.
 * @author DAM
 */
public class MountTypeFilter implements Filter<MountDescription>
{
  private MountType _type;

  /**
   * Constructor.
   * @param type Type to select (may be <code>null</code>).
   */
  public MountTypeFilter(MountType type)
  {
    _type=type;
  }

  /**
   * Get the type to use.
   * @return A type or <code>null</code>.
   */
  public MountType getType()
  {
    return _type;
  }

  /**
   * Set the type to select.
   * @param type Type to use, may be <code>null</code>.
   */
  public void setType(MountType type)
  {
    _type=type;
  }

  public boolean accept(MountDescription mount)
  {
    if (_type==null)
    {
      return true;
    }
    MountType type=mount.getMountType();
    return (_type==type);
  }
}
