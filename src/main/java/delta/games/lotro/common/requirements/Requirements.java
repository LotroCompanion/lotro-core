package delta.games.lotro.common.requirements;

import java.util.HashMap;
import java.util.Map;

/**
 * Set of various requirements.
 * @author DAM
 */
public class Requirements
{
  private Map<Class<? extends Requirement>,Requirement> _requirements;

  /**
   * Constructor.
   */
  public Requirements()
  {
    _requirements=new HashMap<Class<? extends Requirement>,Requirement>();
  }

  /**
   * Indicates if this requirement is empty or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isEmpty()
  {
    return _requirements.isEmpty();
  }


  /**
   * Set a requirement (not-typed, does not support <code>null</code>s).
   * Use version with class specification if possible.
   * @param requirement Requirement to add.
   */
  public void setRequirement(Requirement requirement)
  {
    _requirements.put(requirement.getClass(),requirement);
  }

  /**
   * Set a requirement (typed, supports <code>null</code>).
   * @param requirementClass Requirement class.
   * @param requirement Requirement to add.
   */
  public <T extends Requirement> void setRequirement(Class<T> requirementClass, T requirement)
  {
    if (requirement!=null)
    {
      _requirements.put(requirementClass,requirement);
    }
    else
    {
      _requirements.remove(requirementClass);
    }
  }

  /**
   * Get a requirement.
   * @param <T> Type of requirement to get.
   * @param requirementClass Requirement class.
   * @return A requirement or <code>null</code> if none.
   */
  @SuppressWarnings("unchecked")
  public <T extends Requirement> T getRequirement(Class<T> requirementClass)
  {
    return (T)_requirements.get(requirementClass);
  }
}
