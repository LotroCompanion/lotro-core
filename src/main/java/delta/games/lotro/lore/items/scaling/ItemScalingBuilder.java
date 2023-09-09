package delta.games.lotro.lore.items.scaling;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.money.Money;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.Weapon;
import delta.games.lotro.lore.items.legendary.Legendary;
import delta.games.lotro.lore.items.legendary2.Legendary2;
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
    Progression progression=munging.getProgression();
    if (progression==null)
    {
      // TODO Handle this case
      return false;
    }
    return true;
  }

  private static ItemScaling buildScaling(Item item)
  {
    ItemScaling ret=new ItemScaling(item);
    Munging munging=item.getMunging();
    Integer min=munging.getMin();
    int minLevel=(min!=null?min.intValue():1);
    Integer max=munging.getMax();
    int levelCap=LotroCoreConfig.getInstance().getMaxCharacterLevel();
    int maxLevel=(max!=null?max.intValue():levelCap);

    Progression progression=munging.getProgression();
    StatsProvider statsProvider=item.getStatsProvider();
    for(int level=minLevel;level<=maxLevel;level++)
    {
      Float itemLevel=progression.getValue(level);
      if (itemLevel==null)
      {
        continue;
      }
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
      scaling.setLevel(level);
      scaling.setItemLevel(itemLevel.intValue());
      if (statsProvider!=null)
      {
        BasicStatsSet stats=statsProvider.getStats(1,itemLevel.intValue());
        scaling.setStats(stats);
      }
      Money value=item.getValue(itemLevel.intValue());
      scaling.setMoney(value);
      // Weapon specifics
      if (weaponScaling!=null)
      {
        Weapon weapon=(Weapon)item;
        // DPS
        float dps=weapon.computeDPS(itemLevel.intValue());
        weaponScaling.setDPS(dps);
        // Min/max damage
        float minDamage=weapon.computeMinDamage(itemLevel.intValue());
        weaponScaling.setMinDamage(minDamage);
        float maxDamage=weapon.computeMaxDamage(itemLevel.intValue());
        weaponScaling.setMaxDamage(maxDamage);
      }
      ret.addEntry(scaling);
    }
    return ret;
  }
}
