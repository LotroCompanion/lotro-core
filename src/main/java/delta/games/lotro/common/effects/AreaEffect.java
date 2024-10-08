package delta.games.lotro.common.effects;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.properties.ModPropertyList;

/**
 * Area effect.
 * @author DAM
 */
public class AreaEffect extends InstantEffect
{
  private List<EffectGenerator> _effects;
  private int _flags;
  private float _range;
  private float _detectionBuffer;
  private int _maxTargets;
  private ModPropertyList _maxTargetsModifiers;

  /**
   * Constructor.
   */
  public AreaEffect()
  {
    super();
    _effects=new ArrayList<EffectGenerator>();
  }

  /**
   * Add a generated effect.
   * @param generator Generator to add.
   */
  public void addEffect(EffectGenerator generator)
  {
    _effects.add(generator);
  }
  
  /**
   * Get the managed effects.
   * @return A list of effects.
   */
  public List<EffectGenerator> getEffects()
  {
    return _effects;
  }

  /**
   * Get the raw flags value.
   * @return flags.
   */
  public int getFlags()
  {
    return _flags;
  }

  /**
   * Set the raw flags value.
   * @param flags Flags to set.
   */
  public void setFlags(int flags)
  {
    _flags=flags;
  }

  /**
   * Get a flag value.
   * @param flag Flag to test.
   * @return <code>true</code> if set, <code>false</code> otherwise.
   */
  public boolean getFlag(int flag)
  {
    return ((_flags&flag)!=0);
  }

  /**
   * Set a flag value.
   * @param flag Flag to set (from AreaEffectFlags).
   * @param set Set it or unset it.
   */
  public void setFlag(int flag, boolean set)
  {
    if (set)
    {
      _flags=(_flags|flag);
    }
    else
    {
      _flags=(_flags&(~flag));
    }
  }

  /**
   * Indicates if a flag is set.
   * @param flag Flag to test (from AreaEffectFlags).
   * @return <code>true</code> if it is set, <code>false</code> otherwise.
   */
  public boolean isSet(int flag)
  {
    return ((_flags&flag)==flag);
  }

  /**
   * Get the range.
   * @return A range (meters).
   */
  public float getRange()
  {
    return _range;
  }

  /**
   * Set the range.
   * @param range the range to set (meters).
   */
  public void setRange(float range)
  {
    _range=range;
  }

  /**
   * Get the detection buffer.
   * @return the detection buffer (meters).
   */
  public float getDetectionBuffer()
  {
    return _detectionBuffer;
  }

  /**
   * Set the detection buffer.
   * @param detectionBuffer the detection buffer to set (meters).
   */
  public void setDetectionBuffer(float detectionBuffer)
  {
    _detectionBuffer=detectionBuffer;
  }

  /**
   * Get the maximum number of targets.
   * @return A targets count.
   */
  public int getMaxTargets()
  {
    return _maxTargets;
  }

  /**
   * Set the maximum number of targets.
   * @param maxTargets the value to set.
   */
  public void setMaxTargets(int maxTargets)
  {
    _maxTargets=maxTargets;
  }

  /**
   * Get the modifiers for the maximum number of targets.
   * @return some modifiers or <code>null</code> if none.
   */
  public ModPropertyList getMaxTargetsModifiers()
  {
    return _maxTargetsModifiers;
  }

  /**
   * Set the modifiers for the maximum number of targets.
   * @param maxTargetsModifiers the modifiers to set (may be <code>null</code>).
   */
  public void setMaxTargetsModifiers(ModPropertyList maxTargetsModifiers)
  {
    _maxTargetsModifiers=maxTargetsModifiers;
  }
}
