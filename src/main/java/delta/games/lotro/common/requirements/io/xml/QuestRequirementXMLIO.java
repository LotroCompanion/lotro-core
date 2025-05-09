package delta.games.lotro.common.requirements.io.xml;

import org.w3c.dom.NamedNodeMap;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.requirements.QuestRequirement;

/**
 * XML I/O for quest requirements.
 * @author DAM
 */
public class QuestRequirementXMLIO implements RequirementSAXWriter<QuestRequirement>,RequirementXMLReader<QuestRequirement>
{
  @Override
  public QuestRequirement readSAX(Attributes attributes)
  {
    String questReqStr=attributes.getValue(UsageRequirementXMLConstants.REQUIRED_QUEST_ATTR);
    return QuestRequirement.fromString(questReqStr);
  }

  @Override
  public QuestRequirement readDOM(NamedNodeMap attrs)
  {
    String questReqStr=DOMParsingTools.getStringAttribute(attrs,UsageRequirementXMLConstants.REQUIRED_QUEST_ATTR,null);
    return QuestRequirement.fromString(questReqStr);
  }

  @Override
  public void write(AttributesImpl attrs, QuestRequirement questRequirement)
  {
    if (questRequirement!=null)
    {
      String questReqStr=questRequirement.asString();
      attrs.addAttribute("","",UsageRequirementXMLConstants.REQUIRED_QUEST_ATTR,XmlWriter.CDATA,questReqStr);
    }
  }
}
