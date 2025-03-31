package delta.games.lotro.character.status.travels.io.xml;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.status.travels.AnchorStatus;
import delta.games.lotro.character.status.travels.AnchorsStatusManager;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.common.enums.TravelLink;
import delta.games.lotro.common.geo.ExtendedPosition;
import delta.games.lotro.common.geo.io.xml.PositionXMLConstants;
import delta.games.lotro.common.geo.io.xml.PositionXMLParser;

/**
 * Parser for the anchors status stored in XML.
 * @author DAM
 */
public class AnchorsStatusXMLParser
{
  private static final Logger LOGGER=LoggerFactory.getLogger(AnchorsStatusXMLParser.class);

  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed status or <code>null</code>.
   */
  public AnchorsStatusManager parseXML(File source)
  {
    AnchorsStatusManager status=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      status=parseStatus(root);
    }
    return status;
  }

  private AnchorsStatusManager parseStatus(Element root)
  {
    AnchorsStatusManager status=new AnchorsStatusManager();
    // Status of anchors
    List<Element> statusTags=DOMParsingTools.getChildTagsByName(root,AnchorsStatusXMLConstants.ANCHOR,false);
    for(Element statusTag : statusTags)
    {
      parseAnchorStatus(status,statusTag);
    }
    return status;
  }

  private void parseAnchorStatus(AnchorsStatusManager status, Element statusTag)
  {
    NamedNodeMap attrs=statusTag.getAttributes();
    int typeCode=DOMParsingTools.getIntAttribute(attrs,AnchorsStatusXMLConstants.ANCHOR_TYPE_ATTR,0);
    LotroEnum<TravelLink> travelLinkEnum=LotroEnumsRegistry.getInstance().get(TravelLink.class);
    TravelLink type=travelLinkEnum.getEntry(typeCode);
    if (type==null)
    {
      // Unknown type!
      LOGGER.warn("Unknown type: {}",Integer.valueOf(typeCode));
      return;
    }
    AnchorStatus newStatus=status.get(type,true);
    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,AnchorsStatusXMLConstants.ANCHOR_NAME_ATTR,null);
    newStatus.setName(name);
    // Position
    Element positionTag=DOMParsingTools.getChildTagByName(statusTag,PositionXMLConstants.POSITION);
    ExtendedPosition position=PositionXMLParser.parsePosition(positionTag);
    newStatus.setPosition(position);
  }
}
