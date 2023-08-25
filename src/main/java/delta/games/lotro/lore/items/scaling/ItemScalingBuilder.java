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
    ItemScaling ret=new ItemScaling(item);
    if (item instanceof Legendary)
    {
      // No support for scaling of legendary items
      return ret;
    }
    if (item instanceof Legendary2)
    {
      // No support for scaling of new legendary items
      return ret;
    }
    Munging munging=item.getMunging();
    if (munging==null)
    {
      return ret;
    }
    Progression progression=munging.getProgression();
    if (progression==null)
    {
      // TODO Handle this case
      return ret;
    }
    Integer min=munging.getMin();
    int minLevel=(min!=null?min.intValue():1);
    Integer max=munging.getMax();
    int levelCap=LotroCoreConfig.getInstance().getMaxCharacterLevel();
    int maxLevel=(max!=null?max.intValue():levelCap);

    StatsProvider statsProvider=item.getStatsProvider();
    for(int level=minLevel;level<=maxLevel;level++)
    {
      Float itemLevel=progression.getValue(level);
      if (itemLevel==null)
      {
        continue;
      }
      BasicStatsSet values=null;
      if (statsProvider!=null)
      {
        values=statsProvider.getStats(1,itemLevel.intValue());
      }
      Money value=item.getValue(itemLevel.intValue());
      Float dps=null;
      if (item instanceof Weapon)
      {
        Weapon weapon=(Weapon)item;
        dps=Float.valueOf(weapon.computeDPS(itemLevel.intValue()));
      }
      ItemScalingEntry entry=new ItemScalingEntry(level,itemLevel.intValue(),values,value,dps);
      ret.addEntry(entry);
    }
    return ret;
  }
}
