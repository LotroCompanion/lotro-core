package delta.games.lotro.common.inductions;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.properties.ModPropertyList;

/**
 * Induction.
 * @author DAM
 */
public class Induction implements Identifiable
{
  private int _identifier;
  private float _duration;
  private ModPropertyList _addMods;
  private ModPropertyList _multiplyMods;

  /**
   * Constructor.
   * @param identifier Identifier.
   */
  public Induction(int identifier) 
  {
    _identifier=identifier;
  }

  @Override
  public int getIdentifier()
  {
    return _identifier;
  }

  /**
   * Get induction duration (seconds).
   * @return A duration (seconds).
   */
  public float getDuration()
  {
    return _duration;
  }

  /**
   * Set the duration.
   * @param duration Duration to set.
   */
  public void setDuration(float duration)
  {
    _duration=duration;
  }

  /**
   * Get the additive modifiers.
   * @return some modifiers or <code>null</code>.
   */
  public ModPropertyList getAddMods()
  {
    return _addMods;
  }

  /**
   * Set the additive modifiers.
   * @param addMods the modifiers to set.
   */
  public void setAddMods(ModPropertyList addMods)
  {
    _addMods=addMods;
  }

  /**
   * Get the multiplicative modifiers.
   * @return some modifiers or <code>null</code>.
   */
  public ModPropertyList getMultiplyMods()
  {
    return _multiplyMods;
  }

  /**
   * Set the multiplicative modifiers.
   * @param multiplyMods the modifiers to set.
   */
  public void setMultiplyMods(ModPropertyList multiplyMods)
  {
    _multiplyMods=multiplyMods;
  }
}
