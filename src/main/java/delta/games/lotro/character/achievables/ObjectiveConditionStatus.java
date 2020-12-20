package delta.games.lotro.character.achievables;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.lore.quests.objectives.ObjectiveCondition;

/**
 * Status of a single condition of a single objective of a single achievable.
 * @author DAM
 */
public class ObjectiveConditionStatus
{
  private ObjectiveCondition _condition;
  private AchievableElementState _state;
  //private Long _completionDate;
  private Integer _count;
  private List<String> _keys;

  /**
   * Constructor.
   * @param condition Associated condition.
   */
  public ObjectiveConditionStatus(ObjectiveCondition condition)
  {
    _condition=condition;
    _state=AchievableElementState.UNDEFINED;
    _count=null;
    _keys=null;
  }

  /**
   * Get the associated objective condition.
   * @return the associated objective condition.
   */
  public ObjectiveCondition getCondition()
  {
    return _condition;
  }

  /**
   * Get the state of the managed condition.
   * @return A state.
   */
  public AchievableElementState getState()
  {
    return _state;
  }

  /**
   * Set the state of the managed condition.
   * @param state State to set.
   */
  public void setState(AchievableElementState state)
  {
    _state=state;
  }

  /**
   * Update internal state.
   */
  public void updateInternalState()
  {
    if (_state==AchievableElementState.COMPLETED)
    {
      int count=_condition.getCount();
      setCount(Integer.valueOf(count));
    }
    else
    {
      if (_count==null)
      {
        _count=Integer.valueOf(0);
      }
    }
  }

  /**
   * Get the count.
   * @return a count or <code>null</code>.
   */
  public Integer getCount()
  {
    return _count;
  }

  /**
   * Set the count.
   * @param count Count to set.
   */
  public void setCount(Integer count)
  {
    _count=count;
  }

  /**
   * Get the keys.
   * @return a non-empty list of keys or <code>null</code>.
   */
  public List<String> getKeys()
  {
    return _keys;
  }

  /**
   * Remove all keys.
   */
  public void clearKeys()
  {
    _keys=null;
  }

  /**
   * Add a key.
   * @param key Key to add.
   */
  public void addKey(String key)
  {
    if (_keys==null)
    {
      _keys=new ArrayList<String>();
    }
    _keys.add(key);
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Condition #").append(_condition.getIndex());
    sb.append(": ").append(_state);
    if (_count!=null)
    {
      sb.append(", count=").append(_count);
    }
    if (_keys!=null)
    {
      sb.append(", keys=").append(_keys);
    }
    return sb.toString();
  }
}
