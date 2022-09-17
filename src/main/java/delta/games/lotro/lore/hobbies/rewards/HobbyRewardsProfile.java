package delta.games.lotro.lore.hobbies.rewards;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.Identifiable;

/**
 * Hobby rewards profile.
 * @author DAM
 */
public class HobbyRewardsProfile implements Identifiable
{
  private int _id;
  private List<HobbyRewardEntry> _entries;

  /**
   * Constructor.
   * @param id Identifier.
   */
  public HobbyRewardsProfile(int id)
  {
    _id=id;
    _entries=new ArrayList<HobbyRewardEntry>();
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
  public void addEntry(HobbyRewardEntry entry)
  {
    _entries.add(entry);
  }

  /**
   * Get the managed entries.
   * @return the managed entries.
   */
  public List<HobbyRewardEntry> getEntries()
  {
    return _entries;
  }
}
