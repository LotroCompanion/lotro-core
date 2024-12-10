package delta.games.lotro.character.classes.traitTree;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import delta.games.lotro.character.classes.traitTree.io.xml.TraitTreeXMLParser;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.utils.PerfUtils;

/**
 * Facade for access to trait trees.
 * @author DAM
 */
public class TraitTreesManager
{
  private static TraitTreesManager _instance=null;

  private HashMap<Integer,TraitTree> _cacheByID;
  private HashMap<Integer,TraitTree> _cacheByCode;

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
    _cacheByID=new HashMap<Integer,TraitTree>(10);
    _cacheByCode=new HashMap<Integer,TraitTree>(10);
  }

  /**
   * Load all.
   */
  private void loadAll()
  {
    _cacheByID.clear();
    _cacheByCode.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File traitTreesFile=cfg.getFile(DataFiles.TRAIT_TREES);
    long now=System.currentTimeMillis();
    List<TraitTree> traitTrees=TraitTreeXMLParser.parseTraitTreesFile(traitTreesFile);
    for(TraitTree traitTree : traitTrees)
    {
      registerTraitTree(traitTree);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    PerfUtils.showLoadedLog(_cacheByID.size(),"trait trees",duration);
  }

  private void registerTraitTree(TraitTree traitTree)
  {
    // ID
    Integer id=Integer.valueOf(traitTree.getIdentifier());
    _cacheByID.put(id,traitTree);
    // Code
    Integer code=Integer.valueOf(traitTree.getType().getCode());
    _cacheByCode.put(code,traitTree);
  }

  /**
   * Get a trait tree using its identifier.
   * @param id Identifier of the trait tree to get.
   * @return A class description or <code>null</code> if not found.
   */
  public TraitTree getTraitTree(int id)
  {
    TraitTree ret=_cacheByID.get(Integer.valueOf(id));
    return ret;
  }

  /**
   * Get a trait tree using its enum code.
   * @param code Code of the trait tree to get.
   * @return A class description or <code>null</code> if not found.
   */
  public TraitTree getTraitTreeByCode(int code)
  {
    TraitTree ret=_cacheByCode.get(Integer.valueOf(code));
    return ret;
  }
}
