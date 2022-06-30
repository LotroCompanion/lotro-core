package delta.games.lotro.account.status.rewardsTrack;

import java.util.HashMap;
import java.util.Map;

import delta.games.lotro.lore.rewardsTrack.RewardsTrack;

/**
 * Manager for all rewards tracks status for a single account.
 * @author DAM
 */
public class RewardsTracksStatusManager
{
  private Map<Integer,RewardsTrackStatus> _map;

  /**
   * Constructor.
   */
  public RewardsTracksStatusManager()
  {
    _map=new HashMap<Integer,RewardsTrackStatus>();
  }

  /**
   * Get the status of a rewards track.
   * @param rewardsTrack Rewards track to use.
   * @param createIfNeeded Indicates if it shall be created/registered if it does not exist yet.
   * @return A rewards track status, or <code>null</code>.
   */
  public RewardsTrackStatus getStatus(RewardsTrack rewardsTrack, boolean createIfNeeded)
  {
    Integer key=Integer.valueOf(rewardsTrack.getIdentifier());
    RewardsTrackStatus ret=_map.get(key);
    if ((ret==null) && (createIfNeeded))
    {
      ret=new RewardsTrackStatus(rewardsTrack);
      _map.put(key,ret);
    }
    return ret;
  }
}
