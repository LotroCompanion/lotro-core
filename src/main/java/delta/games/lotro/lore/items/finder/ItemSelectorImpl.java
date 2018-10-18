package delta.games.lotro.lore.items.finder;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.filters.ItemFilter;

/**
 * An implementation of the ItemSelector that uses Item filters.
 * @author DAM
 */
public class ItemSelectorImpl implements ItemSelector
{
  //private static final Logger LOGGER=Logger.getLogger(ItemSelectorImpl.class);

  // Filters that select the items to remove
  private List<ItemFilter> _toRemoveFilters;
  // Filters that select the items to keep
  private List<ItemFilter> _toKeepFilters;

  /**
   * Constructor.
   */
  public ItemSelectorImpl()
  {
    _toRemoveFilters=new ArrayList<ItemFilter>();
    _toKeepFilters=new ArrayList<ItemFilter>();
  }

  /**
   * Add a filter for unwanted items.
   * @param filter Filter to select unwanted items.
   */
  public void addRemoveFilter(ItemFilter filter)
  {
    _toRemoveFilters.add(filter);
  }

  /**
   * Add a filter for wanted items.
   * @param filter Filter to select wanted items.
   */
  public void addKeepFilter(ItemFilter filter)
  {
    _toKeepFilters.add(filter);
  }

  @Override
  public Item chooseItem(List<Item> items)
  {
    //String name=items.get(0).getName();
    // Remove unwanted items
    List<Item> eligibles=new ArrayList<Item>();
    for(Item item : items)
    {
      boolean passed=true;
      for(ItemFilter removeFilter : _toRemoveFilters)
      {
        if (removeFilter.accept(item))
        {
          passed=false;
          break;
        }
      }
      if (passed)
      {
        eligibles.add(item);
      }
    }
    if (_toKeepFilters.size()>0)
    {
      List<Item> selected=new ArrayList<Item>();
      for(Item item : eligibles)
      {
        boolean passed=false;
        for(ItemFilter keepFilter : _toKeepFilters)
        {
          if (keepFilter.accept(item))
          {
            passed=true;
            break;
          }
        }
        if (passed)
        {
          selected.add(item);
        }
      }
      if (selected.size()>0)
      {
        eligibles.clear();
        eligibles.addAll(selected);
      }
    }
    if (eligibles.size()>1)
    {
      //System.out.println("Warn: "+name+" : "+items.size()+" : "+items);
      /*
      LOGGER.warn("Ambiguity on " + name + ": " + eligibles.size());
      for(Item item : items)
      {
        String itemName=item.getName();
        String subCategory=item.getSubCategory();
        LOGGER.warn("\t#" + itemName + " - " + subCategory);
      }
      */
    }
    if (eligibles.size()==1)
    {
      return items.get(0);
    }
    //LOGGER.warn("Not found:" + name);
    return null;
  }
}
