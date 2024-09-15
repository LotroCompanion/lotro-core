package delta.games.lotro.character.skills;

import delta.games.lotro.common.enums.PipType;
import delta.games.lotro.common.properties.ModPropertyList;

/**
 * Skill PIP data.
 * @author DAM
 */
public class SkillPipData
{
  private PipType _type;
  private Integer _change;
  private ModPropertyList _changeMods;
  private Integer _requiredMinValue;
  private ModPropertyList _requiredMinValueMods;
  private Integer _requiredMaxValue;
  private ModPropertyList _requiredMaxValueMods;
  private Integer _towardHome;
  private Integer _changePerInterval;
  private ModPropertyList _changePerIntervalMods;
  private Float _secondsPerPipChange;
  private ModPropertyList _secondsPerPipChangeMods;

  /**
   * Constructor.
   * @param type PIP type.
   */
  public SkillPipData(PipType type)
  {
    _type=type;
    _change=null;
    _changeMods=null;
    _requiredMinValue=null;
    _requiredMinValueMods=null;
    _requiredMaxValue=null;
    _requiredMaxValueMods=null;
    _towardHome=null;
    _changePerInterval=null;
    _changePerIntervalMods=null;
    _secondsPerPipChange=null;
    _secondsPerPipChangeMods=null;
  }

  /**
   * Get the PIP type.
   * @return a PIP type.
   */
  public PipType getType()
  {
    return _type;
  }

  /**
   * Get the change value.
   * @return a change value or <code>null</code>.
   */
  public Integer getChange()
  {
    return _change;
  }

  /**
   * Set the change value.
   * @param change the value to set (may be <code>null</code>).
   */
  public void setChange(Integer change)
  {
    _change=change;
  }

  /**
   * Get the change value modifiers.
   * @return the modifiers (may be <code>null</code).
   */
  public ModPropertyList getChangeMods()
  {
    return _changeMods;
  }

  /**
   * Set the modifiers for the change value.
   * @param changeMods the modifiers to set (may be <code>null</code>).
   */
  public void setChangeMods(ModPropertyList changeMods)
  {
    _changeMods=changeMods;
  }

  /**
   * Get the required minimum value.
   * @return the required minimum value (may be <code>null</code).
   */
  public Integer getRequiredMinValue()
  {
    return _requiredMinValue;
  }

  /**
   * Set the required minimum value.
   * @param requiredMinValue the value to set (may be <code>null</code>).
   */
  public void setRequiredMinValue(Integer requiredMinValue)
  {
    _requiredMinValue=requiredMinValue;
  }

  /**
   * Get the modifiers for the required minimum value.
   * @return the modifiers (may be <code>null</code).
   */
  public ModPropertyList getRequiredMinValueMods()
  {
    return _requiredMinValueMods;
  }

  /**
   * Set the modifiers for the required minimum value.
   * @param requiredMinValueMods the modifiers to set (may be <code>null</code>).
   */
  public void setRequiredMinValueMods(ModPropertyList requiredMinValueMods)
  {
    _requiredMinValueMods=requiredMinValueMods;
  }

  /**
   * Set the required maximum value.
   * @return the value to set (may be <code>null</code).
   */
  public Integer getRequiredMaxValue()
  {
    return _requiredMaxValue;
  }

  /**
   * Set the required maximum value.
   * @param requiredMaxValue the value to set (may be <code>null</code>).
   */
  public void setRequiredMaxValue(Integer requiredMaxValue)
  {
    _requiredMaxValue=requiredMaxValue;
  }

  /**
   * Get the modifiers for the required maximum value.
   * @return the modifiers (may be <code>null</code).
   */
  public ModPropertyList getRequiredMaxValueMods()
  {
    return _requiredMaxValueMods;
  }

  /**
   * Set the modifiers for the required maximum value.
   * @param requiredMaxValueMods the modifiers to set (may be <code>null</code>).
   */
  public void setRequiredMaxValueMods(ModPropertyList requiredMaxValueMods)
  {
    _requiredMaxValueMods=requiredMaxValueMods;
  }

