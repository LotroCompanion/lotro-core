package delta.games.lotro.lore.items.weapons.io.xml;

import java.io.File;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.lore.items.WeaponType;
import delta.games.lotro.lore.items.weapons.WeaponSpeedTables;
import delta.games.lotro.lore.items.weapons.WeaponSpeedTable;
import delta.games.lotro.lore.items.weapons.WeaponSpeedEntry;

/**
 * Writes speed tables to XML files.
 * @author DAM
 */
public class WeaponSpeedTablesXMLWriter
{
  /**
   * Write a file with speed tables.
   * @param toFile Output file.
   * @param speedTables Tables to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeSpeedTablesFile(File toFile, final WeaponSpeedTables speedTables)
  {
    XmlWriter xmlWriter=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",WeaponSpeedTablesXMLConstants.SPEED_TABLES_TAG,new AttributesImpl());
        writeSpeedTables(hd,speedTables);
        hd.endElement("","",WeaponSpeedTablesXMLConstants.SPEED_TABLES_TAG);
      }
    };
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,xmlWriter);
    return ret;
  }

  /**
   * Write speed tables to a XML file.
   * @param hd Output.
   * @param speedTables Tables to write.
   * @throws Exception if an error occurs.
   */
  private static void writeSpeedTables(TransformerHandler hd, final WeaponSpeedTables speedTables) throws Exception
  {
    for(WeaponType weaponType : speedTables.getWeaponTypes())
    {
      writeSpeedWeaponTable(hd,speedTables.getTable(weaponType));
    }
  }

  private static void writeSpeedWeaponTable(TransformerHandler hd, WeaponSpeedTable speedTable) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();

    // Type
    WeaponType type=speedTable.getWeaponType();
    attrs.addAttribute("","",WeaponSpeedTablesXMLConstants.SPEED_TABLE_TYPE_ATTR,XmlWriter.CDATA,type.getKey());
    hd.startElement("","",WeaponSpeedTablesXMLConstants.SPEED_TABLE_TAG,attrs);
    // Entries
    for(Integer code : speedTable.getSpeedCodes())
    {
      WeaponSpeedEntry entry=speedTable.getEntry(code.intValue());
      AttributesImpl entryAttrs=new AttributesImpl();
      // Code
      entryAttrs.addAttribute("","",WeaponSpeedTablesXMLConstants.ENTRY_CODE_ATTR,XmlWriter.CDATA,code.toString());
      // Duration
      float duration=entry.getBaseActionDuration();
      entryAttrs.addAttribute("","",WeaponSpeedTablesXMLConstants.ENTRY_DURATION_ATTR,XmlWriter.CDATA,String.valueOf(duration));
      // Modifier
      float modifier=entry.getBaseAnimationDurationMultiplierModifier();
      entryAttrs.addAttribute("","",WeaponSpeedTablesXMLConstants.ENTRY_MODIFIER_ATTR,XmlWriter.CDATA,String.valueOf(modifier));
      hd.startElement("","",WeaponSpeedTablesXMLConstants.ENTRY_TAG,entryAttrs);
      hd.endElement("","",WeaponSpeedTablesXMLConstants.ENTRY_TAG);
    }
  }
}
