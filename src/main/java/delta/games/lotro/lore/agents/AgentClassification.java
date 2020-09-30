package delta.games.lotro.lore.agents;

/**
 * Agent classification.
 * @author DAM
 */
public class AgentClassification
{
  private String _alignment;
  private String _agentClass;
  private String _classFilter;
  private EntityClassification _classification;

  /**
   * Constructor.
   */
  public AgentClassification()
  {
    _alignment="";
    _agentClass="";
    _classFilter="";
    _classification=new EntityClassification();
  }

  /**
   * Get the alignment.
   * @return an alignement or an empty string.
   */
  public String getAlignment()
  {
    return _alignment;
  }

  /**
   * Set the alignment.
   * @param alignment Alignment to set.
   */
  public void setAlignment(String alignment)
  {
    if (alignment==null)
    {
      alignment="";
    }
    _alignment=alignment;
  }

  /**
   * Get the agent class.
   * @return an agent class or an empty string.
   */
  public String getAgentClass()
  {
    return _agentClass;
  }

  /**
   * Set the agent class.
   * @param agentClass Agent class to set.
   */
  public void setAgentClass(String agentClass)
  {
    if (agentClass==null)
    {
      agentClass="";
    }
    _agentClass=agentClass;
  }

  /**
   * Get the classification filter.
   * @return a classification filter or an empty string.
   */
  public String getClassificationFilter()
  {
    return _classFilter;
  }

  /**
   * Set the classification filter.
   * @param classFilter Classification filter to set.
   */
  public void setClassificationFilter(String classFilter)
  {
    if (classFilter==null)
    {
      classFilter="";
    }
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
}
