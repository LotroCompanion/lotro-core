package delta.games.lotro.lore.pvp;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.pvp.io.xml.PVPDataXMLParser;

/**
 * Facade for ranks.
 * @author DAM
 */
public class RanksManager
{
  private static RanksManager _instance=null;

  private Map<String,RankScale> _scales;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static RanksManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new RanksManager(true);
    }
    return _instance;
  }

  /**
   * Private constructor.
   * @param load Indicates if the data shall be loaded or not.
   */
  public RanksManager(boolean load)
  {
    _scales=new HashMap<String,RankScale>();
    if (load)
    {
      LotroCoreConfig cfg=LotroCoreConfig.getInstance();
      File pvpDataFile=cfg.getFile(DataFiles.PVP);
      List<RankScale> rankScales=new PVPDataXMLParser().parseXML(pvpDataFile);
      for(RankScale rankScale : rankScales)
      {
        register(rankScale);
      }
    }
  }

  /**
   * Register a rank scale.
   * @param rankScale Rank scale to register.
   */
  public void register(RankScale rankScale)
  {
    _scales.put(rankScale.getKey(),rankScale);
  }

  /**
   * Get the known keys for scales.
   * @return A sorted list of keys.
   */
  public List<String> getKeys()
  {
    List<String> ret=new ArrayList<String>(_scales.keySet());
    Collections.sort(ret);
    return ret;
  }

  /**
   * Get a rank scale.
   * @param key Key of the rank scale to get. 
   * @return A rank scale or <code>null</code> if not found.
   */
  public RankScale getRankScale(String key)
  {
    return _scales.get(key);
  }

  /**
   * Get the rank label.
   * @param rankCode Rank code.
   * @param key Rank scale key.
   * @return A string or <code>null</code>.
   */
  public String getRankLabel(Integer rankCode, String key)
  {
    if (rankCode==null)
    {
      return null;
    }
    String rankLabel=null;
    RankScale scale=RanksManager.getInstance().getRankScale(RankScaleKeys.RENOWN);
    RankScaleEntry rank=scale.getRankByCode(rankCode.intValue());
    if (rank!=null)
    {
      rankLabel=rank.getRank().getName();
    }
    return rankLabel;
  }
}
