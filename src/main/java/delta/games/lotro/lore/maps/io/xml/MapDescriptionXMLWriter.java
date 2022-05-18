package delta.games.lotro.lore.maps.io.xml;

import java.awt.geom.Point2D;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.lore.geo.GeoBoundingBox;
import delta.games.lotro.lore.maps.MapDescription;

/**
 * Writes map descriptions to XML files.
 * @author DAM
 */
public class MapDescriptionXMLWriter
{
  /**
   * Write a map description.
   * @param hd Output stream.
   * @param data Data to write.
   * @throws SAXException If an error occurs.
   */
  public static void writeMapDescription(TransformerHandler hd, MapDescription data) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Map ID
    Integer mapId=data.getMapId();
    if (mapId!=null)
    {
      attrs.addAttribute("","",MapDescriptionXMLConstants.MAP_ID_ATTR,XmlWriter.CDATA,mapId.toString());
    }
    // Region
    int region=data.getRegion();
    if (region!=0)
    {
      attrs.addAttribute("","",MapDescriptionXMLConstants.REGION_ATTR,XmlWriter.CDATA,String.valueOf(region));
    }
    // Bounding box
    GeoBoundingBox boundingBox=data.getBoundingBox();
    if (boundingBox!=null)
    {
      writeBoundingBox(attrs,boundingBox);
    }
    hd.startElement("","",MapDescriptionXMLConstants.MAP_TAG,attrs);
    hd.endElement("","",MapDescriptionXMLConstants.MAP_TAG);
  }

  private static void writeBoundingBox(AttributesImpl attrs, GeoBoundingBox data)
  {
    // Min
    Point2D.Float min=data.getMin();
    if (min!=null)
    {
      attrs.addAttribute("","",MapDescriptionXMLConstants.MIN_LON_ATTR,XmlWriter.CDATA,String.valueOf(min.getX()));
      attrs.addAttribute("","",MapDescriptionXMLConstants.MIN_LAT_ATTR,XmlWriter.CDATA,String.valueOf(min.getY()));
    }
    // Max
    Point2D.Float max=data.getMax();
    if (max!=null)
    {
      attrs.addAttribute("","",MapDescriptionXMLConstants.MAX_LON_ATTR,XmlWriter.CDATA,String.valueOf(max.getX()));
      attrs.addAttribute("","",MapDescriptionXMLConstants.MAX_LAT_ATTR,XmlWriter.CDATA,String.valueOf(max.getY()));
    }
  }
}
