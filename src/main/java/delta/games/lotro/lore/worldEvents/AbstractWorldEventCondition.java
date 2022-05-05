package delta.games.lotro.lore.worldEvents;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for world event conditions.
 * @author DAM
 */
public class AbstractWorldEventCondition
{
  private List<WorldEventConditionItem> _items;

  /**
   * Constructor.
   */
  public AbstractWorldEventCondition()
  {
    _items=new ArrayList<WorldEventConditionItem>();
  }

  /**
   * Add a new item.
   * @param item Item to add.
   */
  public void addItem(WorldEventConditionItem item)
  {
    _items.add(item);
  }

  /**
   * Get the managed items.
   * @return A list of the managed items.
   */
  public List<WorldEventConditionItem> getItems()
  {
    return new ArrayList<WorldEventConditionItem>(_items);
  }
}
