package delta.games.lotro.character.skills.io.xml;

import javax.xml.transform.sax.TransformerHandler;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.skills.SkillPipData;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.common.enums.PipType;
import delta.games.lotro.common.properties.ModPropertyList;
import delta.games.lotro.common.properties.io.ModPropertyListIO;

/**
 * XML I/O for pip data of skills.
 * @author DAM
 */
public class SkillPipXmlIO
{
  /**
   * Write skill pip data.
   * @param hd Output
   * @param data Data to write.
   * @throws SAXException If an error occurs.
   */
  public static void writePipData(TransformerHandler hd, SkillPipData data) throws SAXException
  {
    if (data==null)
    {
      return;
    }
    AttributesImpl attrs=new AttributesImpl();
    // Type
    PipType type=data.getType();
    attrs.addAttribute("","",SkillPipXMLConstants.PIP_TYPE_ATTR,XmlWriter.CDATA,String.valueOf(type.getCode()));
    // Change
    Integer change=data.getChange();
    if (change!=null)
    {
      attrs.addAttribute("","",SkillPipXMLConstants.PIP_CHANGE_ATTR,XmlWriter.CDATA,change.toString());
    }
    // Change (mods)
    ModPropertyList changeMods=data.getChangeMods();
    String changeModsStr=ModPropertyListIO.asPersistentString(changeMods);
    if (!changeModsStr.isEmpty())
    {
      attrs.addAttribute("","",SkillPipXMLConstants.PIP_CHANGE_MODS_ATTR,XmlWriter.CDATA,changeModsStr);
    }
    // Required minimum value
    Integer requiredMinValue=data.getRequiredMinValue();
    if (requiredMinValue!=null)
    {
      attrs.addAttribute("","",SkillPipXMLConstants.PIP_REQUIRED_MIN_VALUE_ATTR,XmlWriter.CDATA,requiredMinValue.toString());
    }
    // Required minimum value (mods)
    ModPropertyList requiredMinValueMods=data.getRequiredMinValueMods();
    String requiredMinValueModsStr=ModPropertyListIO.asPersistentString(requiredMinValueMods);
    if (!requiredMinValueModsStr.isEmpty())
    {
      attrs.addAttribute("","",SkillPipXMLConstants.PIP_REQUIRED_MIN_VALUE_MODS_ATTR,XmlWriter.CDATA,requiredMinValueModsStr);
    }
    // Required maximum value
    Integer requiredMaxValue=data.getRequiredMaxValue();
    if (requiredMaxValue!=null)
    {
      attrs.addAttribute("","",SkillPipXMLConstants.PIP_REQUIRED_MAX_VALUE_ATTR,XmlWriter.CDATA,requiredMaxValue.toString());
    }
    // Required maximum value (mods)
    ModPropertyList requiredMaxValueMods=data.getRequiredMaxValueMods();
    String requiredMaxValueModsStr=ModPropertyListIO.asPersistentString(requiredMaxValueMods);
    if (!requiredMaxValueModsStr.isEmpty())
    {
      attrs.addAttribute("","",SkillPipXMLConstants.PIP_REQUIRED_MAX_VALUE_MODS_ATTR,XmlWriter.CDATA,requiredMaxValueModsStr);
    }
    // Toward home
    Integer towardHome=data.getTowardHome();
    if (towardHome!=null)
    {
      attrs.addAttribute("","",SkillPipXMLConstants.PIP_TOWARD_HOME_ATTR,XmlWriter.CDATA,towardHome.toString());
    }
    // Change per interval
    Integer changePerInterval=data.getChangePerInterval();
    if (changePerInterval!=null)
    {
      attrs.addAttribute("","",SkillPipXMLConstants.PIP_CHANGE_PER_INTERVAL_ATTR,XmlWriter.CDATA,changePerInterval.toString());
    }
    // Change per interval (mods)
    ModPropertyList changePerIntervalMods=data.getChangePerIntervalMods();
    String changePerIntervalModsStr=ModPropertyListIO.asPersistentString(changePerIntervalMods);
    if (!changePerIntervalModsStr.isEmpty())
    {
      attrs.addAttribute("","",SkillPipXMLConstants.PIP_CHANGE_PER_INTERVAL_MODS_ATTR,XmlWriter.CDATA,changePerIntervalModsStr);
    }
    // Seconds per pip change
    Float secondsPerPipChange=data.getSecondsPerPipChange();
    if (secondsPerPipChange!=null)
    {
      attrs.addAttribute("","",SkillPipXMLConstants.PIP_SECONDS_PER_PIP_CHANGE_ATTR,XmlWriter.CDATA,secondsPerPipChange.toString());
    }
    // Seconds per pip change (mods)
    ModPropertyList secondsPerPipChangeMods=data.getSecondsPerPipChangeMods();
    String secondsPerPipChangeModsStr=ModPropertyListIO.asPersistentString(secondsPerPipChangeMods);
    if (!secondsPerPipChangeModsStr.isEmpty())
    {
      attrs.addAttribute("","",SkillPipXMLConstants.PIP_SECONDS_PER_PIP_CHANGE_MODS_ATTR,XmlWriter.CDATA,secondsPerPipChangeModsStr);
    }
    hd.startElement("","",SkillPipXMLConstants.PIP_TAG,attrs);
    hd.endElement("","",SkillPipXMLConstants.PIP_TAG);
  }

