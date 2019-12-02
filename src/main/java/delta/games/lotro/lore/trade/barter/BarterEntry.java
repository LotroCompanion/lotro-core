package delta.games.lotro.lore.trade.barter;

import java.util.ArrayList;
import java.util.List;

/**
 * Barter entry.
 * @author DAM
 */
public class BarterEntry
{
  private BarterEntryElement _toReceive;
  private List<ItemBarterEntryElement> _toGive;

  /**
   * Constructor.
   */
  public BarterEntry()
  {
    _toGive=new ArrayList<ItemBarterEntryElement>();
  }

  /**
   * Get the element to receive.
   * @return the element to receive.
   */
  public BarterEntryElement getElementToReceive()
  {
    return _toReceive;
  }

  /**
   * Set the element to receive.
   * @param toReceive Element to set.
   */
  public void setElementToReceive(BarterEntryElement toReceive)
  {
    _toReceive=toReceive;
  }

  /**
   * Add an element to give.
   * @param toGive Element to add.
   */
  public void addElementToGive(ItemBarterEntryElement toGive)
  {
    _toGive.add(toGive);
  }

  /**
   * Get the elements to give.
   * @return a list of elements to give.
   */
  public List<ItemBarterEntryElement> getElementsToGive()
  {
    return _toGive;
  }
}
