package delta.games.lotro.common.treasure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.common.Identifiable;

/**
 * Filtered trophy table.
 * @author DAM
 */
public class FilteredTrophyTable implements Identifiable
{
  private int _id;
  private List<FilteredTrophyTableEntry> _entries;

  /**
   * Constructor.
   * @param identifier Identifier.
   */
  public FilteredTrophyTable(int identifier)
  {
    _id=identifier;
    _entries=new ArrayList<FilteredTrophyTableEntry>();
  }

  public int getIdentifier()
  {
    return _id;
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

  /**
   * Indicates if this loot table may contain the given item.
   * @param itemId Identifier of the item to search.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean contains(int itemId)
  {
    for(FilteredTrophyTableEntry entry : _entries)
    {
      TrophyList trophyList=entry.getTrophyList();
      if ((trophyList!=null) && (trophyList.contains(itemId)))
      {
        return true;
      }
      WeightedTreasureTable weightedTreasureTable=entry.getTreasureTable();
      if ((weightedTreasureTable!=null) && (weightedTreasureTable.contains(itemId)))
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
    for(FilteredTrophyTableEntry entry : _entries)
    {
      TrophyList trophyList=entry.getTrophyList();
      if (trophyList!=null)
      {
        ret.addAll(trophyList.getItemIds());
      }
      WeightedTreasureTable treasureTable=entry.getTreasureTable();
      if (treasureTable!=null)
      {
        ret.addAll(treasureTable.getItemIds());
      }
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
    sb.append("Filtered trophy table ID=").append(_id).append(EndOfLine.NATIVE_EOL);
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
