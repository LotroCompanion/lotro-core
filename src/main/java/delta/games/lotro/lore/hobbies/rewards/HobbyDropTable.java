package delta.games.lotro.lore.hobbies.rewards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import delta.games.lotro.common.comparators.NamedComparator;

/**
 * Drop table for a hobby.
 * @author DAM
 */
public class HobbyDropTable
{
  private List<HobbyDropTableEntry> _entries;

  /**
   * Constructor.
   */
  public HobbyDropTable()
  {
    _entries=new ArrayList<HobbyDropTableEntry>();
  }

  /**
   * Add an entry.
   * @param entry Entry to add.
   */
  public void addEntry(HobbyDropTableEntry entry)
  {
    _entries.add(entry);
  }

  /**
   * Get the entries.
   * @return A list of entries, sorted by name.
   */
  public List<HobbyDropTableEntry> getEntries()
  {
    List<HobbyDropTableEntry> ret=new ArrayList<HobbyDropTableEntry>(_entries);
    Collections.sort(ret,new NamedComparator());
    return ret;
  }
}
