package delta.games.lotro.lore.items.scaling;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.money.Money;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.items.Item;
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
    StatsProvider statsProvider=item.getStatsProvider();
    if (statsProvider==null)
    {
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
      return ret;
    }
    Integer min=munging.getMin();
    int minLevel=(min!=null?min.intValue():1);
    Integer max=munging.getMax();
    int levelCap=LotroCoreConfig.getInstance().getMaxCharacterLevel();
    int maxLevel=(max!=null?max.intValue():levelCap);

    for(int level=minLevel;level<=maxLevel;level++)
    {
      Float itemLevel=progression.getValue(level);
      if (itemLevel==null)
      {
        continue;
      }
      BasicStatsSet values=statsProvider.getStats(1,itemLevel.intValue());
      Money value=item.getValue(itemLevel.intValue());
      ItemScalingEntry entry=new ItemScalingEntry(level,itemLevel.intValue(),values,value);
      ret.addEntry(entry);
    }
    return ret;
  }
}
