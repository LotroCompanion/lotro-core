package delta.games.lotro.lore.items;

import org.apache.log4j.Logger;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.common.utils.valueTables.QualityBasedValuesTable;
import delta.games.lotro.lore.items.weapons.WeaponSpeedEntry;

/**
 * Weapon description.
 * @author DAM
 */
public class Weapon extends Item
{
  private static final Logger LOGGER=Logger.getLogger(Weapon.class);

  //197 - 359 Common Damage
  private int _minDamage;
  private int _maxDamage;
  private DamageType _damageType;
  private float _dps;
  private QualityBasedValuesTable _dpsTable;
  private WeaponType _type;
  private WeaponSpeedEntry _speed;

  /**
   * Constructor.
   */
  public Weapon()
  {
    super();
    _minDamage=0;
    _maxDamage=0;
    _damageType=DamageTypes.COMMON;
    _dps=0.0f;
    _dpsTable=null;
    _type=null;
  }

  @Override
  public ItemCategory getCategory()
  {
    return ItemCategory.WEAPON;
  }

  /**
   * Get the minimum damage.
   * @return the minimum damage.
   */
  public int getMinDamage()
  {
    return _minDamage;
  }

  /**
   * Set the minimum damage.
   * @param minDamage the value to set.
   */
  public void setMinDamage(int minDamage)
  {
    _minDamage=minDamage;
  }

  /**
   * Get the maximum damage.
   * @return the maximum damage.
   */
  public int getMaxDamage()
  {
    return _maxDamage;
  }

  /**
   * Set the maximum damage.
   * @param maxDamage the value to set.
   */
  public void setMaxDamage(int maxDamage)
  {
    _maxDamage=maxDamage;
  }

  /**
   * Get the damage type.
   * @return the damage type.
   */
  public DamageType getDamageType()
  {
    return _damageType;
  }

  /**
   * Set the damage type.
   * @param damageType the damage type to set.
   */
  public void setDamageType(DamageType damageType)
  {
    _damageType=damageType;
  }

  /**
   * Get the DPS value.
   * @return the DPS value.
   */
  public float getDPS()
  {
    return _dps;
  }

  /**
   * Set the DPS value.
   * @param dps value to set.
   */
  public void setDPS(float dps)
  {
    _dps=dps;
  }

  /**
   * Get the DPS table of this weapon.
   * @return a DPS table.
   */
  public QualityBasedValuesTable getDPSTable()
  {
    return _dpsTable;
  }

  /**
   * Get the DPS of this weapon at the given item level.
   * @param itemLevelForDPS Item level to use.
   * @return A DPS or <code>null</code> if none.
   */
  private Float getDPS(int itemLevelForDPS)
  {
    Float ret=null;
    ItemQuality quality=getQuality();
    if ((_dpsTable!=null) && (quality!=null))
    {
      ret=_dpsTable.getValue(quality,itemLevelForDPS);
      if (ret==null)
      {
        LOGGER.warn("Could not compute weapon DPS from table!");
      }
    }
    return ret;
  }

  /**
   * Compute the DPS.
   * @return A DPS value.
   */
  public float computeDPS()
  {
    int itemLevelForDPS=getItemLevelForDPS();
    Float dps=getDPS(itemLevelForDPS);
    return (dps!=null)?dps.floatValue():0.0f;
  }

  /**
   * Compute the DPS.
   * @param baseItemLevel Base item level.
   * @return A DPS value.
   */
  public float computeDPS(int baseItemLevel)
  {
    int itemLevelForDPS=getItemLevelForDPS(baseItemLevel);
    Float dps=getDPS(itemLevelForDPS);
    return (dps!=null)?dps.floatValue():0.0f;
  }

  /**
   * Get the item level to use to compute the DPS.
   * @return An item level.
   */
  public int getItemLevelForDPS()
  {
    Integer itemLevel=getItemLevel();
    int ret=(itemLevel!=null)?itemLevel.intValue():0;
    return getItemLevelForDPS(ret);
  }

  public int getItemLevelForDPS(int baseItemLevel)
  {
    int ret=baseItemLevel;
    Integer offset=getItemLevelOffset();
    if (offset!=null)
    {
      ret+=offset.intValue();
    }
    return ret;
  }

  /**
   * Set the DPS table of this weapon.
   * @param dpsTable the value to set.
   */
  public void setDPSTable(QualityBasedValuesTable dpsTable)
  {
    _dpsTable=dpsTable;
  }

  /**
   * Get weapon type.
   * @return a weapon type.
   */
  public WeaponType getWeaponType()
  {
    return _type;
  }

  /**
   * Set weapon type.
   * @param type Weapon type to set.
   */
  public void setWeaponType(WeaponType type)
  {
    _type=type;
  }

  /**
   * Get the speed data.
   * @return the speed data.
   */
  public WeaponSpeedEntry getSpeed()
  {
    return _speed;
  }

  /**
   * Set the speed data.
   * @param speed Speed data to set.
   */
  public void setSpeed(WeaponSpeedEntry speed)
  {
    _speed=speed;
  }

  /**
   * Dump the contents of this weapon as a string.
   * @return A readable string.
   */
  @Override
  public String dump()
  {
    StringBuilder sb=new StringBuilder();
    String itemDump=super.dump();
    sb.append(itemDump);
    sb.append(EndOfLine.NATIVE_EOL);
    sb.append("Weapon: (type=").append(_type).append(')');
    sb.append(" (DPS=").append(_dps).append(')');
    sb.append(" (damage=").append(_minDamage).append('-').append(_maxDamage);
    sb.append(' ').append(_damageType).append(')');
    return sb.toString();
  }
}
