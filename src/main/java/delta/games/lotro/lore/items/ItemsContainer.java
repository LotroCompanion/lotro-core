package delta.games.lotro.lore.items;

import java.util.HashSet;
import java.util.Set;

import delta.games.lotro.common.treasure.LootTable;
import delta.games.lotro.lore.items.containers.LootType;

/**
 * Container-specific data (items).
 * @author DAM
 */
public class ItemsContainer extends Container
{
  private static final int NB_TABLES=LootType.values().length;
  private LootTable[] _tables;
  private Integer _customSkirmishLootTableId;

  /**
   * Constructor.
   * @param identifier Item identifier.
   */
  public ItemsContainer(int identifier)
  {
    super(identifier);
    _tables=new LootTable[NB_TABLES];
  }

  /**
   * Get a loot table.
   * @param type Table type.
   * @return A table or <code>null</code> if not found.
   */
  public LootTable get(LootType type)
  {
    return _tables[type.ordinal()];
  }

  /**
   * Set a loot table.
   * @param type Table type.
   * @param table Table to set.
   */
  public void set(LootType type, LootTable table)
  {
    _tables[type.ordinal()]=table;
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
    for(LootTable table : _tables)
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
    for(LootTable table : _tables)
    {
      if (table!=null)
      {
        ret.addAll(table.getItemIds());
      }
    }
    return ret;
  }
}
