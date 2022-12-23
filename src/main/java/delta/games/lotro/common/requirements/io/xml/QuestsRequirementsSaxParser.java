package delta.games.lotro.common.requirements.io.xml;

import java.util.ArrayDeque;
import java.util.Deque;

import org.xml.sax.Attributes;

import delta.common.utils.collections.filters.Operator;
import delta.common.utils.xml.SAXParsingTools;
import delta.common.utils.xml.sax.SAXParserValve;
import delta.games.lotro.common.requirements.AbstractAchievableRequirement;
import delta.games.lotro.common.requirements.CompoundQuestRequirement;
import delta.games.lotro.common.requirements.QuestRequirement;
import delta.games.lotro.common.requirements.QuestStatus;
import delta.games.lotro.common.utils.ComparisonOperator;

/**
 * Read quest requirements from XML documents.
 * @author DAM
 */
public class QuestsRequirementsSaxParser extends SAXParserValve<AbstractAchievableRequirement>
{
  private Deque<CompoundQuestRequirement> _compounds=new ArrayDeque<CompoundQuestRequirement>();
  private CompoundQuestRequirement _compound;

  @Override
  public SAXParserValve<?> handleStartTag(String tagName, Attributes attrs)
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
        setResult(requirement);
      }
    }
    else if (QuestsRequirementsXMLConstants.COMPOUND_PREREQUISITE_TAG.equals(tagName))
    {
      CompoundQuestRequirement compound=parseCompoundRequirement(attrs);
      if (_compound==null)
      {
        setResult(compound);
      }
      else
      {
        _compound.addRequirement(compound);
      }
      _compound=compound;
      _compounds.push(_compound);
    }
    return this;
  }

  @Override
  public SAXParserValve<?> handleEndTag(String tagName)
  {
    if (QuestsRequirementsXMLConstants.COMPOUND_PREREQUISITE_TAG.equals(tagName))
    {
      _compound=_compounds.pop();
      if (_compounds.isEmpty())
      {
        _compound=null;
        return getParent();
      }
      _compound=_compounds.peek();
    }
    else if (QuestsRequirementsXMLConstants.PREREQUISITE_TAG.equals(tagName))
    {
      if (_compound==null)
      {
        return getParent();
      }
    }
    return this;
  }

  /**
   * Load a simple quest requirement from XML attributes.
   * @param attrs Attributes to use.
   * @return the loaded data.
   */
  private QuestRequirement parseSimpleRequirement(Attributes attrs)
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
   * @return the loaded compound requirement.
   */
  private CompoundQuestRequirement parseCompoundRequirement(Attributes attrs)
  {
    // Operator
    Operator operator=Operator.AND;
    String operatorStr=SAXParsingTools.getStringAttribute(attrs,QuestsRequirementsXMLConstants.COMPOUND_PREREQUISITE_OPERATOR_ATTR,null);
    if (operatorStr!=null)
    {
      operator=Operator.valueOf(operatorStr);
    }
    CompoundQuestRequirement compound=new CompoundQuestRequirement(operator);
    return compound;
  }
}
