package delta.games.lotro.common.requirements.io.xml;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;

import delta.common.utils.collections.filters.Operator;
import delta.common.utils.xml.SAXParsingTools;
import delta.games.lotro.common.requirements.AbstractAchievableRequirement;
import delta.games.lotro.common.requirements.CompoundQuestRequirement;
import delta.games.lotro.common.requirements.QuestRequirement;
import delta.games.lotro.common.requirements.QuestStatus;
import delta.games.lotro.common.utils.ComparisonOperator;

/**
 * Read quest requirements from XML documents.
 * @author DAM
 */
public class QuestsRequirementsSaxParser
{
  private static final Logger LOGGER=Logger.getLogger(QuestsRequirementsSaxParser.class);

  private AbstractAchievableRequirement _result;
  private CompoundQuestRequirement _compound;

  /**
   * Get the result data.
   * @return the result data.
   */
  public AbstractAchievableRequirement getResult()
  {
    return _result;
  }

  /**
   * Handle a start element event.
   * @param tagName Name of started tag.
   * @param attrs Tag attributes.
   */
  public void startElement(String tagName, Attributes attrs)
  {
    if (QuestsRequirementsXMLConstants.PREREQUISITE_TAG.equals(tagName))
    {
      QuestRequirement requirement=parseSimpleRequirement(attrs);
      if (_compound!=null)
      {
        _compound.addRequirement(requirement);
      }
      else
      {
        _result=requirement;
      }
    }
    else if (QuestsRequirementsXMLConstants.COMPOUND_PREREQUISITE_TAG.equals(tagName))
    {
      parseCompoundRequirement(attrs);
      _result=_compound;
    }
  }

  /**
   * Handle an 'end element' event.
   * @param tagName Name of ended tag.
   */
  public void endElement(String tagName)
  {
    if (QuestsRequirementsXMLConstants.COMPOUND_PREREQUISITE_TAG.equals(tagName))
    {
      _compound=null;
    }
  }

  /**
   * Load a simple quest requirement from XML attributes.
   * @param attrs Attributes to use.
   * @return the loaded data.
   */
  public QuestRequirement parseSimpleRequirement(Attributes attrs)
  {
    // ID
    int questId=SAXParsingTools.getIntAttribute(attrs,QuestsRequirementsXMLConstants.ID_ATTR,-1);
    // Operator
    ComparisonOperator operator=ComparisonOperator.EQUAL;
    String operatorStr=SAXParsingTools.getStringAttribute(attrs,QuestsRequirementsXMLConstants.OPERATOR_ATTR,null);
    if (operatorStr!=null)
    {
      operator=ComparisonOperator.valueOf(operatorStr);
    }
    // Status
    QuestStatus status=QuestStatus.COMPLETED;
    String statusStr=SAXParsingTools.getStringAttribute(attrs,QuestsRequirementsXMLConstants.STATUS_ATTR,null);
    if (statusStr!=null)
    {
      status=QuestStatus.getByKey(statusStr);
    }
    QuestRequirement ret=new QuestRequirement(questId,status);
    ret.setOperator(operator);
    return ret;
  }

  /**
   * Load a compound quest requirement from XML attributes.
   * @param attrs Attributes to use.
   */
  public void parseCompoundRequirement(Attributes attrs)
  {
    // Operator
    Operator operator=Operator.AND;
    String operatorStr=SAXParsingTools.getStringAttribute(attrs,QuestsRequirementsXMLConstants.COMPOUND_PREREQUISITE_OPERATOR_ATTR,null);
    if (operatorStr!=null)
    {
      operator=Operator.valueOf(operatorStr);
    }
    if (_compound!=null)
    {
      LOGGER.warn("Found multiple levels of compound quest requirements!");
    }
    _compound=new CompoundQuestRequirement(operator);
  }
}
