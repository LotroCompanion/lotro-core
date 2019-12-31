package delta.games.lotro.lore.trade.barter;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.requirements.UsageRequirement;

/**
 * A barter profile.
 * @author DAM
 */
public class BarterProfile implements Identifiable
{
  // Identifier
  private int _identifier;
  // Name (optional)
  private String _name;
  // Requirements
  private UsageRequirement _requirements;
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
    _requirements=new UsageRequirement();
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
   * Get the usage requirements for this profile.
   * @return some requirements.
   */
  public UsageRequirement getRequirements()
  {
    return _requirements;
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

  /**
   * Get the maximum number of items to give for an entry of this profile.
   * @return a number of items.
   */
  public int getMaxNumberOfItemsToGive()
  {
    int max=0;
    for(BarterEntry entry : _entries)
    {
      int nbItemsToGive=entry.getNumberOfItemsToGive();
      if (nbItemsToGive>max)
      {
        max=nbItemsToGive;
      }
    }
    return max;
  }
}
