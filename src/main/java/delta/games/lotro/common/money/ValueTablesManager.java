package delta.games.lotro.common.money;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import delta.games.lotro.common.money.io.xml.ValueTablesXMLParser;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;

/**
 * Facade for value tables access.
 * @author DAM
 */
public class ValueTablesManager
{
  private static final Logger LOGGER=Logger.getLogger(ValueTablesManager.class);

  private static ValueTablesManager _instance=null;

  private HashMap<Integer,QualityBasedValueLookupTable> _cache;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static ValueTablesManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new ValueTablesManager();
    }
    return _instance;
  }

  /**
   * Private constructor.
   */
  private ValueTablesManager()
  {
    _cache=new HashMap<Integer,QualityBasedValueLookupTable>(100);
    loadAll();
  }

  /**
   * Load all tables.
   */
  private void loadAll()
  {
    _cache.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File valueTablesFile=cfg.getFile(DataFiles.VALUE_TABLES);
    long now=System.currentTimeMillis();
    List<QualityBasedValueLookupTable> valueTables=new ValueTablesXMLParser().parseXML(valueTablesFile);
    for(QualityBasedValueLookupTable valueTable : valueTables)
    {
      _cache.put(Integer.valueOf(valueTable.getIdentifier()),valueTable);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    LOGGER.info("Loaded "+_cache.size()+" tables in "+duration+"ms.");
  }

  /**
   * Get a value table using its identifier.
   * @param id Table identifier.
   * @return A table or <code>null</code> if not found.
   */
  public QualityBasedValueLookupTable getValueTable(int id)
  {
    QualityBasedValueLookupTable ret=null;
    ret=_cache.get(Integer.valueOf(id));
    return ret;
  }
}
