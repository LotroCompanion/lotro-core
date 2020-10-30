package delta.games.lotro.common.treasure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.utils.Proxy;

/**
 * Items table.
 * @author DAM
 */
public class ItemsTable extends TreasureGroupProfile
{
  private List<ItemsTableEntry> _entries;

  /**
   * Constructor.
   * @param identifier Identifier.
   */
  public ItemsTable(int identifier)
  {
    super(identifier);
    _entries=new ArrayList<ItemsTableEntry>();
  }

  /**
   * Add an entry.
   * @param entry Entry to add.
   */
  public void addEntry(ItemsTableEntry entry)
  {
    _entries.add(entry);
  }

  /**
   * Get the entries.
   * @return a list of entries.
   */
  public List<ItemsTableEntry> getEntries()
  {
    return _entries;
  }

  @Override
  public boolean contains(int itemId)
  {
    for(ItemsTableEntry entry : _entries)
    {
      Proxy<Item> item=entry.getItem();
      if (item.getId()==itemId)
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
    for(ItemsTableEntry entry : _entries)
    {
      Proxy<Item> item=entry.getItem();
      int itemId=item.getId();
      ret.add(Integer.valueOf(itemId));
    }
    return ret;
  }

  /**
   * Dump contents.
   * @param sb Output.
   * @param level Indentation level.
   */
  public void dump(StringBuilder sb, int level)
  {
    for(int i=0;i<level;i++) sb.append('\t');
    sb.append("Items table ID=").append(getIdentifier()).append(EndOfLine.NATIVE_EOL);
    for(ItemsTableEntry entry : _entries)
    {
      for(int i=0;i<level+1;i++) sb.append('\t');
      sb.append(entry).append(EndOfLine.NATIVE_EOL);
    }
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    dump(sb,0);
    return sb.toString().trim();
  }
}
