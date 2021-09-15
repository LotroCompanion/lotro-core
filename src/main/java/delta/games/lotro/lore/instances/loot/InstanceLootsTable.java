package delta.games.lotro.lore.instances.loot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.IdentifiableComparator;

/**
 * Storage for a collection of instance loots.
 * @author DAM
 */
public class InstanceLootsTable implements Identifiable
{
  private int _tableId;
  private Map<Integer,InstanceLoots> _instanceLoots;

  /**
   * Constructor.
   * @param tableId Table identifier.
   */
  public InstanceLootsTable(int tableId)
  {
    _tableId=tableId;
    _instanceLoots=new HashMap<Integer,InstanceLoots>();
  }

  @Override
  public int getIdentifier()
  {
    return _tableId;
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
