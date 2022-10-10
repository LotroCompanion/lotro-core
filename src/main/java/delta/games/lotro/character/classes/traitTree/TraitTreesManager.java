package delta.games.lotro.character.classes.traitTree;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import delta.games.lotro.character.classes.traitTree.io.xml.TraitTreeXMLParser;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;

/**
 * Facade for access to trait trees.
 * @author DAM
 */
public class TraitTreesManager
{
  private static final Logger LOGGER=Logger.getLogger(TraitTreesManager.class);

  private static TraitTreesManager _instance=null;

  private HashMap<Integer,TraitTree> _cache;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static TraitTreesManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new TraitTreesManager();
      _instance.loadAll();
    }
    return _instance;
  }

  /**
   * Constructor.
   */
  private TraitTreesManager()
  {
    _cache=new HashMap<Integer,TraitTree>(10);
  }

  /**
   * Load all.
   */
  private void loadAll()
  {
    _cache.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File traitTreesFile=cfg.getFile(DataFiles.TRAIT_TREES);
    long now=System.currentTimeMillis();
    List<TraitTree> traitTrees=TraitTreeXMLParser.parseTraitTreesFile(traitTreesFile);
    for(TraitTree traitTree : traitTrees)
    {
      Integer id=Integer.valueOf(traitTree.getIdentifier());
      _cache.put(id,traitTree);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    LOGGER.info("Loaded "+_cache.size()+" trait trees in "+duration+"ms.");
  }

  /**
   * Get a trait tree using its identifier.
   * @param id Identifier of the trait tree to get.
   * @return A class description or <code>null</code> if not found.
   */
  public TraitTree getTraitTree(int id)
  {
    TraitTree ret=_cache.get(Integer.valueOf(id));
    return ret;
  }
}
