package delta.games.lotro.lore.trade.barter;

import java.util.ArrayList;
import java.util.List;

/**
 * A barter profile.
 * @author DAM
 */
public class BarterProfile
{
  // Identifier
  private int _identifier;
  // Name (optional)
  private String _name;
  // Requirements
  // - class
  // - reputation
  // - quest
  // Barter entries
  private List<BarterEntry> _entries;

  /**
   * Constructor.
   * @param id Profile identifier.
   */
  public BarterProfile(int id)
  {
    _identifier=id;
    _name="";
    _entries=new ArrayList<BarterEntry>();
  }

  /**
   * Get the identifier.
   * @return an identifier.
   */
  public int getIdentifier()
  {
    return _identifier;
  }

  /**
   * Get the profile name.
   * @return a profile name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Set the profile name.
   * @param name Name to set.
   */
  public void setName(String name)
  {
    if (name==null) name="";
    _name=name;
  }

  /**
   * Add an entry.
   * @param entry Entry to add.
   */
  public void addEntry(BarterEntry entry)
  {
    _entries.add(entry);
  }

  /**
   * Get the barter entries.
   * @return a list of barter entries.
   */
  public List<BarterEntry> getEntries()
  {
    return _entries;
  }
}
