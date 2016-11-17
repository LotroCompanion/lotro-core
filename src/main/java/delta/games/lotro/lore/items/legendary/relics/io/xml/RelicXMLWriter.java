package delta.games.lotro.lore.items.legendary.relics.io.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.StreamTools;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.base.io.xml.BasicStatsSetXMLWriter;
import delta.games.lotro.lore.items.legendary.relics.Relic;
import delta.games.lotro.lore.items.legendary.relics.RelicType;
import delta.games.lotro.lore.items.legendary.relics.RelicsCategory;
import delta.games.lotro.lore.items.legendary.relics.RelicsManager;
import delta.games.lotro.utils.LotroLoggers;

/**
 * Writes LOTRO relics to XML files.
 * @author DAM
 */
public class RelicXMLWriter
{
  private static final Logger _logger=LotroLoggers.getLotroLogger();

  private static final String CDATA="CDATA";

  /**
   * Write items to a XML file.
   * @param outFile Output file.
   * @param relicsMgr Relics manager to use.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean writeRelics(File outFile, RelicsManager relicsMgr, String encoding)
  {
    boolean ret;
    FileOutputStream fos=null;
    try
    {
      File parentFile=outFile.getParentFile();
      if (!parentFile.exists())
      {
        parentFile.mkdirs();
      }
      fos=new FileOutputStream(outFile);
      SAXTransformerFactory tf=(SAXTransformerFactory)TransformerFactory.newInstance();
      TransformerHandler hd=tf.newTransformerHandler();
      Transformer serializer=hd.getTransformer();
      serializer.setOutputProperty(OutputKeys.ENCODING,encoding);
      serializer.setOutputProperty(OutputKeys.INDENT,"yes");

      StreamResult streamResult=new StreamResult(fos);
      hd.setResult(streamResult);
      hd.startDocument();
      hd.startElement("","",RelicXMLConstants.RELICS_TAG,new AttributesImpl());
      List<String> categoryNames=relicsMgr.getCategories();
      for(String categoryName:categoryNames)
      {
        RelicsCategory category=relicsMgr.getRelicCategory(categoryName,false);
        write(hd,category);
      }
      hd.endElement("","",RelicXMLConstants.RELICS_TAG);
      hd.endDocument();
      ret=true;
    }
    catch (Exception exception)
    {
      _logger.error("",exception);
      ret=false;
    }
    finally
    {
      StreamTools.close(fos);
    }
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
      attrs.addAttribute("","",RelicXMLConstants.CATEGORY_NAME_ATTR,CDATA,name);
    }
    hd.startElement("","",RelicXMLConstants.CATEGORY_TAG,attrs);
    List<Relic> relics=category.getAllRelics();
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

    // Name
    String name=relic.getName();
    if (name!=null)
    {
      relicAttrs.addAttribute("","",RelicXMLConstants.RELIC_NAME_ATTR,CDATA,name);
    }
    // Type
    RelicType type=relic.getType();
    if (type!=null)
    {
      relicAttrs.addAttribute("","",RelicXMLConstants.RELIC_TYPE_ATTR,CDATA,type.name());
    }
    // Item level
    Integer level=relic.getRequiredLevel();
    if (level!=null)
    {
      relicAttrs.addAttribute("","",RelicXMLConstants.RELIC_LEVEL_ATTR,CDATA,String.valueOf(level.intValue()));
    }
    // Icon filename
    String icon=relic.getIconFilename();
    if (icon!=null)
    {
      relicAttrs.addAttribute("","",RelicXMLConstants.RELIC_ICON_FILENAME_ATTR,CDATA,icon);
    }
    hd.startElement("","",RelicXMLConstants.RELIC_TAG,relicAttrs);
    // Stats
    BasicStatsSet stats=relic.getStats();
    BasicStatsSetXMLWriter.write(hd,RelicXMLConstants.STATS_TAG,stats);
    hd.endElement("","",RelicXMLConstants.RELIC_TAG);
  }
}
