package delta.games.lotro.lore.travels;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.travels.io.xml.TravelsWebXMLParser;

/**
 * Travels manager.
 * @author DAM
 */
public class TravelsManager
{
  private static final Logger LOGGER=LoggerFactory.getLogger(TravelsManager.class);

  private static TravelsManager _instance=null;

  private Map<Integer,TravelNode> _nodes;
  private Map<Integer,TravelDestination> _destinations;
  private Map<Integer,TravelRoute> _routes;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static TravelsManager getInstance()
  {
    if (_instance==null)
    {
      File from=LotroCoreConfig.getInstance().getFile(DataFiles.TRAVELS_WEB);
      _instance=new TravelsWebXMLParser().parseXML(from);
    }
    return _instance;
  }

  /**
   * Constructor.
   */
  public TravelsManager()
  {
    _nodes=new HashMap<Integer,TravelNode>();
    _destinations=new HashMap<Integer,TravelDestination>();
    _routes=new HashMap<Integer,TravelRoute>();
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
   * Get all the known destinations.
   * @return A list of destinations, sorted by destination identifier.
   */
  public List<TravelDestination> getDestinations()
  {
    List<TravelDestination> destinations=new ArrayList<TravelDestination>(_destinations.values());
    Collections.sort(destinations,new IdentifiableComparator<TravelDestination>());
    return destinations;
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

  /**
   * Get a route using its identifier.
   * @param routeID Route identifier.
   * @return A route or <code>null</code> if not found.
   */
  public TravelRoute getRoute(int routeID)
  {
    return _routes.get(Integer.valueOf(routeID));
  }

  /**
   * Get all the known routes.
   * @return A list of routes, sorted by route identifier.
   */
  public List<TravelRoute> getRoutes()
  {
    List<TravelRoute> routes=new ArrayList<TravelRoute>(_routes.values());
    Collections.sort(routes,new IdentifiableComparator<TravelRoute>());
    return routes;
  }

  /**
   * Add a route.
   * @param route Route to add.
   */
  public void addRoute(TravelRoute route)
  {
    Integer key=Integer.valueOf(route.getIdentifier());
    TravelRoute oldRoute=_routes.put(key,route);
    if (oldRoute!=null)
    {
      LOGGER.warn("Overwriting a route!");
    }
  }
}
