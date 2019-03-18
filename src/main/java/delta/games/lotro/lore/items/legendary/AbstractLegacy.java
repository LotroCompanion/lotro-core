package delta.games.lotro.lore.items.legendary;

import delta.common.utils.collections.filters.CompoundFilter;
import delta.games.lotro.common.constraints.ClassAndSlot;

/**
 * Base class for all legacies.
 * @author DAM
 */
public class AbstractLegacy
{
  private LegacyType _type;
  private CompoundFilter<ClassAndSlot> _classAndSlotFilter;

  /**
   * Constructor.
   */
  public AbstractLegacy()
  {
    _type=LegacyType.STAT;
  }

  /**
   * Get the legacy type.
   * @return a legacy type.
   */
  public LegacyType getType()
  {
    return _type;
  }

  /**
   * Set the legacy type.
   * @param type the type to set.
   */
  public void setType(LegacyType type)
  {
    _type=type;
  }

  /**
   * Get the class and slot filter.
   * @return a class and slot filter.
   */
  public CompoundFilter<ClassAndSlot> getClassAndSlotFilter()
  {
    return _classAndSlotFilter;
  }

  /**
   * Set the class and slot filter.
   * @param classAndSlotFilter class and slot filter to set.
   */
  public void setClassAndSlotFilter(CompoundFilter<ClassAndSlot> classAndSlotFilter)
  {
    _classAndSlotFilter=classAndSlotFilter;
  }
}
