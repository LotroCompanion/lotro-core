package delta.games.lotro.common.treasure;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.Identifiable;

/**
 * Trophy list.
 * @author DAM
 */
public class TrophyList implements Identifiable
{
  private int _id;
  private List<TrophyListEntry> _entries;

  /**
   * Constructor.
   * @param identifier Identifier.
   */
  public TrophyList(int identifier)
  {
    _id=identifier;
    _entries=new ArrayList<TrophyListEntry>();
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
  public void addEntry(TrophyListEntry entry)
  {
    _entries.add(entry);
  }

  /**
   * Get the entries.
   * @return a list of entries.
   */
  public List<TrophyListEntry> getEntries()
  {
    return _entries;
  }
}
