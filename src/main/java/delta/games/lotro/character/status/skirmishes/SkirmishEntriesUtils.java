package delta.games.lotro.character.status.skirmishes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.character.status.skirmishes.cfg.SkirmishEntriesPolicy;
import delta.games.lotro.character.status.skirmishes.filter.SkirmishEntryFilter;
import delta.games.lotro.lore.instances.SkirmishGroupSize;
import delta.games.lotro.lore.instances.SkirmishPrivateEncounter;

/**
 * Utility methods related to skirmish entries.
 * @author DAM
 */
public class SkirmishEntriesUtils
{
  /**
   * Get all the skirmish entries for a stats manager.
   * @param statsMgr Input data.
   * @param filter Entries filter.
   * @param policy Policy (merges).
   * @return A list of skirmish entries.
   */
  public static List<SkirmishEntry> getEntries(SkirmishStatsManager statsMgr, SkirmishEntryFilter filter, SkirmishEntriesPolicy policy)
  {
    List<SkirmishEntry> ret=new ArrayList<SkirmishEntry>();
    for(SingleSkirmishStats stats : statsMgr.getStats())
    {
      List<SkirmishEntry> entries=getEntries(stats);
      for(SkirmishEntry entry : entries)
      {
        if (filter.accept(entry))
        {
          ret.add(entry);
        }
      }
    }
    ret=handleMerges(policy,ret);
    return ret;
  }

  private static List<SkirmishEntry> getEntries(SingleSkirmishStats stats)
  {
    List<SkirmishEntry> ret=new ArrayList<SkirmishEntry>();
    SkirmishPrivateEncounter skirmish=stats.getSkirmish();
    for(SkirmishGroupSize size : SkirmishGroupSize.values())
    {
      for(SkirmishLevel level : SkirmishLevel.values())
      {
        SkirmishStats skirmishStats=stats.getStats(size,level);
        if (skirmishStats!=null)
        {
          SkirmishEntry entry=new SkirmishEntry(skirmish,size,level,skirmishStats);
          ret.add(entry);
        }
      }
    }
    return ret;
  }

  private static List<SkirmishEntry> handleMerges(SkirmishEntriesPolicy policy, List<SkirmishEntry> input)
  {
    List<SkirmishEntry> ret=input;
    if (policy.isMergeLevels())
    {
      ret=mergeLevels(ret);
    }
    if (policy.isMergeSizes())
    {
      ret=mergeGroups(ret);
    }
    return ret;
  }

  private static List<SkirmishEntry> mergeGroups(List<SkirmishEntry> entries)
  {
    Map<String,List<SkirmishEntry>> map=new HashMap<String,List<SkirmishEntry>>();
    for(SkirmishEntry entry : entries)
    {
      SkirmishPrivateEncounter skirmish=entry.getSkirmish();
      String skirmishKey=(skirmish!=null)?String.valueOf(skirmish.getIdentifier()):"";
      SkirmishLevel level=entry.getLevel();
      String levelKey=(level!=null)?level.name():"";
      String key=skirmishKey+"#"+levelKey;
      List<SkirmishEntry> list=map.get(key);
      if (list==null)
      {
        list=new ArrayList<SkirmishEntry>();
        map.put(key,list);
      }
      list.add(entry);
    }
    List<SkirmishEntry> ret=new ArrayList<SkirmishEntry>();
    for(List<SkirmishEntry> entry : map.values())
    {
      if (entry.size()==1)
      {
        ret.add(entry.get(0));
      }
      else
      {
        SkirmishEntry first=entry.get(0);
        SkirmishPrivateEncounter skirmish=first.getSkirmish();
        SkirmishLevel level=first.getLevel();
        ret.add(merge(skirmish,null,level,entry));
      }
    }
    return ret;
  }

  private static List<SkirmishEntry> mergeLevels(List<SkirmishEntry> entries)
  {
    Map<String,List<SkirmishEntry>> map=new HashMap<String,List<SkirmishEntry>>();
    for(SkirmishEntry entry : entries)
    {
      SkirmishPrivateEncounter skirmish=entry.getSkirmish();
      String skirmishKey=(skirmish!=null)?String.valueOf(skirmish.getIdentifier()):"";
      SkirmishGroupSize size=entry.getSize();
      String sizeKey=(size!=null)?size.name():"";
      String key=skirmishKey+"#"+sizeKey;
      List<SkirmishEntry> list=map.get(key);
      if (list==null)
      {
        list=new ArrayList<SkirmishEntry>();
        map.put(key,list);
      }
      list.add(entry);
    }
    List<SkirmishEntry> ret=new ArrayList<SkirmishEntry>();
    for(List<SkirmishEntry> entry : map.values())
    {
      if (entry.size()==1)
      {
        ret.add(entry.get(0));
      }
      else
      {
        SkirmishEntry first=entry.get(0);
        SkirmishPrivateEncounter skirmish=first.getSkirmish();
        SkirmishGroupSize size=first.getSize();
        ret.add(merge(skirmish,size,null,entry));
      }
    }
    return ret;
  }

  private static SkirmishEntry merge(SkirmishPrivateEncounter skirmish, SkirmishGroupSize size, SkirmishLevel level, List<SkirmishEntry> entries)
  {
    SkirmishStats stats=new SkirmishStats();
    for(SkirmishEntry entry : entries)
    {
      stats.add(entry.getStats());
    }
    return new SkirmishEntry(skirmish,size,level,stats);
  }
}
