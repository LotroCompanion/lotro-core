package delta.games.lotro.character.status.achievables.io.xml;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.NumericTools;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.status.achievables.AchievableElementState;
import delta.games.lotro.character.status.achievables.AchievableObjectiveStatus;
import delta.games.lotro.character.status.achievables.AchievableStatus;
import delta.games.lotro.character.status.achievables.AchievablesStatusManager;
import delta.games.lotro.character.status.achievables.ObjectiveConditionStatus;
import delta.games.lotro.lore.deeds.DeedsManager;
import delta.games.lotro.lore.quests.Achievable;
import delta.games.lotro.lore.quests.QuestsManager;

/**
 * Parser for the achievables status stored in XML.
 * @author DAM
 */
public class AchievablesStatusXMLParser
{
  private static final Logger LOGGER=LoggerFactory.getLogger(AchievablesStatusXMLParser.class);

  /**
   * Parse the XML file.
   * @param source Source file.
   * @param deeds Indicates if we load deeds status or quests status.
   * @return Parsed status or <code>null</code>.
   */
  public AchievablesStatusManager parseXML(File source, boolean deeds)
  {
    AchievablesStatusManager status=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      String statusTagName=deeds?AchievablesStatusXMLConstants.DEED_STATUS_TAG:AchievablesStatusXMLConstants.QUEST_STATUS_TAG;
      status=parseStatus(root,statusTagName);
    }
    return status;
  }

  private AchievablesStatusManager parseStatus(Element root, String statusTagName)
  {
    AchievablesStatusManager status=new AchievablesStatusManager();
    List<Element> statusTags=DOMParsingTools.getChildTagsByName(root,statusTagName,false);
    for(Element statusTag : statusTags)
    {
      parseAchievableStatus(status,statusTag);
    }
    return status;
  }

  private void parseAchievableStatus(AchievablesStatusManager status, Element statusTag)
  {
    NamedNodeMap attrs=statusTag.getAttributes();
    String key=DOMParsingTools.getStringAttribute(attrs,AchievablesStatusXMLConstants.STATUS_KEY_ATTR,null);
    if (key==null)
    {
      // No achievable key!
      LOGGER.warn("No achievable key!");
      return;
    }
    // Create status
    Achievable achievable=DeedsManager.getInstance().getDeed(key);
    if (achievable==null)
    {
      achievable=QuestsManager.getInstance().getQuest(Integer.parseInt(key));
    }
    if (achievable==null)
    {
      // Unknown achievable!
      LOGGER.warn("Unknown achievable: "+key);
      return;
    }
    AchievableStatus newStatus=status.get(achievable,true);
    if (newStatus==null)
    {
      // Unknown achievable!
      LOGGER.warn("Unknown achievable: "+key);
      return;
    }
    // State
    String stateStr=DOMParsingTools.getStringAttribute(attrs,AchievablesStatusXMLConstants.STATUS_STATE_ATTR,null);
    if (stateStr!=null)
    {
      AchievableElementState state=parseState(stateStr);
      newStatus.setState(state);
    }
    else
    {
      boolean completed=DOMParsingTools.getBooleanAttribute(attrs,AchievablesStatusXMLConstants.STATUS_COMPLETED_ATTR,false);
      newStatus.setCompleted(completed);
    }
    // Completion date
    String completionDateStr=DOMParsingTools.getStringAttribute(attrs,AchievablesStatusXMLConstants.STATUS_COMPLETION_DATE_ATTR,null);
    if (completionDateStr!=null)
    {
      Long completionDate=NumericTools.parseLong(completionDateStr);
      newStatus.setCompletionDate(completionDate);
    }
    // Completion count
    int completionCount=DOMParsingTools.getIntAttribute(attrs,AchievablesStatusXMLConstants.STATUS_COMPLETION_COUNT_ATTR,-1);
    if (completionCount!=-1)
    {
      newStatus.setCompletionCount(Integer.valueOf(completionCount));
    }
    // Objectives
    AchievableElementState state=newStatus.getState();
    if (state==AchievableElementState.UNDERWAY)
    {
      // Objectives status
      parseObjectivesStatus(statusTag,newStatus);
    }
    // Update internal states
    newStatus.updateInternalState();
  }

  /**
   * Load achievable objectives status from the given XML stream.
   * @param statusTag Status tag.
   * @param status Status to write.
   */
  private void parseObjectivesStatus(Element statusTag, AchievableStatus status)
  {
    List<Element> objectiveStatusTags=DOMParsingTools.getChildTagsByName(statusTag,AchievablesStatusXMLConstants.OBJECTIVE_STATUS_TAG);
    for(Element objectiveStatusTag : objectiveStatusTags)
    {
      NamedNodeMap objectiveAttrs=objectiveStatusTag.getAttributes();
      // Find objective by index
      int objectiveIndex=DOMParsingTools.getIntAttribute(objectiveAttrs,AchievablesStatusXMLConstants.OBJECTIVE_STATUS_INDEX_ATTR,-1);
      AchievableObjectiveStatus objectiveStatus=status.getObjectiveStatus(objectiveIndex);
      if (objectiveStatus==null)
      {
        LOGGER.warn("Objective not found: achievable ID="+status.getAchievableId()+" - index="+objectiveIndex);
        continue;
      }
      // State
      String stateStr=DOMParsingTools.getStringAttribute(objectiveAttrs,AchievablesStatusXMLConstants.OBJECTIVE_STATUS_STATE_ATTR,null);
      AchievableElementState state=parseState(stateStr);
      objectiveStatus.setState(state);
      // Conditions
      parseConditionsStatus(objectiveStatusTag,objectiveStatus);
    }
  }

  /**
   * Load objective conditions status from the given XML stream.
   * @param objectiveStatusTag Status tag.
   * @param objectiveStatus Status to write.
   */
  private void parseConditionsStatus(Element objectiveStatusTag, AchievableObjectiveStatus objectiveStatus)
  {
    List<Element> conditionStatusTags=DOMParsingTools.getChildTagsByName(objectiveStatusTag,AchievablesStatusXMLConstants.CONDITION_STATUS_TAG);
    for(Element conditionStatusTag : conditionStatusTags)
    {
      NamedNodeMap conditionAttrs=conditionStatusTag.getAttributes();
      // Find condition by index
      int conditionIndex=DOMParsingTools.getIntAttribute(conditionAttrs,AchievablesStatusXMLConstants.CONDITION_STATUS_INDEX_ATTR,-1);
      ObjectiveConditionStatus conditionStatus=objectiveStatus.getConditionStatus(conditionIndex);
      if (conditionStatus==null)
      {
        LOGGER.warn("Condition not found: objective index="+objectiveStatus.getObjective().getIndex()+" - condition index="+conditionIndex);
        continue;
      }
      // State
      String stateStr=DOMParsingTools.getStringAttribute(conditionAttrs,AchievablesStatusXMLConstants.CONDITION_STATUS_STATE_ATTR,null);
      AchievableElementState state=parseState(stateStr);
      conditionStatus.setState(state);
      // Count
      int countValue=DOMParsingTools.getIntAttribute(conditionAttrs,AchievablesStatusXMLConstants.CONDITION_STATUS_COUNT_ATTR,-1);
      Integer count=(countValue>=0)?Integer.valueOf(countValue):null;
      conditionStatus.setCount(count);
      // Keys
      String keys=DOMParsingTools.getStringAttribute(conditionAttrs,AchievablesStatusXMLConstants.CONDITION_STATUS_KEYS_ATTR,null);
      if (keys!=null)
      {
        String[] keysArray=keys.split(",");
        for(String key : keysArray)
        {
          conditionStatus.addKey(key);
        }
      }
    }
  }

  private AchievableElementState parseState(String stateStr)
  {
    AchievableElementState ret=null;
    if (stateStr!=null)
    {
      try
      {
        ret=AchievableElementState.valueOf(stateStr);
      }
      catch(Exception e)
      {
        // Ignored
      }
    }
    if (ret==null)
    {
      ret=AchievableElementState.UNDEFINED;
    }
    return ret;
  }
}
