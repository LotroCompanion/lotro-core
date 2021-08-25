package delta.games.lotro.common.treasure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.common.Identifiable;
import delta.games.lotro.lore.items.legendary.relics.Relic;

/**
 * Relics list.
 * @author DAM
 */
public class RelicsList implements Identifiable
{
  private int _id;
  private List<RelicsListEntry> _entries;

  /**
   * Constructor.
   * @param identifier Identifier.
   */
  public RelicsList(int identifier)
  {
    _id=identifier;
    _entries=new ArrayList<RelicsListEntry>();
  }

  @Override
  public int getIdentifier()
  {
    return _id;
  }

  /**
   * Add an entry.
   * @param entry Entry to add.
   */
  public void addEntry(RelicsListEntry entry)
  {
    _entries.add(entry);
  }

  /**
   * Get the entries.
   * @return a list of entries.
   */
  public List<RelicsListEntry> getEntries()
  {
    return _entries;
  }

  /**
   * Indicates if this loot table may contain the given relic.
   * @param relicId Identifier of the relic to search.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean contains(int relicId)
  {
    for(RelicsListEntry entry : _entries)
    {
      Relic relic=entry.getRelic();
      if ((relic!=null) && (relic.getIdentifier()==relicId))
      {
        return true;
      }
      RelicsTreasureGroup relicsTreasureGroup=entry.getRelicsTreasureGroup();
      if ((relicsTreasureGroup!=null) && (relicsTreasureGroup.contains(relicId)))
      {
        return true;
      }
    }
    return false;
  }


  /**
   * Get the identifiers of the reachable relics.
   * @return A set of relic identifiers.
   */
  public Set<Integer> getRelicIds()
  {
    Set<Integer> ret=new HashSet<Integer>();
    for(RelicsListEntry entry : _entries)
    {
      Relic relic=entry.getRelic();
      if (relic!=null)
      {
        int relicId=relic.getIdentifier();
        ret.add(Integer.valueOf(relicId));
      }
      RelicsTreasureGroup relicsTreasureGroup=entry.getRelicsTreasureGroup();
      if (relicsTreasureGroup!=null)
      {
        ret.addAll(relicsTreasureGroup.getRelicIds());
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
    sb.append("Relics list ID=").append(_id).append(EndOfLine.NATIVE_EOL);
    for(RelicsListEntry entry : _entries)
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
