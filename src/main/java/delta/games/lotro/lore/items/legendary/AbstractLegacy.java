package delta.games.lotro.lore.items.legendary;

import delta.common.utils.collections.filters.CompoundFilter;
import delta.common.utils.collections.filters.Filter;
import delta.common.utils.collections.filters.Operator;
import delta.games.lotro.common.constraints.ClassAndSlot;
import delta.games.lotro.common.constraints.ClassAndSlotFilter;

/**
 * Base class for all legacies.
 * @author DAM
 */
public class AbstractLegacy
{
  private LegacyType _type;
  private CompoundFilter<ClassAndSlot> _classAndSlotFilter;
  private int _iconId;

  /**
   * Constructor.
   */
  public AbstractLegacy()
  {
    _type=LegacyType.STAT;
    _iconId=0;
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

  /**
   * Add an allowed class and slot spec.
   * @param spec the spec to add.
   */
  public void addAllowedClassAndSlot(ClassAndSlot spec)
  {
    if (_classAndSlotFilter==null)
    {
      _classAndSlotFilter=new CompoundFilter<ClassAndSlot>(Operator.OR);
    }
    Filter<ClassAndSlot> newConstraint=new ClassAndSlotFilter(spec);
    _classAndSlotFilter.addFilter(newConstraint);
  }

  /**
   * Get the identifier of the associated icon.
   * @return an icon identifier or <code>0</code> if none.
   */
  public int getIconId()
  {
    return _iconId;
  }

  /**
   * Set the identifier of the associated icon.
   * @param iconId Icon identifier (may be 0).
   */
  public void setIconId(int iconId)
  {
    _iconId=iconId;
  }
}
