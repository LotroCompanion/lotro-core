package delta.games.lotro.lore.travels;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.travels.io.xml.TravelNPCXMLParser;
import delta.games.lotro.utils.PerfUtils;

/**
 * Facade for access to travel NPCs.
 * @author DAM
 */
public class TravelNpcsManager
{
  private static TravelNpcsManager _instance=null;

  private HashMap<Integer,TravelNpc> _cache;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static TravelNpcsManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new TravelNpcsManager(true);
    }
    return _instance;
  }

  /**
   * Private constructor.
   * @param load Indicates if the data shall be loaded or not.
   */
  private TravelNpcsManager(boolean load)
  {
    _cache=new HashMap<Integer,TravelNpc>(100);
    if (load)
    {
      loadAll();
    }
  }

  /**
   * Load all.
   */
  private void loadAll()
  {
    _cache.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File travelNPCsFile=cfg.getFile(DataFiles.TRAVEL_NPCS);
    long now=System.currentTimeMillis();
    List<TravelNpc> travelNPCs=new TravelNPCXMLParser().parseXML(travelNPCsFile);
    for(TravelNpc travelNPC : travelNPCs)
    {
      _cache.put(Integer.valueOf(travelNPC.getIdentifier()),travelNPC);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    PerfUtils.showLoadedLog(_cache.size(),"travel NPCs",duration);
  }

  /**
   * Get a list of all travel NPCs, sorted by identifier.
   * @return A list of travel NPCs.
   */
  public List<TravelNpc> getAll()
  {
    ArrayList<TravelNpc> travelNPCs=new ArrayList<TravelNpc>();
    travelNPCs.addAll(_cache.values());
    Collections.sort(travelNPCs,new IdentifiableComparator<TravelNpc>());
    return travelNPCs;
  }

  /**
   * Get a travel NPC using its identifier.
   * @param id Identifier.
   * @return A travel NPC or <code>null</code> if not found.
   */
  public TravelNpc getTravelNPC(int id)
  {
    return _cache.get(Integer.valueOf(id));
  }
}
