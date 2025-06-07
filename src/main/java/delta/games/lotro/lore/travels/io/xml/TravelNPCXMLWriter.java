package delta.games.lotro.lore.travels.io.xml;

import java.io.File;
import java.util.Collections;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.common.geo.io.xml.PositionXMLWriter;
import delta.games.lotro.lore.agents.npcs.NpcDescription;
import delta.games.lotro.lore.travels.TravelNode;
import delta.games.lotro.lore.travels.TravelNpc;

/**
 * Writes travel NPCs to XML files.
 * @author DAM
 */
public class TravelNPCXMLWriter
{
  /**
   * Write a file with travel NPCs.
   * @param toFile Output file.
   * @param travelNPCs Data to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeTravelNPCsFile(File toFile, final List<TravelNpc> travelNPCs)
  {
    Collections.sort(travelNPCs,new IdentifiableComparator<TravelNpc>());
    XmlWriter xmlWriter=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",TravelNPCXMLConstants.TRAVEL_NPCS_TAG,new AttributesImpl());
        for(TravelNpc travelNPC : travelNPCs)
        {
          writeTravelNPC(hd,travelNPC);
        }
        hd.endElement("","",TravelNPCXMLConstants.TRAVEL_NPCS_TAG);
      }
    };
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,xmlWriter);
    return ret;
  }

  private static void writeTravelNPC(TransformerHandler hd, TravelNpc travelNPC) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();

    // Identifier
    int id=travelNPC.getIdentifier();
    attrs.addAttribute("","",TravelNPCXMLConstants.TRAVEL_NPC_ID,XmlWriter.CDATA,String.valueOf(id));
    // NPC
    NpcDescription npc=travelNPC.getNpc();
    // Name
    String name=npc.getName();
    attrs.addAttribute("","",TravelNPCXMLConstants.TRAVEL_NPC_NAME,XmlWriter.CDATA,name);
    // Node
    TravelNode node=travelNPC.getNode();
    if (node!=null)
    {
      int nodeID=node.getIdentifier();
      attrs.addAttribute("","",TravelNPCXMLConstants.TRAVEL_NPC_NODE_ID,XmlWriter.CDATA,String.valueOf(nodeID));
    }
    // Sell factor
    float sellFactor=travelNPC.getSellFactor();
    attrs.addAttribute("","",TravelNPCXMLConstants.TRAVEL_NPC_SELL_FACTOR,XmlWriter.CDATA,String.valueOf(sellFactor));
    // Must be discovered
    boolean mustBeDiscovered=travelNPC.isMustBeDiscovered();
    if (!mustBeDiscovered)
    {
      attrs.addAttribute("","",TravelNPCXMLConstants.TRAVEL_NPC_MUST_BE_DISCOVERED,XmlWriter.CDATA,String.valueOf(mustBeDiscovered));
    }
    hd.startElement("","",TravelNPCXMLConstants.TRAVEL_NPC_TAG,attrs);
    // Position
    PositionXMLWriter.writePosition(hd,travelNPC.getPosition());
    // Discounts
    for(Integer discountId : travelNPC.getDiscounts())
    {
      AttributesImpl discountAttrs=new AttributesImpl();
      discountAttrs.addAttribute("","",TravelNPCXMLConstants.DISCOUNT_ID_ATTR,XmlWriter.CDATA,discountId.toString());
      hd.startElement("","",TravelNPCXMLConstants.DISCOUNT_TAG,discountAttrs);
      hd.endElement("","",TravelNPCXMLConstants.DISCOUNT_TAG);
    }
    hd.endElement("","",TravelNPCXMLConstants.TRAVEL_NPC_TAG);
  }
}
