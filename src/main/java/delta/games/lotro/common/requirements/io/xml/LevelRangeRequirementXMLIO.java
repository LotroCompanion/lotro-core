package delta.games.lotro.common.requirements.io.xml;

import org.w3c.dom.NamedNodeMap;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.xml.DOMParsingTools;
import delta.common.utils.xml.SAXParsingTools;
import delta.games.lotro.common.requirements.LevelRangeRequirement;

/**
 * XML I/O for level range requirements.
 * @author DAM
 */
public class LevelRangeRequirementXMLIO implements RequirementSAXWriter<LevelRangeRequirement>,RequirementXMLReader<LevelRangeRequirement>
{
  @Override
  public LevelRangeRequirement readSAX(Attributes attributes)
  {
    Integer minimumLevel=SAXParsingTools.getIntegerAttribute(attributes,UsageRequirementXMLConstants.MIN_LEVEL_ATTR,null);
    Integer maximumLevel=SAXParsingTools.getIntegerAttribute(attributes,UsageRequirementXMLConstants.MAX_LEVEL_ATTR,null);
    return buildRequirement(minimumLevel,maximumLevel);
  }

  @Override
  public LevelRangeRequirement readDOM(NamedNodeMap attrs)
  {
    Integer minimumLevel=DOMParsingTools.getIntegerAttribute(attrs,UsageRequirementXMLConstants.MIN_LEVEL_ATTR,null);
    Integer maximumLevel=DOMParsingTools.getIntegerAttribute(attrs,UsageRequirementXMLConstants.MAX_LEVEL_ATTR,null);
    return buildRequirement(minimumLevel,maximumLevel);
  }

  private LevelRangeRequirement buildRequirement(Integer minLevel, Integer maxLevel)
  {
    if ((minLevel!=null) || (maxLevel!=null))
    {
      LevelRangeRequirement ret=new LevelRangeRequirement(minLevel,maxLevel);
      return ret;
    }
    return null;
  }

  @Override
  public void write(AttributesImpl attrs, LevelRangeRequirement levelRequirement)
  {
    if (levelRequirement!=null)
    {
      // Min level
      Integer minLevel=levelRequirement.getMinLevel();
      if (minLevel!=null)
      {
        attrs.addAttribute("","",UsageRequirementXMLConstants.MIN_LEVEL_ATTR,XmlWriter.CDATA,minLevel.toString());
      }
      // Max level
      Integer maxLevel=levelRequirement.getMaxLevel();
      if (maxLevel!=null)
      {
        attrs.addAttribute("","",UsageRequirementXMLConstants.MAX_LEVEL_ATTR,XmlWriter.CDATA,maxLevel.toString());
      }
    }
  }
}
