package delta.games.lotro.common.requirements;

import org.apache.log4j.Logger;

import delta.common.utils.NumericTools;

/**
 * Quest requirement.
 * @author DAM
 */
public class QuestRequirement
{
  private static final Logger LOGGER=Logger.getLogger(QuestRequirement.class);

  private static final String SEPARATOR=";";
  private int _questId;
  private QuestStatus _status;

  /**
   * Constructor.
   * @param questId Quest identifier.
   * @param status Required quest status.
   */
  public QuestRequirement(int questId, QuestStatus status)
  {
    _questId=questId;
    _status=status;
  }

  /**
   * Get the identifier of the targeted quest.
   * @return a quest identifier.
   */
  public int getQuestId()
  {
    return _questId;
  }

  /**
   * Get the required status for the targeted quest.
   * @return a quest status.
   */
  public QuestStatus getQuestStatus()
  {
    return _status;
  }

  /**
   * Get a string representation of this requirement.
   * @return A persistable string.
   */
  public String asString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append(_questId);
    sb.append(SEPARATOR);
    sb.append(_status);
    return sb.toString();
  }

  /**
   * Build a quest requirement from a string.
   * @param input Input string ("questId;status").
   * @return A quest requirement or <code>null</code> if none.
   */
  public static QuestRequirement fromString(String input)
  {
    QuestRequirement ret=null;
    if ((input!=null) && (input.length()>0))
    {
      String[] parts=input.split(SEPARATOR);
      if (parts.length==2)
      {
        int questId=NumericTools.parseInt(parts[0],0);
        QuestStatus status=null;
        try
        {
          status=QuestStatus.valueOf(parts[1]);
        }
        catch(Exception e)
        {
          LOGGER.warn("Unsupported quest status: "+parts[1]);
        }
        if ((questId!=0) && (status!=null))
        {
          ret=new QuestRequirement(questId,status);
        }
      }
    }
    return ret;
  }

  @Override
  public String toString()
  {
    return "Quest "+_questId+" "+_status;
  }
}