  /**
   * Read skill pip data.
   * @param pipTag Pip data tag.
   * @return the loaded data.
   */
  public static SkillPipData readPipData(Element pipTag)
  {
    NamedNodeMap attrs=pipTag.getAttributes();

    // PIP Type
    int pipTypeCode=DOMParsingTools.getIntAttribute(attrs,SkillPipXMLConstants.PIP_TYPE_ATTR,0);
    LotroEnum<PipType> pipTypeEnum=LotroEnumsRegistry.getInstance().get(PipType.class);
    PipType pipType=pipTypeEnum.getEntry(pipTypeCode);
    SkillPipData ret=new SkillPipData(pipType);

    // Change
    Integer change=DOMParsingTools.getIntegerAttribute(attrs,SkillPipXMLConstants.PIP_CHANGE_ATTR,null);
    ret.setChange(change);
    // Change (mods)
    String changeModsStr=DOMParsingTools.getStringAttribute(attrs,SkillPipXMLConstants.PIP_CHANGE_MODS_ATTR,null);
    ret.setChangeMods(ModPropertyListIO.fromPersistedString(changeModsStr));
    // Required minimum value
    Integer requiredMinValue=DOMParsingTools.getIntegerAttribute(attrs,SkillPipXMLConstants.PIP_REQUIRED_MIN_VALUE_ATTR,null);
    ret.setRequiredMinValue(requiredMinValue);
    // Required minimum value (mods)
    String requiredMinValueModsStr=DOMParsingTools.getStringAttribute(attrs,SkillPipXMLConstants.PIP_REQUIRED_MIN_VALUE_MODS_ATTR,null);
    ret.setRequiredMinValueMods(ModPropertyListIO.fromPersistedString(requiredMinValueModsStr));
    // Required maximum value
    Integer requiredMaxValue=DOMParsingTools.getIntegerAttribute(attrs,SkillPipXMLConstants.PIP_REQUIRED_MAX_VALUE_ATTR,null);
    ret.setRequiredMaxValue(requiredMaxValue);
    // Required maximum value (mods)
    String requiredMaxValueModsStr=DOMParsingTools.getStringAttribute(attrs,SkillPipXMLConstants.PIP_REQUIRED_MAX_VALUE_MODS_ATTR,null);
    ret.setRequiredMaxValueMods(ModPropertyListIO.fromPersistedString(requiredMaxValueModsStr));
    // Toward home
    Integer towardHome=DOMParsingTools.getIntegerAttribute(attrs,SkillPipXMLConstants.PIP_TOWARD_HOME_ATTR,null);
    ret.setTowardHome(towardHome);
    // Change per interval
    Integer changePerInterval=DOMParsingTools.getIntegerAttribute(attrs,SkillPipXMLConstants.PIP_CHANGE_PER_INTERVAL_ATTR,null);
    ret.setChangePerInterval(changePerInterval);
    // Change per interval (mods)
    String changePerIntervalModsStr=DOMParsingTools.getStringAttribute(attrs,SkillPipXMLConstants.PIP_CHANGE_PER_INTERVAL_MODS_ATTR,null);
    ret.setChangePerIntervalMods(ModPropertyListIO.fromPersistedString(changePerIntervalModsStr));
    // Seconds per pip change
    Float secondsPerPipChange=DOMParsingTools.getFloatAttribute(attrs,SkillPipXMLConstants.PIP_SECONDS_PER_PIP_CHANGE_ATTR,null);
    ret.setSecondsPerPipChange(secondsPerPipChange);
    // Seconds per pip change (mods)
    String secondsPerPipChangeModsStr=DOMParsingTools.getStringAttribute(attrs,SkillPipXMLConstants.PIP_SECONDS_PER_PIP_CHANGE_MODS_ATTR,null);
    ret.setSecondsPerPipChangeMods(ModPropertyListIO.fromPersistedString(secondsPerPipChangeModsStr));
    return ret;
  }
}
