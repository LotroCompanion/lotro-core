package delta.games.lotro.common.requirements.io.xml;

import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.collections.filters.Operator;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.requirements.AbstractAchievableRequirement;
import delta.games.lotro.common.requirements.CompoundQuestRequirement;
import delta.games.lotro.common.requirements.QuestRequirement;
import delta.games.lotro.common.requirements.QuestStatus;
import delta.games.lotro.common.utils.ComparisonOperator;

/**
 * Read usage requirements from XML documents.
 * @author DAM
 */
public class QuestsRequirementsXMLParser
{
  /**
   * Load a requirement from a parent tag.
   * @param rootTag Parent tag.
   * @return A requirement or <code>null</code> if none.
   */
  public static AbstractAchievableRequirement loadRequirement(Element rootTag)
  {
    Element simpleReqTag=DOMParsingTools.getChildTagByName(rootTag,QuestsRequirementsXMLConstants.PREREQUISITE_TAG);
    if (simpleReqTag!=null)
    {
      return parseSimpleRequirement(simpleReqTag);
    }
    Element compoundReqTag=DOMParsingTools.getChildTagByName(rootTag,QuestsRequirementsXMLConstants.COMPOUND_PREREQUISITE_TAG);
    if (compoundReqTag!=null)
    {
      return parseCompoundRequirement(compoundReqTag);
    }
    return null;
  }

  /**
   * Load a quest requirement from an XML tag.
   * @param tag Tag to use.
   * @return the loaded data.
   */
  public static AbstractAchievableRequirement parseRequirement(Element tag)
  {
    String tagName=tag.getNodeName();
    if (QuestsRequirementsXMLConstants.PREREQUISITE_TAG.equals(tagName))
    {
      return parseSimpleRequirement(tag);
    }
    else if (QuestsRequirementsXMLConstants.COMPOUND_PREREQUISITE_TAG.equals(tagName))
    {
      return parseCompoundRequirement(tag);
    }
    return null;
  }

  /**
   * Load a simple quest requirement from an XML tag.
   * @param tag Tag to use.
   * @return the loaded data.
   */
  public static QuestRequirement parseSimpleRequirement(Element tag)
  {
    NamedNodeMap attrs=tag.getAttributes();
    // ID
    int questId=DOMParsingTools.getIntAttribute(attrs,QuestsRequirementsXMLConstants.ID_ATTR,-1);
    // Operator
    ComparisonOperator operator=ComparisonOperator.EQUAL;
    String operatorStr=DOMParsingTools.getStringAttribute(attrs,QuestsRequirementsXMLConstants.OPERATOR_ATTR,null);
    if (operatorStr!=null)
    {
      operator=ComparisonOperator.valueOf(operatorStr);
    }
    // Status
    QuestStatus status=QuestStatus.COMPLETED;
    String statusStr=DOMParsingTools.getStringAttribute(attrs,QuestsRequirementsXMLConstants.STATUS_ATTR,null);
    if (statusStr!=null)
    {
      status=QuestStatus.getByKey(statusStr);
    }
    QuestRequirement ret=new QuestRequirement(questId,status);
    ret.setOperator(operator);
    return ret;
  }

  /**
   * Load a simple quest requirement from an XML tag.
   * @param tag Tag to use.
   * @return the loaded data.
   */
  public static CompoundQuestRequirement parseCompoundRequirement(Element tag)
  {
    NamedNodeMap attrs=tag.getAttributes();
    // Operator
    Operator operator=Operator.AND;
    String operatorStr=DOMParsingTools.getStringAttribute(attrs,QuestsRequirementsXMLConstants.COMPOUND_PREREQUISITE_OPERATOR_ATTR,null);
    if (operatorStr!=null)
    {
      operator=Operator.valueOf(operatorStr);
    }
    CompoundQuestRequirement ret=new CompoundQuestRequirement(operator);
    List<Element> childTags=DOMParsingTools.getChildTags(tag);
    for(Element childTag : childTags)
    {
      AbstractAchievableRequirement childRequirement=parseRequirement(childTag);
      if (childRequirement!=null)
      {
        ret.addRequirement(childRequirement);
      }
    }
    return ret;
  }
}
