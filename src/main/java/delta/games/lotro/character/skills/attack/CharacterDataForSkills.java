package delta.games.lotro.character.skills.attack;

import delta.games.lotro.common.enums.ImplementUsageType;
import delta.games.lotro.common.enums.ImplementUsageTypes;
import delta.games.lotro.common.properties.ModPropertyList;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatsManager;
import delta.games.lotro.common.stats.StatsRegistry;
import delta.games.lotro.lore.items.EquipmentLocation;
import delta.games.lotro.lore.items.EquipmentLocations;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemInstance;

/**
 * Character data for skill details.
 * @author DAM
 */
public class CharacterDataForSkills
{
  private ClassDataForSkills _classData;

  /**
   * Constructor.
   */
  public CharacterDataForSkills()
  {
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
    return 0f;
  }

  /**
   * Get the character level.
   * @return the character level.
   */
  public int getLevel()
  {
    return 1;
  }

  private boolean useMainDPS()
  {
    return true;
  }

  private boolean useMainTDR()
  {
    return false;
  }

  private boolean useSecondaryDPS()
  {
    return false;
  }

  private boolean useRangedDPS()
  {
    return false;
  }

  private boolean useRangedTHR()
  {
    return false;
  }

  private boolean useClassTHR()
  {
    return false;
  }

  private ItemInstance<? extends Item> getItem(EquipmentLocation slot)
  {
    return null;
  }

  public ItemInstance<? extends Item> getImplement(ImplementUsageType implementUsageType)
  {
    if (implementUsageType==ImplementUsageTypes.PRIMARY)
    {
      if (useMainDPS())
      {
        return getItem(EquipmentLocations.MAIN_HAND);
      }
      return null;
    }
    else if (implementUsageType==ImplementUsageTypes.SECONDARY)
    {
      if (useSecondaryDPS())
      {
        return getItem(EquipmentLocations.OFF_HAND);
      }
      return null;
    }
    else if (implementUsageType==ImplementUsageTypes.RANGED)
    {
      if (useRangedDPS())
      {
        return getItem(EquipmentLocations.RANGED_ITEM);
      }
      return null;
    }
    else if (implementUsageType==ImplementUsageTypes.TACTICAL_DPS)
    {
      if (useMainTDR())
      {
        return getItem(EquipmentLocations.MAIN_HAND);
      }
      return null;
    }
    else if (implementUsageType==ImplementUsageTypes.TACTICAL_HPS)
    {
      if (useRangedTHR())
      {
        return getItem(EquipmentLocations.RANGED_ITEM);
      }
      if (useClassTHR())
      {
        return getItem(EquipmentLocations.CLASS_SLOT);
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
    if (mods==null) return 0;
    float ret=0;
    for(Integer id : mods.getIDs())
    {
      StatDescription stat=StatsRegistry.getInstance().getById(id.intValue());
      float statValue=getStat(stat);
      ret+=statValue;
    }
    return ret;
  }
}
