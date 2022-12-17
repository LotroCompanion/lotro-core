package delta.games.lotro.character.stats;

import java.util.Objects;

import org.apache.log4j.Logger;

import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatOperator;
import delta.games.lotro.common.stats.StatType;
import delta.games.lotro.common.stats.StatUtils;
import delta.games.lotro.utils.NumericUtils;

/**
 * Element of a stats set.
 * @author DAM
 */
public class StatsSetElement
{
  private static final Logger LOGGER=Logger.getLogger(StatsSetElement.class);

  private StatDescription _stat;
  private StatOperator _operator;
  private Number _value;
  private String _descriptionOverride;

  /**
   * Constructor.
   * @param stat Targeted stat.
   * @param operator Stat operator.
   */
  public StatsSetElement(StatDescription stat, StatOperator operator)
  {
    _stat=stat;
    _operator=operator;
  }

  /**
   * Copy constructor.
   * @param source Source to copy.
   */
  public StatsSetElement(StatsSetElement source)
  {
    _stat=source._stat;
    _operator=source._operator;
    _descriptionOverride=source._descriptionOverride;
    _value=source._value;
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
   * Get the stat operator.
   * @return a stat operator.
   */
  public StatOperator getOperator()
  {
    return _operator;
  }

  /**
   * Set operator.
   * @param operator Operator to set.
   */
  public void setOperator(StatOperator operator)
  {
    _operator=operator;
  }

  /**
   * Get the stat value.
   * @return a value.
   */
  public Number getValue()
  {
    return _value;
  }

  /**
   * Get a float value.
   * @return a float value.
   */
  public float getFloatValue()
  {
    return (_value!=null)?_value.floatValue():0f;
  }

  /**
   * Get an integer value.
   * @return an integer value.
   */
  public int getIntValue()
  {
    return (_value!=null)?_value.intValue():0;
  }

  /**
   * Set the value for this element.
   * @param number Number to set.
   */
  public void setValue(Number number)
  {
    if (number==null)
    {
      _value=null;
    }
    else if (number instanceof Float)
    {
      setValue(((Float)number).floatValue());
    }
    else if (number instanceof Integer)
    {
      setValue(((Integer)number).intValue());
    }
  }

  /**
   * Set the stat value.
   * @param value Value to set.
   */
  public void setValue(float value)
  {
    StatType type=_stat.getType();
    if (type==StatType.FLOAT)
    {
      _value=Float.valueOf(value);
    }
    else if ((type==StatType.INTEGER) || (type==StatType.BOOLEAN))
    {
      if (_operator==StatOperator.MULTIPLY)
      {
        _value=Float.valueOf(value);
      }
      else
      {
        _value=Integer.valueOf(Math.round(value));
      }
    }
    else if ((type==StatType.BITFIELD) || (type==StatType.DID))
    {
      // TODO Better support for DIDs and bitfields!
      LOGGER.info("Set float value of type: "+type+" "+_stat.getKey()+" => "+value);
      _value=Float.valueOf(value);
    }
    else
    {
      LOGGER.warn("Attempt to set a float value for stat: "+_stat.getKey()+": "+value);
      _value=Integer.valueOf(0);
    }
  }

  /**
   * Set the stat value.
   * @param value Value to set
   */
  public void setValue(int value)
  {
    StatType type=_stat.getType();
    if (type==StatType.FLOAT)
    {
      _value=Float.valueOf(value);
    }
    else if ((type==StatType.INTEGER) || (type==StatType.BOOLEAN))
    {
      _value=Integer.valueOf(value);
    }
    else if ((type==StatType.BITFIELD) || (type==StatType.DID))
    {
      // TODO Better support for DIDs bitfields!
      LOGGER.info("Set integer value of type: "+type+" "+_stat.getKey()+" => "+value);
      _value=Integer.valueOf(value);
    }
    else
    {
      LOGGER.warn("Attempt to set an integer value for stat: "+_stat+": "+value);
      _value=Integer.valueOf(0);
    }
  }

  /**
   * Remove a value from this stat.
   * @param value Value to remove.
   */
  public void substract(Number value)
  {
    _value=NumericUtils.diff(_value,value);
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
