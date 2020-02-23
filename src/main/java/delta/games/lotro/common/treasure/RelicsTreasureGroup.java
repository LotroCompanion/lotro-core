package delta.games.lotro.common.treasure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.common.Identifiable;
import delta.games.lotro.lore.items.legendary.relics.Relic;

/**
 * Relics treasure group table.
 * @author DAM
 */
public class RelicsTreasureGroup implements Identifiable
{
  private int _id;
  private List<RelicsTreasureGroupEntry> _entries;
  private Map<Integer,Integer> _countToWeight;

  /**
   * Constructor.
   * @param identifier Identifier.
   */
  public RelicsTreasureGroup(int identifier)
  {
    _id=identifier;
    _entries=new ArrayList<RelicsTreasureGroupEntry>();
    _countToWeight=new HashMap<Integer,Integer>();
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
  public void addEntry(RelicsTreasureGroupEntry entry)
  {
    _entries.add(entry);
  }

  /**
   * Get the entries.
   * @return a list of entries.
   */
  public List<RelicsTreasureGroupEntry> getEntries()
  {
    return _entries;
  }

  /**
   * Add a count.
   * @param count Count to add.
   * @param weight Weight to set.
   */
  public void addCount(int count, int weight)
  {
    _countToWeight.put(Integer.valueOf(count),Integer.valueOf(weight));
  }

  /**
   * Get the available counts.
   * @return A sorted list of counts.
   */
  public List<Integer> getCounts()
  {
    List<Integer> ret=new ArrayList<Integer>(_countToWeight.keySet());
    Collections.sort(ret);
    return ret;
  }

  /**
   * Get the weight for the given count.
   * @param count Count to use.
   * @return A weight or <code>null</code> if not found.
   */
  public Integer getWeightForCount(int count)
  {
    return _countToWeight.get(Integer.valueOf(count));
  }

  /**
   * Indicates if this loot table may contain the given relic.
   * @param relicId Identifier of the relic to search.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean contains(int relicId)
  {
    for(RelicsTreasureGroupEntry entry : _entries)
    {
      Relic relic=entry.getRelic();
      if (relic.getIdentifier()==relicId)
      {
        return true;
      }
    }
    return false;
  }

  /**
   * Dump contents.
   * @param sb Output.
   * @param level Indentation level.
   */
  public void dump(StringBuilder sb, int level)
  {
    for(int i=0;i<level;i++) sb.append('\t');
    sb.append("Relics treasure group ID=").append(getIdentifier()).append(EndOfLine.NATIVE_EOL);
    for(int i=0;i<level;i++) sb.append('\t');
    sb.append("Pull counts: ").append(EndOfLine.NATIVE_EOL);
    for(Integer count : getCounts())
    {
      for(int i=0;i<level+1;i++) sb.append('\t');
      int weight=getWeightForCount(count.intValue()).intValue();
      sb.append("Count: ").append(count).append(" (weight ").append(weight).append(')').append(EndOfLine.NATIVE_EOL);
    }
    for(int i=0;i<level;i++) sb.append('\t');
    sb.append("Relics: ").append(EndOfLine.NATIVE_EOL);
    for(RelicsTreasureGroupEntry entry : _entries)
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
