package delta.games.lotro.lore.rewardsTrack;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.rewardsTrack.io.xml.RewardsTracksXMLParser;

/**
 * Rewards tracks manager.
 * @author DAM
 */
public class RewardsTracksManager
{
  private static final Logger LOGGER=LoggerFactory.getLogger(RewardsTracksManager.class);

  private static RewardsTracksManager _instance=null;

  private Map<Integer,RewardsTrack> _tracks;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static RewardsTracksManager getInstance()
  {
    if (_instance==null)
    {
      _instance=load();
    }
    return _instance;
  }

  /**
   * Constructor.
   */
  public RewardsTracksManager()
  {
    _tracks=new HashMap<Integer,RewardsTrack>();
  }

  /**
   * Load data from a file.
   * @return the loaded data.
   */
  private static RewardsTracksManager load()
  {
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File rewardsTracksFile=cfg.getFile(DataFiles.REWARDS_TRACKS);
    long now=System.currentTimeMillis();
    RewardsTracksManager mgr=new RewardsTracksManager();
    List<RewardsTrack> rewardsTracks=new RewardsTracksXMLParser().parseXML(rewardsTracksFile);
    for(RewardsTrack rewardsTrack : rewardsTracks)
    {
      mgr.registerRewardsTrack(rewardsTrack);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    int nbRewardsTracks=mgr.getAllRewardsTracks().size();
    LOGGER.info("Loaded "+nbRewardsTracks+" rewards tracks in "+duration+"ms.");
    return mgr;
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

  /**
   * Get rewards tracks.
   * @param monsterPlay get tracks for monster play or not.
   * @return A list of rewards tracks, sorted by identifier.
   */
  public List<RewardsTrack> getRewardsTracks(boolean monsterPlay)
  {
    List<RewardsTrack> ret=new ArrayList<RewardsTrack>();
    for(RewardsTrack track : _tracks.values())
    {
      if (track.isMonsterPlay()==monsterPlay)
      {
        ret.add(track);
      }
    }
    Collections.sort(ret,new IdentifiableComparator<RewardsTrack>());
    return ret;
  }
}
