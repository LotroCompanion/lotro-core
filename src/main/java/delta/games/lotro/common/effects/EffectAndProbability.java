package delta.games.lotro.common.effects;

/**
 * Effect+probability.
 * @author DAM
 */
public class EffectAndProbability
{
  private Effect2 _effect;
  private float _probability;

  /**
   * Constructor.
   * @param effect Effect.
   * @param probability Probability.
   */
  public EffectAndProbability(Effect2 effect, float probability)
  {
    _effect=effect;
    _probability=probability;
  }

  /**
   * Get the managed effect.
   * @return an effect.
   */
  public Effect2 getEffect()
  {
    return _effect;
  }

  /**
   * Set the managed effect.
   * @param effect Effect to set.
   */
  public void setEffect(Effect2 effect)
  {
    _effect=effect;
  }

  /**
   * Get the probability.
   * @return a probability (0-1).
   */
  public float getProbability()
  {
    return _probability;
  }
}
