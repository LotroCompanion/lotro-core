package delta.games.lotro.character.status.travels.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.status.travels.AnchorStatus;
import delta.games.lotro.character.status.travels.AnchorsStatusManager;
import delta.games.lotro.common.enums.TravelLink;
import delta.games.lotro.common.geo.ExtendedPosition;
import delta.games.lotro.common.geo.Position;
import delta.games.lotro.lore.maps.Zone;

/**
 * Writes a anchors status to an XML file.
 * @author DAM
 */
public class AnchorsStatusXMLWriter
{
  /**
   * Write a status to an XML file.
   * @param outFile Output file.
   * @param status Status to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final AnchorsStatusManager status, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeStatus(hd,status);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  /**
   * Write a status to the given XML stream.
   * @param hd XML output stream.
   * @param statusMgr Status to write.
   * @throws Exception If an error occurs.
   */
  private void writeStatus(TransformerHandler hd, AnchorsStatusManager statusMgr) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    hd.startElement("","",AnchorsStatusXMLConstants.ANCHORS_STATUS_TAG,attrs);

    List<AnchorStatus> anchorStatuses=statusMgr.getAll();
    for(AnchorStatus anchorStatus : anchorStatuses)
    {
      AttributesImpl statusAttrs=new AttributesImpl();
      // Type
      TravelLink type=anchorStatus.getType();
      if (type!=null)
      {
        statusAttrs.addAttribute("","",AnchorsStatusXMLConstants.ANCHOR_TYPE_ATTR,XmlWriter.CDATA,String.valueOf(type.getCode()));
      }
      // Name
      String name=anchorStatus.getName();
      if (name!=null)
      {
        statusAttrs.addAttribute("","",AnchorsStatusXMLConstants.ANCHOR_NAME_ATTR,XmlWriter.CDATA,name);
      }
      // Position
      ExtendedPosition extendedPosition=anchorStatus.getPosition();
      if (extendedPosition!=null)
      {
        AttributesImpl positionAttrs=new AttributesImpl();
        Position position=extendedPosition.getPosition();
        if (position!=null)
        {
          // Region
          int region=position.getRegion();
          positionAttrs.addAttribute("","",AnchorsStatusXMLConstants.POSITION_REGION_ATTR,XmlWriter.CDATA,String.valueOf(region));
          // Longitude
          float longitude=position.getLongitude();
          positionAttrs.addAttribute("","",AnchorsStatusXMLConstants.POSITION_LONGITUDE_ATTR,XmlWriter.CDATA,String.valueOf(longitude));
          // Latitude
          float latitude=position.getLatitude();
          positionAttrs.addAttribute("","",AnchorsStatusXMLConstants.POSITION_LATITUDE_ATTR,XmlWriter.CDATA,String.valueOf(latitude));
        }
        // Zone ID
        Zone zone=extendedPosition.getZone();
        if (zone!=null)
        {
          int zoneID=zone.getIdentifier();
          positionAttrs.addAttribute("","",AnchorsStatusXMLConstants.POSITION_ZONE_ID_ATTR,XmlWriter.CDATA,String.valueOf(zoneID));
        }
        hd.startElement("","",AnchorsStatusXMLConstants.POSITION,positionAttrs);
        hd.endElement("","",AnchorsStatusXMLConstants.POSITION);
      }
      hd.startElement("","",AnchorsStatusXMLConstants.ANCHOR,statusAttrs);
      hd.endElement("","",AnchorsStatusXMLConstants.ANCHOR);
    }
    hd.endElement("","",AnchorsStatusXMLConstants.ANCHORS_STATUS_TAG);
  }
}
