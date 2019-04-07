package delta.games.lotro.common.objects;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.lore.items.Item;
import delta.games.lotro.utils.Proxy;

/**
 * A set of object items.
 * @author DAM
 */
public class ObjectsSet
{
  private List<Proxy<Item>> _items;
  private List<Integer> _quantities;

  /**
   * Constructor.
   */
  public ObjectsSet()
  {
    _items=new ArrayList<Proxy<Item>>();
    _quantities=new ArrayList<Integer>();
  }

  /**
   * Add an item in this set.
   * @param item Proxy for the item to add.
   * @param quantity Quantity.
   */
  public void addObject(Proxy<Item> item, int quantity)
  {
    if ((item!=null) && (quantity>0))
    {
      _items.add(item);
      _quantities.add(Integer.valueOf(quantity));
    }
  }

  /**
   * Get the number of items.
   * @return a positive integer. 
   */
  public int getNbObjectItems()
  {
    return _items.size();
  }

  /**
   * Get the quantity of a given item.
   * @param index Index of the targeted item.
   * @return a strictly positive integer.
   */
  public int getQuantity(int index)
  {
    return _quantities.get(index).intValue();
  }

  /**
   * Get the item proxy at specified index.
   * @param index Index of the targeted item.
   * @return An item proxy.
   */
  public Proxy<Item> getItem(int index)
  {
    return _items.get(index);
  }

  @Override
  public String toString()
  {
    String ret="";
    int size=_items.size();
    if (size>0)
    {
      StringBuilder sb=new StringBuilder();
      for(int i=0;i<size;i++)
      {
        if (i>0)
        {
          sb.append(", ");
        }
        Proxy<Item> item=getItem(i);
        sb.append(item);
        int quantity=getQuantity(i);
        if (quantity>1)
        {
          sb.append(" (x").append(quantity).append(')');
        }
      }
      ret=sb.toString();
    }
    return ret;
  }
}
