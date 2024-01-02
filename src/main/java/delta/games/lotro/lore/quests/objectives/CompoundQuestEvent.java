package delta.games.lotro.lore.quests.objectives;

import java.util.ArrayList;
import java.util.List;

/**
 * Compound quest event.
 * @author DAM
 */
public class CompoundQuestEvent extends AbstractQuestEvent
{
  private String _compoundProgressOverride;
  private List<ObjectiveCondition> _events;

  /**
   * Constructor.
   */
  public CompoundQuestEvent()
  {
    _compoundProgressOverride=null;
    _events=new ArrayList<ObjectiveCondition>();
  }

  /**
   * Get the compound progress override, if any.
   * @return A value or <code>null</code>.
   */
  public String getCompoundProgressOverride()
  {
    return _compoundProgressOverride;
  }

  /**
   * Set the compound progress override.
   * @param compoundProgressOverride Progress to set (may be <code>null</code>).
   */
  public void setCompoundProgressOverride(String compoundProgressOverride)
  {
    _compoundProgressOverride=compoundProgressOverride;
  }

  /**
   * Add a quest event.
   * @param event Event to add.
   */
  public void addQuestEvent(ObjectiveCondition event)
  {
    _events.add(event);
  }

  /**
   * Get the managed quest events.
   * @return a list of quest events.
   */
  public List<ObjectiveCondition> getEvents()
  {
    return _events;
  }
}
