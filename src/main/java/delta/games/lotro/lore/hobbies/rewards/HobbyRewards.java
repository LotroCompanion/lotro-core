package delta.games.lotro.lore.hobbies.rewards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.common.IdentifiableComparator;

/**
 * Hobby rewards facade.
 * @author DAM
 */
public class HobbyRewards
{
  /**
   * Map of territory IDs to profiles.
   * Map values may be <code>null</code> to indicate there's no reward profile for this territory.
   */
  private Map<Integer,HobbyRewardsProfile> _profilesByTerritory;
  private Map<Integer,HobbyRewardsProfile> _profilesById;

  /**
   * Constructor.
   */
  public HobbyRewards()
  {
    _profilesByTerritory=new HashMap<Integer,HobbyRewardsProfile>();
    _profilesById=new HashMap<Integer,HobbyRewardsProfile>();
  }

  /**
   * Register a new profile.
   * @param territory Territory to use.
   * @param profile Profile to use.
   */
  public void registerProfile(int territory, HobbyRewardsProfile profile)
  {
    Integer key=Integer.valueOf(territory);
    _profilesByTerritory.put(key,profile);
    if (profile!=null)
    {
      Integer profileKey=Integer.valueOf(profile.getIdentifier());
      _profilesById.put(profileKey,profile);
    }
  }

  /**
   * Get the profile for a territory.
   * @param territory Territory ID.
   * @return A profile or <code>null</code> if not found or no reward.
   */
  public HobbyRewardsProfile getProfile(int territory)
  {
    return _profilesByTerritory.get(Integer.valueOf(territory));
  }

  /**
   * Get the known territories.
   * @return A list of sorted territory IDs.
   */
  public List<Integer> getKnownTerritories()
  {
    List<Integer> ret=new ArrayList<Integer>(_profilesByTerritory.keySet());
    Collections.sort(ret);
    return ret;
  }

  /**
   * Get the known profiles.
   * @return A list of sorted territory IDs.
   */
  public List<HobbyRewardsProfile> getKnownProfiles()
  {
    List<HobbyRewardsProfile> ret=new ArrayList<HobbyRewardsProfile>(_profilesById.values());
    Collections.sort(ret,new IdentifiableComparator<HobbyRewardsProfile>());
    return ret;
  }
}
