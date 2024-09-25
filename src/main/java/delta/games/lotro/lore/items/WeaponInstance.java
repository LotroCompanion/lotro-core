package delta.games.lotro.lore.items;

/**
 * Weapon instance.
 * @param <T> Type of the reference item.
 * @author DAM
 */
public class WeaponInstance<T extends Weapon> extends ItemInstance<T>
{
  private Float _maxDamage;
  private Float _dps;
  private DamageType _damageType;

  /**
   * Constructor.
   */
  public WeaponInstance()
  {
    super();
  }

  /**
   * Copy constructor.
   * @param source Source.
   */
  public WeaponInstance(WeaponInstance<T> source)
  {
    this();
    copyFrom(source);
  }

  /**
   * Get the max damage for this instance.
   * @return A value or <code>null</code> to use default from the reference weapon.
   */
  public Float getMaxDamage()
  {
    return _maxDamage;
  }

  /**
   * Set the max damage.
   * @param maxDamage Max damage to set.
   */
  public void setMaxDamage(Float maxDamage)
  {
    _maxDamage=maxDamage;
  }

  /**
   * Get the effective max damage.
   * @return A max damage value.
   */
  public int getEffectiveMaxDamage()
  {
    return Math.round(getEffectiveMaxDamageFloat());
  }

  /**
   * Get the effective max damage.
   * @return A max damage value.
   */
  public float getEffectiveMaxDamageFloat()
  {
    if (_maxDamage==null)
    {
      Weapon w=getReference();
      int itemLevel=getApplicableItemLevel();
      return w.computeMaxDamage(itemLevel);
    }
    return _maxDamage.floatValue();
  }

  /**
   * Get the effective min damage.
   * @return A min damage value.
   */
  public int getEffectiveMinDamage()
  {
    Weapon w=getReference();
    if (_maxDamage==null)
    {
      int itemLevel=getApplicableItemLevel();
      return Math.round(w.computeMinDamage(itemLevel));
    }
    float max=_maxDamage.floatValue();
    float v=w.getVariance();
    float min=max*(1-v);
    return Math.round(min);
  }

  /**
   * Get the DPS for this instance.
   * @return A value or <code>null</code> to use default from the reference weapon.
   */
  public Float getDPS()
  {
    return _dps;
  }

  /**
   * Set the DPS.
   * @param dps DPS to set.
   */
  public void setDPS(Float dps)
  {
    _dps=dps;
  }

  /**
   * Get the effective DPS.
   * @return the effective DPS.
   */
  public float getEffectiveDPS()
  {
    if (_dps==null)
    {
      Weapon w=getReference();
      int itemLevel=getApplicableItemLevel();
      return w.computeDPS(itemLevel);
    }
    return _dps.floatValue();
  }

  /**
   * Get the damage type for this instance.
   * @return A damage type or <code>null</code> to use default from the reference weapon.
   */
  public DamageType getDamageType()
  {
    return _damageType;
  }

  /**
   * Set the damage type.
   * @param damageType Damage type to set.
   */
  public void setDamageType(DamageType damageType)
  {
    _damageType=damageType;
  }

  /**
   * Get the effective damage type.
   * @return A damage type.
   */
  public DamageType getEffectiveDamageType()
  {
    if (_damageType==null)
    {
      Weapon w=getReference();
      return w.getDamageType();
    }
    return _damageType;
  }

  /**
   * Copy item instance data from a source.
   * @param itemInstance Source item instance.
   */
  @Override
  public void copyFrom(ItemInstance<?> itemInstance)
  {
    super.copyFrom(itemInstance);
    if (itemInstance instanceof WeaponInstance)
    {
      WeaponInstance<?> weaponInstance=(WeaponInstance<?>)itemInstance;
      _maxDamage=weaponInstance._maxDamage;
      _dps=weaponInstance._dps;
      _damageType=weaponInstance._damageType;
    }
  }
}
