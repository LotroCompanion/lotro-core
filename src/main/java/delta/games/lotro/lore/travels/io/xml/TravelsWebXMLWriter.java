package delta.games.lotro.lore.travels.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.common.money.Money;
import delta.games.lotro.common.requirements.io.xml.UsageRequirementsXMLWriter;
import delta.games.lotro.lore.travels.TravelDestination;
import delta.games.lotro.lore.travels.TravelMode;
import delta.games.lotro.lore.travels.TravelNode;
import delta.games.lotro.lore.travels.TravelRoute;
import delta.games.lotro.lore.travels.TravelRouteAction;
import delta.games.lotro.lore.travels.TravelRouteInstance;
import delta.games.lotro.lore.travels.TravelsManager;

/**
 * Write travels web data to XML files.
 * @author DAM
 */
public class TravelsWebXMLWriter
{
  /**
   * Write a file with travels web data.
   * @param toFile Output file.
   * @param travelsMgr Data to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeTravelsWebFile(File toFile, TravelsManager travelsMgr)
  {
    TravelsWebXMLWriter writer=new TravelsWebXMLWriter();
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter w=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writer.writeTravelsWeb(hd,travelsMgr);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,w);
    return ret;
  }

  private void writeTravelsWeb(TransformerHandler hd, TravelsManager travelsMgr) throws SAXException
  {
    hd.startElement("","",TravelsWebXMLConstants.TRAVELS_WEB_TAG,new AttributesImpl());
    // Destinations
    List<TravelDestination> destinations=new ArrayList<TravelDestination>();
    destinations.addAll(travelsMgr.getDestinations());
    Collections.sort(destinations,new IdentifiableComparator<TravelDestination>());
    for(TravelDestination destination : destinations)
    {
      writeDestination(hd,destination);
    }
    // Routes
    List<TravelRoute> routes=new ArrayList<TravelRoute>();
    routes.addAll(travelsMgr.getRoutes());
    Collections.sort(routes,new IdentifiableComparator<TravelRoute>());
    for(TravelRoute route : routes)
    {
      writeRoute(hd,route);
    }
    // Nodes
    List<TravelNode> nodes=new ArrayList<TravelNode>();
    nodes.addAll(travelsMgr.getNodes());
    Collections.sort(nodes,new IdentifiableComparator<TravelNode>());
    for(TravelNode node : nodes)
    {
      writeNode(hd,node);
    }
    hd.endElement("","",TravelsWebXMLConstants.TRAVELS_WEB_TAG);
  }

  private void writeDestination(TransformerHandler hd, TravelDestination destination) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();

    // In-game identifier
    int id=destination.getIdentifier();
    attrs.addAttribute("","",TravelsWebXMLConstants.DESTINATION_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Name
    String name=destination.getName();
    attrs.addAttribute("","",TravelsWebXMLConstants.DESTINATION_NAME_ATTR,XmlWriter.CDATA,name);
    // Swift
    boolean swiftTravel=destination.isSwiftTravel();
    if (swiftTravel)
    {
      attrs.addAttribute("","",TravelsWebXMLConstants.DESTINATION_SWIFT_ATTR,XmlWriter.CDATA,String.valueOf(swiftTravel));
    }
    hd.startElement("","",TravelsWebXMLConstants.DESTINATION_TAG,attrs);
    hd.endElement("","",TravelsWebXMLConstants.DESTINATION_TAG);
  }

  private void writeRoute(TransformerHandler hd, TravelRoute route) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();

    // In-game identifier
    int id=route.getIdentifier();
    attrs.addAttribute("","",TravelsWebXMLConstants.ROUTE_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Name
    String name=route.getName();
    attrs.addAttribute("","",TravelsWebXMLConstants.ROUTE_NAME_ATTR,XmlWriter.CDATA,name);
    // Destination
    TravelDestination destination=route.getDestination();
    if (destination!=null)
    {
      int destinationID=destination.getIdentifier();
      attrs.addAttribute("","",TravelsWebXMLConstants.ROUTE_DESTINATION_ID_ATTR,XmlWriter.CDATA,String.valueOf(destinationID));
    }
    // Mode
    TravelMode mode=route.getMode();
    attrs.addAttribute("","",TravelsWebXMLConstants.ROUTE_MODE_ATTR,XmlWriter.CDATA,mode.name());
    UsageRequirementsXMLWriter.write(attrs,route.getRequirements());
    hd.startElement("","",TravelsWebXMLConstants.ROUTE_TAG,attrs);
    // Route actions
    for(TravelRouteAction action : route.getRouteActions())
    {
      AttributesImpl actionAttrs=new AttributesImpl();
      // In-game identifier
      int actionID=action.getIdentifier();
      actionAttrs.addAttribute("","",TravelsWebXMLConstants.ROUTE_ACTION_ID_ATTR,XmlWriter.CDATA,String.valueOf(actionID));
      // Head name
      String headName=action.getHeadname();
      if (headName!=null)
      {
        actionAttrs.addAttribute("","",TravelsWebXMLConstants.ROUTE_ACTION_HEADNAME_ATTR,XmlWriter.CDATA,headName);
      }
      // Delay
      Float hopDelay=action.getDelay();
      if (hopDelay!=null)
      {
        actionAttrs.addAttribute("","",TravelsWebXMLConstants.ROUTE_ACTION_DELAY_ATTR,XmlWriter.CDATA,hopDelay.toString());
      }
      // Scene ID
      Integer sceneID=action.getSceneID();
      if (sceneID!=null)
      {
        actionAttrs.addAttribute("","",TravelsWebXMLConstants.ROUTE_ACTION_SCENE_ID_ATTR,XmlWriter.CDATA,sceneID.toString());
      }
      // Location
      String location=action.getLocation();
      if (location!=null)
      {
        actionAttrs.addAttribute("","",TravelsWebXMLConstants.ROUTE_ACTION_LOCATION_ATTR,XmlWriter.CDATA,location);
      }
      hd.startElement("","",TravelsWebXMLConstants.ROUTE_ACTION_TAG,actionAttrs);
      hd.endElement("","",TravelsWebXMLConstants.ROUTE_ACTION_TAG);
    }
    hd.endElement("","",TravelsWebXMLConstants.ROUTE_TAG);
  }

  private void writeNode(TransformerHandler hd, TravelNode node) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();

    // In-game identifier
    int id=node.getIdentifier();
    attrs.addAttribute("","",TravelsWebXMLConstants.NODE_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    hd.startElement("","",TravelsWebXMLConstants.NODE_TAG,attrs);
    // Locations
    for(TravelDestination location : node.getLocations())
    {
      AttributesImpl locationAttrs=new AttributesImpl();
      // ID
      int locationID=location.getIdentifier();
      locationAttrs.addAttribute("","",TravelsWebXMLConstants.LOCATION_ID_ATTR,XmlWriter.CDATA,String.valueOf(locationID));
      // Name
      String locationName=location.getName();
      locationAttrs.addAttribute("","",TravelsWebXMLConstants.LOCATION_NAME_ATTR,XmlWriter.CDATA,locationName);
      hd.startElement("","",TravelsWebXMLConstants.LOCATION_TAG,locationAttrs);
      hd.endElement("","",TravelsWebXMLConstants.LOCATION_TAG);
    }
    // Routes
    for(TravelRouteInstance routeInstance : node.getRoutes())
    {
      AttributesImpl routeAttrs=new AttributesImpl();
      TravelRoute route=routeInstance.getRoute();
      // ID
      int routeID=route.getIdentifier();
      routeAttrs.addAttribute("","",TravelsWebXMLConstants.NODE_ROUTE_ID_ATTR,XmlWriter.CDATA,String.valueOf(routeID));
      // Name
      String routeName=route.getName();
      routeAttrs.addAttribute("","",TravelsWebXMLConstants.NODE_ROUTE_NAME_ATTR,XmlWriter.CDATA,routeName);
      // Cost
      Money money=routeInstance.getCost();
      int value=money.getInternalValue();
      routeAttrs.addAttribute("","",TravelsWebXMLConstants.NODE_ROUTE_COST_ATTR,XmlWriter.CDATA,String.valueOf(value));
      hd.startElement("","",TravelsWebXMLConstants.NODE_ROUTE_TAG,routeAttrs);
      hd.endElement("","",TravelsWebXMLConstants.NODE_ROUTE_TAG);
    }
    hd.endElement("","",TravelsWebXMLConstants.NODE_TAG);
  }
}
