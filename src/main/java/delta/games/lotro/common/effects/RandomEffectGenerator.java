package delta.games.lotro.common.effects;

/**
 * Effect generator specialized for random effects.
 * @author DAM
 */
public class RandomEffectGenerator extends EffectGenerator
{
  private boolean _toCaster;
  private float _weight;

  /**
   * Constructor.
   */
  public RandomEffectGenerator()
  {
    super();
    _toCaster=false;
    _weight=1;
  }

  /**
   * Get the 'to caster' flag.
   * @return a boolean value.
   */
  public boolean isToCaster()
  {
    return _toCaster;
  }

  /**
   * Set the 'to caster' flag.
   * @param toCaster Value to set.
   */
  public void setToCaster(boolean toCaster)
  {
    _toCaster=toCaster;
  }

  /**
   * Get the weight of this generator.
   * @return A weight.
   */
  public float getWeight()
  {
    return _weight;
  }

  /**
   * Set the weight of this generator.
   * @param weight Weight to set.
   */
  public void setWeight(float weight)
  {
    _weight=weight;
  }
}
