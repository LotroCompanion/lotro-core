package delta.games.lotro.character.status.skirmishes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.character.status.skirmishes.cfg.SkirmishEntriesPolicy;
import delta.games.lotro.character.status.skirmishes.filter.SkirmishEntryFilter;
import delta.games.lotro.common.enums.GroupSize;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
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
    LotroEnum<GroupSize> groupSizesMgr=LotroEnumsRegistry.getInstance().get(GroupSize.class);
    for(GroupSize size : groupSizesMgr.getAll())
    {
      for(SkirmishLevel level : SkirmishLevel.values())
      {
        SkirmishStats skirmishStats=stats.getStats(size,level);
        if ((skirmishStats!=null) && (!skirmishStats.isEmpty()))
        {
          SkirmishEntry entry=new SkirmishEntry(skirmish,size,level,skirmishStats);
          ret.add(entry);
        }
      }
    }
    return ret;
  }

  private static List<SkirmishEntry> handleMerges(SkirmishEntriesPolicy policy, List<SkirmishEntry> entries)
  {
    Map<String,List<SkirmishEntry>> map=new HashMap<String,List<SkirmishEntry>>();
    for(SkirmishEntry entry : entries)
    {
      SkirmishPrivateEncounter skirmish=entry.getSkirmish();
      String key=(skirmish!=null)?String.valueOf(skirmish.getIdentifier()):"";
      if (!policy.isMergeLevels())
      {
        SkirmishLevel level=entry.getLevel();
        String levelKey=(level!=null)?level.name():"";
        key=key+"#"+levelKey;
      }
      if (!policy.isMergeSizes())
      {
        GroupSize size=entry.getSize();
        String sizeKey=(size!=null)?String.valueOf(size.getCode()):"";
        key=key+"#"+sizeKey;
      }
      List<SkirmishEntry> list=map.get(key);
      if (list==null)
      {
        list=new ArrayList<SkirmishEntry>();
        map.put(key,list);
      }
      list.add(entry);
    }
    List<SkirmishEntry> ret=new ArrayList<SkirmishEntry>();
    for(List<SkirmishEntry> entryList : map.values())
    {
      int nbEntries=entryList.size();
      if (nbEntries==1)
      {
        ret.add(entryList.get(0));
      }
      else
      {
        SkirmishEntry first=entryList.get(0);
        SkirmishPrivateEncounter skirmish=first.getSkirmish();
        GroupSize size=first.getSize();
        SkirmishLevel level=first.getLevel();
        for(int i=1;i<nbEntries;i++)
        {
          SkirmishEntry entry=entryList.get(i);
          if (entry.getSize()!=size)
          {
            size=null;
          }
          if (entry.getLevel()!=level)
          {
            level=null;
          }
        }
        ret.add(merge(skirmish,size,level,entryList));
      }
    }
    return ret;
  }

  private static SkirmishEntry merge(SkirmishPrivateEncounter skirmish, GroupSize size, SkirmishLevel level, List<SkirmishEntry> entries)
  {
    SkirmishStats stats=new SkirmishStats();
    for(SkirmishEntry entry : entries)
    {
      if (!entry.getStats().isEmpty())
      {
        stats.add(entry.getStats());
      }
    }
    return new SkirmishEntry(skirmish,size,level,stats);
  }
}
