package delta.games.lotro.common.action;

import java.util.ArrayList;
import java.util.List;

/**
 * Action tables for a mob.
 * @author DAM
 */
public class ActionTables
{
  private List<ActionTablesEntry> _entries;

  /**
   * Constructor.
   */
  public ActionTables()
  {
    _entries=new ArrayList<ActionTablesEntry>();
  }

  /**
   * Add an entry.
   * @param entry Entry to add.
   */
  public void addActionTablesEntry(ActionTablesEntry entry)
  {
    _entries.add(entry);
  }

  /**
   * Get the entries.
   * @return a list of entries.
   */
  public List<ActionTablesEntry> getEntries()
  {
    return _entries;
  }
}
