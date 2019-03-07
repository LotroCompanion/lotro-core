package delta.games.lotro.lore.warbands.io.xml;

import java.io.File;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.common.Size;
import delta.games.lotro.lore.warbands.WarbandDefinition;
import delta.games.lotro.lore.warbands.WarbandsRegistry;

/**
 * Writes a warbands registry to an XML file.
 * @author DAM
 */
public class WarbandsRegistryXMLWriter
{
  /**
   * Write a warbands registry to an XML file.
   * @param outFile Output file.
   * @param registry Registry to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final WarbandsRegistry registry, String encoding)
  {
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        write(hd,registry);
      }
    };
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void write(TransformerHandler hd, WarbandsRegistry registry) throws Exception
  {
    hd.startElement("","",WarbandsRegistryXMLConstants.WARBANDS_TAG,new AttributesImpl());

    WarbandDefinition[] warbands=registry.getAllWarbands();
    for(WarbandDefinition warband : warbands)
    {
      AttributesImpl attrs=new AttributesImpl();
      String name=warband.getName();
      attrs.addAttribute("","",WarbandsRegistryXMLConstants.WARBAND_NAME_ATTR,XmlWriter.CDATA,name);
      String shortName=warband.getShortName();
      if (shortName!=null)
      {
        attrs.addAttribute("","",WarbandsRegistryXMLConstants.WARBAND_SHORTNAME_ATTR,XmlWriter.CDATA,shortName);
      }
      String iconName=warband.getIconName();
      attrs.addAttribute("","",WarbandsRegistryXMLConstants.WARBAND_ICON_NAME_ATTR,XmlWriter.CDATA,iconName);
      Integer level=warband.getLevel();
      if (level!=null) attrs.addAttribute("","",WarbandsRegistryXMLConstants.WARBAND_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(level));
      Integer morale=warband.getMorale();
      if (morale!=null) attrs.addAttribute("","",WarbandsRegistryXMLConstants.WARBAND_MORALE_ATTR,XmlWriter.CDATA,String.valueOf(morale));
      String region=warband.getRegion();
      if (region!=null) attrs.addAttribute("","",WarbandsRegistryXMLConstants.WARBAND_REGION_ATTR,XmlWriter.CDATA,region);
      String description=warband.getDescription();
      if (description!=null) attrs.addAttribute("","",WarbandsRegistryXMLConstants.WARBAND_DESCRIPTION_ATTR,XmlWriter.CDATA,description);
      Size size=warband.getSize();
      if (size!=null) attrs.addAttribute("","",WarbandsRegistryXMLConstants.WARBAND_SIZE_ATTR,XmlWriter.CDATA,size.name());
      hd.startElement("","",WarbandsRegistryXMLConstants.WARBAND_TAG,attrs);
      hd.endElement("","",WarbandsRegistryXMLConstants.WARBAND_TAG);
    }
    hd.endElement("","",WarbandsRegistryXMLConstants.WARBANDS_TAG);
  }
}
