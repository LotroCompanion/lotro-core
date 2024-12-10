package delta.games.lotro.character.classes.initialGear;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.character.classes.initialGear.io.xml.InitialGearXMLParser;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.utils.PerfUtils;

/**
 * Facade for access to initial gear for all classes.
 * @author DAM
 */
public class InitialGearManager
{
  private static InitialGearManager _instance=null;

  private Map<String,InitialGearDefinition> _gearMap;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static InitialGearManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new InitialGearManager();
      _instance.loadAll();
    }
    return _instance;
  }

  /**
   * Constructor.
   */
  private InitialGearManager()
  {
    _gearMap=new HashMap<String,InitialGearDefinition>(10);
  }

  /**
   * Load all.
   */
  private void loadAll()
  {
    _gearMap.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File initialGearFile=cfg.getFile(DataFiles.INITIAL_GEAR);
    long now=System.currentTimeMillis();
    List<InitialGearDefinition> initialGearDefinitions=InitialGearXMLParser.parseInitialGearFile(initialGearFile);
    for(InitialGearDefinition initialGearDefinition : initialGearDefinitions)
    {
      _gearMap.put(initialGearDefinition.getClassKey(),initialGearDefinition);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    PerfUtils.showLoadedLog(_gearMap.size(),"initial gear sets",duration);
  }

  /**
   * Get an initial gear definition for a class.
   * @param key Key of the class to use.
   * @return An initial gear definition or <code>null</code> if not found.
   */
  public InitialGearDefinition getByKey(String key)
  {
    InitialGearDefinition ret=_gearMap.get(key);
    return ret;
  }
}
