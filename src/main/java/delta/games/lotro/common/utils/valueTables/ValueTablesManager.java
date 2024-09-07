package delta.games.lotro.common.utils.valueTables;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.games.lotro.common.utils.valueTables.io.xml.ValueTablesXMLParser;

/**
 * Facade for value tables access.
 * @author DAM
 */
public class ValueTablesManager
{
  private static final Logger LOGGER=LoggerFactory.getLogger(ValueTablesManager.class);

  private HashMap<Integer,QualityBasedValuesTable> _cache;

  /**
   * Constructor.
   */
  public ValueTablesManager()
  {
    _cache=new HashMap<Integer,QualityBasedValuesTable>(100);
  }

  /**
   * Load all tables.
   * @param inputFile From file.
   */
  public void loadAll(File inputFile)
  {
    _cache.clear();
    long now=System.currentTimeMillis();
    List<QualityBasedValuesTable> valueTables=new ValueTablesXMLParser().parseXML(inputFile);
    for(QualityBasedValuesTable valueTable : valueTables)
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
  public QualityBasedValuesTable getValueTable(int id)
  {
    QualityBasedValuesTable ret=null;
    ret=_cache.get(Integer.valueOf(id));
    return ret;
  }
}
