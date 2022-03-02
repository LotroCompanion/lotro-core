package delta.games.lotro.common.requirements.io.xml;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.collections.filters.Operator;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.common.requirements.AbstractAchievableRequirement;
import delta.games.lotro.common.requirements.CompoundQuestRequirement;
import delta.games.lotro.common.requirements.QuestRequirement;
import delta.games.lotro.common.requirements.QuestStatus;
import delta.games.lotro.common.utils.ComparisonOperator;
import delta.games.lotro.lore.quests.Achievable;
import delta.games.lotro.utils.Proxy;

/**
 * Writes quest requirements to XML documents.
 * @author DAM
 */
public class QuestsRequirementsXMLWriter
{
  /**
   * Write a quest requirement to the given XML stream.
   * @param hd XML output stream.
   * @param abstractQuestRequirement Data to write.
   * @throws SAXException If an error occurs.
   */
  public static void writeQuestRequirement(TransformerHandler hd, AbstractAchievableRequirement abstractQuestRequirement) throws SAXException
  {
    if (abstractQuestRequirement instanceof QuestRequirement)
    {
      writeSimpleQuestRequirement(hd,(QuestRequirement)abstractQuestRequirement);
    }
    else if (abstractQuestRequirement instanceof CompoundQuestRequirement)
    {
      writeCompoundQuestRequirement(hd,(CompoundQuestRequirement)abstractQuestRequirement);
    }
  }

  private static void writeCompoundQuestRequirement(TransformerHandler hd, CompoundQuestRequirement compoundQuestRequirement) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    Operator operator=compoundQuestRequirement.getOperator();
    if (operator!=Operator.AND)
    {
      attrs.addAttribute("","",QuestsRequirementsXMLConstants.COMPOUND_PREREQUISITE_OPERATOR_ATTR,XmlWriter.CDATA,operator.name());
    }
    hd.startElement("","",QuestsRequirementsXMLConstants.COMPOUND_PREREQUISITE_TAG,attrs);
    for(AbstractAchievableRequirement abstractQuestRequirement : compoundQuestRequirement.getRequirements())
    {
      writeQuestRequirement(hd,abstractQuestRequirement);
    }
    hd.endElement("","",QuestsRequirementsXMLConstants.COMPOUND_PREREQUISITE_TAG);
  }

  private static void writeSimpleQuestRequirement(TransformerHandler hd, QuestRequirement questRequirement) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // ID
    int questId=questRequirement.getQuestId();
    attrs.addAttribute("","",QuestsRequirementsXMLConstants.ID_ATTR,XmlWriter.CDATA,String.valueOf(questId));
    // Name
    Proxy<Achievable> achievableProxy=questRequirement.getRequiredAchievable();
    Achievable achievable=achievableProxy.getObject();
    if (achievable!=null)
    {
      String name=achievable.getName();
      attrs.addAttribute("","",QuestsRequirementsXMLConstants.NAME_ATTR,XmlWriter.CDATA,name);
    }
    // Operator
    ComparisonOperator operator=questRequirement.getOperator();
    if (operator!=ComparisonOperator.EQUAL)
    {
      attrs.addAttribute("","",QuestsRequirementsXMLConstants.OPERATOR_ATTR,XmlWriter.CDATA,operator.name());
    }
    // Status
    QuestStatus status=questRequirement.getQuestStatus();
    if (status!=QuestStatus.COMPLETED)
    {
      attrs.addAttribute("","",QuestsRequirementsXMLConstants.STATUS_ATTR,XmlWriter.CDATA,status.getKey());
    }
    hd.startElement("","",QuestsRequirementsXMLConstants.PREREQUISITE_TAG,attrs);
    hd.endElement("","",QuestsRequirementsXMLConstants.PREREQUISITE_TAG);
  }
}
