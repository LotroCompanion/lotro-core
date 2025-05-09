package delta.games.lotro.common.requirements.io.xml;

import org.w3c.dom.NamedNodeMap;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.xml.DOMParsingTools;
import delta.common.utils.xml.SAXParsingTools;
import delta.games.lotro.common.requirements.DifficultyRequirement;

/**
 * XML I/O for difficulty requirements.
 * @author DAM
 */
public class DifficultyRequirementXMLIO implements RequirementSAXWriter<DifficultyRequirement>,RequirementXMLReader<DifficultyRequirement>
{
  @Override
  public DifficultyRequirement readSAX(Attributes attributes)
  {
    int difficulty=SAXParsingTools.getIntAttribute(attributes,UsageRequirementXMLConstants.REQUIRED_DIFFICULTY_ATTR,0);
    return new DifficultyRequirement(difficulty);
  }

  @Override
  public DifficultyRequirement readDOM(NamedNodeMap attrs)
  {
    int difficulty=DOMParsingTools.getIntAttribute(attrs,UsageRequirementXMLConstants.REQUIRED_DIFFICULTY_ATTR,0);
    return new DifficultyRequirement(difficulty);
  }

  @Override
  public void write(AttributesImpl attrs, DifficultyRequirement difficultyRequirement)
  {
    if (difficultyRequirement!=null)
    {
      int difficulty=difficultyRequirement.getDifficulty();
      attrs.addAttribute("","",UsageRequirementXMLConstants.REQUIRED_DIFFICULTY_ATTR,XmlWriter.CDATA,String.valueOf(difficulty));
    }
  }
}
