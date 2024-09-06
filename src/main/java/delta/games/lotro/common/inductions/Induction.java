package delta.games.lotro.common.inductions;

import delta.games.lotro.common.Identifiable;

/**
 * Induction.
 * @author DAM
 */
public class Induction implements Identifiable
{
  private int _identifier;
  private float _duration;
  // TODO Add Additive Modifiers
  // TODO Add Multiplicative Modifiers

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
}
