package delta.games.lotro.lore.items.legendary.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.common.stats.io.xml.StatsProviderXMLWriter;
import delta.games.lotro.lore.items.legendary.LegacyType;
import delta.games.lotro.lore.items.legendary.imbued.ImbuedLegacy;

/**
 * Writes legacies to XML files.
 * @author DAM
 */
public class LegacyXMLWriter
{
  /**
   * Write some legacies to a XML file.
   * @param toFile File to write to.
   * @param legacies Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean write(File toFile, final List<ImbuedLegacy> legacies)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",LegacyXMLConstants.LEGACIES_TAG,new AttributesImpl());
        for(ImbuedLegacy legacy : legacies)
        {
          writeLegacy(hd,legacy);
        }
        hd.endElement("","",LegacyXMLConstants.LEGACIES_TAG);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private static void writeLegacy(TransformerHandler hd, ImbuedLegacy legacy) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Identifier
    int id=legacy.getIdentifier();
    attrs.addAttribute("","",LegacyXMLConstants.LEGACY_IDENTIFIER_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Type
    LegacyType type=legacy.getType();
    if (type!=null)
    {
      attrs.addAttribute("","",LegacyXMLConstants.LEGACY_TYPE_ATTR,XmlWriter.CDATA,type.name());
    }
    // Maximum initial level
    int maxInitialLevel=legacy.getMaxInitialLevel();
    attrs.addAttribute("","",LegacyXMLConstants.LEGACY_MAX_INITIAL_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(maxInitialLevel));
    // Maximum level
    int maxLevel=legacy.getMaxLevel();
    attrs.addAttribute("","",LegacyXMLConstants.LEGACY_MAX_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(maxLevel));

    hd.startElement("","",LegacyXMLConstants.LEGACY_TAG,attrs);
    // Stats
    StatsProvider statsProvider=legacy.getStatsProvider();
    if (statsProvider!=null)
    {
      StatsProviderXMLWriter.writeXml(hd,null,statsProvider,null);
    }
    hd.endElement("","",LegacyXMLConstants.LEGACY_TAG);
  }
}
