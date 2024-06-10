package delta.games.lotro.common.action;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.Identifiable;

/**
 * Action table.
 * @author DAM
 */
public class ActionTable implements Identifiable
{
  private int _id;
  private List<ActionTableEntry> _entries;

  /**
   * Constructor.
   * @param id Identifier.
   */
  public ActionTable(int id)
  {
    _id=id;
    _entries=new ArrayList<ActionTableEntry>();
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
  public void addEntry(ActionTableEntry entry)
  {
    _entries.add(entry);
  }

  /**
   * Get the managed entries.
   * @return a list of entries.
   */
  public List<ActionTableEntry> getEntries()
  {
    return _entries;
  }
}
