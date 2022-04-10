package delta.games.lotro.character.status.achievables.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.status.achievables.AchievableElementState;
import delta.games.lotro.character.status.achievables.AchievableObjectiveStatus;
import delta.games.lotro.character.status.achievables.AchievableStatus;
import delta.games.lotro.character.status.achievables.AchievablesStatusManager;
import delta.games.lotro.character.status.achievables.ObjectiveConditionStatus;
import delta.games.lotro.lore.quests.Achievable;

/**
 * Writes an achievables status to an XML file.
 * @author DAM
 */
public class AchievablesStatusXMLWriter
{
  private static final String CDATA="CDATA";

  private String _mainTag;
  private String _childTag;

  /**
   * Constructor.
   * @param deeds Write deeds status or quests status.
   */
  public AchievablesStatusXMLWriter(boolean deeds)
  {
    _mainTag=deeds?AchievablesStatusXMLConstants.DEEDS_STATUS_TAG:AchievablesStatusXMLConstants.QUESTS_STATUS_TAG;
    _childTag=deeds?AchievablesStatusXMLConstants.DEED_STATUS_TAG:AchievablesStatusXMLConstants.QUEST_STATUS_TAG;
  }

  /**
   * Write a status to an XML file.
   * @param outFile Output file.
   * @param status Status to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final AchievablesStatusManager status, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeStatus(hd,status);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  /**
   * Write a status to the given XML stream.
   * @param hd XML output stream.
   * @param statusMgr Status to write.
   * @throws Exception If an error occurs.
   */
  private void writeStatus(TransformerHandler hd, AchievablesStatusManager statusMgr) throws Exception
  {
    statusMgr.cleanup();
    AttributesImpl attrs=new AttributesImpl();
    hd.startElement("","",_mainTag,attrs);

    List<AchievableStatus> status=statusMgr.getAll();

    for(AchievableStatus achievableStatus : status)
    {
      AttributesImpl statusAttrs=new AttributesImpl();
      // Key
      int achievableId=achievableStatus.getAchievableId();
      statusAttrs.addAttribute("","",AchievablesStatusXMLConstants.STATUS_KEY_ATTR,CDATA,String.valueOf(achievableId));
      // Name
      Achievable achievable=achievableStatus.getAchievable();
      String name=achievable.getName();
      if (name!=null)
      {
        statusAttrs.addAttribute("","",AchievablesStatusXMLConstants.STATUS_NAME_ATTR,CDATA,name);
      }
      // Status
      AchievableElementState state=achievableStatus.getState();
      if ((state!=null) && (state!=AchievableElementState.UNDEFINED))
      {
        statusAttrs.addAttribute("","",AchievablesStatusXMLConstants.STATUS_STATE_ATTR,CDATA,state.name());
      }
      // Completion date
      Long completionDate=achievableStatus.getCompletionDate();
      if (completionDate!=null)
      {
        statusAttrs.addAttribute("","",AchievablesStatusXMLConstants.STATUS_COMPLETION_DATE_ATTR,CDATA,completionDate.toString());
      }
      // Completion count
      Integer completionCount=achievableStatus.getCompletionCount();
      if (completionCount!=null)
      {
        statusAttrs.addAttribute("","",AchievablesStatusXMLConstants.STATUS_COMPLETION_COUNT_ATTR,CDATA,completionCount.toString());
      }
      hd.startElement("","",_childTag,statusAttrs);
      // Write objectives status
      if (state==AchievableElementState.UNDERWAY)
      {
        writeObjectivesStatus(hd,achievableStatus);
      }
      hd.endElement("","",_childTag);
    }
    hd.endElement("","",_mainTag);
  }

  /**
   * Write achievable objectives status to the given XML stream.
   * @param hd XML output stream.
   * @param status Status to write.
   * @throws Exception If an error occurs.
   */
  private void writeObjectivesStatus(TransformerHandler hd, AchievableStatus status) throws Exception
  {
    List<AchievableObjectiveStatus> objectiveStatuses=status.getObjectiveStatuses();

    for(AchievableObjectiveStatus objectiveStatus : objectiveStatuses)
    {
      AttributesImpl attrs=new AttributesImpl();
      // Index
      int index=objectiveStatus.getObjective().getIndex();
      attrs.addAttribute("","",AchievablesStatusXMLConstants.OBJECTIVE_STATUS_INDEX_ATTR,CDATA,String.valueOf(index));
      // State
      AchievableElementState state=objectiveStatus.getState();
      if ((state!=null) && (state!=AchievableElementState.UNDEFINED))
      {
        attrs.addAttribute("","",AchievablesStatusXMLConstants.OBJECTIVE_STATUS_STATE_ATTR,CDATA,state.name());
      }
      hd.startElement("","",AchievablesStatusXMLConstants.OBJECTIVE_STATUS_TAG,attrs);
      List<ObjectiveConditionStatus> conditionStatuses=objectiveStatus.getConditionStatuses();
      for(ObjectiveConditionStatus conditionStatus : conditionStatuses)
      {
        writeObjectiveConditionStatus(hd,conditionStatus);
      }
      hd.endElement("","",AchievablesStatusXMLConstants.OBJECTIVE_STATUS_TAG);
    }
  }

  /**
   * Write objective condition status to the given XML stream.
   * @param hd XML output stream.
   * @param status Status to write.
   * @throws Exception If an error occurs.
   */
  private void writeObjectiveConditionStatus(TransformerHandler hd, ObjectiveConditionStatus status) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    // Index
    int index=status.getCondition().getIndex();
    attrs.addAttribute("","",AchievablesStatusXMLConstants.CONDITION_STATUS_INDEX_ATTR,CDATA,String.valueOf(index));
    // Status
    AchievableElementState state=status.getState();
    if ((state!=null) && (state!=AchievableElementState.UNDEFINED))
    {
      attrs.addAttribute("","",AchievablesStatusXMLConstants.CONDITION_STATUS_STATE_ATTR,CDATA,state.name());
    }
    // Count
    Integer count=status.getCount();
    if (count!=null)
    {
      attrs.addAttribute("","",AchievablesStatusXMLConstants.CONDITION_STATUS_COUNT_ATTR,CDATA,count.toString());
    }
    // Keys
    List<String> keys=status.getKeys();
    if ((keys!=null) && (keys.size()>0))
    {
      String keysStr=buildKeysString(keys);
      attrs.addAttribute("","",AchievablesStatusXMLConstants.CONDITION_STATUS_KEYS_ATTR,CDATA,keysStr);
    }
    hd.startElement("","",AchievablesStatusXMLConstants.CONDITION_STATUS_TAG,attrs);
    hd.endElement("","",AchievablesStatusXMLConstants.CONDITION_STATUS_TAG);
  }

  private String buildKeysString(List<String> keys)
  {
    StringBuilder sb=new StringBuilder();
    boolean useComma=false;
    for(String key : keys)
    {
      if (useComma) sb.append(",");
      sb.append(key);
      useComma=true;
    }
    return sb.toString();
  }
}
