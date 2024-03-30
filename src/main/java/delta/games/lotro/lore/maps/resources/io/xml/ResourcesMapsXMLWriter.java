package delta.games.lotro.lore.maps.resources.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.lore.crafting.CraftingLevel;
import delta.games.lotro.lore.crafting.Profession;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.maps.resources.ResourcesMapDescriptor;
import delta.games.lotro.utils.io.xml.SharedXMLUtils;

/**
 * Writes resources maps to XML files.
 * @author DAM
 */
public class ResourcesMapsXMLWriter
{
  /**
   * Write a file with resources maps.
   * @param toFile Output file.
   * @param data Data to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeResourcesMapsFile(File toFile, List<ResourcesMapDescriptor> data)
  {
    ResourcesMapsXMLWriter writer=new ResourcesMapsXMLWriter();
    boolean ok=writer.writeResourcesMaps(toFile,data,EncodingNames.UTF_8);
    return ok;
  }

  /**
   * Write resources maps to a XML file.
   * @param outFile Output file.
   * @param data Data to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean writeResourcesMaps(File outFile, final List<ResourcesMapDescriptor> data, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeResourcesMaps(hd,data);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void writeResourcesMaps(TransformerHandler hd, List<ResourcesMapDescriptor> data) throws SAXException
  {
    hd.startElement("","",ResourcesMapsXMLConstants.RESOURCES_MAPS_TAG,new AttributesImpl());
    // Maps
    for(ResourcesMapDescriptor map : data)
    {
      writeResourcesMap(hd,map);
    }
    hd.endElement("","",ResourcesMapsXMLConstants.RESOURCES_MAPS_TAG);
  }

  private void writeResourcesMap(TransformerHandler hd, ResourcesMapDescriptor map) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();

    CraftingLevel level=map.getLevel();
    // Profession
    Profession profession=level.getProfession();
    String professionKey=profession.getKey();
    attrs.addAttribute("","",ResourcesMapsXMLConstants.PROFESSION_KEY_ATTR,XmlWriter.CDATA,professionKey);
    // Tier
    int tier=level.getTier();
    attrs.addAttribute("","",ResourcesMapsXMLConstants.PROFESSION_TIER_ATTR,XmlWriter.CDATA,String.valueOf(tier));
    hd.startElement("","",ResourcesMapsXMLConstants.RESOURCES_MAP_TAG,attrs);
    // Items
    for(Item item : map.getItems())
    {
      SharedXMLUtils.writeItem(hd,ResourcesMapsXMLConstants.ITEM_TAG,item);
    }
    // Maps
    for(Integer mapID : map.getMapIds())
    {
      AttributesImpl mapAttrs=new AttributesImpl();
      mapAttrs.addAttribute("","",ResourcesMapsXMLConstants.MAP_ID_ATTR,XmlWriter.CDATA,mapID.toString());
      hd.startElement("","",ResourcesMapsXMLConstants.MAP_TAG,mapAttrs);
      hd.endElement("","",ResourcesMapsXMLConstants.MAP_TAG);
    }
    hd.endElement("","",ResourcesMapsXMLConstants.RESOURCES_MAP_TAG);
  }
}
