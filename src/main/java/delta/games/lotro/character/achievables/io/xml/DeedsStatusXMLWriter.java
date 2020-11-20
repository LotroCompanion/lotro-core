package delta.games.lotro.character.achievables.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.achievables.AchievableElementState;
import delta.games.lotro.character.achievables.AchievableObjectiveStatus;
import delta.games.lotro.character.achievables.AchievableStatus;
import delta.games.lotro.character.achievables.DeedsStatusManager;
import delta.games.lotro.character.achievables.ObjectiveConditionStatus;

/**
 * Writes a deeds status to an XML file.
 * @author DAM
 */
public class DeedsStatusXMLWriter
{
  private static final String CDATA="CDATA";

  /**
   * Write a deeds status to an XML file.
   * @param outFile Output file.
   * @param status Status to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final DeedsStatusManager status, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeDeedsStatus(hd,status);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  /**
   * Write a deed status to the given XML stream.
   * @param hd XML output stream.
   * @param status Status to write.
   * @throws Exception If an error occurs.
   */
  private void writeDeedsStatus(TransformerHandler hd, DeedsStatusManager status) throws Exception
  {
    status.cleanup();
    AttributesImpl attrs=new AttributesImpl();
    hd.startElement("","",DeedStatusXMLConstants.DEEDS_STATUS_TAG,attrs);

    List<AchievableStatus> deedStatuses=status.getAll();

    for(AchievableStatus deedStatus : deedStatuses)
    {
      AttributesImpl deedAttrs=new AttributesImpl();
      // Key
      int achievableId=deedStatus.getAchievableId();
      deedAttrs.addAttribute("","",DeedStatusXMLConstants.DEED_STATUS_KEY_ATTR,CDATA,String.valueOf(achievableId));
      // Status
      AchievableElementState state=deedStatus.getState();
      if ((state!=null) && (state!=AchievableElementState.UNDEFINED))
      {
        deedAttrs.addAttribute("","",DeedStatusXMLConstants.DEED_STATUS_STATE_ATTR,CDATA,state.name());
      }
      // Completion date
      Long completionDate=deedStatus.getCompletionDate();
      if (completionDate!=null)
      {
        deedAttrs.addAttribute("","",DeedStatusXMLConstants.DEED_STATUS_COMPLETION_DATE_ATTR,CDATA,completionDate.toString());
      }
      hd.startElement("","",DeedStatusXMLConstants.DEED_STATUS_TAG,deedAttrs);
      // Write objectives status
      writeObjectivesStatus(hd,deedStatus);
      hd.endElement("","",DeedStatusXMLConstants.DEED_STATUS_TAG);
    }
    hd.endElement("","",DeedStatusXMLConstants.DEEDS_STATUS_TAG);
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
      attrs.addAttribute("","",DeedStatusXMLConstants.OBJECTIVE_STATUS_INDEX_ATTR,CDATA,String.valueOf(index));
      // State
      AchievableElementState state=objectiveStatus.getState();
      if ((state!=null) && (state!=AchievableElementState.UNDEFINED))
      {
        attrs.addAttribute("","",DeedStatusXMLConstants.OBJECTIVE_STATUS_STATE_ATTR,CDATA,state.name());
      }
      hd.startElement("","",DeedStatusXMLConstants.OBJECTIVE_STATUS_TAG,attrs);
      List<ObjectiveConditionStatus> conditionStatuses=objectiveStatus.getConditionStatuses();
      for(ObjectiveConditionStatus conditionStatus : conditionStatuses)
      {
        writeObjectiveConditionStatus(hd,conditionStatus);
      }
      hd.endElement("","",DeedStatusXMLConstants.OBJECTIVE_STATUS_TAG);
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
    attrs.addAttribute("","",DeedStatusXMLConstants.CONDITION_STATUS_INDEX_ATTR,CDATA,String.valueOf(index));
    // Status
    AchievableElementState state=status.getState();
    if ((state!=null) && (state!=AchievableElementState.UNDEFINED))
    {
      attrs.addAttribute("","",DeedStatusXMLConstants.CONDITION_STATUS_STATE_ATTR,CDATA,state.name());
    }
    // Count
    Integer count=status.getCount();
    if (count!=null)
    {
      attrs.addAttribute("","",DeedStatusXMLConstants.CONDITION_STATUS_COUNT_ATTR,CDATA,count.toString());
    }
    // Keys
    List<String> keys=status.getKeys();
    if ((keys!=null) && (keys.size()>0))
    {
      String keysStr=buildKeysString(keys);
      attrs.addAttribute("","",DeedStatusXMLConstants.CONDITION_STATUS_KEYS_ATTR,CDATA,keysStr);
    }
    hd.startElement("","",DeedStatusXMLConstants.CONDITION_STATUS_TAG,attrs);
    hd.endElement("","",DeedStatusXMLConstants.CONDITION_STATUS_TAG);
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
