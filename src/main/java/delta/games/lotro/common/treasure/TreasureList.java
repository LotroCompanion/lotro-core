package delta.games.lotro.common.treasure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.common.utils.text.EndOfLine;

/**
 * Treasure list.
 * @author DAM
 */
public class TreasureList extends TreasureGroupProfile
{
  private List<TreasureListEntry> _entries;

  /**
   * Constructor.
   * @param identifier Identifier.
   */
  public TreasureList(int identifier)
  {
    super(identifier);
    _entries=new ArrayList<TreasureListEntry>();
  }

  /**
   * Add an entry.
   * @param entry Entry to add.
   */
  public void addEntry(TreasureListEntry entry)
  {
    _entries.add(entry);
  }

  /**
   * Get the entries.
   * @return a list of entries.
   */
  public List<TreasureListEntry> getEntries()
  {
    return _entries;
  }

  @Override
  public boolean contains(int itemId)
  {
    for(TreasureListEntry entry : _entries)
    {
      TreasureGroupProfile treasureGroupProfile=entry.getTreasureGroup();
      boolean found=treasureGroupProfile.contains(itemId);
      if (found)
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
    for(TreasureListEntry entry : _entries)
    {
      TreasureGroupProfile treasureGroupProfile=entry.getTreasureGroup();
      ret.addAll(treasureGroupProfile.getItemIds());
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
    sb.append("Treasure list ID=").append(getIdentifier()).append(EndOfLine.NATIVE_EOL);
    for(TreasureListEntry entry : _entries)
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
