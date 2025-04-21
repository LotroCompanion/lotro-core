package delta.games.lotro.lore.collections.birds;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.collections.birds.io.xml.BirdsXMLParser;
import delta.games.lotro.utils.PerfUtils;

/**
 * Facade for birds access.
 * @author DAM
 */
public class BirdsManager
{
  private static BirdsManager _instance=null;

  private HashMap<Integer,BirdDescription> _cache;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static BirdsManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new BirdsManager();
    }
    return _instance;
  }

  /**
   * Private constructor.
   */
  private BirdsManager()
  {
    _cache=new HashMap<Integer,BirdDescription>(100);
    loadAll();
  }

  /**
   * Load all birds.
   */
  private void loadAll()
  {
    _cache.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File baublesFile=cfg.getFile(DataFiles.BIRDS);
    long now=System.currentTimeMillis();
    List<BirdDescription> birds=new BirdsXMLParser().parseBirdsFile(baublesFile);
    for(BirdDescription bird : birds)
    {
      _cache.put(Integer.valueOf(bird.getIdentifier()),bird);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    PerfUtils.showLoadedLog(_cache.size(),"birds",duration);
  }

  /**
   * Get a bird using its identifier.
   * @param birdID Bird identifier.
   * @return A bird or <code>null</code> if not found.
   */
  public BirdDescription getBird(int birdID)
  {
    return _cache.get(Integer.valueOf(birdID));
  }

  /**
   * Get a bird using its type code.
   * @param typeCode Type code.
   * @return A bird or <code>null</code> if not found.
   */
  public BirdDescription getByTypeCode(int typeCode)
  {
    for(BirdDescription bird : _cache.values())
    {
      if (bird.getTypeCode()==typeCode)
      {
        return bird;
      }
    }
    return null;
  }

  /**
   * Get a list of all birds, sorted by identifier.
   * @return A list of birds.
   */
  public List<BirdDescription> getAll()
  {
    ArrayList<BirdDescription> ret=new ArrayList<BirdDescription>();
    ret.addAll(_cache.values());
    Collections.sort(ret,new IdentifiableComparator<BirdDescription>());
    return ret;
  }
}
