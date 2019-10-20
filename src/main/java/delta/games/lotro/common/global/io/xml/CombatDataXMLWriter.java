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
    RatingsMgr ratingsMgr=data.getRatingsMgr();
    hd.startElement("","",CombatDataXMLConstants.COMBAT_TAG,new AttributesImpl());
    for(RatingCurveId curveId : RatingCurveId.values())
    {
      RatingCurve curve=ratingsMgr.getCurve(curveId);
      AttributesImpl attrs=new AttributesImpl();
      // - id
      attrs.addAttribute("","",CombatDataXMLConstants.CURVE_ID_ATTR,XmlWriter.CDATA,curveId.name());

      ProgressionRatingCurveImpl progCurve=(ProgressionRatingCurveImpl)curve;
      // - hard cap
      int hardCapId=progCurve.getHardCapProgression().getIdentifier();
      attrs.addAttribute("","",CombatDataXMLConstants.HARD_CAP_ID_ATTR,XmlWriter.CDATA,String.valueOf(hardCapId));
      // - rating
      int ratingId=progCurve.getRatingProgression().getIdentifier();
      attrs.addAttribute("","",CombatDataXMLConstants.RATING_ID_ATTR,XmlWriter.CDATA,String.valueOf(ratingId));
      // - target cap
      int targetCapId=progCurve.getTargetCapProgression().getIdentifier();
      attrs.addAttribute("","",CombatDataXMLConstants.TARGET_CAP_ID_ATTR,XmlWriter.CDATA,String.valueOf(targetCapId));

      hd.startElement("","",CombatDataXMLConstants.CURVE_TAG,attrs);
      hd.endElement("","",CombatDataXMLConstants.CURVE_TAG);
    }
    hd.endElement("","",CombatDataXMLConstants.COMBAT_TAG);
  }
}
