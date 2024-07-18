package delta.games.lotro.lore.items.scaling;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.money.Money;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.Weapon;
import delta.games.lotro.lore.items.legendary.Legendary;
import delta.games.lotro.lore.items.legendary2.Legendary2;
import delta.games.lotro.lore.parameters.Game;
import delta.games.lotro.utils.maths.Progression;

/**
 * Builder for item scaling data.
 * @author DAM
 */
public class ItemScalingBuilder
{
  /**
   * Build scaling data for a single item
   * @param item Item to use.
   * @return scaling data.
   */
  public static ItemScaling build(Item item)
  {
    if (!doScaling(item))
    {
      return new ItemScaling(item);
    }
    return buildScaling(item);
  }

  private static boolean doScaling(Item item)
  {
    if (item instanceof Legendary)
    {
      // No support for scaling of legendary items
      return false;
    }
    if (item instanceof Legendary2)
    {
      // No support for scaling of new legendary items
      return false;
    }
    Munging munging=item.getMunging();
    if (munging==null)
    {
      return false;
    }
    return true;
  }

  private static ItemScaling buildScaling(Item item)
  {
    ItemScaling ret=new ItemScaling(item);
    ScalingData scaling=item.getScaling();
    Munging munging=scaling.getMunging();
    Integer min=munging.getMin();
    int minLevel=(min!=null?min.intValue():1);
    Integer max=munging.getMax();
    int levelCap=Game.getParameters().getMaxCharacterLevel();
    int maxLevel=(max!=null?max.intValue():levelCap);

    Progression progression=munging.getProgression();
    for(int level=minLevel;level<=maxLevel;level++)
    {
      int levelValue;
      Float itemLevel=null;
      if (progression!=null)
      {
        itemLevel=progression.getValue(level);
        levelValue=level;
      }
      else
      {
        itemLevel=Float.valueOf(level);
        levelValue=0;
      }
      if (itemLevel==null)
      {
        continue;
      }
      int minItemLevel=itemLevel.intValue();
      int maxItemLevel=minItemLevel;
      ItemLevelBonus bonus=scaling.getItemLevelBonus();
      if (bonus!=null)
      {
        maxItemLevel+=bonus.getBonusLimit();
      }
      for(int itemLevelToUse=minItemLevel;itemLevelToUse<=maxItemLevel;itemLevelToUse++)
      {
        ItemScalingEntry entry=buildEntry(item,levelValue,itemLevelToUse);
        ret.addEntry(entry);
      }
    }
    return ret;
  }

  private static ItemScalingEntry buildEntry(Item item,int levelValue,int itemLevel)
  {
    ItemScalingEntry scaling=null;
    WeaponScalingEntry weaponScaling=null;
    if (item instanceof Weapon)
    {
      weaponScaling=new WeaponScalingEntry();
      scaling=weaponScaling;
    }
    else
    {
      scaling=new ItemScalingEntry();
    }
    scaling.setLevel(levelValue);
    scaling.setItemLevel(itemLevel);
    StatsProvider statsProvider=item.getStatsProvider();
    if (statsProvider!=null)
    {
      BasicStatsSet stats=statsProvider.getStats(1,itemLevel);
      scaling.setStats(stats);
    }
    Money value=item.getValue(itemLevel);
    scaling.setMoney(value);
    // Weapon specifics
    if (weaponScaling!=null)
    {
      Weapon weapon=(Weapon)item;
      // DPS
      float dps=weapon.computeDPS(itemLevel);
      weaponScaling.setDPS(dps);
      // Min/max damage
      float minDamage=weapon.computeMinDamage(itemLevel);
      weaponScaling.setMinDamage(minDamage);
      float maxDamage=weapon.computeMaxDamage(itemLevel);
      weaponScaling.setMaxDamage(maxDamage);
    }
    return scaling;
  }
}
