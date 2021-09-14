package delta.games.lotro.lore.instances.loot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.difficulty.Difficulty;
import delta.games.lotro.common.groupSize.GroupSize;
import delta.games.lotro.common.treasure.TrophyList;
import delta.games.lotro.lore.instances.PrivateEncounter;

/**
 * Gathers all the loot tables of a given instance.
 * @author DAM
 */
public class InstanceLoots implements Identifiable
{
  private PrivateEncounter _instance;
  private List<InstanceLootEntry> _entries;

  /**
   * Constructor.
   * @param instance Associated instance.
   */
  public InstanceLoots(PrivateEncounter instance)
  {
    _instance=instance;
    _entries=new ArrayList<InstanceLootEntry>();
  }

  @Override
  public int getIdentifier()
  {
    return _instance.getIdentifier();
  }

  /**
   * Get the associated instance.
   * @return the associated instance.
   */
  public PrivateEncounter getInstance()
  {
    return _instance;
  }

  /**
   * Get the instance loot entries.
   * @return A list of instance loot entries.
   */
  public List<InstanceLootEntry> getEntries()
  {
    return _entries;
  }

  /**
   * Add a loot entry.
   * @param entry Entry to add.
   */
  public void addEntry(InstanceLootEntry entry)
  {
    _entries.add(entry);
  }

  /**
   * Get the loot entry that applies to the given parameters.
   * @param difficulty Difficulty.
   * @param size Group size.
   * @param level Instance level.
   * @return An entry or <code>null</code> if none.
   */
  public InstanceLootEntry getLootTable(Difficulty difficulty, GroupSize size, int level)
  {
    int difficultyCode=difficulty.getCode();
    int sizeCode=size.getCode();

    for(InstanceLootEntry entry : _entries)
    {
      InstanceLootParameters parameters=entry.getParameters();
      if (parameters.accept(difficultyCode,sizeCode,level))
      {
        return entry;
      }
    }
    return null;
  }

  /**
   * Get the identifiers of the reachable items.
   * @return A set of item identifiers.
   */
  public Set<Integer> getItemIds()
  {
    Set<Integer> itemIds=new HashSet<Integer>();
    for(TrophyList trophyList : getTrophyLists().values())
    {
      itemIds.addAll(trophyList.getItemIds());
    }
    return itemIds;
  }

  private Map<Integer,TrophyList> getTrophyLists()
  {
    Map<Integer,TrophyList> ret=new HashMap<Integer,TrophyList>();
    for(InstanceLootEntry entry : _entries)
    {
      TrophyList trophyList=entry.getTrophyList();
      if (trophyList!=null)
      {
        Integer key=Integer.valueOf(trophyList.getIdentifier());
        ret.put(key,trophyList);
      }
    }
    return ret;
  }
}
