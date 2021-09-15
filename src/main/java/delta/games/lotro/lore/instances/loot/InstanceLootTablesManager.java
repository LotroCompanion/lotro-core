package delta.games.lotro.lore.instances.loot;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.instances.loot.io.xml.InstanceLootXMLParser;

/**
 * Manager for all the instance loot tables.
 * @author DAM
 */
public class InstanceLootTablesManager
{
  private static final InstanceLootTablesManager _instance=load();
  private Map<Integer,InstanceLootsTable> _tables;

  /**
   * Get the reference instance of this class.
   * @return the reference instance of this class.
   */
  public static InstanceLootTablesManager getInstance()
  {
    return _instance;
  }

  private static InstanceLootTablesManager load()
  {
    File from=LotroCoreConfig.getInstance().getFile(DataFiles.INSTANCE_LOOTS);
    return new InstanceLootXMLParser().parseXML(from);
  }

  /**
   * Constructor.
   */
  public InstanceLootTablesManager()
  {
    _tables=new HashMap<Integer,InstanceLootsTable>();
  }

  /**
   * Add a table.
   * @param table Table to add.
   */
  public void addTable(InstanceLootsTable table)
  {
    Integer key=Integer.valueOf(table.getIdentifier());
    _tables.put(key,table);
  }

  /**
   * Get a table using its identifier.
   * @param tableId Table identifier.
   * @return A table or <code>null</code> if not found.
   */
  public InstanceLootsTable getTableById(int tableId)
  {
    return _tables.get(Integer.valueOf(tableId));
  }

  /**
   * Get all tables.
   * @return a list of tables.
   */
  public List<InstanceLootsTable> getTables()
  {
    List<InstanceLootsTable> ret=new ArrayList<InstanceLootsTable>();
    ret.addAll(_tables.values());
    Collections.sort(ret,new IdentifiableComparator<InstanceLootsTable>());
    return ret;
  }
}
