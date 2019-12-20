package delta.games.lotro.common.treasure;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.common.Identifiable;

/**
 * Items table.
 * @author DAM
 */
public class ItemsTable implements Identifiable
{
  private int _id;
  private List<ItemsTableEntry> _entries;

  /**
   * Constructor.
   * @param identifier Identifier.
   */
  public ItemsTable(int identifier)
  {
    _id=identifier;
    _entries=new ArrayList<ItemsTableEntry>();
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

  /**
   * Dump contents.
   * @param sb Output.
   * @param level Indentation level.
   */
  public void dump(StringBuilder sb, int level)
  {
    for(int i=0;i<level;i++) sb.append('\t');
    sb.append("Items table ID=").append(_id).append(EndOfLine.NATIVE_EOL);
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
