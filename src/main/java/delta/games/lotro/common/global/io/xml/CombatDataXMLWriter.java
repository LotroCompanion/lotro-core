package delta.games.lotro.common.global.io.xml;

import java.io.File;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.stats.ratings.ProgressionRatingCurveImpl;
import delta.games.lotro.character.stats.ratings.RatingCurve;
import delta.games.lotro.character.stats.ratings.RatingCurveId;
import delta.games.lotro.character.stats.ratings.RatingsMgr;
import delta.games.lotro.common.global.CombatData;
import delta.games.lotro.common.global.WeaponStrikeModifiers;
import delta.games.lotro.common.global.WeaponStrikeModifiersManager;
import delta.games.lotro.lore.items.WeaponType;
import delta.games.lotro.utils.maths.Progression;

/**
 * Writes data of the combat system to XML files.
 * @author DAM
 */
public class CombatDataXMLWriter
{
  /**
   * Write data of the combat system to a XML file.
   * @param toFile File to write to.
   * @param data Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean write(File toFile, final CombatData data)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeCombatData(hd,data);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private static void writeCombatData(TransformerHandler hd, CombatData data) throws SAXException
  {
    hd.startElement("","",CombatDataXMLConstants.COMBAT_TAG,new AttributesImpl());
    writeCurves(hd,data.getRatingsMgr());
    writeWeaponStrikeModifiers(hd,data.getWeaponStrikeModifiersMgr());
    hd.endElement("","",CombatDataXMLConstants.COMBAT_TAG);
  }

  private static void writeCurves(TransformerHandler hd, RatingsMgr ratingsMgr) throws SAXException
  {
    for(RatingCurveId curveId : RatingCurveId.values())
    {
      RatingCurve curve=ratingsMgr.getCurve(curveId);
      AttributesImpl attrs=new AttributesImpl();
      // - id
      attrs.addAttribute("","",CombatDataXMLConstants.CURVE_ID_ATTR,XmlWriter.CDATA,curveId.name());

      ProgressionRatingCurveImpl progCurve=(ProgressionRatingCurveImpl)curve;
      if (progCurve!=null)
      {
        // - hard cap
        Progression hardCap=progCurve.getHardCapProgression();
        int hardCapId=hardCap.getIdentifier();
        attrs.addAttribute("","",CombatDataXMLConstants.HARD_CAP_ID_ATTR,XmlWriter.CDATA,String.valueOf(hardCapId));
        // - rating
        Progression rating=progCurve.getRatingProgression();
        int ratingId=rating.getIdentifier();
        attrs.addAttribute("","",CombatDataXMLConstants.RATING_ID_ATTR,XmlWriter.CDATA,String.valueOf(ratingId));
        // - target cap
        Progression targetCap=progCurve.getTargetCapProgression();
        int targetCapId=targetCap.getIdentifier();
        attrs.addAttribute("","",CombatDataXMLConstants.TARGET_CAP_ID_ATTR,XmlWriter.CDATA,String.valueOf(targetCapId));
      }

      hd.startElement("","",CombatDataXMLConstants.CURVE_TAG,attrs);
      hd.endElement("","",CombatDataXMLConstants.CURVE_TAG);
    }
  }

  private static void writeWeaponStrikeModifiers(TransformerHandler hd, WeaponStrikeModifiersManager mgr) throws SAXException
  {
    for(WeaponType type : mgr.getWeaponTypes())
    {
      WeaponStrikeModifiers mods=mgr.getStrikeModifiers(type);
      AttributesImpl attrs=new AttributesImpl();
      // Type
      attrs.addAttribute("","",WeaponStrikeModifiersXMLConstants.WEAPON_TYPE_ATTR,XmlWriter.CDATA,String.valueOf(type.getCode()));
      // Critical mod
      Integer criticalMod=mods.getCriticalMultiplierAddMod();
      if (criticalMod!=null)
      {
        attrs.addAttribute("","",WeaponStrikeModifiersXMLConstants.CRITICAL_MULTIPLIER_ADD_MOD_ATTR,XmlWriter.CDATA,criticalMod.toString());
      }
      // SuperCritical mod
      Integer superCriticalMod=mods.getSuperCriticalMultiplierAddMod();
      if (superCriticalMod!=null)
      {
        attrs.addAttribute("","",WeaponStrikeModifiersXMLConstants.SUPERCRITICAL_MULTIPLIER_ADD_MOD_ATTR,XmlWriter.CDATA,superCriticalMod.toString());
      }
      // Weapon damage multiplier
      Integer weaponDamageMultiplier=mods.getWeaponDamageMultiplier();
      if (weaponDamageMultiplier!=null)
      {
        attrs.addAttribute("","",WeaponStrikeModifiersXMLConstants.WEAPON_DAMAGE_MULTIPLIER_ATTR,XmlWriter.CDATA,weaponDamageMultiplier.toString());
      }
      hd.startElement("","",WeaponStrikeModifiersXMLConstants.WEAPON_STRIKE_MODIFIERS_TAG,attrs);
      hd.endElement("","",WeaponStrikeModifiersXMLConstants.WEAPON_STRIKE_MODIFIERS_TAG);
    }
  }
}
