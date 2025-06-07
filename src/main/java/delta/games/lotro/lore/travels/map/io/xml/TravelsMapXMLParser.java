package delta.games.lotro.lore.travels.map.io.xml;

import java.awt.Dimension;
import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.lore.travels.TravelNpc;
import delta.games.lotro.lore.travels.TravelNpcsManager;
import delta.games.lotro.lore.travels.map.TravelsMap;
import delta.games.lotro.lore.travels.map.TravelsMapLabel;
import delta.games.lotro.lore.travels.map.TravelsMapNode;
import delta.games.lotro.utils.i18n.I18nFacade;
import delta.games.lotro.utils.i18n.I18nRuntimeUtils;

/**
 * Parser for the travels map stored in XML.
 * @author DAM
 */
public class TravelsMapXMLParser
{
  private static final Logger LOGGER=LoggerFactory.getLogger(TravelsMapXMLParser.class);

  private SingleLocaleLabelsManager _i18n;

  /**
   * Constructor.
   */
  public TravelsMapXMLParser()
  {
    _i18n=I18nFacade.getLabelsMgr("travelsMap");
  }

  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed data.
   */
  public TravelsMap parseXML(File source)
  {
    TravelsMap ret=new TravelsMap();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      // Labels
      List<Element> labelTags=DOMParsingTools.getChildTagsByName(root,TravelsMapXMLConstants.LABEL_TAG);
      for(Element labelTag : labelTags)
      {
        TravelsMapLabel label=parseLabel(labelTag);
        ret.addLabel(label);
      }
      // Nodes
      List<Element> nodeTags=DOMParsingTools.getChildTagsByName(root,TravelsMapXMLConstants.NODE_TAG);
      for(Element nodeTag : nodeTags)
      {
        TravelsMapNode node=parseNode(nodeTag);
        if (node!=null)
        {
          ret.addNode(node);
        }
      }
    }
    return ret;
  }

  private TravelsMapLabel parseLabel(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Text
    String text=DOMParsingTools.getStringAttribute(attrs,TravelsMapXMLConstants.LABEL_TEXT_ATTR,"");
    text=I18nRuntimeUtils.getLabel(_i18n,text);
    // Position
    Dimension uiPosition=parseUIPosition(root);
    TravelsMapLabel ret=new TravelsMapLabel(uiPosition,text);
    return ret;
  }

  private TravelsMapNode parseNode(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    // NPC ID
    int npcID=DOMParsingTools.getIntAttribute(attrs,TravelsMapXMLConstants.NODE_NPC_ID_ATTR,0);
    TravelNpcsManager mgr=TravelNpcsManager.getInstance();
    TravelNpc npc=mgr.getTravelNPC(npcID);
    if (npc==null)
    {
      LOGGER.warn("NPC not found: {}",Integer.valueOf(npcID));
      return null;
    }
    // Position
    Dimension uiPosition=parseUIPosition(root);
    // Tooltip
    String tooltip=DOMParsingTools.getStringAttribute(attrs,TravelsMapXMLConstants.NODE_TOOLTIP_ATTR,"");
    tooltip=I18nRuntimeUtils.getLabel(_i18n,tooltip);
    // Capital
    boolean capital=DOMParsingTools.getBooleanAttribute(attrs,TravelsMapXMLConstants.NODE_CAPITAL_ATTR,false);
    TravelsMapNode ret=new TravelsMapNode(npc,uiPosition,tooltip,capital);
    return ret;
  }

  private Dimension parseUIPosition(Element parentTag)
  {
    Element uiPositionTag=DOMParsingTools.getChildTagByName(parentTag,TravelsMapXMLConstants.UI_POSITION_TAG);
    if (uiPositionTag!=null)
    {
      NamedNodeMap positionAttrs=uiPositionTag.getAttributes();
      int x=DOMParsingTools.getIntAttribute(positionAttrs,TravelsMapXMLConstants.X_ATTR,0);
      int y=DOMParsingTools.getIntAttribute(positionAttrs,TravelsMapXMLConstants.Y_ATTR,0);
      return new Dimension(x,y);
    }
    return null;
  }
}
