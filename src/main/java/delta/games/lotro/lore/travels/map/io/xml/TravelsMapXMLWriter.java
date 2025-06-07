package delta.games.lotro.lore.travels.map.io.xml;

import java.awt.Dimension;
import java.io.File;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.lore.travels.map.TravelsMap;
import delta.games.lotro.lore.travels.map.TravelsMapLabel;
import delta.games.lotro.lore.travels.map.TravelsMapNode;

/**
 * Writes travels map data to an XML file.
 * @author DAM
 */
public class TravelsMapXMLWriter
{
  /**
   * Write a file with travels map data.
   * @param toFile Output file.
   * @param travelsMap Data to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeTravelsMapFile(File toFile, final TravelsMap travelsMap)
  {
    XmlWriter xmlWriter=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeTravelsMap(hd,travelsMap);
      }
    };
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,xmlWriter);
    return ret;
  }

  private static void writeTravelsMap(TransformerHandler hd, TravelsMap travelsMap) throws SAXException
  {
    hd.startElement("","",TravelsMapXMLConstants.TRAVELS_MAP_TAG,new AttributesImpl());
    // Labels
    for(TravelsMapLabel label : travelsMap.getLabels())
    {
      writeLabel(hd,label);
    }
    // Nodes
    for(TravelsMapNode node : travelsMap.getNodes())
    {
      writeNode(hd,node);
    }
    hd.endElement("","",TravelsMapXMLConstants.TRAVELS_MAP_TAG);
  }

  private static void writeLabel(TransformerHandler hd, TravelsMapLabel label) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Text
    String text=label.getText();
    attrs.addAttribute("","",TravelsMapXMLConstants.LABEL_TEXT_ATTR,XmlWriter.CDATA,text);
    hd.startElement("","",TravelsMapXMLConstants.LABEL_TAG,attrs);
    // UI Position
    Dimension uiPosition=label.getUIPosition();
    writePosition(hd,uiPosition);
    hd.endElement("","",TravelsMapXMLConstants.LABEL_TAG);
  }

  private static void writeNode(TransformerHandler hd, TravelsMapNode node) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // NPC ID
    int npcID=node.getNpc().getIdentifier();
    attrs.addAttribute("","",TravelsMapXMLConstants.NODE_NPC_ID_ATTR,XmlWriter.CDATA,String.valueOf(npcID));
    // Capital
    boolean capital=node.isCapital();
    if (capital)
    {
      attrs.addAttribute("","",TravelsMapXMLConstants.NODE_CAPITAL_ATTR,XmlWriter.CDATA,String.valueOf(capital));
    }
    // Tooltip
    String tooltip=node.getTooltip();
    attrs.addAttribute("","",TravelsMapXMLConstants.NODE_TOOLTIP_ATTR,XmlWriter.CDATA,tooltip);
    hd.startElement("","",TravelsMapXMLConstants.NODE_TAG,attrs);
    // UI Position
    Dimension uiPosition=node.getUIPosition();
    writePosition(hd,uiPosition);
    hd.endElement("","",TravelsMapXMLConstants.NODE_TAG);
  }

  private static void writePosition(TransformerHandler hd, Dimension uiPosition) throws SAXException
  {
    if (uiPosition!=null)
    {
      AttributesImpl positionAttrs=new AttributesImpl();
      positionAttrs.addAttribute("","",TravelsMapXMLConstants.X_ATTR,XmlWriter.CDATA,String.valueOf(uiPosition.width));
      positionAttrs.addAttribute("","",TravelsMapXMLConstants.Y_ATTR,XmlWriter.CDATA,String.valueOf(uiPosition.height));
      hd.startElement("","",TravelsMapXMLConstants.UI_POSITION_TAG,positionAttrs);
      hd.endElement("","",TravelsMapXMLConstants.UI_POSITION_TAG);
    }
  }
}
