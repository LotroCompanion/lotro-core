package delta.games.lotro.common.effects;

/**
 * Effect application probability.
 * @author DAM
 */
public class ApplicationProbability
{
  private float _probability;
  private float _probabilityVariance;
  private Integer _modProperty;

  /**
   * Constructor.
   * @param probability Probability.
   */
  private ApplicationProbability(float probability)
  {
    _probability=probability;
    _probabilityVariance=0.0f;
    _modProperty=null;
  }

  /**
   * Constructor.
   * @param probability Probability.
   * @param probabilityVariance Variance.
   */
  private ApplicationProbability(float probability, float probabilityVariance)
  {
    _probability=probability;
    _probabilityVariance=probabilityVariance;
    _modProperty=null;
  }

  /**
   * Constructor.
   * @param probability Probability.
   * @param probabilityVariance Variance.
   * @param modProperty Modifier property ID.
   */
  private ApplicationProbability(float probability, float probabilityVariance, int modProperty)
  {
    _probability=probability;
    _probabilityVariance=probabilityVariance;
    _modProperty=Integer.valueOf(modProperty);
  }

  /**
   * Always.
   */
  public static ApplicationProbability ALWAYS=new ApplicationProbability(1);

  /**
   * Get the probability.
   * @return the probability (0-1).
   */
  public float getProbability()
  {
    return _probability;
  }

  /**
   * Get the probability variance.
   * @return the probability variance.
   */
  public float getProbabilityVariance()
  {
    return _probabilityVariance;
  }

  /**
   * Get the modifier property ID.
   * @return a modifier property ID or <code>null</code>.
   */
  public Integer getModProperty()
  {
    return _modProperty;
  }

  @Override
  public String toString()
  {
    if (this==ALWAYS) return "Always";
    StringBuilder sb=new StringBuilder("Probability: ").append(_probability);
    if (_probabilityVariance>0)
    {
      sb.append(", variance: ").append(_probabilityVariance);
    }
    if (_modProperty!=null)
    {
      sb.append(", modifier property ID: ").append(_modProperty);
    }
    return sb.toString();
  }

  /**
   * Factory method.
   * @param probability Probability.
   * @param variance Variance.
   * @param modifierPropertyID Modifier property ID.
   * @return An instance of this class.
   */
  public static ApplicationProbability from(float probability, float variance, int modifierPropertyID)
  {
    if ((probability==1.0f) && (variance==0.0f) && (modifierPropertyID==0))
    {
      return ALWAYS;
    }
    if (modifierPropertyID==0)
    {
      return new ApplicationProbability(probability,variance);
    }
    return new ApplicationProbability(probability,variance,modifierPropertyID);
  }
}
