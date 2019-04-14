package delta.games.lotro.common.rewards;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.money.Money;

/**
 * Rewards description.
 * @author DAM
 */
public class Rewards
{
  private boolean _itemXP;
  private Money _money;
  private int _destinyPoints;
  private int _lotroPoints;
  private int _classPoints;
  private List<RewardElement> _rewardElements;

  /**
   * Constructor.
   */
  public Rewards()
  {
    _itemXP=false;
    _money=new Money();
    _rewardElements=new ArrayList<RewardElement>();
    _destinyPoints=0;
    _lotroPoints=0;
    _classPoints=0;
  }

  /**
   * Get the money reward.
   * @return the money reward.
   */
  public Money getMoney()
  {
    return _money;
  }

  /**
   * Get the destiny points.
   * @return the destiny points.
   */
  public int getDestinyPoints()
  {
    return _destinyPoints;
  }

  /**
   * Set the destiny points.
   * @param destinyPoints Destiny points to set.
   */
  public void setDestinyPoints(int destinyPoints)
  {
    _destinyPoints=destinyPoints;
  }

  /**
   * Get the LOTRO points.
   * @return the LOTRO points.
   */
  public int getLotroPoints()
  {
    return _lotroPoints;
  }

  /**
   * Set the LOTRO points.
   * @param lotroPoints LOTRO points to set.
   */
  public void setLotroPoints(int lotroPoints)
  {
    _lotroPoints=lotroPoints;
  }

  /**
   * Get the class points.
   * @return the class points.
   */
  public int getClassPoints()
  {
    return _classPoints;
  }

  /**
   * Set the class points.
   * @param classPoints Class points to set.
   */
  public void setClassPoints(int classPoints)
  {
    _classPoints=classPoints;
  }

  /**
   * Indicates if this reward includes item XP.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean hasItemXP()
  {
    return _itemXP;
  }

  /**
   * Set the 'item XP' flag.
   * @param itemXP Value to set.
   */
  public void setHasItemXP(boolean itemXP)
  {
    _itemXP=itemXP;
  }

  /**
   * Add a reward element.
   * @param rewardElement Reward element to add.
   */
  public void addRewardElement(RewardElement rewardElement)
  {
    _rewardElements.add(rewardElement);
  }

  /**
   * Get a list of all reward elements.
   * @return a list of reward elements.
   */
  public List<RewardElement> getRewardElements()
  {
    return _rewardElements;
  }

  /**
   * Get a list of reward elements of a given class.
   * @param rewardElementClass Reward element class.
   * @return A possibly empty but not <code>null</code> list of reward element of this type.
   */
  public <T extends RewardElement> List<T> getRewardElementsOfClass(Class<T> rewardElementClass)
  {
    return getRewardElementsOfClass(rewardElementClass,_rewardElements);
  }

  private <T extends RewardElement> List<T> getRewardElementsOfClass(Class<T> rewardElementClass, List<RewardElement> elements)
  {
    List<T> ret=new ArrayList<T>();
    for(RewardElement element : elements)
    {
      if (rewardElementClass.isAssignableFrom(element.getClass()))
      {
        ret.add(rewardElementClass.cast(element));
      }
      else if (element instanceof SelectableRewardElement)
      {
        SelectableRewardElement selectableRewardElement=(SelectableRewardElement)element;
        ret.addAll(getRewardElementsOfClass(rewardElementClass,selectableRewardElement.getElements()));
      }
    }
    return ret;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    boolean firstDone=false;
    if (!_money.isEmpty())
    {
      sb.append(_money);
      firstDone=true;
    }
    if (_destinyPoints>0)
    {
      if (firstDone) sb.append(" / ");
      sb.append(_destinyPoints).append(" destiny points");
      firstDone=true;
    }
    if (_lotroPoints>0)
    {
      if (firstDone) sb.append(" / ");
      sb.append(_lotroPoints).append(" LOTRO points");
      firstDone=true;
    }
    if (_classPoints>0)
    {
      if (firstDone) sb.append(" / ");
      sb.append(_classPoints).append(" class points");
      firstDone=true;
    }
    if (_itemXP)
    {
      if (firstDone) sb.append(" / ");
      sb.append("Item XP");
      firstDone=true;
    }
    for(RewardElement rewardElement : _rewardElements)
    {
      if (firstDone) sb.append(" / ");
      sb.append(rewardElement);
      firstDone=true;
    }
    return sb.toString();
  }
}