  /**
   * Get the 'toward home' value.
   * @return the 'toward home' value (may be <code>null</code).
   */
  public Integer getTowardHome()
  {
    return _towardHome;
  }

  /**
   * Set the 'toward home' value.
   * @param towardHome the value to set (may be <code>null</code>).
   */
  public void setTowardHome(Integer towardHome)
  {
    _towardHome=towardHome;
  }

  /**
   * Get the change per interval.
   * @return the change per interval (may be <code>null</code).
   */
  public Integer getChangePerInterval()
  {
    return _changePerInterval;
  }

  /**
   * Set the change per interval.
   * @param changePerInterval the value to set (may be <code>null</code>).
   */
  public void setChangePerInterval(Integer changePerInterval)
  {
    _changePerInterval=changePerInterval;
  }

  /**
   * Get the modifiers for the change per interval.
   * @return the modifiers (may be <code>null</code).
   */
  public ModPropertyList getChangePerIntervalMods()
  {
    return _changePerIntervalMods;
  }

  /**
   * Set the modifiers for the change per interval.
   * @param changePerIntervalMods the modifiers to set (may be <code>null</code>).
   */
  public void setChangePerIntervalMods(ModPropertyList changePerIntervalMods)
  {
    _changePerIntervalMods=changePerIntervalMods;
  }

  /**
   * Get the seconds per PIP change.
   * @return a duration in seconds (may be <code>null</code).
   */
  public Float getSecondsPerPipChange()
  {
    return _secondsPerPipChange;
  }

  /**
   * Set the seconds per PIP change.
   * @param secondsPerPipChange the value to set (may be <code>null</code>).
   */
  public void setSecondsPerPipChange(Float secondsPerPipChange)
  {
    _secondsPerPipChange=secondsPerPipChange;
  }

  /**
   * Get the modifiers for the seconds per PIP change.
   * @return the modifiers (may be <code>null</code).
   */
  public ModPropertyList getSecondsPerPipChangeMods()
  {
    return _secondsPerPipChangeMods;
  }

  /**
   * Set the modifiers for the seconds per PIP change.
   * @param secondsPerPipChangeMods the modifiers to set (may be <code>null</code>).
   */
  public void setSecondsPerPipChangeMods(ModPropertyList secondsPerPipChangeMods)
  {
    _secondsPerPipChangeMods=secondsPerPipChangeMods;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("PIP type: ").append(_type);
    if (_change!=null)
    {
      sb.append(", change=").append(_change);
    }
    if (_changeMods!=null)
    {
      sb.append(", change mods=").append(_changeMods);
    }
    if (_requiredMinValue!=null)
    {
      sb.append(", required min value=").append(_requiredMinValue);
    }
    if (_requiredMinValueMods!=null)
    {
      sb.append(", required min value mods=").append(_requiredMinValueMods);
    }
    if (_requiredMaxValue!=null)
    {
      sb.append(", required max value=").append(_requiredMaxValue);
    }
    if (_requiredMaxValueMods!=null)
    {
      sb.append(", required max value mods=").append(_requiredMaxValueMods);
    }
    if ((_towardHome!=null) && (_towardHome.intValue()!=0))
    {
      sb.append(", toward home=").append(_towardHome);
    }
    if (_changePerInterval!=null)
    {
      sb.append(", change per interval=").append(_changePerInterval);
    }
    if (_changePerIntervalMods!=null)
    {
      sb.append(", change per interval mods=").append(_changePerIntervalMods);
    }
    if (_secondsPerPipChange!=null)
    {
      sb.append(", seconds per PIP change=").append(_secondsPerPipChange);
    }
    if (_secondsPerPipChangeMods!=null)
    {
      sb.append(", seconds per PIP change mods=").append(_secondsPerPipChangeMods);
    }
    return sb.toString();
  }
}
