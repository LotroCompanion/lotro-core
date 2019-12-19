package delta.games.lotro.common.treasure;

import java.util.ArrayList;
import java.util.List;

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
}
