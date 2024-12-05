package delta.games.lotro.lore.allegiances.io.xml;

import java.io.File;
import java.util.Collections;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.common.enums.AllegianceGroup;
import delta.games.lotro.lore.allegiances.AllegianceDescription;
import delta.games.lotro.lore.allegiances.AllegiancesManager;
import delta.games.lotro.lore.allegiances.Points2LevelCurve;
import delta.games.lotro.lore.allegiances.Points2LevelCurvesManager;
import delta.games.lotro.lore.deeds.DeedDescription;

/**
 * Writes LOTRO allegiances to XML files.
 * @author DAM
 */
public class AllegianceXMLWriter
{
  /**
   * Write a file with allegiances.
   * @param toFile Output file.
   * @param mgr Data to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeAllegiancesFile(File toFile, final AllegiancesManager mgr)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeAllegiances(hd,mgr);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private static void writeAllegiances(TransformerHandler hd, AllegiancesManager data) throws SAXException
  {
    hd.startElement("","",AllegianceXMLConstants.ALLEGIANCES_TAG,new AttributesImpl());
    List<AllegianceDescription> allegiances=data.getAll();
    Collections.sort(allegiances,new IdentifiableComparator<AllegianceDescription>());
    for(AllegianceDescription allegiance : allegiances)
    {
      writeAllegiance(hd,allegiance);
    }
    writeCurves(hd,data);
    hd.endElement("","",AllegianceXMLConstants.ALLEGIANCES_TAG);
  }

  private static void writeAllegiance(TransformerHandler hd, AllegianceDescription allegiance) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();

    // In-game identifier
    int id=allegiance.getIdentifier();
    if (id!=0)
    {
      attrs.addAttribute("","",AllegianceXMLConstants.ALLEGIANCE_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    }
    // Name
    String name=allegiance.getName();
    if (!name.isEmpty())
    {
      attrs.addAttribute("","",AllegianceXMLConstants.ALLEGIANCE_NAME_ATTR,XmlWriter.CDATA,name);
    }
    // Advancement progression ID
    int advancementProgressionID=allegiance.getAdvancementProgressionID();
    attrs.addAttribute("","",AllegianceXMLConstants.ALLEGIANCE_ADVANCEMENT_PROGRESSION_ID_ATTR,XmlWriter.CDATA,String.valueOf(advancementProgressionID));
    // Icon identifier
    int iconId=allegiance.getIconId();
    if (iconId!=0)
    {
      attrs.addAttribute("","",AllegianceXMLConstants.ALLEGIANCE_ICON_ATTR,XmlWriter.CDATA,String.valueOf(iconId));
    }
    // Group
    AllegianceGroup group=allegiance.getGroup();
    if (group!=null)
    {
      int groupCode=group.getCode();
      attrs.addAttribute("","",AllegianceXMLConstants.ALLEGIANCE_GROUP_ATTR,XmlWriter.CDATA,String.valueOf(groupCode));
    }
    // Min level
    Integer minLevel=allegiance.getMinLevel();
    if (minLevel!=null)
    {
      attrs.addAttribute("","",AllegianceXMLConstants.ALLEGIANCE_MIN_LEVEL_ATTR,XmlWriter.CDATA,minLevel.toString());
    }
    // Travel skill
    SkillDescription travelSkill=allegiance.getTravelSkill();
    if (travelSkill!=null)
    {
      attrs.addAttribute("","",AllegianceXMLConstants.ALLEGIANCE_TRAVEL_SKILL_ID_ATTR,XmlWriter.CDATA,String.valueOf(travelSkill.getIdentifier()));
    }
    // Description
    String description=allegiance.getDescription();
    if (!description.isEmpty())
    {
      attrs.addAttribute("","",AllegianceXMLConstants.ALLEGIANCE_DESCRIPTION_ATTR,XmlWriter.CDATA,description);
    }
    hd.startElement("","",AllegianceXMLConstants.ALLEGIANCE_TAG,attrs);
    // Deeds
    for(DeedDescription deed : allegiance.getDeeds())
    {
      AttributesImpl deedAttrs=new AttributesImpl();
      // ID
      int deedId=deed.getIdentifier();
      deedAttrs.addAttribute("","",AllegianceXMLConstants.DEED_ID_ATTR,XmlWriter.CDATA,String.valueOf(deedId));
      // Name
      String deedName=deed.getName();
      deedAttrs.addAttribute("","",AllegianceXMLConstants.DEED_NAME_ATTR,XmlWriter.CDATA,deedName);
      hd.startElement("","",AllegianceXMLConstants.DEED_TAG,deedAttrs);
      hd.endElement("","",AllegianceXMLConstants.DEED_TAG);
    }
    hd.endElement("","",AllegianceXMLConstants.ALLEGIANCE_TAG);
  }

  private static void writeCurves(TransformerHandler hd, AllegiancesManager mgr) throws SAXException
  {
    Points2LevelCurvesManager curves=mgr.getCurvesManager();
    List<Integer> ids=curves.getCurveIdentifiers();
    for(Integer id : ids)
    {
      Points2LevelCurve curve=curves.getCurve(id.intValue());
      writeCurve(hd,curve);
    }
  }

  private static void writeCurve(TransformerHandler hd, Points2LevelCurve curve) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();

    // Identifier
    int id=curve.getIdentifier();
    attrs.addAttribute("","",AllegianceXMLConstants.ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    hd.startElement("","",AllegianceXMLConstants.CURVE_TAG,attrs);
    int maxLevel=curve.getMaxLevel();
    for(int i=0;i<=maxLevel;i++)
    {
      AttributesImpl levelAttrs=new AttributesImpl();
      // Level
      levelAttrs.addAttribute("","",AllegianceXMLConstants.LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(i));
      // Points
      int points=curve.getMinPointsForLevel(i);
      levelAttrs.addAttribute("","",AllegianceXMLConstants.POINTS_ATTR,XmlWriter.CDATA,String.valueOf(points));
      hd.startElement("","",AllegianceXMLConstants.LEVEL_TAG,levelAttrs);
      hd.endElement("","",AllegianceXMLConstants.LEVEL_TAG);
    }
    hd.endElement("","",AllegianceXMLConstants.CURVE_TAG);
  }
}
