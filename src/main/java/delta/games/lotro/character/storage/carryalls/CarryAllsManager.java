package delta.games.lotro.character.storage.carryalls;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import delta.games.lotro.character.storage.carryalls.io.xml.CarryAllDefinitionXMLParser;
import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;

/**
 * Facade for carry-all access.
 * @author DAM
 */
public class CarryAllsManager
{
  private static final Logger LOGGER=Logger.getLogger(CarryAllsManager.class);

  private static CarryAllsManager _instance=null;

  private HashMap<Integer,CarryAllDefinition> _cache;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static CarryAllsManager getInstance()
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
  public CarryAllsManager()
  {
    _cache=new HashMap<Integer,CarryAllDefinition>(10);
  }

  /**
   * Load data from a file.
   * @return the loaded data.
   */
  private static CarryAllsManager load()
  {
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File carryAllsFile=cfg.getFile(DataFiles.CARRY_ALLS);
    long now=System.currentTimeMillis();
    CarryAllsManager mgr=new CarryAllDefinitionXMLParser().parseXML(carryAllsFile);
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    int nbCarryAlls=mgr.getAll().size();
    LOGGER.info("Loaded "+nbCarryAlls+" carry-alls in "+duration+"ms.");
    return mgr;
  }

  /**
   * Add a carry-all.
   * @param carryAllDescription Carry-all to add.
   */
  public void addCarryAll(CarryAllDefinition carryAllDescription)
  {
    Integer key=Integer.valueOf(carryAllDescription.getIdentifier());
    _cache.put(key,carryAllDescription);
  }

  /**
   * Get a list of all carry-alls, sorted by identifier.
   * @return A list of carry-alls.
   */
  public List<CarryAllDefinition> getAll()
  {
    ArrayList<CarryAllDefinition> carryAlls=new ArrayList<CarryAllDefinition>();
    carryAlls.addAll(_cache.values());
    Collections.sort(carryAlls,new IdentifiableComparator<CarryAllDefinition>());
    return carryAlls;
  }

  /**
   * Get a carry-all using its identifier.
   * @param id Carry-all identifier.
   * @return A carry-all description or <code>null</code> if not found.
   */
  public CarryAllDefinition getCarryAll(int id)
  {
    CarryAllDefinition ret=null;
    ret=_cache.get(Integer.valueOf(id));
    return ret;
  }
}
