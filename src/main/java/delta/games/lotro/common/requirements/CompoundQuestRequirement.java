package delta.games.lotro.common.requirements;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.collections.filters.Operator;

/**
 * Compound quest requirement.
 * @author DAM
 */
public class CompoundQuestRequirement extends AbstractAchievableRequirement
{
  private Operator _operator;
  private List<AbstractAchievableRequirement> _requirements;

  /**
   * Constructor.
   * @param operator
   */
  public CompoundQuestRequirement(Operator operator)
  {
    _operator=operator;
    _requirements=new ArrayList<AbstractAchievableRequirement>();
  }

  /**
   * Get the operator to use.
   * @return An operator.
   */
  public Operator getOperator()
  {
    return _operator;
  }

  /**
   * Add a requirement.
   * @param requirement Requirement to add.
   */
  public void addRequirement(AbstractAchievableRequirement requirement)
  {
    _requirements.add(requirement);
  }

  /**
   * Get the requirement at the given index.
   * @param index Index of the given requirement (starting at 0).
   * @return A requirement.
   */
  public AbstractAchievableRequirement getRequirement(int index)
  {
    return _requirements.get(index);
  }

  /**
   * Set the requirement at the given index.
   * @param index Index to use (starting at 0).
   * @param requirement Requirement to set.
   */
  public void setRequirement(int index, AbstractAchievableRequirement requirement)
  {
    _requirements.set(index,requirement);
  }

  /**
   * Remove a requirement.
   * @param index Index of the requirement to remove.
   */
  public void removeRequirement(int index)
  {
    _requirements.remove(index);
  }

  /**
   * Get the number of requirements.
   * @return A number of requirements.
   */
  public int getNumberOfRequirements()
  {
    return _requirements.size();
  }

  /**
   * Get the managed requirements.
   * @return A list of quest requirements.
   */
  public List<AbstractAchievableRequirement> getRequirements()
  {
    return _requirements;
  }

  @Override
  public String toString()
  {
    return _operator+": "+_requirements.toString();
  }
}
