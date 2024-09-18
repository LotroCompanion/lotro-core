package delta.games.lotro.character.skills;

/**
 * Skill vital costs.
 * @author DAM
 */
public class SkillCostData
{
  private SkillVitalCost _moraleCostPerSecond;
  private SkillVitalCost _powerCostPerSecond; 
  private SkillVitalCost _moraleCost;
  private SkillVitalCost _powerCost;

  /**
   * Constructor.
   */
  public SkillCostData()
  {
    _moraleCostPerSecond=null;
    _powerCostPerSecond=null;
    _moraleCost=null;
    _powerCost=null;
  }

  /**
   * Get the morale cost per second.
   * @return a cost or <code>null</code>.
   */
  public SkillVitalCost getMoraleCostPerSecond()
  {
    return _moraleCostPerSecond;
  }

  /**
   * Set the morale cost per second.
   * @param moraleCostPerSecond the cost to set.
   */
  public void setMoraleCostPerSecond(SkillVitalCost moraleCostPerSecond)
  {
    _moraleCostPerSecond=moraleCostPerSecond;
  }

  /**
   * Get the power cost per second.
   * @return a cost or <code>null</code>.
   */
  public SkillVitalCost getPowerCostPerSecond()
  {
    return _powerCostPerSecond;
  }

  /**
   * Set the power cost per second.
   * @param powerCostPerSecond the cost to set.
   */
  public void setPowerCostPerSecond(SkillVitalCost powerCostPerSecond)
  {
    _powerCostPerSecond=powerCostPerSecond;
  }

  /**
   * Get the morale cost.
   * @return a cost or <code>null</code>.
   */
  public SkillVitalCost getMoraleCost()
  {
    return _moraleCost;
  }

  /**
   * Set the morale cost.
   * @param moraleCost cost to set
   */
  public void setMoraleCost(SkillVitalCost moraleCost)
  {
    _moraleCost=moraleCost;
  }

  /**
   * Get the power cost.
   * @return a cost or <code>null</code>.
   */
  public SkillVitalCost getPowerCost()
  {
    return _powerCost;
  }

  /**
   * Set the power cost.
   * @param powerCost the cost to set.
   */
  public void setPowerCost(SkillVitalCost powerCost)
  {
    _powerCost=powerCost;
  }

  /**
   * Indicates if this skill has a cost.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean hasCost()
  {
    return ((_moraleCost!=null) || (_powerCost!=null) && (_moraleCostPerSecond!=null) && (_powerCostPerSecond!=null));
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    if (hasCost())
    {
      sb.append("Skill cost:");
      if (_moraleCost!=null)
      {
        sb.append(" Morale: ");
        sb.append(_moraleCost);
      }
      if (_powerCost!=null)
      {
        sb.append(" Power: ");
        sb.append(_powerCost);
      }
      if (_moraleCostPerSecond!=null)
      {
        sb.append(" Morale/second: ");
        sb.append(_moraleCostPerSecond);
      }
      if (_powerCostPerSecond!=null)
      {
        sb.append(" Power/second: ");
        sb.append(_powerCostPerSecond);
      }
    }
    return sb.toString();
  }
}
