package delta.games.lotro.lore.deeds.io.xml;

import java.io.File;
import java.util.Collections;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.Race;
import delta.games.lotro.common.rewards.io.xml.RewardsXMLWriter;
import delta.games.lotro.lore.deeds.DeedDescription;
import delta.games.lotro.lore.deeds.DeedProxy;
import delta.games.lotro.lore.deeds.DeedType;
import delta.games.lotro.lore.deeds.comparators.DeedIdComparator;
import delta.games.lotro.lore.deeds.geo.DeedGeoData;
import delta.games.lotro.lore.deeds.geo.DeedGeoPoint;

/**
 * Writes LOTRO deeds to XML files.
 * @author DAM
 */
public class DeedXMLWriter
{
  /**
   * Write a file with deeds.
   * @param toFile Output file.
   * @param deeds Deeds to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeDeedsFile(File toFile, List<DeedDescription> deeds)
  {
    DeedXMLWriter writer=new DeedXMLWriter();
    Collections.sort(deeds,new DeedIdComparator());
    boolean ok=writer.writeDeeds(toFile,deeds,EncodingNames.UTF_8);
    return ok;
  }

  /**
   * Write a deed to a XML file.
   * @param outFile Output file.
   * @param deed Deed to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final DeedDescription deed, String encoding)
  {
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeDeed(hd,deed);
      }
    };
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  /**
   * Write deeds to a XML file.
   * @param outFile Output file.
   * @param deeds Deeds to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean writeDeeds(File outFile, final List<DeedDescription> deeds, String encoding)
  {
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",DeedXMLConstants.DEEDS_TAG,new AttributesImpl());
        for(DeedDescription deed : deeds)
        {
          writeDeed(hd,deed);
        }
        hd.endElement("","",DeedXMLConstants.DEEDS_TAG);
      }
    };
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void writeDeed(TransformerHandler hd, DeedDescription deed) throws Exception
  {
    AttributesImpl deedAttrs=new AttributesImpl();

    // In-game identifier
    int id=deed.getIdentifier();
    if (id!=0)
    {
      deedAttrs.addAttribute("","",DeedXMLConstants.DEED_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    }
    // String key (from lotro-wiki)
    String key=deed.getKey();
    if (key!=null)
    {
      deedAttrs.addAttribute("","",DeedXMLConstants.DEED_KEY_ATTR,XmlWriter.CDATA,key);
    }
    // Name
    String name=deed.getName();
    if (name!=null)
    {
      deedAttrs.addAttribute("","",DeedXMLConstants.DEED_NAME_ATTR,XmlWriter.CDATA,name);
    }
    // Type
    DeedType type=deed.getType();
    if (type!=null)
    {
      deedAttrs.addAttribute("","",DeedXMLConstants.DEED_TYPE_ATTR,XmlWriter.CDATA,type.name());
    }
    // Character class
    CharacterClass characterClass=deed.getRequiredClass();
    if (characterClass!=null)
    {
      String cClass=characterClass.getKey();
      deedAttrs.addAttribute("","",DeedXMLConstants.DEED_CLASS_ATTR,XmlWriter.CDATA,cClass);
    }
    // Race
    Race race=deed.getRequiredRace();
    if (race!=null)
    {
      String cRace=race.getKey();
      deedAttrs.addAttribute("","",DeedXMLConstants.DEED_RACE_ATTR,XmlWriter.CDATA,cRace);
    }
    // Category
    String category=deed.getCategory();
    if (category!=null)
    {
      deedAttrs.addAttribute("","",DeedXMLConstants.DEED_CATEGORY_ATTR,XmlWriter.CDATA,category);
    }
    // Required level
    Integer minLevel=deed.getMinLevel();
    if (minLevel!=null)
    {
      deedAttrs.addAttribute("","",DeedXMLConstants.DEED_MIN_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(minLevel));
    }
    // Description
    String description=deed.getDescription();
    if (description.length()>0)
    {
      deedAttrs.addAttribute("","",DeedXMLConstants.DEED_DESCRIPTION_ATTR,XmlWriter.CDATA,description);
    }
    // Objectives
    String objectives=deed.getObjectives();
    if (objectives!=null)
    {
      deedAttrs.addAttribute("","",DeedXMLConstants.DEED_OBJECTIVES_ATTR,XmlWriter.CDATA,objectives);
    }
    hd.startElement("","",DeedXMLConstants.DEED_TAG,deedAttrs);
    writeDeedProxy(hd,DeedXMLConstants.PREVIOUS_TAG,deed.getPreviousDeedProxy());
    writeDeedProxy(hd,DeedXMLConstants.NEXT_TAG,deed.getNextDeedProxy());
    for(DeedProxy parentProxy : deed.getParentDeedProxies().getDeedProxies())
    {
      writeDeedProxy(hd,DeedXMLConstants.PARENT_TAG,parentProxy);
    }
    for(DeedProxy childProxy : deed.getChildDeedProxies().getDeedProxies())
    {
      writeDeedProxy(hd,DeedXMLConstants.CHILD_TAG,childProxy);
    }
    RewardsXMLWriter.write(hd,deed.getRewards());
    // Geographic data
    DeedGeoData data=deed.getGeoData();
    if (data!=null)
    {
      writeGeoData(hd,data);
    }
    hd.endElement("","",DeedXMLConstants.DEED_TAG);
  }

  private void writeDeedProxy(TransformerHandler hd, String tagName, DeedProxy proxy) throws Exception
  {
    if (proxy==null)
    {
      return;
    }
    AttributesImpl deedProxyAttrs=new AttributesImpl();

    int id=proxy.getId();
    if (id!=0)
    {
      deedProxyAttrs.addAttribute("","",DeedXMLConstants.DEED_PROXY_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    }
    String key=proxy.getKey();
    if (key!=null)
    {
      deedProxyAttrs.addAttribute("","",DeedXMLConstants.DEED_PROXY_KEY_ATTR,XmlWriter.CDATA,key);
    }
    String name=proxy.getName();
    if (name!=null)
    {
      deedProxyAttrs.addAttribute("","",DeedXMLConstants.DEED_PROXY_NAME_ATTR,XmlWriter.CDATA,name);
    }
    hd.startElement("","",tagName,deedProxyAttrs);
    hd.endElement("","",tagName);
  }

  /**
   * Write geo deed data.
   * @param hd
   * @param data
   * @throws Exception
   */
  private void writeGeoData(TransformerHandler hd, DeedGeoData data) throws Exception
  {
    AttributesImpl geoDeedAttrs=new AttributesImpl();
    int nbPoints=data.getRequiredPoints();
    geoDeedAttrs.addAttribute("","",DeedXMLConstants.GEO_REQUIRED_POINTS_ATTR,XmlWriter.CDATA,String.valueOf(nbPoints));
    hd.startElement("","",DeedXMLConstants.GEO_TAG,geoDeedAttrs);

    for(DeedGeoPoint point : data.getPoints())
    {
      AttributesImpl pointAttrs=new AttributesImpl();
      String mapKey=point.getMapKey();
      pointAttrs.addAttribute("","",DeedXMLConstants.POINT_MAP_KEY_ATTR,XmlWriter.CDATA,mapKey);
      int pointId=point.getPointId();
      pointAttrs.addAttribute("","",DeedXMLConstants.POINT_ID_ATTR,XmlWriter.CDATA,String.valueOf(pointId));
      hd.startElement("","",DeedXMLConstants.POINT_TAG,pointAttrs);
      hd.endElement("","",DeedXMLConstants.POINT_TAG);
    }
    hd.endElement("","",DeedXMLConstants.GEO_TAG);
  }
}
