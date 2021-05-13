package delta.games.lotro.lore.items.finder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemProxy;
import delta.games.lotro.lore.items.ItemsManager;

/**
 * Find items using their name/icons.
 * @author DAM
 */
public class ItemsFinder
{
  private HashMap<String,List<Item>> _icons;
  private HashMap<String,List<Item>> _names;

  /**
   * Constructor.
   */
  public ItemsFinder()
  {
    loadItemsMaps();
  }

  /**
   * Build a proxy for an item.
   * @param itemName Item name.
   * @param iconId Item icon id.
   * @param selector Selector to choose among candidates, may be <code>null</code>.
   * @return A proxy.
   */
  public ItemProxy buildProxy(String itemName, int iconId, ItemSelector selector)
  {
    Item item=resolve(itemName,iconId,selector);
    String icon=String.valueOf(iconId);
    return buildProxy(item,itemName,icon);
  }

  /**
   * Build a proxy for an item.
   * @param itemName Item name.
   * @param iconId Icon id of the item.
   * @param backgroundIconId Background icon id of the item.
   * @param selector Selector to choose among candidates, may be <code>null</code>.
   * @return A proxy.
   */
  public ItemProxy buildProxy(String itemName, int iconId, int backgroundIconId, ItemSelector selector)
  {
    Item item=resolve(itemName,iconId,backgroundIconId,selector);
    String icon=iconId+"-"+backgroundIconId;
    return buildProxy(item,itemName,icon);
  }

  private ItemProxy buildProxy(Item item, String itemName, String icon)
  {
    ItemProxy proxy=new ItemProxy();
    if (item!=null)
    {
      proxy.setItem(item);
    }
    else
    {
      proxy.setName(itemName);
      proxy.setIcon(icon);
    }
    return proxy;
  }

  private Item resolve(String name, int iconId, ItemSelector selector)
  {
    String icon=String.valueOf(iconId);
    return resolve(name,icon,selector);
  }

  private Item resolve(String name, int iconId, int backgroundIconId, ItemSelector selector)
  {
    String icon=iconId+"-"+backgroundIconId;
    return resolve(name,icon,selector);
  }

  private Item resolve(String name, String iconKey, ItemSelector selector)
  {
    List<Item> ret=new ArrayList<Item>();
    List<Item> itemsWithRightIcon=_icons.get(iconKey);
    if (itemsWithRightIcon!=null)
    {
      for(Item item : itemsWithRightIcon)
      {
        if (name.equals(item.getName()))
        {
          ret.add(item);
        }
      }
      if (ret.size()==0)
      {
        ret.addAll(itemsWithRightIcon);
      }
    }
    return resolveAmbiguity(name,ret,selector);
  }

  private Item resolveAmbiguity(String name, List<Item> items, ItemSelector selector)
  {
    if (items==null)
    {
      return null;
    }
    if (items.size()>1)
    {
      Item selected=null;
      if (selector!=null)
      {
        selected=selector.chooseItem(items);
      }
      if (selected==null)
      {
        /*
        LOGGER.warn("Ambiguity on " + name + ": " + items.size());
        for(Item item : items)
        {
          String itemName=item.getName();
          String subCategory=item.getSubCategory();
          LOGGER.warn("\t#" + itemName + " - " + subCategory);
        }
        */
      }
      else
      {
        return selected;
      }
    }
    if (items.size()==1)
    {
      return items.get(0);
    }
    //LOGGER.warn("Not found:" + name);
    return null;
  }

  /**
   * Load items maps (key->list of item) where key is the item iconID/iconID-backgroundIconID or the item name.
   */
  private void loadItemsMaps()
  {
    _icons=new HashMap<String,List<Item>>(); 
    _names=new HashMap<String,List<Item>>(); 
    List<Item> items=ItemsManager.getInstance().getAllItems();
    for(Item item : items)
    {
      String icon=item.getIcon();
      if (icon!=null)
      {
        registerMapping(_icons,icon,item);
        String mainIconId=icon.substring(0,icon.indexOf('-'));
        registerMapping(_icons,mainIconId,item);
        String name=item.getName();
        registerMapping(_names,name,item);
      }
    }
  }

  private void registerMapping(HashMap<String,List<Item>> map, String key, Item item)
  {
    if (key!=null)
    {
      List<Item> ids=map.get(key);
      if (ids==null)
      {
        ids=new ArrayList<Item>();
        map.put(key,ids);
      }
      if (!ids.contains(item))
      {
        ids.add(item);
      }
    }
  }
}
