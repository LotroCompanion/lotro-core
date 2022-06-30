package delta.games.lotro.lore.rewardsTrack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.common.IdentifiableComparator;

/**
 * @author dmorcellet
 */
public class RewardsTracksManager
{
  private Map<Integer,RewardsTrack> _tracks;

  /**
   * Constructor.
   */
  public RewardsTracksManager()
  {
    _tracks=new HashMap<Integer,RewardsTrack>();
  }

  /**
   * Register a rewards track.
   * @param rewardsTrack Rewards track to register.
   */
  public void registerRewardsTrack(RewardsTrack rewardsTrack)
  {
    Integer key=Integer.valueOf(rewardsTrack.getIdentifier());
    _tracks.put(key,rewardsTrack);
  }

  /**
   * Get a rewards track using its identifier.
   * @param rewardsTrackID Identifier of the rewards track to get.
   * @return A rewards track or <code>null</code> if not found.
   */
  public RewardsTrack getRewardsTrack(int rewardsTrackID)
  {
    return _tracks.get(Integer.valueOf(rewardsTrackID));
  }

  /**
   * Get all rewards tracks.
   * @return A list of rewards tracks, sorted by identifier.
   */
  public List<RewardsTrack> getAllRewardsTracks()
  {
    List<RewardsTrack> ret=new ArrayList<RewardsTrack>();
    ret.addAll(_tracks.values());
    Collections.sort(ret,new IdentifiableComparator<RewardsTrack>());
    return ret;
  }
}
