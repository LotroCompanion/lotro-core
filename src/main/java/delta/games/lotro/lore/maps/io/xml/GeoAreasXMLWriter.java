package delta.games.lotro.lore.maps.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.lore.maps.Area;
import delta.games.lotro.lore.maps.GeoAreasManager;
import delta.games.lotro.lore.maps.Region;
import delta.games.lotro.lore.maps.Territory;

/**
 * Writes LOTRO geographic areas to XML files.
 * @author DAM
 */
public class GeoAreasXMLWriter
{
  /**
   * Write a file with geographic areas.
   * @param toFile Output file.
   * @param data Data to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeGeoAreasFile(File toFile, GeoAreasManager data)
  {
    GeoAreasXMLWriter writer=new GeoAreasXMLWriter();
    boolean ok=writer.writeGeoAreas(toFile,data,EncodingNames.UTF_8);
    return ok;
  }

  /**
   * Write geographic areas to a XML file.
   * @param outFile Output file.
   * @param data Data to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean writeGeoAreas(File outFile, final GeoAreasManager data, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeGeoAreas(hd,data);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void writeGeoAreas(TransformerHandler hd, GeoAreasManager data) throws SAXException
  {
    hd.startElement("","",GeoAreasXMLConstants.GEO_AREAS_TAG,new AttributesImpl());
    // Regions
    List<Region> regions=data.getRegions();
    for(Region region : regions)
    {
      writeRegion(hd,region);
    }
    // Territories
    List<Territory> territories=data.getTerritories();
    for(Territory territory : territories)
    {
      writeTerritory(hd,territory);
    }
    // Areas
    List<Area> areas=data.getAreas();
    for(Area area : areas)
    {
      writeArea(hd,area);
    }
    hd.endElement("","",GeoAreasXMLConstants.GEO_AREAS_TAG);
  }

  private void writeRegion(TransformerHandler hd, Region region) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();

    // In-game identifier
    int id=region.getIdentifier();
    if (id!=0)
    {
      attrs.addAttribute("","",GeoAreasXMLConstants.ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    }
    // Code
    int code=region.getCode();
    attrs.addAttribute("","",GeoAreasXMLConstants.REGION_CODE_ATTR,XmlWriter.CDATA,String.valueOf(code));
    // Name
    String name=region.getName();
    attrs.addAttribute("","",GeoAreasXMLConstants.NAME_ATTR,XmlWriter.CDATA,name);
    hd.startElement("","",GeoAreasXMLConstants.REGION_TAG,attrs);
    hd.endElement("","",GeoAreasXMLConstants.REGION_TAG);
  }

  private void writeTerritory(TransformerHandler hd, Territory territory) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();

    // In-game identifier
    int id=territory.getIdentifier();
    attrs.addAttribute("","",GeoAreasXMLConstants.ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Name
    String name=territory.getName();
    attrs.addAttribute("","",GeoAreasXMLConstants.NAME_ATTR,XmlWriter.CDATA,name);
    // Parent region
    Region region=territory.getParentRegion();
    if (region!=null)
    {
      int regionId=region.getIdentifier();
      attrs.addAttribute("","",GeoAreasXMLConstants.PARENT_ID_ATTR,XmlWriter.CDATA,String.valueOf(regionId));
    }
    hd.startElement("","",GeoAreasXMLConstants.TERRITORY_TAG,attrs);
    hd.endElement("","",GeoAreasXMLConstants.TERRITORY_TAG);
  }

  private void writeArea(TransformerHandler hd, Area area) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();

    // In-game identifier
    int id=area.getIdentifier();
    attrs.addAttribute("","",GeoAreasXMLConstants.ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Name
    String name=area.getName();
    attrs.addAttribute("","",GeoAreasXMLConstants.NAME_ATTR,XmlWriter.CDATA,name);
    // Parent region
    Territory territory=area.getParentTerritory();
    if (territory!=null)
    {
      int territoryId=territory.getIdentifier();
      attrs.addAttribute("","",GeoAreasXMLConstants.PARENT_ID_ATTR,XmlWriter.CDATA,String.valueOf(territoryId));
    }
    // Icon identifier
    Integer iconId=area.getIconId();
    if ((iconId!=null) && (iconId.intValue()!=0))
    {
      attrs.addAttribute("","",GeoAreasXMLConstants.AREA_ICON_ID_ATTR,XmlWriter.CDATA,iconId.toString());
    }
    hd.startElement("","",GeoAreasXMLConstants.AREA_TAG,attrs);
    hd.endElement("","",GeoAreasXMLConstants.AREA_TAG);
  }
}
