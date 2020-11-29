package delta.games.lotro.character.stats;

import java.util.Objects;

import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatUtils;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Element of a stats set.
 * @author DAM
 */
public class StatsSetElement
{
  private StatDescription _stat;
  private FixedDecimalsInteger _value;
  private String _descriptionOverride;

  /**
   * Constructor.
   * @param stat Targeted stat.
   * @param value Value.
   * @param descriptionOverride Description override.
   */
  public StatsSetElement(StatDescription stat, FixedDecimalsInteger value, String descriptionOverride)
  {
    _stat=stat;
    _value=value;
    _descriptionOverride=descriptionOverride;
  }

  /**
   * Copy constructor.
   * @param source Source to copy.
   */
  public StatsSetElement(StatsSetElement source)
  {
    _stat=source._stat;
    _descriptionOverride=source._descriptionOverride;
    _value=new FixedDecimalsInteger(source._value);
  }

  /**
   * Get the targeted stat.
   * @return a stat.
   */
  public StatDescription getStat()
  {
    return _stat;
  }

  /**
   * Get the stat value.
   * @return a value.
   */
  public FixedDecimalsInteger getValue()
  {
    return _value;
  }

  /**
   * Set the stat value.
   * @param value
   */
  public void setValue(FixedDecimalsInteger value)
  {
    _value=value;
  }

  /**
   * Get the description override.
   * @return a description or <code>null</code> if none.
   */
  public String getDescriptionOverride()
  {
    return _descriptionOverride;
  }

  /**
   * Set the description override.
   * @param descriptionOverride Description to set (may be <code>null</code>).
   */
  public void setDescriptionOverride(String descriptionOverride)
  {
    _descriptionOverride=descriptionOverride;
  }

  @Override
  public boolean equals(Object object)
  {
    if (this==object) return true;
    if (!(object instanceof StatsSetElement))
    {
      return false;
    }
    StatsSetElement other=(StatsSetElement)object;
    return ((_stat==other._stat) && (Objects.equals(_descriptionOverride,other._descriptionOverride))
        && (Objects.equals(_value,other._value)));
  }

  @Override
  public String toString()
  {
    return StatUtils.getStatDisplay(this);
  }
}
