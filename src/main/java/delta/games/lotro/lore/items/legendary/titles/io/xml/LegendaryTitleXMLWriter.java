package delta.games.lotro.lore.items.legendary.titles.io.xml;

import java.io.File;
import java.util.Collections;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.base.io.xml.BasicStatsSetXMLWriter;
import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.lore.items.DamageType;
import delta.games.lotro.lore.items.legendary.titles.LegendaryTitle;

/**
 * Writes legendary titles to XML files.
 * @author DAM
 */
public class LegendaryTitleXMLWriter
{
  /**
   * Write a file with legendary titles.
   * @param toFile Output file.
   * @param titles Titles to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeLegendaryTitlesFile(File toFile, List<LegendaryTitle> titles)
  {
    LegendaryTitleXMLWriter writer=new LegendaryTitleXMLWriter();
    Collections.sort(titles,new IdentifiableComparator<LegendaryTitle>());
    boolean ok=writer.writeTitles(toFile,titles,EncodingNames.UTF_8);
    return ok;
  }

  /**
   * Write legendary titles to a XML file.
   * @param outFile Output file.
   * @param titles Legendary titles to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean writeTitles(File outFile, final List<LegendaryTitle> titles, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeTitles(hd,titles);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void writeTitles(TransformerHandler hd, List<LegendaryTitle> titles) throws Exception
  {
    hd.startElement("","",LegendaryTitleXMLConstants.TITLES_TAG,new AttributesImpl());
    for(LegendaryTitle title : titles)
    {
      writeTitle(hd,title);
    }
    hd.endElement("","",LegendaryTitleXMLConstants.TITLES_TAG);
  }

  private void writeTitle(TransformerHandler hd, LegendaryTitle title) throws Exception
  {
    AttributesImpl titleAttrs=new AttributesImpl();

    // In-game identifier
    int id=title.getIdentifier();
    if (id!=0)
    {
      titleAttrs.addAttribute("","",LegendaryTitleXMLConstants.TITLE_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    }
    // Name
    String name=title.getName();
    if (name.length()>0)
    {
      titleAttrs.addAttribute("","",LegendaryTitleXMLConstants.TITLE_NAME_ATTR,XmlWriter.CDATA,name);
    }
    // Category
    String category=title.getCategory();
    if ((category!=null) && (category.length()>0))
    {
      titleAttrs.addAttribute("","",LegendaryTitleXMLConstants.TITLE_CATEGORY_ATTR,XmlWriter.CDATA,category);
    }
    // Tier
    int tier=title.getTier();
    titleAttrs.addAttribute("","",LegendaryTitleXMLConstants.TITLE_TIER_ATTR,XmlWriter.CDATA,String.valueOf(tier));
    // Damage type
    DamageType damageType=title.getDamageType();
    if (damageType!=null)
    {
      titleAttrs.addAttribute("","",LegendaryTitleXMLConstants.TITLE_DAMAGE_TYPE_ATTR,XmlWriter.CDATA,damageType.getKey());
    }
    // Slayer genus
    String slayerGenus=title.getSlayerGenusType();
    if ((slayerGenus!=null) && (slayerGenus.length()>0))
    {
      titleAttrs.addAttribute("","",LegendaryTitleXMLConstants.TITLE_SLAYER_ATTR,XmlWriter.CDATA,slayerGenus);
    }
    hd.startElement("","",LegendaryTitleXMLConstants.TITLE_TAG,titleAttrs);
    // Stats
    BasicStatsSet stats=title.getStats();
    BasicStatsSetXMLWriter.write(hd,LegendaryTitleXMLConstants.STATS_TAG,stats);
    hd.endElement("","",LegendaryTitleXMLConstants.TITLE_TAG);
  }
}
