package delta.games.lotro.character.traits.prerequisites;

import java.util.ArrayList;
import java.util.List;

/**
 * Compound trait pre-requisite.
 * <p>
 * A collection trait pre-requisites gathered by a logical operator.
 * @author DAM
 */
public class CompoundTraitPrerequisite extends AbstractTraitPrerequisite
{
  private TraitLogicOperator _operator;
  private List<AbstractTraitPrerequisite> _prerequisites;

  /**
   * Constructor.
   * @param operator Logical operator.
   */
  public CompoundTraitPrerequisite(TraitLogicOperator operator)
  {
    _operator=operator;
    _prerequisites=new ArrayList<AbstractTraitPrerequisite>();
  }

  /**
   * Get the operator.
   * @return the operator.
   */
  public TraitLogicOperator getOperator()
  {
    return _operator;
  }

  /**
   * Get the child pre-requisites.
   * @return A list of pre-requisites.
   */
  public List<AbstractTraitPrerequisite> getPrerequisites()
  {
    return _prerequisites;
  }

  /**
   * Add a pre-requisite.
   * @param prerequisite Pre-requisite to add.
   */
  public void addPrerequisite(AbstractTraitPrerequisite prerequisite)
  {
    _prerequisites.add(prerequisite);
  }
}
