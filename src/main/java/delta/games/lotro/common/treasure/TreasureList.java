package delta.games.lotro.common.treasure;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.Identifiable;

/**
 * Treasure list.
 * @author DAM
 */
public class TreasureList implements Identifiable
{
  private int _id;
  private List<TreasureListEntry> _entries;

  /**
   * Constructor.
   * @param identifier Identifier.
   */
  public TreasureList(int identifier)
  {
    _id=identifier;
    _entries=new ArrayList<TreasureListEntry>();
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
}
