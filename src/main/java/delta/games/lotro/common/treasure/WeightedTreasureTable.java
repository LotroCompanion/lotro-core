package delta.games.lotro.common.treasure;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.common.Identifiable;

/**
 * Weighted treasure table.
 * @author DAM
 */
public class WeightedTreasureTable implements Identifiable
{
  private int _id;
  private List<WeightedTreasureTableEntry> _entries;

  /**
   * Constructor.
   * @param identifier Identifier.
   */
  public WeightedTreasureTable(int identifier)
  {
    _id=identifier;
    _entries=new ArrayList<WeightedTreasureTableEntry>();
  }

  public int getIdentifier()
  {
    return _id;
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

  /**
   * Dump contents.
   * @param sb Output.
   * @param level Indentation level.
   */
  public void dump(StringBuilder sb, int level)
  {
    for(int i=0;i<level;i++) sb.append('\t');
    sb.append("Weighted treasure table ID=").append(_id).append(EndOfLine.NATIVE_EOL);
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
