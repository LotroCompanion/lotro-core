package delta.games.lotro.common.requirements;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.common.utils.NumericTools;
import delta.games.lotro.common.utils.ComparisonOperator;
import delta.games.lotro.lore.quests.Achievable;
import delta.games.lotro.utils.Proxy;

/**
 * Quest requirement.
 * @author DAM
 */
public class QuestRequirement extends AbstractAchievableRequirement
{
  private static final Logger LOGGER=LoggerFactory.getLogger(QuestRequirement.class);

  private static final String SEPARATOR=";";
  private Proxy<Achievable> _quest;
  private ComparisonOperator _operator;
  private QuestStatus _status;

  /**
   * Constructor.
   * @param questId Quest identifier.
   * @param status Required quest status.
   */
  public QuestRequirement(int questId, QuestStatus status)
  {
    Proxy<Achievable> achievableProxy=new Proxy<Achievable>();
    achievableProxy.setId(questId);
    _quest=achievableProxy;
    _operator=ComparisonOperator.EQUAL;
    _status=status;
  }

  /**
   * Get the identifier of the targeted quest.
   * @return a quest identifier.
   */
  public int getQuestId()
  {
    return _quest.getId();
  }

  /**
   * Get the required achievable.
   * @return An achievable proxy.
   */
  public Proxy<Achievable> getRequiredAchievable()
  {
    return _quest;
  }

  /**
   * Get the comparison operator.
   * @return An operator.
   */
  public ComparisonOperator getOperator()
  {
    return _operator;
  }

  /**
   * Set the comparison operator.
   * @param operator Operator to set.
   */
  public void setOperator(ComparisonOperator operator)
  {
    _operator=operator;
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
    sb.append(_quest.getId());
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
          status=QuestStatus.getByKey(parts[1]);
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
    return "Quest "+_quest+" "+_operator+" "+_status;
  }
}
