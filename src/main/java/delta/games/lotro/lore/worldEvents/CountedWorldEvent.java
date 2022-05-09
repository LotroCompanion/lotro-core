package delta.games.lotro.lore.worldEvents;

import java.util.ArrayList;
import java.util.List;

/**
 * Counted world event, that uses some world event conditions.
 * @author DAM
 */
public class CountedWorldEvent extends AbstractIntegerWorldEvent
{
  private int _max;
  private List<AbstractWorldEventCondition> _conditions;

  /**
   * Constructor.
   */
  public CountedWorldEvent()
  {
    _max=0;
    _conditions=new ArrayList<AbstractWorldEventCondition>();
  }

  /**
   * Get the maximum value.
   * @return a value.
   */
  public int getMax()
  {
    return _max;
  }

  /**
   * Set the maximum value.
   * @param max Maximum value to set.
   */
  public void setMax(int max)
  {
    _max=max;
  }

  /**
   * Add a new condition.
   * @param condition Condition to add.
   */
  public void addCondition(AbstractWorldEventCondition condition)
  {
    _conditions.add(condition);
  }

  /**
   * Get the managed conditions.
   * @return A list of the managed conditions.
   */
  public List<AbstractWorldEventCondition> getConditions()
  {
    return new ArrayList<AbstractWorldEventCondition>(_conditions);
  }
}
