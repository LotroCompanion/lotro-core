package delta.games.lotro.common.rewards;

import java.util.ArrayList;
import java.util.List;

/**
 * A set of selectable reward elements.
 * @author DAM
 */
public class SelectableRewardElement extends RewardElement
{
  private List<RewardElement> _elements;

  /**
   * Constructor.
   */
  public SelectableRewardElement()
  {
    _elements=new ArrayList<RewardElement>();
  }

  /**
   * Add a reward element.
   * @param element Proxy for the item to add.
   */
  public void addElement(RewardElement element)
  {
    _elements.add(element);
  }

  /**
   * Get the selectable elements.
   * @return a list of reward elements.
   */
  public List<RewardElement> getElements()
  {
    return _elements;
  }

  /**
   * Get the number of elements.
   * @return a positive integer. 
   */
  public int getNbElements()
  {
    return _elements.size();
  }

  /**
   * Get the element at specified index.
   * @param index Index of the targeted element.
   * @return A reward element.
   */
  public RewardElement getElement(int index)
  {
    return _elements.get(index);
  }

  @Override
  public String toString()
  {
    return _elements.toString();
  }
}
