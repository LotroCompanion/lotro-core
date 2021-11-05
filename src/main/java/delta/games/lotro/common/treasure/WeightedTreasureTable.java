package delta.games.lotro.common.treasure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.common.utils.text.EndOfLine;

/**
 * Weighted treasure table.
 * @author DAM
 */
public class WeightedTreasureTable extends LootTable
{
  private List<WeightedTreasureTableEntry> _entries;

  /**
   * Constructor.
   * @param identifier Identifier.
   */
  public WeightedTreasureTable(int identifier)
  {
    super(identifier);
    _entries=new ArrayList<WeightedTreasureTableEntry>();
  }

  /**
   * Add an entry.
   * @param entry Entry to add.
   */
  public void addEntry(WeightedTreasureTableEntry entry)
  {
    _entries.add(entry);
  }

  /**
   * Get the entries.
   * @return a list of entries.
   */
  public List<WeightedTreasureTableEntry> getEntries()
  {
    return _entries;
  }

  @Override
  public boolean contains(int itemId)
  {
    for(WeightedTreasureTableEntry entry : _entries)
    {
      TrophyList trophyList=entry.getTrophyList();
      boolean found=trophyList.contains(itemId);
      if (found)
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
    for(WeightedTreasureTableEntry entry : _entries)
    {
      TrophyList trophyList=entry.getTrophyList();
      if (trophyList!=null)
      {
        ret.addAll(trophyList.getItemIds());
      }
    }
    return ret;
  }

  @Override
  public void dump(StringBuilder sb, int level)
  {
    for(int i=0;i<level;i++) sb.append('\t');
    sb.append("Weighted treasure table ID=").append(getIdentifier()).append(EndOfLine.NATIVE_EOL);
    for(WeightedTreasureTableEntry entry : _entries)
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
