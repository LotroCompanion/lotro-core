package delta.games.lotro.lore.items.legendary2;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.sets.ItemsSet;

/**
 * Utility methods for traceries sets.
 * @author DAM
 */
public class TraceriesSetsUtils
{
  private static final Logger LOGGER=LoggerFactory.getLogger(TraceriesSetsUtils.class);

  /**
   * Get the member traceries for a set.
   * @param set Set to use.
   * @return A list of traceries.
   */
  public static List<Tracery> getMemberTraceries(ItemsSet set)
  {
    List<Tracery> ret=new ArrayList<Tracery>();
    TraceriesManager traceriesMgr=TraceriesManager.getInstance();
    for(Item item : set.getMembers())
    {
      Tracery tracery=traceriesMgr.getTracery(item.getIdentifier());
      ret.add(tracery);
    }
    return ret;
  }

  /**
   * Find the character level range for the given traceries.
   * @param traceries Traceries to use.
   * @return An array of min/max character level.
   */
  public static int[] findCharacterLevelRange(List<Tracery> traceries)
  {
    int min=1000;
    int max=-1;
    for(Tracery tracery : traceries)
    {
      Item item=tracery.getItem();
      Integer minLevel=item.getMinLevel();
      if (minLevel!=null)
      {
        if (min>minLevel.intValue())
        {
          min=minLevel.intValue();
        }
      }
      else
      {
        LOGGER.warn("No min level for tracery: {}",tracery);
      }
      Integer maxLevel=item.getMaxLevel();
      if (maxLevel!=null)
      {
        if (max<maxLevel.intValue())
        {
          max=maxLevel.intValue();
        }
      }
      else
      {
        LOGGER.warn("No max level for tracery: {}",tracery);
      }
    }
    return new int[]{min,max};
  }

  /**
   * Find the traceries that can be used at the given character level.
   * @param traceries Traceries to choose from.
   * @param characterLevel Character level.
   * @return A list of traceries.
   */
  public static List<Tracery> findTraceriesForCharacterLevel(List<Tracery> traceries, int characterLevel)
  {
    List<Tracery> ret=new ArrayList<Tracery>();
    for(Tracery tracery : traceries)
    {
      Item item=tracery.getItem();
      Integer minLevel=item.getMinLevel();
      if ((minLevel!=null) && (minLevel.intValue()>characterLevel))
      {
        continue;
      }
      Integer maxLevel=item.getMaxLevel();
      if ((maxLevel!=null) && (maxLevel.intValue()<characterLevel))
      {
        continue;
      }
      ret.add(tracery);
    }
    return ret;
  }

  /**
   * Find the tracery items that can be used at the given character level.
   * @param from Items to choose from.
   * @param characterLevel Character level.
   * @return A list of items.
   */
  public static List<Item> findTraceryItemsForCharacterLevel(List<Item> from, int characterLevel)
  {
    List<Item> ret=new ArrayList<Item>();
    for(Item item : from)
    {
      Integer minLevel=item.getMinLevel();
      if ((minLevel!=null) && (minLevel.intValue()>characterLevel))
      {
        continue;
      }
      Integer maxLevel=item.getMaxLevel();
      if ((maxLevel!=null) && (maxLevel.intValue()<characterLevel))
      {
        continue;
      }
      ret.add(item);
    }
    return ret;
  }

  /**
   * Find the item level range for the given traceries.
   * @param traceries Traceries to use.
   * @return An array of min/max item level.
   */
  public static int[] findItemLevelRange(List<Tracery> traceries)
  {
    int min=1000;
    int max=-1;
    for(Tracery tracery : traceries)
    {
      int minLevel=tracery.getMinItemLevel();
      if (min>minLevel)
      {
        min=minLevel;
      }
      int maxLevel=tracery.getMaxItemLevel();
      if (max<maxLevel)
      {
        max=maxLevel;
      }
    }
    return new int[]{min,max};
  }
}
