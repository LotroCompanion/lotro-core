package delta.games.lotro.lore.items.weapons.io.xml;

import java.io.File;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.lore.items.WeaponType;
import delta.games.lotro.lore.items.weapons.WeaponDamageManager;

/**
 * Writes weapon damage data to XML files.
 * @author DAM
 */
public class WeaponDamageXMLWriter
{
  /**
   * Write a file with weapon damage data.
   * @param toFile Output file.
   * @param mgr Data to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeWeaponDamageFile(File toFile, final WeaponDamageManager mgr)
  {
    XmlWriter xmlWriter=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",WeaponDamageXMLConstants.WEAPON_DAMAGE_TAG,new AttributesImpl());
        writeWeaponDamage(hd,mgr);
        hd.endElement("","",WeaponDamageXMLConstants.WEAPON_DAMAGE_TAG);
      }
    };
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,xmlWriter);
    return ret;
  }

  /**
   * Write weapon damage data to a XML file.
   * @param hd Output.
   * @param mgr Data to write.
   * @throws SAXException if an error occurs.
   */
  private static void writeWeaponDamage(TransformerHandler hd, final WeaponDamageManager mgr) throws SAXException
  {
    for(WeaponType weaponType : mgr.getWeaponTypes())
    {
      AttributesImpl attrs=new AttributesImpl();
      // Type
      attrs.addAttribute("","",WeaponDamageXMLConstants.WEAPON_TYPE_ATTR,XmlWriter.CDATA,weaponType.getKey());
      // Variance
      Float variance=mgr.getVariance(weaponType);
      attrs.addAttribute("","",WeaponDamageXMLConstants.DAMAGE_VARIANCE_ATTR,XmlWriter.CDATA,variance.toString());
      hd.startElement("","",WeaponDamageXMLConstants.WEAPON_TAG,attrs);
      hd.endElement("","",WeaponDamageXMLConstants.WEAPON_TAG);
    }
  }
}
