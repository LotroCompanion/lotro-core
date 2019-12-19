package delta.games.lotro.common.treasure;

import java.util.ArrayList;
import java.util.List;

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
}
