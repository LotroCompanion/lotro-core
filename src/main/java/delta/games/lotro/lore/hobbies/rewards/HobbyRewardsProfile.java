package delta.games.lotro.lore.hobbies.rewards;

import java.util.ArrayList;
import java.util.List;

/**
 * Hobby rewards profile.
 * @author DAM
 */
public class HobbyRewardsProfile
{
  private List<HobbyRewardEntry> _entries;

  /**
   * Constructor.
   */
  public HobbyRewardsProfile()
  {
    _entries=new ArrayList<HobbyRewardEntry>();
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
