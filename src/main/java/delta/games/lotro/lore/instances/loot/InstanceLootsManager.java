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
 * Manager for all instance loot tables.
 * @author DAM
 */
public class InstanceLootsManager
{
  private static final InstanceLootsManager _instance=load();
  private Map<Integer,InstanceLoots> _instanceLoots;

  /**
   * Get the reference instance of this class.
   * @return the reference instance of this class.
   */
  public static InstanceLootsManager getInstance()
  {
    return _instance;
  }

  private static InstanceLootsManager load()
  {
    File from=LotroCoreConfig.getInstance().getFile(DataFiles.INSTANCE_LOOTS);
    return new InstanceLootXMLParser().parseXML(from);
  }

  /**
   * Constructor.
   */
  public InstanceLootsManager()
  {
    _instanceLoots=new HashMap<Integer,InstanceLoots>();
  }

  /**
   * Add an instance loots.
   * @param instanceLoots Instance loots to add.
   */
  public void addInstanceLoots(InstanceLoots instanceLoots)
  {
    Integer key=Integer.valueOf(instanceLoots.getIdentifier());
    _instanceLoots.put(key,instanceLoots);
  }

  /**
   * Get an instance loots using its identifier.
   * @param id Private encounter identifier.
   * @return A instance loots or <code>null</code> if not found.
   */
  public InstanceLoots getInstanceLootsById(int id)
  {
    return _instanceLoots.get(Integer.valueOf(id));
  }

  /**
   * Get all instance loots.
   * @return a list of instance loots.
   */
  public List<InstanceLoots> getInstanceLoots()
  {
    List<InstanceLoots> ret=new ArrayList<InstanceLoots>();
    ret.addAll(_instanceLoots.values());
    Collections.sort(ret,new IdentifiableComparator<InstanceLoots>());
    return ret;
  }
}
