package delta.games.lotro.lore.items.containers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import delta.games.lotro.common.treasure.LootTable;

/**
 * Set of loot tables.
 * @author DAM
 */
public class LootTables
{
  private Map<LootType,LootTable> _tables;
  private Integer _customSkirmishLootTableId;

  /**
   * Constructor.
   */
  public LootTables()
  {
    _tables=new HashMap<LootType,LootTable>();
  }

  /**
   * Indicates if this container has at least a table.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean hasTables()
  {
    return ((_customSkirmishLootTableId!=null) || (_tables.size()>0));
  }

  /**
   * Get a loot table.
   * @param type Table type.
   * @return A table or <code>null</code> if not found.
   */
  public LootTable get(LootType type)
  {
    return _tables.get(type);
  }

  /**
   * Set a loot table.
   * @param type Table type.
   * @param table Table to set.
   */
  public void set(LootType type, LootTable table)
  {
    _tables.put(type,table);
  }

  /**
   * Get the identifier of the associated custom skirmish table, if any.
   * @return An identifier, or <code>null</code>.
   */
  public Integer getCustomSkirmishLootTableId()
  {
    return _customSkirmishLootTableId;
  }

  /**
   * Set the identifier of the associated custom skirmish table.
   * @param customSkirmishLootTableId Identifier to set.
   */
  public void setCustomSkirmishLootTableId(Integer customSkirmishLootTableId)
  {
    _customSkirmishLootTableId=customSkirmishLootTableId;
  }

  /**
   * Indicates if this container may contain the given item.
   * @param itemId Identifier of the item to search.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean contains(int itemId)
  {
    for(LootTable table : _tables.values())
    {
      if ((table!=null) && (table.contains(itemId)))
      {
        return true;
      }
    }
    return false;
  }

  /**
   * Get the identifiers of the reachable items.
   * @return A set of item identifiers.
   */
  public Set<Integer> getItemIds()
  {
    Set<Integer> ret=new HashSet<Integer>();
    for(LootTable table : _tables.values())
    {
      if (table!=null)
      {
        ret.addAll(table.getItemIds());
      }
    }
    return ret;
  }
}
