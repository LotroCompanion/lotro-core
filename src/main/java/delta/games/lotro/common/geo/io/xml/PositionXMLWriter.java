package delta.games.lotro.common.geo.io.xml;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.common.geo.ExtendedPosition;
import delta.games.lotro.common.geo.Position;

/**
 * Writes position data to XML documents.
 * @author DAM
 */
public class PositionXMLWriter
{
  /**
   * Write a position in an XML document.
   * @param hd Output stream.
   * @param extendedPosition Position to write.
   * @throws SAXException If an error occurs.
   */
  public static void writePosition(TransformerHandler hd, ExtendedPosition extendedPosition) throws SAXException
  {
    if (extendedPosition==null)
    {
      return;
    }
    AttributesImpl positionAttrs=new AttributesImpl();
    Position position=extendedPosition.getPosition();
    if (position!=null)
    {
      // Region
      int region=position.getRegion();
      positionAttrs.addAttribute("","",PositionXMLConstants.POSITION_REGION_ATTR,XmlWriter.CDATA,String.valueOf(region));
      // Longitude
      float longitude=position.getLongitude();
      positionAttrs.addAttribute("","",PositionXMLConstants.POSITION_LONGITUDE_ATTR,XmlWriter.CDATA,String.valueOf(longitude));
      // Latitude
      float latitude=position.getLatitude();
      positionAttrs.addAttribute("","",PositionXMLConstants.POSITION_LATITUDE_ATTR,XmlWriter.CDATA,String.valueOf(latitude));
    }
    // Zone ID
    Integer zoneID=extendedPosition.getZoneID();
    if (zoneID!=null)
    {
      positionAttrs.addAttribute("","",PositionXMLConstants.POSITION_ZONE_ID_ATTR,XmlWriter.CDATA,zoneID.toString());
    }
    hd.startElement("","",PositionXMLConstants.POSITION,positionAttrs);
    hd.endElement("","",PositionXMLConstants.POSITION);
  }
}
