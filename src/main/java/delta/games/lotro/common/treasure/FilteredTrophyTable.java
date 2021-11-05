package delta.games.lotro.common.treasure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.common.utils.text.EndOfLine;

/**
 * Filtered trophy table.
 * @author DAM
 */
public class FilteredTrophyTable extends LootTable
{
  private List<FilteredTrophyTableEntry> _entries;

  /**
   * Constructor.
   * @param identifier Identifier.
   */
  public FilteredTrophyTable(int identifier)
  {
    super(identifier);
    _entries=new ArrayList<FilteredTrophyTableEntry>();
  }

  /**
   * Add an entry.
   * @param entry Entry to add.
   */
  public void addEntry(FilteredTrophyTableEntry entry)
  {
    _entries.add(entry);
  }

  /**
   * Get the entries.
   * @return a list of entries.
   */
  public List<FilteredTrophyTableEntry> getEntries()
  {
    return _entries;
  }

  @Override
  public boolean contains(int itemId)
  {
    for(FilteredTrophyTableEntry entry : _entries)
    {
      LootTable lootTable=entry.getLootTable();
      if ((lootTable!=null) && (lootTable.contains(itemId)))
      {
        return true;
      }
    }
    return false;
  }

  @Override
  public Set<Integer> getItemIds()
  {
    Set<Integer> ret=new HashSet<Integer>();
    for(FilteredTrophyTableEntry entry : _entries)
    {
      LootTable lootTable=entry.getLootTable();
      if (lootTable!=null)
      {
        ret.addAll(lootTable.getItemIds());
      }
    }
    return ret;
  }

  @Override
  public void dump(StringBuilder sb, int level)
  {
    for(int i=0;i<level;i++) sb.append('\t');
    sb.append("Filtered trophy table ID=").append(getIdentifier()).append(EndOfLine.NATIVE_EOL);
    for(FilteredTrophyTableEntry entry : _entries)
    {
      entry.dump(sb,level+1);
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
