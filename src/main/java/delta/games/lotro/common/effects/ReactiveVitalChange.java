package delta.games.lotro.common.effects;

/**
 * Reactive vital change (attacker or defender).
 * @author DAM
 */
public class ReactiveVitalChange extends AbstractVitalChange
{
  private float _probability;
  private boolean _multiplicative;

  /**
   * Constructor.
   */
  public ReactiveVitalChange()
  {
    super();
  }

  /**
   * Set the probability.
   * @param probability Probability to set.
   */
  public void setProbability(float probability)
  {
    _probability=probability;
  }

  /**
   * Get the probability.
   * @return the probability.
   */
  public float getProbability()
  {
    return _probability;
  }

  /**
   * Indicates if the change is multiplicative or additive.
   * @param multiplicative <code>true</code> means multiplicative, <code>false</code> means additive.
   */
  public void setMultiplicative(boolean multiplicative)
  {
    _multiplicative=multiplicative;
  }

  /**
   * Indicates if this change is multiplicative (<code>true</code>) or additive (<code>false</code>).
   * @return <code>true</code> if multiplicative, <code>false</code> if additive.
   */
  public boolean isMultiplicative()
  {
    return _multiplicative;
  }
}
