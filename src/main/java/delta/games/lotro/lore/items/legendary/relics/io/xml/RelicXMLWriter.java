package delta.games.lotro.lore.items.legendary.relics.io.xml;

import java.io.File;
import java.util.Collections;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.base.io.xml.BasicStatsSetXMLWriter;
import delta.games.lotro.lore.items.legendary.relics.Relic;
import delta.games.lotro.lore.items.legendary.relics.RelicComparator;
import delta.games.lotro.lore.items.legendary.relics.RelicType;
import delta.games.lotro.lore.items.legendary.relics.RelicsCategory;
import delta.games.lotro.lore.items.legendary.relics.RelicsManager;

/**
 * Writes LOTRO relics to XML files.
 * @author DAM
 */
public class RelicXMLWriter
{
  /**
   * Write items to a XML file.
   * @param outFile Output file.
   * @param relicsMgr Relics manager to use.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean writeRelics(File outFile, final RelicsManager relicsMgr, String encoding)
  {
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",RelicXMLConstants.RELICS_TAG,new AttributesImpl());
        List<String> categoryNames=relicsMgr.getCategories();
        for(String categoryName:categoryNames)
        {
          RelicsCategory category=relicsMgr.getRelicCategory(categoryName,false);
          write(hd,category);
        }
        hd.endElement("","",RelicXMLConstants.RELICS_TAG);
      }
    };
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  /**
   * Write a relics category to the given XML stream.
   * @param hd XML output stream.
   * @param category Category to write.
   * @throws Exception
   */
  private void write(TransformerHandler hd, RelicsCategory category) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();

    // Name
    String name=category.getName();
    if (name!=null)
    {
      attrs.addAttribute("","",RelicXMLConstants.CATEGORY_NAME_ATTR,XmlWriter.CDATA,name);
    }
    hd.startElement("","",RelicXMLConstants.CATEGORY_TAG,attrs);
    List<Relic> relics=category.getAllRelics();
    RelicComparator comparator=new RelicComparator();
    Collections.sort(relics,comparator);
    for(Relic relic:relics)
    {
      write(hd,relic);
    }
    hd.endElement("","",RelicXMLConstants.CATEGORY_TAG);
  }

  /**
   * Write a relic to the given XML stream.
   * @param hd XML output stream.
   * @param relic Relic to write.
   * @throws Exception
   */
  private void write(TransformerHandler hd, Relic relic) throws Exception
  {
    AttributesImpl relicAttrs=new AttributesImpl();

    // ID
    int id=relic.getIdentifier();
    if (id!=0)
    {
      relicAttrs.addAttribute("","",RelicXMLConstants.RELIC_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    }
    // Name
    String name=relic.getName();
    if (name!=null)
    {
      relicAttrs.addAttribute("","",RelicXMLConstants.RELIC_NAME_ATTR,XmlWriter.CDATA,name);
    }
    // Type
    RelicType type=relic.getType();
    if (type!=null)
    {
      relicAttrs.addAttribute("","",RelicXMLConstants.RELIC_TYPE_ATTR,XmlWriter.CDATA,type.name());
    }
    // Bridle?
    boolean isBridleRelic=relic.isBridleRelic();
    if (isBridleRelic)
    {
      relicAttrs.addAttribute("","",RelicXMLConstants.RELIC_BRIDLE_ATTR,XmlWriter.CDATA,String.valueOf(isBridleRelic));
    }
    // Item level
    Integer level=relic.getRequiredLevel();
    if (level!=null)
    {
      relicAttrs.addAttribute("","",RelicXMLConstants.RELIC_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(level.intValue()));
    }
    // Icon filename
    String icon=relic.getIconFilename();
    if (icon!=null)
    {
      relicAttrs.addAttribute("","",RelicXMLConstants.RELIC_ICON_FILENAME_ATTR,XmlWriter.CDATA,icon);
    }
    hd.startElement("","",RelicXMLConstants.RELIC_TAG,relicAttrs);
    // Stats
    BasicStatsSet stats=relic.getStats();
    BasicStatsSetXMLWriter.write(hd,RelicXMLConstants.STATS_TAG,stats);
    hd.endElement("","",RelicXMLConstants.RELIC_TAG);
  }
}
