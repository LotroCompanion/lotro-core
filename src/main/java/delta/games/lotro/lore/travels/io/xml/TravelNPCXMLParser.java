package delta.games.lotro.lore.travels.io.xml;

import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.geo.ExtendedPosition;
import delta.games.lotro.common.geo.io.xml.PositionXMLConstants;
import delta.games.lotro.common.geo.io.xml.PositionXMLParser;
import delta.games.lotro.lore.agents.npcs.NPCsManager;
import delta.games.lotro.lore.agents.npcs.NpcDescription;
import delta.games.lotro.lore.travels.TravelNode;
import delta.games.lotro.lore.travels.TravelNpc;
import delta.games.lotro.lore.travels.TravelsManager;

/**
 * Parser for travel NPCs stored in XML.
 * @author DAM
 */
public class TravelNPCXMLParser
{
  private static final Logger LOGGER=LoggerFactory.getLogger(TravelNPCXMLParser.class);

  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed data.
   */
  public List<TravelNpc> parseXML(File source)
  {
    List<TravelNpc> ret=new ArrayList<TravelNpc>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> travelNPCTags=DOMParsingTools.getChildTagsByName(root,TravelNPCXMLConstants.TRAVEL_NPC_TAG);
      for(Element travelNPCTag : travelNPCTags)
      {
        TravelNpc travelNPC=parseTravelNPC(travelNPCTag);
        if (travelNPC!=null)
        {
          ret.add(travelNPC);
        }
      }
    }
    return ret;
  }

  private TravelNpc parseTravelNPC(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    // NPC
    int id=DOMParsingTools.getIntAttribute(attrs,TravelNPCXMLConstants.TRAVEL_NPC_ID,0);
    NpcDescription npc=NPCsManager.getInstance().getNPCById(id);
    if (npc==null)
    {
      LOGGER.warn("NPC not found: ID={}",Integer.valueOf(id));
      return null;
    }
    TravelNpc ret=new TravelNpc(npc);
    // Node
    int nodeID=DOMParsingTools.getIntAttribute(attrs,TravelNPCXMLConstants.TRAVEL_NPC_NODE_ID,0);
    TravelsManager travelsMgr=TravelsManager.getInstance();
    TravelNode node=travelsMgr.getNode(nodeID);
    ret.setNode(node);
    // Sell factor
    float sellFactor=DOMParsingTools.getFloatAttribute(attrs,TravelNPCXMLConstants.TRAVEL_NPC_SELL_FACTOR,1);
    ret.setSellFactor(sellFactor);
    // Must be discovered
    boolean mustBeDiscovered=DOMParsingTools.getBooleanAttribute(attrs,TravelNPCXMLConstants.TRAVEL_NPC_MUST_BE_DISCOVERED,true);
    ret.setMustBeDiscovered(mustBeDiscovered);
    // Position
    Element positionTag=DOMParsingTools.getChildTagByName(root,PositionXMLConstants.POSITION);
    ExtendedPosition position=PositionXMLParser.parsePosition(positionTag);
    ret.setPosition(position);
    // UI Position
    Element uiPositionTag=DOMParsingTools.getChildTagByName(root,TravelNPCXMLConstants.UI_POSITION_TAG);
    if (uiPositionTag!=null)
    {
      NamedNodeMap positionAttrs=uiPositionTag.getAttributes();
      int x=DOMParsingTools.getIntAttribute(positionAttrs,TravelNPCXMLConstants.X_ATTR,0);
      int y=DOMParsingTools.getIntAttribute(positionAttrs,TravelNPCXMLConstants.Y_ATTR,0);
      ret.setUIPosition(new Dimension(x,y));
    }
    // Discounts
    List<Element> discountTags=DOMParsingTools.getChildTagsByName(root,TravelNPCXMLConstants.DISCOUNT_TAG);
    for(Element discountTag : discountTags)
    {
      int discountId=DOMParsingTools.getIntAttribute(discountTag.getAttributes(),TravelNPCXMLConstants.DISCOUNT_ID_ATTR,0);
      if (discountId!=0)
      {
        ret.addDiscount(discountId);
      }
    }
    return ret;
  }
}
