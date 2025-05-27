package delta.games.lotro.common.requirements.io.xml;

import org.w3c.dom.NamedNodeMap;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.xml.DOMParsingTools;
import delta.common.utils.xml.SAXParsingTools;
import delta.games.lotro.common.requirements.LevelCapRequirement;

/**
 * XML I/O for level cap requirements.
 * @author DAM
 */
public class LevelCapRequirementXMLIO implements RequirementSAXWriter<LevelCapRequirement>,RequirementXMLReader<LevelCapRequirement>
{
  @Override
  public LevelCapRequirement readSAX(Attributes attributes)
  {
    int levelCap=SAXParsingTools.getIntAttribute(attributes,UsageRequirementXMLConstants.REQUIRED_LEVEL_CAP_ATTR,0);
    if (levelCap>0)
    {
      return new LevelCapRequirement(levelCap);
    }
    return null;
  }

  @Override
  public LevelCapRequirement readDOM(NamedNodeMap attrs)
  {
    int levelCap=DOMParsingTools.getIntAttribute(attrs,UsageRequirementXMLConstants.REQUIRED_LEVEL_CAP_ATTR,0);
    if (levelCap>0)
    {
      return new LevelCapRequirement(levelCap);
    }
    return null;
  }

  @Override
  public void write(AttributesImpl attrs, LevelCapRequirement levelCapRequirement)
  {
    if (levelCapRequirement!=null)
    {
      int levelCap=levelCapRequirement.getLevelCap();
      attrs.addAttribute("","",UsageRequirementXMLConstants.REQUIRED_LEVEL_CAP_ATTR,XmlWriter.CDATA,String.valueOf(levelCap));
    }
  }
}
