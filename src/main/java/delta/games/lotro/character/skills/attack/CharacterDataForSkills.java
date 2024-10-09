package delta.games.lotro.character.skills.attack;

import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.gear.CharacterGear;
import delta.games.lotro.character.gear.GearSlot;
import delta.games.lotro.character.gear.GearSlots;
import delta.games.lotro.common.enums.ImplementUsageType;
import delta.games.lotro.common.enums.ImplementUsageTypes;
import delta.games.lotro.common.properties.ModPropertyList;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatsRegistry;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemInstance;

/**
 * Character data for skill computations.
 * @author DAM
 */
public class CharacterDataForSkills
{
  private ClassDataForSkills _classData;
  private CharacterData _data;

  /**
   * Constructor.
   * @param data Character data.
   */
  public CharacterDataForSkills(CharacterData data)
  {
    _data=data;
    _classData=new ClassDataForSkills();
  }

  /**
   * Get the class data.
   * @return the class data.
   */
  public ClassDataForSkills getClassData()
  {
    return _classData;
  }

  /**
   * Get a stat value for this character.
   * @param stat Stat to use.
   * @return the stat value.
   */
  public float getStat(StatDescription stat)
  {
    Number value=_data.getStats().getStat(stat);
    return (value!=null)?value.floatValue():0.0f;
  }

  /**
   * Get the character level.
   * @return the character level.
   */
  public int getLevel()
  {
    return _data.getLevel();
  }

  private boolean useRangedTHR()
  {
    return false;
  }

  private boolean useClassTHR()
  {
    return true;
  }

  private ItemInstance<? extends Item> getItem(GearSlot slot)
  {
    CharacterGear gear=_data.getEquipment();
    return gear.getItemForSlot(slot);
  }

  /**
   * Get tehe item to use for the given implement usage.
   * @param implementUsageType Implement usage.
   * @return An item instance or <code>null</code>. 
   */
  public ItemInstance<? extends Item> getImplement(ImplementUsageType implementUsageType)
  {
    if (implementUsageType==ImplementUsageTypes.PRIMARY)
    {
      return getItem(GearSlots.MAIN_MELEE);
    }
    else if (implementUsageType==ImplementUsageTypes.SECONDARY)
    {
      return getItem(GearSlots.OTHER_MELEE);
    }
    else if (implementUsageType==ImplementUsageTypes.RANGED)
    {
      return getItem(GearSlots.RANGED);
    }
    else if (implementUsageType==ImplementUsageTypes.TACTICAL_DPS)
    {
      return getItem(GearSlots.MAIN_MELEE);
    }
    else if (implementUsageType==ImplementUsageTypes.TACTICAL_HPS)
    {
      if (useRangedTHR())
      {
        return getItem(GearSlots.RANGED);
      }
      if (useClassTHR())
      {
        return getItem(GearSlots.CLASS_ITEM);
      }
      return null;
    }
    return null;
  }

  /**
   * Compute the value of modifier properties.
   * @param mods Modifiers.
   * @return A value to add.
   */
  public float computeAdditiveModifiers(ModPropertyList mods)
  {
    if (mods==null)
    {
      return 0;
    }
    float ret=0;
    System.out.println("Computing additive modifiers: "+mods);
    for(Integer id : mods.getIDs())
    {
      StatDescription stat=StatsRegistry.getInstance().getById(id.intValue());
      if (stat==null)
      {
        continue;
      }
      float statValue=getStat(stat);
      if (stat.isPercentage())
      {
        statValue/=100;
      }
      System.out.println("\tStat "+stat.getPersistenceKey()+" => "+statValue);
      ret+=statValue;
    }
    System.out.println("\tTotal: "+ret);
    return ret;
  }

  /**
   * Compute the value of modifier properties.
   * @param mods Modifiers.
   * @return A value to add.
   */
  public float computeMultiplicativeModifiers(ModPropertyList mods)
  {
    if (mods==null) return 1;
    float ret=1;
    System.out.println("Computing multiplicative modifiers: "+mods);
    for(Integer id : mods.getIDs())
    {
      StatDescription stat=StatsRegistry.getInstance().getById(id.intValue());
      float statValue=getStat(stat);
      if (stat.isPercentage())
      {
        statValue/=100;
      }
      System.out.println("\tStat "+stat.getPersistenceKey()+" => "+statValue);
      ret*=(1+statValue);
    }
    System.out.println("\tTotal: "+ret);
    return ret;
  }
}
