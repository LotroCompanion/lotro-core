package delta.games.lotro.lore.items.sets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.common.utils.math.Range;
import delta.games.lotro.common.stats.RangedStatProvider;
import delta.games.lotro.common.stats.ScalableStatProvider;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.common.stats.StatsProviderEntry;
import delta.games.lotro.common.stats.TieredScalableStatProvider;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.scaling.Munging;
import delta.games.lotro.lore.parameters.Game;

/**
 * Utility methods for items sets.
 * @author DAM
 */
public class ItemsSetsUtils
{
  private static final Logger LOGGER=LoggerFactory.getLogger(ItemsSetsUtils.class);

  /**
   * Find the item level range for the set.
   * @param set Set to use.
   * @return A range.
   */
  public static Range findItemLevelRange(ItemsSet set)
  {
    int min=1000;
    int max=-1;
    for(Item item : set.getMembers())
    {
      Range itemLevelRange=findItemLevelRange(set,item);
      Integer minLevel=itemLevelRange.getMin();
      if (minLevel!=null)
      {
        if (min>minLevel.intValue())
        {
          min=minLevel.intValue();
        }
      }
      else
      {
        LOGGER.warn("No min level for item: "+item);
      }
      Integer maxLevel=itemLevelRange.getMax();
      if (maxLevel!=null)
      {
        if (max<maxLevel.intValue())
        {
          max=maxLevel.intValue();
        }
      }
      else
      {
        LOGGER.warn("No max level for item: "+item);
      }
    }
    return new Range(min,max);
  }

  /**
   * Find the item level range for the given item part of the given set.
   * @param set Set to use.
   * @param item Item to use.
   * @return A range.
   */
  private static Range findItemLevelRange(ItemsSet set, Item item)
  {
    Munging munging=item.getMunging();
    if (munging==null)
    {
      Integer itemLevel=item.getItemLevel();
      int itemLevelInt=(itemLevel!=null)?itemLevel.intValue():0;
      return new Range(itemLevelInt,itemLevelInt);
    }
    Range characterLevelRange=findCharacterLevelRange(set,item);
    Integer minLevel=munging.getItemLevel(characterLevelRange.getMin().intValue());
    Integer maxLevel=munging.getItemLevel(characterLevelRange.getMax().intValue());
    Range ret=new Range(minLevel,maxLevel);
    return ret;
  }

  /**
   * Find the character level range for the given item part of the given set.
   * @param set Set to use.
   * @param item Item to use.
   * @return A range.
   */
  private static Range findCharacterLevelRange(ItemsSet set, Item item)
  {
    // Min character level
    int minLevel=1;
    int minSetLevel=set.getRequiredMinLevel();
    if (minSetLevel>minLevel)
    {
      minLevel=minSetLevel;
    }
    Integer minItemLevel=item.getMinLevel();
    if (minItemLevel!=null)
    {
      if (minItemLevel.intValue()>minLevel)
      {
        minLevel=minItemLevel.intValue();
      }
    }
    // Max character level
    int maxLevel=Game.getParameters().getMaxCharacterLevel();
    Integer maxSetLevel=set.getRequiredMaxLevel();
    if (maxSetLevel!=null)
    {
      if (maxSetLevel.intValue()<maxLevel)
      {
        maxLevel=maxSetLevel.intValue();
      }
    }
    Integer maxItemLevel=item.getMaxLevel();
    if (maxItemLevel!=null)
    {
      if (maxItemLevel.intValue()<maxLevel)
      {
        maxLevel=maxItemLevel.intValue();
      }
    }
    Range ret=new Range(minLevel,maxLevel);
    if (LOGGER.isDebugEnabled())
    {
      LOGGER.debug("Character level range for "+item+": "+ret);
    }
    return ret;
  }

  /**
   * Indicates if the given set has multiple item levels.
   * @param set Set to use.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public static boolean hasMultipleItemLevels(ItemsSet set)
  {
    if (set.useAverageItemLevelForSetLevel())
    {
      int membersCount=set.getMembers().size();
      if (membersCount>0)
      {
        boolean scalable=ItemsSetsUtils.hasScalableStats(set);
        if (scalable)
        {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Indicates if the given set has scalable bonuses.
   * @param set Set to use.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  private static boolean hasScalableStats(ItemsSet set)
  {
    for(SetBonus bonus : set.getBonuses())
    {
      if (hasScalableStats(bonus.getStatsProvider()))
      {
        return true;
      }
    }
    return false;
  }

  private static boolean hasScalableStats(StatsProvider statsProvider)
  {
    int nbEntries=statsProvider.getEntriesCount();
    for(int i=0;i<nbEntries;i++)
    {
      StatsProviderEntry entry=statsProvider.getEntry(i);
      if (entry instanceof ScalableStatProvider)
      {
        return true;
      }
      if (entry instanceof TieredScalableStatProvider)
      {
        return true;
      }
      if (entry instanceof RangedStatProvider)
      {
        return true;
      }
    }
    return false;
  }
}
