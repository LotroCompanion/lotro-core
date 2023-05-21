package delta.games.lotro.common.money.io.xml;

import java.io.File;
import java.util.Collections;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.common.money.QualityBasedValueLookupTable;
import delta.games.lotro.lore.items.ItemQualities;
import delta.games.lotro.lore.items.ItemQuality;

/**
 * Writes value tables to XML files.
 * @author DAM
 */
public class ValueTablesXMLWriter
{
  /**
   * Write a file with value tables.
   * @param toFile Output file.
   * @param valueTables Tables to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeValueTablesFile(File toFile, final List<QualityBasedValueLookupTable> valueTables)
  {
    Collections.sort(valueTables,new IdentifiableComparator<QualityBasedValueLookupTable>());
    XmlWriter xmlWriter=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",ValueTablesXMLConstants.VALUE_TABLES_TAG,new AttributesImpl());
        writeValueTables(hd,valueTables);
        hd.endElement("","",ValueTablesXMLConstants.VALUE_TABLES_TAG);
      }
    };
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,xmlWriter);
    return ret;
  }

  /**
   * Write value tables to a XML file.
   * @param hd Output.
   * @param valueTables Tables to write.
   * @throws Exception if an error occurs.
   */
  private static void writeValueTables(TransformerHandler hd, final List<QualityBasedValueLookupTable> valueTables) throws Exception
  {
    for(QualityBasedValueLookupTable valueTable : valueTables)
    {
      writeValueTable(hd,valueTable);
    }
  }

  private static void writeValueTable(TransformerHandler hd, QualityBasedValueLookupTable valueTable) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();

    // Identifier
    int id=valueTable.getIdentifier();
    attrs.addAttribute("","",ValueTablesXMLConstants.VALUE_TABLE_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    hd.startElement("","",ValueTablesXMLConstants.VALUE_TABLE_TAG,attrs);
    // Quality factors
    for(ItemQuality quality : ItemQualities.ALL)
    {
      Float factor=valueTable.getQualityFactor(quality);
      if (factor!=null)
      {
        AttributesImpl qualityAttrs=new AttributesImpl();
        qualityAttrs.addAttribute("","",ValueTablesXMLConstants.QUALITY_KEY_ATTR,XmlWriter.CDATA,quality.getKey());
        qualityAttrs.addAttribute("","",ValueTablesXMLConstants.QUALITY_FACTOR_ATTR,XmlWriter.CDATA,factor.toString());
        hd.startElement("","",ValueTablesXMLConstants.QUALITY_TAG,qualityAttrs);
        hd.endElement("","",ValueTablesXMLConstants.QUALITY_TAG);
      }
    }
    // Base values
    List<Integer> levels=valueTable.getLevels();
    for(Integer level : levels)
    {
      Float value=valueTable.getBaseValue(level.intValue());
      AttributesImpl baseValueAttrs=new AttributesImpl();
      baseValueAttrs.addAttribute("","",ValueTablesXMLConstants.BASE_VALUE_LEVEL_ATTR,XmlWriter.CDATA,level.toString());
      baseValueAttrs.addAttribute("","",ValueTablesXMLConstants.BASE_VALUE_VALUE_ATTR,XmlWriter.CDATA,value.toString());
      hd.startElement("","",ValueTablesXMLConstants.BASE_VALUE_TAG,baseValueAttrs);
      hd.endElement("","",ValueTablesXMLConstants.BASE_VALUE_TAG);
    }
    hd.endElement("","",ValueTablesXMLConstants.VALUE_TABLE_TAG);
  }
}
