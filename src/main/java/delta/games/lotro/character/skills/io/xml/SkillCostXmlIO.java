package delta.games.lotro.character.skills.io.xml;

import javax.xml.transform.sax.TransformerHandler;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.skills.SkillCostData;
import delta.games.lotro.character.skills.SkillVitalCost;
import delta.games.lotro.common.enums.VitalType;
import delta.games.lotro.common.enums.VitalTypes;
import delta.games.lotro.common.progression.ProgressionsManager;
import delta.games.lotro.common.properties.ModPropertyList;
import delta.games.lotro.common.properties.io.ModPropertyListIO;
import delta.games.lotro.utils.maths.Progression;

/**
 * XML I/O for cost data of skills.
 * @author DAM
 */
public class SkillCostXmlIO
{
  /**
   * Write skill cost data.
   * @param hd Output
   * @param data Data to write.
   * @throws SAXException If an error occurs.
   */
  public static void writeCostData(TransformerHandler hd, SkillCostData data) throws SAXException
  {
    if (data==null)
    {
      return;
    }
    writeVitalCostData(hd,data.getMoraleCost(),SkillCostXMLConstants.MORALE_COST_TAG);
    writeVitalCostData(hd,data.getMoraleCostPerSecond(),SkillCostXMLConstants.MORALE_COST_PER_SECOND_TAG);
    writeVitalCostData(hd,data.getPowerCost(),SkillCostXMLConstants.POWER_COST_TAG);
    writeVitalCostData(hd,data.getPowerCostPerSecond(),SkillCostXMLConstants.POWER_COST_PER_SECOND_TAG);
  }

  private static void writeVitalCostData(TransformerHandler hd, SkillVitalCost data, String tag) throws SAXException
  {
    if (data==null)
    {
      return;
    }
    AttributesImpl attrs=new AttributesImpl();
    // Consumes all
    boolean consumesAll=data.isConsumesAll();
    if (consumesAll)
    {
      attrs.addAttribute("","",SkillCostXMLConstants.CONSUMES_ALL_ATTR,XmlWriter.CDATA,String.valueOf(consumesAll));
    }
    // Percentage
    Float percentage=data.getPercentage();
    if (percentage!=null)
    {
      attrs.addAttribute("","",SkillCostXMLConstants.PERCENTAGE_ATTR,XmlWriter.CDATA,percentage.toString());
    }
    // Points
    Float points=data.getPoints();
    if (points!=null)
    {
      attrs.addAttribute("","",SkillCostXMLConstants.POINTS_ATTR,XmlWriter.CDATA,points.toString());
    }
    // Points progression
    Progression pointsProgression=data.getPointsProgression();
    if (pointsProgression!=null)
    {
      attrs.addAttribute("","",SkillCostXMLConstants.POINTS_PROGRESSION_ATTR,XmlWriter.CDATA,String.valueOf(pointsProgression.getIdentifier()));
    }
    // Modifiers
    ModPropertyList mods=data.getVitalMods();
    String modsStr=ModPropertyListIO.asPersistentString(mods);
    if (!modsStr.isEmpty())
    {
      attrs.addAttribute("","",SkillCostXMLConstants.MODS_ATTR,XmlWriter.CDATA,modsStr);
    }
    hd.startElement("","",tag,attrs);
    hd.endElement("","",tag);
  }

  /**
   * Read skill cost data.
   * @param skillTag Parent tag.
   * @return the loaded data or <code>null</code> if no data.
   */
  public static SkillCostData readCostData(Element skillTag)
  {
    SkillCostData ret=null;
    // Morale
    Element moraleCostTag=DOMParsingTools.getChildTagByName(skillTag,SkillCostXMLConstants.MORALE_COST_TAG);
    SkillVitalCost moraleCost=readVitalCostData(moraleCostTag,VitalTypes.MORALE);
    // Morale per second
    Element moraleCostPerSecondTag=DOMParsingTools.getChildTagByName(skillTag,SkillCostXMLConstants.MORALE_COST_PER_SECOND_TAG);
    SkillVitalCost moraleCostPerSecond=readVitalCostData(moraleCostPerSecondTag,VitalTypes.MORALE);
    // Power
    Element powerCostTag=DOMParsingTools.getChildTagByName(skillTag,SkillCostXMLConstants.POWER_COST_TAG);
    SkillVitalCost powerCost=readVitalCostData(powerCostTag,VitalTypes.POWER);
    // Power per second
    Element powerCostPerSecondTag=DOMParsingTools.getChildTagByName(skillTag,SkillCostXMLConstants.POWER_COST_PER_SECOND_TAG);
    SkillVitalCost powerCostPerSecond=readVitalCostData(powerCostPerSecondTag,VitalTypes.POWER);
    if ((moraleCost!=null) || (moraleCostPerSecond!=null) || (powerCost!=null) || (powerCostPerSecond!=null))
    {
      ret=new SkillCostData();
      ret.setMoraleCost(moraleCost);
      ret.setMoraleCostPerSecond(moraleCostPerSecond);
      ret.setPowerCost(powerCost);
      ret.setPowerCostPerSecond(powerCostPerSecond);
    }
    return ret;
  }

  private static SkillVitalCost readVitalCostData(Element vitalCostTag, VitalType type)
  {
    if (vitalCostTag==null)
    {
      return null;
    }
    NamedNodeMap attrs=vitalCostTag.getAttributes();

    SkillVitalCost ret=new SkillVitalCost(type);
    // Consumes all
    boolean consumesAll=DOMParsingTools.getBooleanAttribute(attrs,SkillCostXMLConstants.CONSUMES_ALL_ATTR,false);
    ret.setConsumesAll(consumesAll);
    // Percentage
    Float percentage=DOMParsingTools.getFloatAttribute(attrs,SkillCostXMLConstants.PERCENTAGE_ATTR,null);
    ret.setPercentage(percentage);
    // Points
    Float points=DOMParsingTools.getFloatAttribute(attrs,SkillCostXMLConstants.POINTS_ATTR,null);
    ret.setPoints(points);
    // Points progression
    Integer pointsProgressionID=DOMParsingTools.getIntegerAttribute(attrs,SkillCostXMLConstants.POINTS_PROGRESSION_ATTR,null);
    if (pointsProgressionID!=null)
    {
      Progression progression=ProgressionsManager.getInstance().getProgression(pointsProgressionID.intValue());
      ret.setPointsProgression(progression);
    }
    // Modifiers
    String modsStr=DOMParsingTools.getStringAttribute(attrs,SkillCostXMLConstants.MODS_ATTR,null);
    ret.setVitalMods(ModPropertyListIO.fromPersistedString(modsStr));
    return ret;
  }
}
