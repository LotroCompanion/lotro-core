package delta.games.lotro.lore.items.scaling;

/**
 * Weapon scaling data.
 * @author DAM
 */
public class WeaponScalingEntry extends ItemScalingEntry
{
  private float _dps;
  private float _minDamage;
  private float _maxDamage;

  /**
   * Constructor.
   */
  public WeaponScalingEntry()
  {
    super();
  }

  /**
   * Get the DPS.
   * @return a DPS.
   */
  public float getDPS()
  {
    return _dps;
  }

  /**
   * Set the DPS.
   * @param dps DPS to set.
   */
  public void setDPS(float dps)
  {
    _dps=dps;
  }

  /**
   * Get the minimum damage.
   * @return damage value.
   */
  public float getMinDamage()
  {
    return _minDamage;
  }

  /**
   * Set the minimum damage.
   * @param minDamage Value to set.
   */
  public void setMinDamage(float minDamage)
  {
    _minDamage=minDamage;
  }

  /**
   * Get the maximum damage.
   * @return damage value.
   */
  public float getMaxDamage()
  {
    return _maxDamage;
  }

  /**
   * Set the maximum damage.
   * @param maxDamage Value to set.
   */
  public void setMaxDamage(float maxDamage)
  {
    _maxDamage=maxDamage;
  }
}
