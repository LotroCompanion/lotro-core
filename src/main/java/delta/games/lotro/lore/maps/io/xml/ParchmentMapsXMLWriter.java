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
import delta.games.lotro.lore.maps.ParchmentMap;

/**
 * Writes parchment maps to XML files.
 * @author DAM
 */
public class ParchmentMapsXMLWriter
{
  /**
   * Write a file with parchment maps.
   * @param toFile Output file.
   * @param data Data to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeParchmentMapsFile(File toFile, List<ParchmentMap> data)
  {
    ParchmentMapsXMLWriter writer=new ParchmentMapsXMLWriter();
    boolean ok=writer.writeParchmentMaps(toFile,data,EncodingNames.UTF_8);
    return ok;
  }

  /**
   * Write parchment maps to a XML file.
   * @param outFile Output file.
   * @param data Data to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean writeParchmentMaps(File outFile, final List<ParchmentMap> data, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeParchmentMaps(hd,data);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void writeParchmentMaps(TransformerHandler hd, List<ParchmentMap> data) throws SAXException
  {
    hd.startElement("","",ParchmentMapsXMLConstants.PARCHMENT_MAPS_TAG,new AttributesImpl());
    // Maps
    for(ParchmentMap map : data)
    {
      writeParchmentMap(hd,map);
    }
    hd.endElement("","",ParchmentMapsXMLConstants.PARCHMENT_MAPS_TAG);
  }

  private void writeParchmentMap(TransformerHandler hd, ParchmentMap map) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();

    // In-game identifier
    int id=map.getIdentifier();
    attrs.addAttribute("","",ParchmentMapsXMLConstants.ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Name
    String name=map.getName();
    attrs.addAttribute("","",ParchmentMapsXMLConstants.NAME_ATTR,XmlWriter.CDATA,name);
    // Region
    int region=map.getRegion();
    attrs.addAttribute("","",ParchmentMapsXMLConstants.PARCHMENT_MAP_REGION_ATTR,XmlWriter.CDATA,String.valueOf(region));
    // Parent map
    int parentMapId=map.getParentMapId();
    if (parentMapId!=0)
    {
      attrs.addAttribute("","",ParchmentMapsXMLConstants.PARCHMENT_PARENT_MAP_ID_ATTR,XmlWriter.CDATA,String.valueOf(parentMapId));
    }
    // Quest guide disabled
    boolean questGuideDisabled=map.isQuestGuideDisabled();
    if (questGuideDisabled)
    {
      attrs.addAttribute("","",ParchmentMapsXMLConstants.PARCHMENT_QUEST_GUIDE_DISABLED_ATTR,XmlWriter.CDATA,String.valueOf(questGuideDisabled));
    }
    hd.startElement("","",ParchmentMapsXMLConstants.PARCHMENT_MAP_TAG,attrs);
    // Areas
    for(Area area : map.getAreas())
    {
      AttributesImpl areaAttrs=new AttributesImpl();
      // ID
      int areaId=area.getIdentifier();
      areaAttrs.addAttribute("","",ParchmentMapsXMLConstants.ID_ATTR,XmlWriter.CDATA,String.valueOf(areaId));
      // Name
      String areaName=area.getName();
      areaAttrs.addAttribute("","",ParchmentMapsXMLConstants.NAME_ATTR,XmlWriter.CDATA,areaName);
      hd.startElement("","",ParchmentMapsXMLConstants.AREA_TAG,areaAttrs);
      hd.endElement("","",ParchmentMapsXMLConstants.AREA_TAG);
    }
    hd.endElement("","",ParchmentMapsXMLConstants.PARCHMENT_MAP_TAG);
  }
}
