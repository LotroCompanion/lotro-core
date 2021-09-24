package delta.games.lotro.lore.agents;

import delta.games.lotro.common.enums.AgentClass;
import delta.games.lotro.common.enums.Alignment;
import delta.games.lotro.common.enums.ClassificationFilter;

/**
 * Agent classification.
 * @author DAM
 */
public class AgentClassification
{
  private Alignment _alignment;
  private AgentClass _agentClass;
  private ClassificationFilter _classFilter;
  private EntityClassification _classification;

  /**
   * Constructor.
   */
  public AgentClassification()
  {
    _alignment=null;
    _agentClass=null;
    _classFilter=null;
    _classification=new EntityClassification();
  }

  /**
   * Get the alignment.
   * @return an alignement or an empty string.
   */
  public Alignment getAlignment()
  {
    return _alignment;
  }

  /**
   * Set the alignment.
   * @param alignment Alignment to set.
   */
  public void setAlignment(Alignment alignment)
  {
    _alignment=alignment;
  }

  /**
   * Get the agent class.
   * @return an agent class or an empty string.
   */
  public AgentClass getAgentClass()
  {
    return _agentClass;
  }

  /**
   * Set the agent class.
   * @param agentClass Agent class to set.
   */
  public void setAgentClass(AgentClass agentClass)
  {
    _agentClass=agentClass;
  }

  /**
   * Get the classification filter.
   * @return a classification filter or an empty string.
   */
  public ClassificationFilter getClassificationFilter()
  {
    return _classFilter;
  }

  /**
   * Set the classification filter.
   * @param classFilter Classification filter to set.
   */
  public void setClassificationFilter(ClassificationFilter classFilter)
  {
    _classFilter=classFilter;
  }

  /**
   * Get the entity classification.
   * @return the entity classification.
   */
  public EntityClassification getEntityClassification()
  {
    return _classification;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append(_classification);
    if (_alignment!=null)
    {
      sb.append(',').append(_alignment);
    }
    if (_agentClass!=null)
    {
      sb.append(',').append(_agentClass);
    }
    if (_classFilter!=null)
    {
      sb.append(',').append(_classFilter);
    }
    return sb.toString();
  }
}
