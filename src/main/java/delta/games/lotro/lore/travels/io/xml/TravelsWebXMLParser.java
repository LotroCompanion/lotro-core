package delta.games.lotro.lore.travels.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.requirements.io.xml.UsageRequirementsXMLParser;
import delta.games.lotro.lore.travels.TravelDestination;
import delta.games.lotro.lore.travels.TravelMode;
import delta.games.lotro.lore.travels.TravelNode;
import delta.games.lotro.lore.travels.TravelRoute;
import delta.games.lotro.lore.travels.TravelRouteAction;
import delta.games.lotro.lore.travels.TravelRouteInstance;
import delta.games.lotro.lore.travels.TravelsManager;

/**
 * Parser for the travels web stored in XML.
 * @author DAM
 */
public class TravelsWebXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed data.
   */
  public TravelsManager parseXML(File source)
  {
    TravelsManager ret=new TravelsManager();
    Element root=DOMParsingTools.parse(source);
    if (root==null)
    {
      return ret;
    }
    // Destinations
    List<Element> destinationTags=DOMParsingTools.getChildTagsByName(root,TravelsWebXMLConstants.DESTINATION_TAG,false);
    for(Element destinationTag : destinationTags)
    {
      TravelDestination destination=parseDestination(destinationTag);
      ret.addDestination(destination);
    }
    // Routes
    List<Element> routeTags=DOMParsingTools.getChildTagsByName(root,TravelsWebXMLConstants.ROUTE_TAG,false);
    for(Element routeTag : routeTags)
    {
      TravelRoute route=parseRoute(routeTag,ret);
      ret.addRoute(route);
    }
    // Nodes
    List<Element> nodeTags=DOMParsingTools.getChildTagsByName(root,TravelsWebXMLConstants.NODE_TAG,false);
    for(Element nodeTag : nodeTags)
    {
      TravelNode node=parseNode(nodeTag,ret);
      ret.addNode(node);
    }
    return ret;
  }

  private TravelDestination parseDestination(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,TravelsWebXMLConstants.DESTINATION_ID_ATTR,0);
    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,TravelsWebXMLConstants.DESTINATION_NAME_ATTR,"");
    // Swift?
    boolean swift=DOMParsingTools.getBooleanAttribute(attrs,TravelsWebXMLConstants.DESTINATION_SWIFT_ATTR,false);
    TravelDestination ret=new TravelDestination(id,name,swift);
    return ret;
  }

  private TravelRoute parseRoute(Element root, TravelsManager mgr)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,TravelsWebXMLConstants.ROUTE_ID_ATTR,0);
    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,TravelsWebXMLConstants.ROUTE_NAME_ATTR,"");
    // Mode
    TravelMode mode=null;
    String modeStr=DOMParsingTools.getStringAttribute(attrs,TravelsWebXMLConstants.ROUTE_MODE_ATTR,null);
    if (modeStr!=null)
    {
      mode=TravelMode.valueOf(modeStr);
    }
    // Destination
    int destinationID=DOMParsingTools.getIntAttribute(attrs,TravelsWebXMLConstants.ROUTE_DESTINATION_ID_ATTR,0);
    TravelDestination destination=mgr.getDestination(destinationID);
    TravelRoute ret=new TravelRoute(id,name,mode,destination);
    // Actions
    List<Element> actionTags=DOMParsingTools.getChildTagsByName(root,TravelsWebXMLConstants.ROUTE_ACTION_TAG);
    for(Element actionTag : actionTags)
    {
      TravelRouteAction action=parseRouteAction(actionTag);
      ret.addRouteAction(action);
    }
    // Requirements
    UsageRequirementsXMLParser.parseRequirements(ret.getRequirements(),root);
    return ret;
  }

  private TravelRouteAction parseRouteAction(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,TravelsWebXMLConstants.ROUTE_ID_ATTR,0);
    // Head-name
    String headName=DOMParsingTools.getStringAttribute(attrs,TravelsWebXMLConstants.ROUTE_ACTION_HEADNAME_ATTR,null);
    // Delay
    Float delay=DOMParsingTools.getFloatAttribute(attrs,TravelsWebXMLConstants.ROUTE_ACTION_DELAY_ATTR,null);
    // Scene ID
    Integer sceneID=DOMParsingTools.getIntegerAttribute(attrs,TravelsWebXMLConstants.ROUTE_ACTION_SCENE_ID_ATTR,null);
    // Location
    String location=DOMParsingTools.getStringAttribute(attrs,TravelsWebXMLConstants.ROUTE_ACTION_LOCATION_ATTR,null);
    TravelRouteAction ret=new TravelRouteAction(id,headName,delay,sceneID,location);
    return ret;
  }

  private TravelNode parseNode(Element root, TravelsManager mgr)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,TravelsWebXMLConstants.NODE_ID_ATTR,0);
    TravelNode ret=new TravelNode(id);
    // Main location
    Element mainLocationTag=DOMParsingTools.getChildTagByName(root,TravelsWebXMLConstants.MAIN_LOCATION_TAG);
    if (mainLocationTag!=null)
    {
      TravelDestination mainLocation=loadLocation(mainLocationTag,mgr);
      ret.setMainLocation(mainLocation);
    }
    // Locations
    List<Element> locationTags=DOMParsingTools.getChildTagsByName(root,TravelsWebXMLConstants.LOCATION_TAG);
    for(Element locationTag : locationTags)
    {
      TravelDestination destination=loadLocation(locationTag,mgr);
      ret.addLocation(destination);
    }
    // Route instances
    List<Element> routeTags=DOMParsingTools.getChildTagsByName(root,TravelsWebXMLConstants.NODE_ROUTE_TAG);
    for(Element routeTag : routeTags)
    {
      NamedNodeMap routeAttrs=routeTag.getAttributes();
      // Route ID
      int routeID=DOMParsingTools.getIntAttribute(routeAttrs,TravelsWebXMLConstants.NODE_ROUTE_ID_ATTR,0);
      TravelRoute route=mgr.getRoute(routeID);
      // Cost
      int cost=DOMParsingTools.getIntAttribute(routeAttrs,TravelsWebXMLConstants.NODE_ROUTE_COST_ATTR,0);
      TravelRouteInstance routeInstance=new TravelRouteInstance(cost,route);
      ret.addRoute(routeInstance);
    }
    return ret;
  }

  private TravelDestination loadLocation(Element locationTag, TravelsManager mgr)
  {
    NamedNodeMap locationAttrs=locationTag.getAttributes();
    int destinationID=DOMParsingTools.getIntAttribute(locationAttrs,TravelsWebXMLConstants.LOCATION_ID_ATTR,0);
    TravelDestination destination=mgr.getDestination(destinationID);
    return destination;
  }
}
