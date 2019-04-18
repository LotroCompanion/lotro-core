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
  private Money _money;
  private int _xp;
  private int _itemXp;
  private int _mountXp;
  private int _glory;
  private int _destinyPoints;
  private int _lotroPoints;
  private int _classPoints;
  private List<RewardElement> _rewardElements;

  /**
   * Constructor.
   */
  public Rewards()
  {
    _money=new Money();
    _xp=0;
    _itemXp=0;
    _mountXp=0;
    _glory=0;
    _destinyPoints=0;
    _lotroPoints=0;
    _classPoints=0;
    _rewardElements=new ArrayList<RewardElement>();
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
   * Get the rewarded XP.
   * @return an XP amount.
   */
  public int getXp()
  {
    return _xp;
  }

  /**
   * Set the rewarded XP.
   * @param xp XP amount to set.
   */
  public void setXp(int xp)
  {
    _xp=xp;
  }

  /**
   * Get the rewarded item XP.
   * @return an item XP amount.
   */
  public int getItemXp()
  {
    return _itemXp;
  }

  /**
   * Set the rewarded item XP.
   * @param itemXp Item XP amount to set.
   */
  public void setItemXp(int itemXp)
  {
    _itemXp=itemXp;
  }

  /**
   * Get the rewarded mount XP.
   * @return a mount XP amount.
   */
  public int getMountXp()
  {
    return _mountXp;
  }

  /**
   * Set the rewarded mount XP.
   * @param mountXp Mount XP amount to set.
   */
  public void setMountXp(int mountXp)
  {
    _mountXp=mountXp;
  }

  /**
   * Get the rewarded glory amount.
   * @return a glory (renown/infamy) amount.
   */
  public int getGlory()
  {
    return _glory;
  }

  /**
   * Set the rewarded glory amount.
   * @param glory glory amount to set.
   */
  public void setGlory(int glory)
  {
    _glory=glory;
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
    if (_xp>0)
    {
      if (firstDone) sb.append(" / ");
      sb.append("XP=").append(_xp);
      firstDone=true;
    }
    if (_itemXp>0)
    {
      if (firstDone) sb.append(" / ");
      sb.append("Item XP=").append(_itemXp);
      firstDone=true;
    }
    if (_mountXp>0)
    {
      if (firstDone) sb.append(" / ");
      sb.append("Mount XP=").append(_mountXp);
      firstDone=true;
    }
    if (_glory>0)
    {
      if (firstDone) sb.append(" / ");
      sb.append("Glory=").append(_glory);
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
    for(RewardElement rewardElement : _rewardElements)
    {
      if (firstDone) sb.append(" / ");
      sb.append(rewardElement);
      firstDone=true;
    }
    return sb.toString();
  }
}
