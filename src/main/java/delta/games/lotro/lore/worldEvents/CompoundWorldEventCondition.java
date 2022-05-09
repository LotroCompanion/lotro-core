package delta.games.lotro.lore.worldEvents;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.collections.filters.Operator;

/**
 * World event condition that uses a boolean logical operator AND/OR.
 * @author DAM
 */
public class CompoundWorldEventCondition extends AbstractWorldEventCondition
{
  private Operator _operator;
  private List<AbstractWorldEventCondition> _items;


  /**
   * Constructor.
   * @param operator Operator.
   */
  public CompoundWorldEventCondition(Operator operator)
  {
    super();
    _operator=operator;
    _items=new ArrayList<AbstractWorldEventCondition>();
  }

  /**
   * Get the logical operator.
   * @return a logical operator.
   */
  public Operator getOperator()
  {
    return _operator;
  }


  /**
   * Add a new item.
   * @param item Item to add.
   */
  public void addItem(AbstractWorldEventCondition item)
  {
    _items.add(item);
  }

  /**
   * Get the managed items.
   * @return A list of the managed items.
   */
  public List<AbstractWorldEventCondition> getItems()
  {
    return new ArrayList<AbstractWorldEventCondition>(_items);
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append(_operator).append(' ');
    sb.append(getItems());
    return sb.toString();
  }
}
