package delta.games.lotro.lore.quests.objectives;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Objectives manager.
 * @author DAM
 */
public class ObjectivesManager
{
  private List<Objective> _objectives;

  /**
   * Constructor.
   */
  public ObjectivesManager()
  {
    _objectives=new ArrayList<Objective>();
  }

  /**
   * Add an objective.
   * @param objective Objective to add.
   */
  public void addObjective(Objective objective)
  {
    _objectives.add(objective);
  }

  /**
   * Get the number of objectives.
   * @return an objectives count.
   */
  public int getObjectivesCount()
  {
    return _objectives.size();
  }

  /**
   * Get all the objectives.
   * @return a list of objectives.
   */
  public List<Objective> getObjectives()
  {
    return _objectives;
  }

  /**
   * Sort objectives.
   */
  public void sort()
  {
    Collections.sort(_objectives,new ObjectiveIndexComparator());
  }

  @Override
  public String toString()
  {
    return _objectives.toString();
  }
}
