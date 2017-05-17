package delta.games.lotro.lore.items.stats;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.STAT;
import delta.games.lotro.lore.items.Armour;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemPropertyNames;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Item scaling utils.
 * @author DAM
 */
public class Scaling
{
  private static ScalingManager _scalingManager=new ScalingManager();

  /**
   * Get the scaling rule for an item.
   * @param item Item to use.
   * @return A rule or <code>null</code> if not found.
   */
  public static ScalingRule getScalingRule(Item item)
  {
    ScalingRule rule=null;
    String scalingId=item.getProperty(ItemPropertyNames.SCALING);
    if (scalingId!=null)
    {
      rule=_scalingManager.getRule(scalingId);
    }
    return rule;
  }

  /**
   * Scale an item to a given required level.
   * @param item Item to update.
   * @param requiredLevel Level to use.
   * @return <code>true</code> if scaling was done, <code>false</code> otherwise.
   */
  public static boolean scaleToRequiredLevel(Item item, int requiredLevel)
  {
    boolean ret=false;
    ScalingRule rule=getScalingRule(item);
    if (rule!=null)
    {
      Integer itemLevel=rule.getItemLevel(requiredLevel);
      if (itemLevel!=null)
      {
        ret=scaleToItemLevel(item,itemLevel.intValue());
        if (ret)
        {
          item.setMinLevel(Integer.valueOf(requiredLevel));
        }
      }
    }
    return ret;
  }

  /**
   * Scale an item to a given item level.
   * @param item Item to update.
   * @param itemLevel Level to use.
   * @return <code>true</code> if scaling was done, <code>false</code> otherwise.
   */
  public static boolean scaleToItemLevel(Item item, int itemLevel)
  {
    boolean ret=false;
    String formulas=item.getProperty(ItemPropertyNames.FORMULAS);
    if (formulas!=null)
    {
      SlicesBasedItemStatsProvider provider=SlicesBasedItemStatsProvider.fromPersistedString(formulas);
      BasicStatsSet scaledStats=provider.getStats(itemLevel);
      if (item instanceof Armour)
      {
        FixedDecimalsInteger armourValue=scaledStats.getStat(STAT.ARMOUR);
        if (armourValue!=null)
        {
          Armour armour=(Armour)item;
          armour.setArmourValue(armourValue.intValue());
          scaledStats.removeStat(STAT.ARMOUR);
        }
      }
      BasicStatsSet itemStats=item.getStats();
      itemStats.clear();
      itemStats.setStats(scaledStats);
      item.setItemLevel(Integer.valueOf(itemLevel));
      ret=true;
    }
    return ret;
  }
}
