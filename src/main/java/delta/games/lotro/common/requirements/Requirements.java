package delta.games.lotro.common.requirements;

import java.util.ArrayList;
import java.util.List;

/**
 * Set of various requirements.
 * @author DAM
 */
public class Requirements
{
  private List<Requirement> _requirements;

  /**
   * Constructor.
   */
  public Requirements()
  {
    _requirements=new ArrayList<Requirement>();
  }

  /**
   * Add a requirement.
   * @param requirement Requirement to add.
   */
  public void addRequirement(Requirement requirement)
  {
    _requirements.add(requirement);
  }

  /**
   * Get the requirements.
   * @return A list of requirements.
   */
  public List<Requirement> getRequirements()
  {
    return _requirements;
  }
}
