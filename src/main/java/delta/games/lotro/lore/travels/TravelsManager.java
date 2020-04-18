package delta.games.lotro.lore.travels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.common.IdentifiableComparator;

/**
 * Travels manager.
 * @author DAM
 */
public class TravelsManager
{
  private Map<Integer,TravelNode> _nodes;
  private Map<Integer,TravelDestination> _destinations;

  /**
   * Constructor.
   */
  public TravelsManager()
  {
    _nodes=new HashMap<Integer,TravelNode>();
    _destinations=new HashMap<Integer,TravelDestination>();
  }

  /**
   * Get a travel destination using its identifier.
   * @param destinationId Destination identifier.
   * @return A travel destination or <code>null</code> if not found.
   */
  public TravelDestination getDestination(int destinationId)
  {
    return _destinations.get(Integer.valueOf(destinationId));
  }

  /**
   * Add a travel destination.
   * @param destination Destination to add.
   */
  public void addDestination(TravelDestination destination)
  {
    Integer key=Integer.valueOf(destination.getIdentifier());
    _destinations.put(key,destination);
  }

  /**
   * Get a travel node using its identifier.
   * @param nodeId Travel node identifier.
   * @return A travel node or <code>null</code> if not found.
   */
  public TravelNode getNode(int nodeId)
  {
    return _nodes.get(Integer.valueOf(nodeId));
  }

  /**
   * Get all the known nodes.
   * @return A list of nodes, sorted by node identifier.
   */
  public List<TravelNode> getNodes()
  {
    List<TravelNode> nodes=new ArrayList<TravelNode>(_nodes.values());
    Collections.sort(nodes,new IdentifiableComparator<TravelNode>());
    return nodes;
  }

  /**
   * Add a travel node.
   * @param node Node to add.
   */
  public void addNode(TravelNode node)
  {
    Integer key=Integer.valueOf(node.getIdentifier());
    _nodes.put(key,node);
  }
}
