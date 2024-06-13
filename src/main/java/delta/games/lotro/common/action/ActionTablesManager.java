package delta.games.lotro.common.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.common.action.io.xml.ActionTablesXMLParser;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;

/**
 * Facade for action tables access.
 * @author DAM
 */
public class ActionTablesManager
{
  private static final Logger LOGGER=Logger.getLogger(ActionTablesManager.class);

  private static ActionTablesManager _instance=null;

  private HashMap<Integer,ActionTable> _cache;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static ActionTablesManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new ActionTablesManager(true);
    }
    return _instance;
  }

  /**
   * Private constructor.
   * @param load Indicates if the action tables database shall be loaded or not.
   */
  private ActionTablesManager(boolean load)
  {
    _cache=new HashMap<Integer,ActionTable>(1000);
    if (load)
    {
      loadAll();
    }
  }

  /**
   * Load all tables.
   */
  private void loadAll()
  {
    _cache.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File actionTablesFile=cfg.getFile(DataFiles.ACTION_TABLES);
    long now=System.currentTimeMillis();
    List<ActionTable> actionTables=new ActionTablesXMLParser().parseXML(actionTablesFile);
    for(ActionTable actionTable : actionTables)
    {
      _cache.put(Integer.valueOf(actionTable.getIdentifier()),actionTable);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    LOGGER.info("Loaded "+_cache.size()+" action tables in "+duration+"ms.");
  }

  /**
   * Get a list of all action tables, sorted by identifier.
   * @return A list of action tables.
   */
  public List<ActionTable> getAll()
  {
    ArrayList<ActionTable> actionTables=new ArrayList<ActionTable>();
    actionTables.addAll(_cache.values());
    Collections.sort(actionTables,new IdentifiableComparator<ActionTable>());
    return actionTables;
  }

  /**
   * Get an action table using its identifier.
   * @param id Identifier.
   * @return An action table or <code>null</code> if not found.
   */
  public ActionTable getActionTable(int id)
  {
    return _cache.get(Integer.valueOf(id));
  }
}
